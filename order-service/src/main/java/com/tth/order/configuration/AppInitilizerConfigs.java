package com.tth.product.configuration;

import com.tth.product.dto.response.supplier.SupplierResponse;
import com.tth.product.entity.Category;
import com.tth.product.entity.Product;
import com.tth.product.entity.Tag;
import com.tth.product.entity.Unit;
import com.tth.product.repository.CategoryRepository;
import com.tth.product.repository.ProductRepository;
import com.tth.product.repository.TagRepository;
import com.tth.product.repository.UnitRepository;
import com.tth.product.repository.httpclient.IdentityClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppInitilizerConfigs {

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    public ApplicationRunner applicationRunner(
            CategoryRepository categoryRepository,
            TagRepository tagRepository,
            UnitRepository unitRepository,
            ProductRepository productRepository,
            IdentityClient identityClient
    ) {
        return args -> {
            log.info("Initializing application.....");

            if (productRepository.count() == 0) {
                log.info("Creating categories.....");
                this.createCategory(categoryRepository);

                log.info("Creating tags.....");
                this.createTag(tagRepository);

                log.info("Creating units.....");
                this.createUnit(unitRepository);

                log.info("Creating products.....");
                this.createProduct(productRepository, categoryRepository, tagRepository, unitRepository, identityClient);
            }

            log.info("Application initialization completed.....");
        };
    }

    private void createCategory(CategoryRepository categoryRepository) {
        categoryRepository.saveAll(List.of(
                Category.builder().name("Thiết Bị Mạng").description("Thiết Bị Mạng").build(),
                Category.builder().name("Thiết Bị Di Động").description("Thiết Bị Di Động").build(),
                Category.builder().name("Công Nghệ Đám Mây").description("Công Nghệ Đám Mây").build(),
                Category.builder().name("Lưu Trữ và Đám Mây").description("Lưu Trữ và Đám Mây").build(),
                Category.builder().name("An Ninh và Bảo Mật").description("An Ninh và Bảo Mật").build(),
                Category.builder().name("Phần Mềm và Ứng Dụng").description("Phần Mềm và Ứng Dụng").build(),
                Category.builder().name("Máy Tính và Phụ Kiện").description("Máy Tính và Phụ Kiện").build(),
                Category.builder().name("Thiết Bị Đầu Vào và Đầu Ra").description("Thiết Bị Đầu Vào và Đầu Ra").build(),
                Category.builder().name("Phát Triển Web và Ứng Dụng").description("Phát Triển Web và Ứng Dụng").build(),
                Category.builder().name("Khoa Học Dữ Liệu và Trí Tuệ Nhân Tạo").description("Khoa Học Dữ Liệu và Trí Tuệ Nhân Tạo").build()
        ));
    }

    private void createTag(TagRepository tagRepository) {
        tagRepository.saveAll(List.of(
                Tag.builder().name("Trí Tuệ Nhân Tạo").description("Trí Tuệ Nhân Tạo").build(),
                Tag.builder().name("An Ninh Mạng").description("An Ninh Mạng").build(),
                Tag.builder().name("Điện Toán Đám Mây").description("Điện Toán Đám Mây").build(),
                Tag.builder().name("Khoa Học Dữ Liệu").description("Khoa Học Dữ Liệu").build(),
                Tag.builder().name("Chuỗi Khối").description("Chuỗi Khối").build(),
                Tag.builder().name("Phát Triển và Vận Hành").description("Phát Triển và Vận Hành").build(),
                Tag.builder().name("Học Máy").description("Học Máy").build(),
                Tag.builder().name("Internet Vạn Vật").description("Internet Vạn Vật").build(),
                Tag.builder().name("Dữ Liệu Lớn").description("Dữ Liệu Lớn").build(),
                Tag.builder().name("Phát Triển Phần Mềm").description("Phát Triển Phần Mềm").build()
        ));

    }

    private void createUnit(UnitRepository unitRepository) {
        unitRepository.saveAll(List.of(
                Unit.builder().name("Cái").abbreviation("PCS").build(),
                Unit.builder().name("Hộp").abbreviation("BOX").build(),
                Unit.builder().name("Kilogram").abbreviation("KG").build(),
                Unit.builder().name("Grams").abbreviation("G").build(),
                Unit.builder().name("Lít").abbreviation("L").build(),
                Unit.builder().name("Mét").abbreviation("M").build(),
                Unit.builder().name("Centimet").abbreviation("CM").build(),
                Unit.builder().name("Gói").abbreviation("PKG").build(),
                Unit.builder().name("Tá").abbreviation("DZ").build(),
                Unit.builder().name("Cuộn").abbreviation("RL").build()
        ));
    }

    private void createProduct(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            TagRepository tagRepository,
            UnitRepository unitRepository,
            IdentityClient identityClient) {
        AtomicInteger count = new AtomicInteger(1);
        Random random = new Random();

        List<Category> categories = categoryRepository.findAll();
        List<Tag> tags = tagRepository.findAll();
        List<Unit> units = unitRepository.findAll();

        categories.forEach(category -> {
            // Tạo sản phẩm hết hạn
            this.createProductsWithExpiryDates(productRepository, category, tags, units, identityClient, -30, random, count);

            // Tạo sản phẩm sắp hết hạn
            this.createProductsWithExpiryDates(productRepository, category, tags, units, identityClient, 15, random, count);

            // Tạo sản phẩm còn hạn
            this.createProductsWithExpiryDates(productRepository, category, tags, units, identityClient, 60, random, count);
        });
    }

    private void createProductsWithExpiryDates(
            ProductRepository productRepository,
            Category category,
            List<Tag> tags,
            List<Unit> units,
            IdentityClient identityClient,
            int daysFromNow,
            Random random,
            AtomicInteger count) {

        List<SupplierResponse> suppliers = identityClient.listSuppliers(null, 1, 10).getData();

        suppliers.forEach(supplier -> {
            for (int i = 0; i < 10; i++) {
                BigDecimal price = BigDecimal.valueOf(30000 + (random.nextDouble() * (500000 - 50000)));

                LocalDate expiryDate = LocalDate.now().plusDays(daysFromNow);

                Collections.shuffle(tags, random);
                Set<Tag> randomTags = tags.parallelStream()
                        .limit(2)
                        .collect(Collectors.toSet());

                Unit unit = units.get(random.nextInt(units.size()));

                Product product = Product.builder()
                        .name("Product " + count)
                        .price(price)
                        .unit(unit)
                        .description("Product " + count)
                        .expiryDate(expiryDate)
                        .supplierId(supplier.getId())
                        .category(category)
                        .tags(randomTags)
                        .build();
                productRepository.save(product);

                count.getAndIncrement();
            }
        });
    }

}
