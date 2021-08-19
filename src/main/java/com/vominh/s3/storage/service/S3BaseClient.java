package com.vominh.s3.storage.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public abstract class S3BaseClient implements IStorageClient {

    protected String account;
    protected String key;
    protected AmazonS3 s3Client;

    @Override
    public void authenticate(String key, String secret, Regions region) {
        AWSCredentials credentials = new BasicAWSCredentials(key, secret);

        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    protected String getPublicUrlByKey(String bucket, String key) {
        return s3Client.getUrl(bucket, key).toExternalForm();
    }

    protected String getKeyFromPublicUrl(String publicUrl) {
        return publicUrl.substring(publicUrl.lastIndexOf("/") + 1);
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
