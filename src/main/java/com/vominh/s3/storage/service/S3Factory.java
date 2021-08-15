package com.vominh.s3.storage.service;

import com.vominh.s3.storage.enums.ClientType;

public class S3Factory {

    public static IStorageClient getClient(ClientType type) {
        if (type == ClientType.SDK) {
            return new S3SdkImplement();
        } else if (type == ClientType.API) {
            return new S3ApiImplement();
        }

        return null;
    }
}
