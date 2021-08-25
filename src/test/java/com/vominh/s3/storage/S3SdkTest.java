package com.vominh.s3.storage;

import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.vominh.s3.storage.enums.ClientType;
import com.vominh.s3.storage.model.UploadResponse;
import com.vominh.s3.storage.service.IStorageClient;
import com.vominh.s3.storage.service.S3Factory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@LocalstackDockerProperties(services = {"s3"})
public class S3SdkTest {

    private static final Logger log = LoggerFactory.getLogger(S3SdkTest.class);

    private IStorageClient client;
    private String bucket;

    @BeforeAll
    public void setUp() {

        log.info("START SDK TEST");

        this.client = S3Factory.getClient(ClientType.SDK);

//        String key = System.getProperty("aws-key");
//        String secret = System.getProperty("aws-secret");
//        client.authenticate(key, secret, "ap-southeast-1", null);
//        bucket = "developer47";

        String key = "localstackTest";
        String secret = "localstackTest";
        client.authenticate(key, secret, Region.of("local"), URI.create("http://localhost:4566"));
        bucket = "s3-storage";

    }

    @AfterAll
    public void cleanUp() {
        client.deleteBucket(bucket);
    }

    @Test
    @Order(0)
    public void createBucket() {

        try {
            client.createBucket(bucket);
        } catch (BucketAlreadyExistsException exception) {
            log.info("Bucket already exist");
        }

        List<String> buckets = client.listBucket();
        Assertions.assertTrue(buckets.contains(bucket));
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
        UploadResponse response = client.uploadFile(bucket, "xxx/" + file.getName(), file);
        Assertions.assertEquals(file.getName(), response.getFileName());
    }

    @Test
    @Order(3)
    public void uploadStream() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("me.jpg");
        File file = new File(url.getPath());
        UploadResponse response = client.uploadFile(bucket, file.getName(), file.getName(), new FileInputStream(file), file.length());
        Assertions.assertEquals(file.getName(), response.getFileName());
    }

    @Test
    @Order(4)
    public void deleteFile() {
        String key1 = "xxx/kobe.jpg";
        String key2 = "me.jpg";

        client.delete(bucket, key1);
        client.delete(bucket, key2);

        Assertions.assertThrows(S3Exception.class, () -> client.download(bucket, key1));
        Assertions.assertThrows(S3Exception.class, () -> client.download(bucket, key2));
    }
}
