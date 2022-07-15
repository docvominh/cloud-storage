package com.vominh.s3.storage.service;

import com.vominh.s3.storage.model.UploadResponse;
import software.amazon.awssdk.regions.Region;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html
 */
public interface IS3Client {

    void authenticate(String key, String secret, Region region, URI endpointOverride);

    void createBucket(String name);

    void deleteBucket(String name);

    List<String> listBucket();

    UploadResponse uploadFile(String bucket, String key, File file);

    UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, long contentLength);

    byte[] download(String bucket, String key);

    byte[] downloadPortion(String bucket, String key, String range);

    void delete(String bucket, String key);

    void deleteByUri(String bucket, String uri);
}
