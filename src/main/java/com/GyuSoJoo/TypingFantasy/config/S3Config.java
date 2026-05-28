package com.GyuSoJoo.TypingFantasy.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    @Bean
    public AmazonS3 amazonS3() {
        // 1. 발급받은 액세스 키와 시크릿 키로 인증 객체 생성
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // 2. AWS 주소가 아닌 오라클 오브젝트 스토리지 호환 주소(Endpoint)로 타겟 지정
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(endpoint, region);

        // 3. 커스텀 설정이 반영된 AmazonS3 클라이언트 빌드 및 스프링 빈 등록
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
