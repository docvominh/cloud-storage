package com.vominh.s3.storage.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.net.URI;

public abstract class S3BaseClient implements IStorageClient {

    protected String account;
    protected String key;
    protected S3Client s3Client;

    @Override
    public void authenticate(String key, String secret, Region region, URI endpointOverride) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(key, secret);

        if (endpointOverride != null) {
            this.s3Client = S3Client.builder()
                    .endpointOverride(endpointOverride)
                    .region(region)
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .build();
        } else {
            this.s3Client = S3Client.builder()
                    .region(region)
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .build();
        }
    }

    protected String getPublicUrlByKey(String bucket, String key) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder().bucket(bucket).key(key).build();
        return s3Client.utilities().getUrl(getUrlRequest).toExternalForm();
    }

    protected String getKeyFromPublicUrl(String publicUrl) {
        return publicUrl.substring(publicUrl.lastIndexOf("amazonaws.com/") + 14);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
