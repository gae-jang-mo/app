package com.gaejangmo.apiserver.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class S3ConnectorTest {
    private S3Connector s3Connector;
    private AmazonS3 mockS3Client;
    private S3Mock s3Mock;
    private String bucket;

    private MockMultipartFile mockMultipartFile;
    private String dirName = "dir";
    private String fileName = "savedFileName";
    private String serviceEndpoint = "http://localhost:8001";

    @BeforeEach
    void setUp() {
        bucket = "gae-jang-mo";
        mockMultipartFile = new MockMultipartFile("file", "mock1.png", "image/png", "test data".getBytes());

        s3Mock = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        s3Mock.start();

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, "ap-northeast-2");
        mockS3Client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        mockS3Client.createBucket(bucket);

        s3Connector = new S3Connector(mockS3Client, bucket);
    }

    @Test
    void 파일_업로드_테스트() {
        // given
        String key = getFileKey(dirName, fileName);
        String expectedUrl = String.format("%s/%s/%s", serviceEndpoint, bucket, key);

        // when
        String actualUrl = s3Connector.upload(mockMultipartFile, dirName, fileName);

        // then
        assertThat(actualUrl).isEqualTo(expectedUrl);
    }

    @Test
    void 파일_삭제_테스트() {
        //given
        s3Connector.upload(mockMultipartFile, dirName, fileName);
        String key = getFileKey(dirName, fileName);
        assertDoesNotThrow(() -> mockS3Client.getObject(bucket, key));

        //when
        s3Connector.delete(key);

        //then
        assertThrows(AmazonS3Exception.class, () -> mockS3Client.getObject(bucket, key));
    }

    private String getFileKey(String dirName, String fileName) {
        return String.format("%s/%s", dirName, fileName);
    }

    @AfterEach
    void tearDown() {
        s3Mock.shutdown();
    }
}