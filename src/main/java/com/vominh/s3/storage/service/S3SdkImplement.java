package com.vominh.s3.storage.service;

import com.amazonaws.services.s3.model.*;
import com.vominh.s3.storage.model.UploadResponse;

import java.io.File;
import java.io.InputStream;

public class S3SdkImplement extends S3BaseClient implements IStorageClient {

    public UploadResponse uploadFile(String bucket, String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicReadWrite);
        s3Client.putObject(putObjectRequest);

        UploadResponse response = new UploadResponse();
        response.setKey(key);
        response.setFileName(file.getName());
        response.setDownloadUrl(this.getPublicUrlByKey(bucket, key));
        return response;
    }

    public UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, fileStream, metadata);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicReadWrite);
        s3Client.putObject(putObjectRequest);

        UploadResponse response = new UploadResponse();
        response.setKey(key);
        response.setFileName(fileName);
        response.setDownloadUrl(this.getPublicUrlByKey(bucket, key));
        return response;
    }

    @Override
    public InputStream download(String bucket, String key) throws AmazonS3Exception {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object object = s3Client.getObject(getObjectRequest);
        return object.getObjectContent().getDelegateStream();
    }

    public void deleteFile(String bucket, String publicUrl) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, getKeyFromPublicUrl(publicUrl));
        s3Client.deleteObject(deleteObjectRequest);
    }
}
