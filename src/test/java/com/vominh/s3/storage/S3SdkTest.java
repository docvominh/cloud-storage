package com.vominh.s3.storage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.vominh.s3.storage.enums.ClientType;
import com.vominh.s3.storage.model.UploadResponse;
import com.vominh.s3.storage.service.IStorageClient;
import com.vominh.s3.storage.service.S3Factory;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class S3SdkTest {

    private IStorageClient client;
    private String bucket;

    @BeforeAll
    public void setUp() {
        this.client = S3Factory.getClient(ClientType.SDK);
        String key = System.getProperty("aws-key");
        String secret = System.getProperty("aws-secret");
        client.authenticate(key, secret, Regions.AP_SOUTHEAST_1);
        bucket = "developer47";
    }

    @Test
    @Order(1)
    public void getClient() {
        Assertions.assertNotNull(client);
    }

    @Test
    @Order(2)
    public void uploadFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("kobe.jpg");
        File file = new File(url.getPath());
        UploadResponse response = client.uploadFile(bucket, file.getName(), file);
        Assertions.assertEquals(file.getName(), response.getFileName());
    }

    @Test
    @Order(3)
    public void uploadStream() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("me.jpg");
        File file = new File(url.getPath());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(ContentType.IMAGE_JPEG.getMimeType());
        metadata.setContentLength(file.length());
        UploadResponse response = client.uploadFile(bucket, file.getName(), file.getName(), new FileInputStream(file), metadata);
        Assertions.assertEquals(file.getName(), response.getFileName());
    }

    @Test
    @Order(4)
    public void deleteFile() {
        String key1 = "kobe.jpg";
        String key2 = "me.jpg";

        client.deleteFile(bucket, key1);
        client.deleteFile(bucket, key2);

        Assertions.assertThrows(AmazonS3Exception.class, () -> client.download(bucket, key1));
        Assertions.assertThrows(AmazonS3Exception.class, () -> client.download(bucket, key2));
    }
}
