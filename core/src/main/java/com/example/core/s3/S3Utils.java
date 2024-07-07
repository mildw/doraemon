package com.example.core.s3;

import com.example.core.util.AwsUtils;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.transfer.s3.*;

import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class S3Utils {

    public final String TEMP_DIR_PATH = System.getProperty("java.io.tmpdir");

    public static String getPresignedUrl(S3Presigner s3Presigner, Bucket bucket, String key, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket.getBucketName())
                .key(key)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toString();
    }

    public static void tempFileUpload(Bucket bucket, String key, byte[] bytes) {
        S3Client s3Client = getS3Client(bucket);
        setLifecyclePolicy(s3Client, key, bucket.getBucketName());

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket.getBucketName())
                .key(key)
                .contentLength((long) bytes.length)
                .build();

        s3Client.putObject(request, RequestBody.fromByteBuffer(byteBuffer));
    }

    public static void setLifecyclePolicy(S3Client s3Client, String key, String bucketName) {

        List<LifecycleRule> rules = List.of(
                LifecycleRule.builder()
                        .id("ExpirationRule")
                        .filter(LifecycleRuleFilter.builder().prefix(key).build())
                        .expiration(LifecycleExpiration.builder().days(1).build())
                        .status(ExpirationStatus.ENABLED)
                        .build()
        );

        PutBucketLifecycleConfigurationRequest request = PutBucketLifecycleConfigurationRequest.builder()
                .bucket(bucketName)
                .lifecycleConfiguration(BucketLifecycleConfiguration.builder().rules(rules).build())
                .build();

        s3Client.putBucketLifecycleConfiguration(request);
    }

    public static boolean upload(Bucket bucket, String key, byte[] bytes) {
        try (S3TransferManager s3TransferManager = getS3TransferManager(bucket)) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket.getBucketName()).key(key)
                    .serverSideEncryption(ServerSideEncryption.AES256).build();
            UploadRequest uploadRequest = UploadRequest.builder().putObjectRequest(putObjectRequest)
                    .requestBody(AsyncRequestBody.fromBytes(bytes)).build();

            Upload upload = s3TransferManager.upload(uploadRequest);
            CompletedUpload completedUpload = upload.completionFuture().join();
            PutObjectResponse putObjectResponse = completedUpload.response();

            return putObjectResponse != null;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static byte[] download(Bucket bucket, String key) {
        try (S3TransferManager s3TransferManager = getS3TransferManager(bucket)) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket.getBucketName()).key(key).build();
            DownloadRequest<ResponseBytes<GetObjectResponse>> downloadRequest = DownloadRequest.builder()
                    .getObjectRequest(getObjectRequest).responseTransformer(AsyncResponseTransformer.toBytes()).build();

            Download<ResponseBytes<GetObjectResponse>> download = s3TransferManager.download(downloadRequest);
            CompletedDownload<ResponseBytes<GetObjectResponse>> completedDownload = download.completionFuture().join();
            ResponseBytes<GetObjectResponse> responseBytes = completedDownload.result();

            return responseBytes.asByteArray();
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public void downloadToLocal(Bucket bucket, String key) {
        S3Client client = getS3Client(bucket);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket.getBucketName())
                .key(key)
                .build();
        client.getObject(getObjectRequest, Paths.get(key));
    }

    public void uploadFromLocal(Bucket bucket, String key) {
        S3Client client = getS3Client(bucket);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket.getBucketName())
                .key(key)
                .build();
        client.putObject(putObjectRequest, Paths.get(key));
    }

    public static void delete(Bucket bucket, String key) {
        try (S3Client s3Client = getS3Client(bucket)) {
            ObjectIdentifier objectIdentifier = ObjectIdentifier.builder().key(key).build();

            DeleteObjectsRequest dor = DeleteObjectsRequest.builder().bucket(bucket.getBucketName())
                    .delete(Delete.builder().objects(objectIdentifier).build()).build();
            s3Client.deleteObjects(dor);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static List<String> getAllKeys(Bucket bucket, String prefix) {
        try (S3Client s3Client = getS3Client(bucket)) {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket(bucket.getBucketName()).prefix(prefix).build();
            ListObjectsResponse listObjectsResponse;
            List<String> keys = new ArrayList<>();

            do {
                listObjectsResponse = s3Client.listObjects(listObjectsRequest);
                keys.addAll(listObjectsResponse.contents().stream().map(S3Object::key).collect(Collectors.toList()));
                listObjectsRequest = ListObjectsRequest.builder()
                        .bucket(bucket.getBucketName()).prefix(prefix).marker(listObjectsResponse.nextMarker()).build();
            } while (listObjectsResponse.isTruncated());

            return keys;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static void copy(Bucket bucket, String fromKey, String toKey) {
        try (S3Client s3Client = getS3Client(bucket)) {
            String bucketName = bucket.getBucketName();

            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder().sourceBucket(bucketName).sourceKey(fromKey)
                    .destinationBucket(bucketName).destinationKey(toKey).build();

            s3Client.copyObject(copyObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static S3Client getS3Client(Bucket bucket) {
        S3ClientBuilder s3ClientBuilder = S3Client.builder().region(AwsUtils.getAwsRegion());

        if (bucket.getAccessKey() != null && bucket.getSecretKey() != null) {
            s3ClientBuilder.credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(bucket.getAccessKey(), bucket.getSecretKey())));
        }

        return s3ClientBuilder.build();
    }

    private static S3TransferManager getS3TransferManager(Bucket bucket) {
        S3ClientConfiguration s3ClientConfiguration = getS3ClientConfiguration(bucket);
        return S3TransferManager.builder().s3ClientConfiguration(s3ClientConfiguration).build();
    }

    private static S3ClientConfiguration getS3ClientConfiguration(Bucket bucket) {
        S3ClientConfiguration.Builder builder = S3ClientConfiguration.builder().region(AwsUtils.getAwsRegion());

        if (bucket.getAccessKey() != null && bucket.getSecretKey() != null) {
            builder.credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(bucket.getAccessKey(), bucket.getSecretKey())));
        }

        return builder.build();
    }

}