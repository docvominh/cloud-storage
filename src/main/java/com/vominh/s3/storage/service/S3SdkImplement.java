package com.vominh.s3.storage.service;

import com.vominh.s3.storage.model.UploadResponse;

import java.io.InputStream;

public class S3SdkImplement extends S3BaseClient implements IStorageClient {

    public UploadResponse uploadFile(String containerName, String fileName, InputStream fileStream) {
        return null;
    }

    public UploadResponse uploadFile(String containerName, String fileName, InputStream fileStream, String contentType) {
        return null;
    }

    public void deleteFile(String containerName, String fileName) {

    }
}
