package com.vominh.s3.storage.service.impl.api;

import com.vominh.s3.storage.model.UploadResponse;
import com.vominh.s3.storage.service.IS3Client;
import com.vominh.s3.storage.service.impl.S3BaseImplement;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class S3ApiImplement extends S3BaseImplement implements IS3Client {


    @Override
    public void createBucket(String name) {

    }

    @Override
    public void deleteBucket(String name) {

    }

    @Override
    public List<String> listBucket() {
        return null;
    }

    @Override
    public UploadResponse uploadFile(String bucket, String key, File file) {
        return null;
    }

    @Override
    public UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, long contentLength) {
        return null;
    }

    @Override
    public byte[] download(String bucket, String key) {
        return new byte[0];
    }

    @Override
    public void delete(String bucket, String key) {
    }

    @Override
    public void deleteByUri(String bucket, String url) {

    }
}
