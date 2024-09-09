package by.latushka.resourceservice.client.impl;

import by.latushka.resourceservice.client.StorageClient;
import by.latushka.resourceservice.exception.StorageClientException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class MinIOStorageClientImpl implements StorageClient {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;


    @Override
    public void put(String key, byte[] object) {
        if(!bucketExists()) {
            makeBucket();
        }

        InputStream is = new ByteArrayInputStream(object);
        PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(key)
                .stream(is, object.length, -1).build();
        try {
            minioClient.putObject(args);
        } catch (Exception e) {
            log.error("Failed to save object to storage", e);
            throw new StorageClientException(e);
        }
    }

    @Override
    public byte[] get(String key) {
        GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(key).build();
        GetObjectResponse response;
        byte[] data;
        try {
            response = minioClient.getObject(args);
            data = response.readAllBytes();
        } catch (Exception e) {
            log.error("Failed to get object from storage", e);
            throw new StorageClientException(e);
        }
        return data;
    }

    @Override
    public void delete(String key) {
        RemoveObjectArgs args = RemoveObjectArgs.builder().bucket(bucketName).object(key).build();
        try {
            minioClient.removeObject(args);
        } catch (Exception e) {
            log.error("Failed to delete object from storage", e);
            throw new StorageClientException(e);
        }
    }


    private void makeBucket() {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName).build());
        } catch (Exception e) {
            log.error("Failed to create bucket", e);
            throw new StorageClientException(e);
        }
    }


    private Boolean bucketExists() {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
        } catch (Exception e) {
            log.error("Failed to check if the bucket exists", e);
            throw new StorageClientException(e);
        }
    }
}
