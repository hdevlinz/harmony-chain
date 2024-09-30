package com.tth.rating.configuration;

import com.tth.commonlibrary.dto.response.identity.user.UserResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.enums.CriteriaType;
import com.tth.commonlibrary.enums.UserRole;
import com.tth.rating.entity.Rating;
import com.tth.rating.repository.RatingRepository;
import com.tth.rating.repository.httpclient.IdentityClient;
import com.tth.rating.repository.httpclient.UserProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
@RequiredArgsConstructor
public class AppInitializerConfigs {

    @Bean
    public ApplicationRunner applicationRunner(
            RatingRepository ratingRepository,
            IdentityClient identityClient,
            UserProfileClient userProfileClient
    ) {
        return args -> {
            log.info("Initializing application.....");

            if (ratingRepository.count() == 0) {
                log.info("Creating ratings.....");
                this.createRating(ratingRepository, identityClient, userProfileClient);
            }

            log.info("Application initialization completed.....");
        };
    }

    private void createRating(
            RatingRepository ratingRepository,
            IdentityClient identityClient,
            UserProfileClient userProfileClient
    ) {
        List<CriteriaType> criteriaTypes = new ArrayList<>(List.of(CriteriaType.values()));
        List<SupplierResponse> suppliers = userProfileClient.listSuppliers(null, 1, 10).getData();
        List<UserResponse> users = new ArrayList<>(identityClient.listUsers().getResult()
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

            ratingRepository.save(rating);
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
