package com.vominh.s3.storage.service;

import com.vominh.s3.storage.enums.ImplementType;
import com.vominh.s3.storage.service.impl.api.S3ApiImplement;
import com.vominh.s3.storage.service.impl.sdk.S3SdkImplement;

public class S3ClientFactory {

    public static IS3Client getClient(ImplementType type) {
        if (type == ImplementType.SDK) {
            return new S3SdkImplement();
        } else if (type == ImplementType.API) {
            return new S3ApiImplement();
        }

        return null;
    }
}
