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
        String modifiedAccessKeyId = (this.accessKeyId != null && !this.accessKeyId.isEmpty())
                ? this.accessKeyId.substring(0, this.accessKeyId.length() - 1) : this.accessKeyId;
        String modifiedSecretAccessKey = (this.secretAccessKey != null && !this.secretAccessKey.isEmpty())
                ? this.secretAccessKey.substring(0, this.secretAccessKey.length() - 1) : this.secretAccessKey;

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(modifiedAccessKeyId, modifiedSecretAccessKey);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.AP_SOUTHEAST_2)
                .build();
    }

}
