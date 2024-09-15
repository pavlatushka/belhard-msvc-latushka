package by.latushka.resourceservice.client.impl;

import by.latushka.resourceservice.client.StorageClient;
import by.latushka.resourceservice.exception.StorageClientException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
            log.info("Save object {} to MinIO", key);
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
            log.info("Get object {} from MinIO", key);
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
            log.info("Remove object {} from MinIO", key);
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
