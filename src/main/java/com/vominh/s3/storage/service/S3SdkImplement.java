package com.vominh.s3.storage.service;

import com.vominh.s3.storage.model.UploadResponse;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class S3SdkImplement extends S3BaseClient implements IStorageClient {

    @Override
    public void createBucket(String name) {
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(name)
                .acl(BucketCannedACL.PUBLIC_READ_WRITE)
                .build();

        s3Client.createBucket(request);
    }

    @Override
    public void deleteBucket(String name) {
        DeleteBucketRequest request = DeleteBucketRequest.builder()
                .bucket(name)
                .build();
        s3Client.deleteBucket(request);
    }

    @Override
    public List<String> listBucket() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        return listBucketsResponse.buckets().stream().map(Bucket::name).collect(Collectors.toList());
    }

    public UploadResponse uploadFile(String bucket, String key, File file) {
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, file);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

        UploadResponse response = new UploadResponse();
        response.setKey(key);
        response.setFileName(file.getName());
        response.setDownloadUrl(this.getPublicUrlByKey(bucket, key));
        return response;
    }

    public UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, long contentLength) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileStream, contentLength));

        UploadResponse response = new UploadResponse();
        response.setKey(key);
        response.setFileName(fileName);
        response.setDownloadUrl(this.getPublicUrlByKey(bucket, key));
        return response;
    }

    @Override
    public byte[] download(String bucket, String key) throws S3Exception {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
        ResponseBytes<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());
        return s3Object.asByteArray();
    }

    @Override
    public void delete(String bucket, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public void deleteByPublicUrl(String bucket, String url) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket).key(getKeyFromPublicUrl(url)).build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
