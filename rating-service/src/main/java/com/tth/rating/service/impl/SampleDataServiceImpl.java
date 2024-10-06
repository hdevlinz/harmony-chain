package com.tth.rating.service.impl;

import com.tth.commonlibrary.dto.response.identity.user.UserResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.enums.CriteriaType;
import com.tth.commonlibrary.enums.UserRole;
import com.tth.rating.entity.Rating;
import com.tth.rating.repository.RatingRepository;
import com.tth.rating.repository.httpclient.IdentityClient;
import com.tth.rating.repository.httpclient.UserProfileClient;
import com.tth.rating.service.SampleDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SampleDataServiceImpl implements SampleDataService {

    private final RatingRepository ratingRepository;
    private final IdentityClient identityClient;
    private final UserProfileClient userProfileClient;

    @Override
    public boolean createSampleData() {
        if (this.ratingRepository.count() == 0) {
            log.info("Creating ratings.....");
            this.createRating();
        }

        return true;
    }

    private void createRating() {
        List<CriteriaType> criteriaTypes = new ArrayList<>(List.of(CriteriaType.values()));
        List<SupplierResponse> suppliers = this.userProfileClient.listSuppliers(null, 1, 10).getData();
        List<UserResponse> users = new ArrayList<>(this.identityClient.listUsers().getResult()
                .stream().filter(u -> u.getRole() != UserRole.ROLE_SUPPLIER).toList());
        Random random = new Random();

        suppliers.forEach(supplier -> IntStream.range(1, 101).forEach(index -> {
            Collections.shuffle(criteriaTypes, random);
            Collections.shuffle(users, random);

            Rating rating = Rating.builder()
                    .userId(users.getFirst().getId())
                    .supplierId(supplier.getId())
                    .rating(BigDecimal.valueOf(1 + (random.nextDouble() * (5 - 1))))
                    .content("Rating " + index + " for " + supplier.getName())
                    .criteria(criteriaTypes.getFirst())
                    .build();
            rating.setCreatedAt(this.getRandomDateTimeInYear());

            this.ratingRepository.save(rating);
        }));
    }

    private LocalDateTime getRandomDateTimeInYear() {
        // Lấy tháng hiện tại
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();

        // Random tháng từ 1 đến tháng hiện tại
        int randomMonth = ThreadLocalRandom.current().nextInt(1, currentMonth + 1);

        // Tạo ngày bắt đầu và ngày kết thúc cho tháng random
        LocalDate start = LocalDate.of(now.getYear(), randomMonth, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        // Random ngày trong khoảng từ start đến end
        long randomDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay() + 1);

        // Chuyển đổi từ epoch day sang LocalDate và sau đó sang Date
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
    }

}
