package com.vominh.s3.storage;

import com.vominh.s3.storage.enums.ClientType;
import com.vominh.s3.storage.service.IStorageClient;
import com.vominh.s3.storage.service.S3Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class S3SdkTest {
    //    cloud.s3.account=AKIARSX465JKRYGXVMH6
//    cloud.s3.key=
    @Test
    public void getClient() throws Exception {
        IStorageClient client = S3Factory.getClient(ClientType.SDK);
        client.authenticate("AKIARSX465JKRYGXVMH6", "1c6HJeV9guT5Vmnc9JxEMkZyYjEgEn7YfDuxdkE+");
        Assertions.assertNotNull(client);
    }
}
