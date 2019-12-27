package com.gaejangmo.apiserver.config.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gaejangmo.apiserver.config.aws.exception.S3FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class S3Connector {
    private final AmazonS3 amazonS3Client;
    private final String bucket;

    public S3Connector(final AmazonS3 amazonS3Client, final String bucket) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }

    public String upload(final MultipartFile uploadFile, final String keyName) {
        return putS3(uploadFile, keyName);
    }

    private String putS3(final MultipartFile uploadFile, final String filePath) {
        try (InputStream fileContent = uploadFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(uploadFile.getContentType());
            objectMetadata.setContentLength(uploadFile.getSize());

            amazonS3Client.putObject(new PutObjectRequest(bucket, filePath, fileContent, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3Client.getUrl(bucket, filePath).toString();
        } catch (IOException e) {
            throw new S3FileUploadException("파일 업로드 실패");
        }
    }

    public void delete(final String keyName) {
        amazonS3Client.deleteObject(bucket, keyName);
    }

}
