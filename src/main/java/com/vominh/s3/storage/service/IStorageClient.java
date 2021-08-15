package com.vominh.s3.storage.service;

import com.vominh.s3.storage.model.UploadResponse;

import java.io.InputStream;

public interface IStorageClient {

    void authenticate(String key, String secret);

    UploadResponse uploadFile(String containerName, String fileName, InputStream fileStream);

    UploadResponse uploadFile(String containerName, String fileName, InputStream fileStream, String contentType);

    void deleteFile(String containerName, String fileName);
}
