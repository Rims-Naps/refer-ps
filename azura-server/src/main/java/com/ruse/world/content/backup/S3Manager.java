package com.ruse.world.content.backup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
// import software.amazon.awssdk.services.s3.endpoints.internal.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public class S3Manager {
    private static final String CONFIG_PATH = "config/s3config.json";
    // Static block will initialise these
    private static final S3Config config;
    private static final S3Client s3;

    static {
        try{
            InputStream in = S3Manager.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_PATH);
            if (in == null) {
                throw new FileNotFoundException("Classpath resource not found: " + CONFIG_PATH);
            }
            // Read config from JSON
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(in, S3Config.class);

            //Build Credentials and client
            AwsBasicCredentials creds = AwsBasicCredentials.create(
                    config.accessKeyId,
                    config.secretAccessKey
            );

            s3 = S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(creds))
                    .region(Region.of(config.region))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load S3 config " + CONFIG_PATH, e);
        }
    }


    /** Uploads a local file to S3 under the given key. */
    public static void uploadFile(String key, Path filePath) {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(config.bucket)
                .key(key)
                .build();
        s3.putObject(req, RequestBody.fromFile(filePath));
    }


    private static class S3Config {
        @JsonProperty("accessKeyId")
        public String accessKeyId;

        @JsonProperty("secretAccessKey")
        public String secretAccessKey;

        @JsonProperty("region")
        public String region;

        @JsonProperty("bucket")
        public String bucket;
    }
}