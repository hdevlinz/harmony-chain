package com.tth.file.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AWSConfigs {

    @Value("${app.services.aws.access-key-id}")
    private String accessKeyId;

    @Value("${app.services.aws.secret-access-key}")
    private String secretAccessKey;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(this.accessKeyId, this.secretAccessKey);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.AP_SOUTHEAST_2)
                .build();
    }

}
