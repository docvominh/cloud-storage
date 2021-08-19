package com.vominh.s3.storage.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.vominh.s3.storage.model.UploadResponse;

import java.io.File;
import java.io.InputStream;

public class S3ApiImplement extends S3BaseClient implements IStorageClient {


    @Override
    public UploadResponse uploadFile(String bucket, String key, File file) {
        return null;
    }

    @Override
    public UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, ObjectMetadata metadata) {
        return null;
    }

    @Override
    public InputStream download(String bucket, String key) {
        return null;
    }

    @Override
    public void deleteFile(String bucket, String fileName) {

    }
}
