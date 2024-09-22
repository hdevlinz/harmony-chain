package com.tth.identity.service.service.impl;

import com.tth.identity.service.dto.request.user.RegisterRequest;
import com.tth.identity.service.dto.request.user.UpdateRequest;
import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.user.UserResponse;
import com.tth.identity.service.entity.Customer;
import com.tth.identity.service.entity.Shipper;
import com.tth.identity.service.entity.Supplier;
import com.tth.identity.service.entity.User;
import com.tth.identity.service.enums.ErrorCode;
import com.tth.identity.service.enums.UserRole;
import com.tth.identity.service.exception.AppException;
import com.tth.identity.service.mapper.CustomerMapper;
import com.tth.identity.service.mapper.ShipperMapper;
import com.tth.identity.service.mapper.SupplierMapper;
import com.tth.identity.service.mapper.UserMapper;
import com.tth.identity.service.repository.CustomerRepository;
import com.tth.identity.service.repository.ShipperRepository;
import com.tth.identity.service.repository.SupplierRepository;
import com.tth.identity.service.repository.UserRepository;
import com.tth.identity.service.repository.specification.UserSpecification;
import com.tth.identity.service.service.UserService;
import com.tth.identity.service.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;
    private final ShipperMapper shipperMapper;
    private final SupplierMapper supplierMapper;
    private final UserMapper userMapper;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        if (this.userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (this.userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = this.userMapper.toUser(registerRequest);
        user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));

        switch (user.getRole()) {
            case ROLE_ADMIN -> {
            }
            case ROLE_CUSTOMER -> {
                Customer customer = this.customerMapper.toCustomer(registerRequest);
                customer.setUser(user);
                user.setCustomer(customer);
            }
//            case ROLE_DISTRIBUTOR -> {
//            }
//            case ROLE_MANUFACTURER -> {
//            }
            case ROLE_SHIPPER -> {
                Shipper shipper = this.shipperMapper.toShipper(registerRequest);
                shipper.setUser(user);
                user.setShipper(shipper);
            }
            case ROLE_SUPPLIER -> {
                Supplier supplier = this.supplierMapper.toSupplier(registerRequest);
                supplier.setUser(user);
                user.setSupplier(supplier);
            }
        }

        try {
            this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return this.userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return this.userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateInfo(UpdateRequest updateRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (this.hasPasswordUpdateRequest(updateRequest)) {
            if (!this.passwordEncoder.matches(updateRequest.getOldPassword(), user.getPassword())) {
                throw new AppException(ErrorCode.PASSWORD_INCORRECT);
            }

            user.setPassword(this.passwordEncoder.encode(updateRequest.getNewPassword()));
        }

        this.updateUserFields(user, updateRequest);
        this.userRepository.save(user);

        return this.userMapper.toUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<User> spec = UserSpecification.filterUsers(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserResponse> result = this.userRepository.findAll(spec, pageable).map(this.userMapper::toUserResponse);

        return PageResponse.of(result);
    }

    private boolean hasPasswordUpdateRequest(UpdateRequest updateRequest) {
        return updateRequest.getOldPassword() != null && !updateRequest.getOldPassword().isEmpty() &&
                updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isEmpty();
    }

    private void updateUserFields(User user, UpdateRequest updateRequest) {
        Field[] fields = UpdateRequest.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(updateRequest);
                if (value == null || value.toString().isEmpty()) {
                    continue;
                }

                if (this.trySetField(user, field.getName(), value)) {
                    continue;
                }

                switch (user.getRole()) {
                    case ROLE_CUSTOMER -> {
                        String fieldName = field.getName().substring(UserRole.ROLE_CUSTOMER.alias().length());
                        fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                        this.trySetField(user.getCustomer(), fieldName, value);
                    }
//                    case ROLE_DISTRIBUTOR -> {
//                    }
//                    case ROLE_MANUFACTURER -> {
//                    }
                    case ROLE_SHIPPER -> {
                        String fieldName = field.getName().substring(UserRole.ROLE_SHIPPER.alias().length());
                        fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                        this.trySetField(user.getShipper(), fieldName, value);
                    }
                    case ROLE_SUPPLIER -> {
                        String fieldName = field.getName().substring(UserRole.ROLE_SUPPLIER.alias().length());
                        fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
                        this.trySetField(user.getSupplier(), fieldName, value);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
    }

    private boolean trySetField(Object target, String field, Object value) {
        try {
            Field targetField = target.getClass().getDeclaredField(field);
            targetField.setAccessible(true);

            if (field.equals("avatar")) {
//                String avatarUrl = this.globalService.uploadImage((MultipartFile) value);
//                targetField.set(target, avatarUrl);
                return true;
            } else {
                Object convertedValue = Utils.convertValue(targetField.getType(), value.toString());
                targetField.set(target, convertedValue);
            }

            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
