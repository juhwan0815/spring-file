package com.example.file.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

//    @PostConstruct
//    public void setS3Client(){
//        // 자격 증명 객체를 얻는다.
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);
//
//        // 자격 증명을 통해 S3 client를 가져온다.
//        s3Client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(this.region)
//                .build();
//    }

    public String upload(MultipartFile file) throws Exception{

        String fileName = file.getOriginalFilename();

        // 파일 업로드
        s3Client.putObject(new PutObjectRequest(bucket,fileName,file.getInputStream(),null)
        .withCannedAcl(CannedAccessControlList.PublicRead));

        // 업로드를 한뒤 해당 URL을 DB에 저장
        return s3Client.getUrl(bucket,fileName).toString();
    }


}
