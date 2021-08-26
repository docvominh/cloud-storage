# s3-storage
Java lib to working with AWS S3

## Usage

:collision::collision::collision: Don't push your AWS credential on public Git repository :collision::collision::collision:

### Pure Java

```
// ImplementType = SDK or API(not support yet)
IS3Client client = S3ClientFactory.getClient(ImplementType.SDK);

// AWS authenticate
String key = System.getProperty("aws-key");
String secret = System.getProperty("aws-secret");
client.authenticate(key, secret, Region.AP_SOUTHEAST_1, null);

// Localstack with self key create
String key = "localstackTest";
String secret = "localstackTest";
client.authenticate(key, secret, Region.of("local"), URI.create("http://localhost:4566"));

```

### Spring Bean

```
@Configuration
public class SpringConfig {

    private static final Logger log = LoggerFactory.getLogger(SpringConfig.class);

    @Value("${s3.key}")
    private String s3Key;

    @Value("${s3.secret}")
    private String s3Secret;

    @Value("${s3.bucket}")
    private String bucket;

    @Bean
    public IS3Client storageClient() {
        IS3Client client = S3ClientFactory.getClient(ImplementType.SDK);
        client.authenticate(s3Key, s3Secret, Region.AP_SOUTHEAST_1, null);

        try {
            client.createBucket(bucket);
        } catch (BucketAlreadyExistsException exception) {
            log.info(exception.getMessage());
        }

        return client;
    }
}
```

### Support operator

void authenticate(String key, String secret, Region region, URI endpointOverride);

void createBucket(String name);

void deleteBucket(String name);

List<String> listBucket();

UploadResponse uploadFile(String bucket, String key, File file);

UploadResponse uploadFile(String bucket, String key, String fileName, InputStream fileStream, long contentLength);

byte[] download(String bucket, String key);

void delete(String bucket, String key);

void deleteByUri(String bucket, String uri);
