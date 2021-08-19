package com.vominh.s3.storage.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.vominh.s3.storage.model.UploadResponse;

import java.io.File;
import java.io.InputStream;

public interface IStorageClient {

    void authenticate(String key, String secret, Regions region);

    UploadResponse uploadFile(String bucket, String key, File file);

    UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, ObjectMetadata metadata);

    InputStream download(String bucket, String key);

    void deleteFile(String bucket, String fileName);
}
