package uk.co.jumperfly.aws.s3proxy;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.identitymanagement.model.InstanceProfile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    @Value("${aws.region}")
    private String region;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AmazonS3 s3client(@Autowired AWSCredentialsProvider credentialsProvider) {
        return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build();
    }

    @Bean
    public AWSCredentialsProvider credentialsProvider() {
        return new AWSCredentialsProviderChain(InstanceProfileCredentialsProvider.getInstance(), new EnvironmentVariableCredentialsProvider());
    }

}
