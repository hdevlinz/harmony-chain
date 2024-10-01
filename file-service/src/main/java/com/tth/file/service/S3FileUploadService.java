package com.tth.file.service;

import com.tth.commonlibrary.enums.FileCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final S3Client s3Client;

    @Value("${app.services.aws.s3.bucket-name}")
    private String bucketName;

    public List<String> uploadMultiFiles(List<MultipartFile> files, String category) {
        List<String> fileURLS = new ArrayList<>();

        files.forEach(file -> {
            try {
                fileURLS.add(this.uploadFile(file, category));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return fileURLS;
    }

    private String uploadFile(MultipartFile file, String category) throws IOException {
        File convertedFile = this.convertMultiPartToFile(file);
        String fileName = this.generateFileName(file, category);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(this.bucketName)
                .key(fileName)
                .cacheControl("public, max-age=31536000")
                .build();
        this.s3Client.putObject(putObjectRequest, convertedFile.toPath());

        PutObjectAclRequest putObjectAclRequest = PutObjectAclRequest.builder()
                .bucket(this.bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        this.s3Client.putObjectAcl(putObjectAclRequest);

        convertedFile.delete();

        return this.getFileUrl(fileName);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();

        return convertedFile;
    }

    private String generateFileName(MultipartFile file, String category) {
        FileCategory fileCategory = FileCategory.valueOf(category);

        return String.format("%s/%s", fileCategory.getFolderName(), UUID.randomUUID() + "_" + file.getOriginalFilename());
    }

    private String getFileUrl(String fileName) {
        return this.s3Client.utilities().getUrl(b -> b.bucket(this.bucketName).key(fileName)).toExternalForm();
    }

}
