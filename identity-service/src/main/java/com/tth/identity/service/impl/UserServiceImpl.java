package com.tth.identity.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.dto.request.identity.UpdateRequest;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.customer.CustomerRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.enums.FileCategory;
import com.tth.commonlibrary.enums.UserRole;
import com.tth.commonlibrary.exception.AppException;
import com.tth.event.dto.NotificationEvent;
import com.tth.identity.entity.User;
import com.tth.identity.mapper.UserMapper;
import com.tth.identity.repository.UserRepository;
import com.tth.identity.repository.httpclient.FileClient;
import com.tth.identity.repository.httpclient.UserProfileClient;
import com.tth.identity.service.UserService;
import com.tth.identity.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileClient fileClient;
    private final UserProfileClient userProfileClient;

    @Override
    public User findById(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserResponse registration(RegisterRequest request) {
        if (this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = this.userMapper.toUser(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user = this.userRepository.save(user);

        UserProfileResponse profile = null;
        switch (user.getRole()) {
            case UserRole.ROLE_CARRIER -> {
                CarrierRequestCreate carrierRequest = this.userMapper.toCarrierRequestCreate(request);
                carrierRequest.setUserId(user.getId());
                profile = this.userProfileClient.createUserProfile(carrierRequest);
            }
            case UserRole.ROLE_CUSTOMER -> {
                CustomerRequestCreate customerRequest = this.userMapper.toCustomerRequestCreate(request);
                customerRequest.setUserId(user.getId());
                profile = this.userProfileClient.createUserProfile(customerRequest);
            }
            case UserRole.ROLE_SUPPLIER -> {
                SupplierRequestCreate supplierRequest = this.userMapper.toSupplierRequestCreate(request);
                supplierRequest.setUserId(user.getId());
                profile = this.userProfileClient.createUserProfile(supplierRequest);
            }
//            case ROLE_DISTRIBUTOR -> {}
//            case ROLE_MANUFACTURER -> {}
        }

        // Publish message to Kafka
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .chanel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to Harmony SCMS")
                .body(String.format("Chào mừng bạn đến với Harmony Supply Chain, %s!", request.getUsername()))
                .build();
        this.kafkaTemplate.send("notification-delivery", notificationEvent);

        UserResponse userResponse = this.userMapper.toUserResponse(user);
        userResponse.setProfile(profile);

        return userResponse;
    }

    @Override
    public UserResponse getInfo() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UserProfileResponse profile = switch (user.getRole()) {
            case UserRole.ROLE_CARRIER -> this.userProfileClient.getCarrierProfile(userId);
            case UserRole.ROLE_CUSTOMER -> this.userProfileClient.getCustomerProfile(userId);
            case UserRole.ROLE_SUPPLIER -> this.userProfileClient.getSupplierProfile(userId);
//            case ROLE_DISTRIBUTOR -> {}
//            case ROLE_MANUFACTURER -> {}
            default -> null;
        };

        UserResponse userResponse = this.userMapper.toUserResponse(user);
        userResponse.setProfile(profile);

        return userResponse;
    }

    @Override
    public UserResponse updateInfo(UpdateRequest request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (this.hasPasswordUpdateRequest(request)) {
            if (!this.passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new AppException(ErrorCode.PASSWORD_INCORRECT);
            }

            user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
        }

        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            List<MultipartFile> files = new ArrayList<>();
            files.add(request.getAvatar());

            String avatarUrl = this.fileClient.uploadImages(files, FileCategory.AVATAR.name()).getFirst();
            user.setAvatar(avatarUrl);
        }

        this.userMapper.updateUser(user, request);
        this.userRepository.save(user);

        return this.userMapper.toUserResponse(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<User> spec = UserSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserResponse> result = this.userRepository.findAll(spec, pageable).map(this.userMapper::toUserResponse);

        return PageResponse.of(result);
    }

    private boolean hasPasswordUpdateRequest(UpdateRequest request) {
        return request.getOldPassword() != null && !request.getOldPassword().isEmpty() &&
                request.getNewPassword() != null && !request.getNewPassword().isEmpty();
    }

}
