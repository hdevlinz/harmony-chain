package com.tth.file.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AWSConfigs {

    @Bean
    public S3Client s3Client() {
        String accessKeyId = "AKIA6K5V7JXAJRAGPNOP";
        String secretAccessKey = "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";

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.AP_SOUTHEAST_2)
                .build();
    }

}
