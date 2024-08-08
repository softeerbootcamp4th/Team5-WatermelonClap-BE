package com.watermelon.server.admin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.watermelon.server.admin.exception.S3ImageFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageService {

    private final AmazonS3 s3Client;
    private List<String> allowedImageExtentionList = Arrays.asList("jpg","jped","png","gif");

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile image) throws S3ImageFormatException {
        if(image.isEmpty() ||Objects.isNull(image.getOriginalFilename())){
            throw new S3ImageFormatException("No file Provided");
        }
        return checkFileExtensionAndUpload(image);
    }

    public String checkFileExtensionAndUpload(MultipartFile image) throws S3ImageFormatException {
        this.validateImageFileExtension(image.getOriginalFilename());
        try{
            return uploadImageToS3(image);
        }
        catch(Exception e){
            throw new S3ImageFormatException(e.getMessage());
        }
    }
    public String uploadImageToS3(MultipartFile image) throws S3ImageFormatException, IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            //실제로 S3에 이미지 데이터를 넣는 부분이다.
            s3Client.putObject(putObjectRequest); // put image to S3
        }catch (Exception e){
            throw new S3ImageFormatException(e.getMessage());
        }finally {
            byteArrayInputStream.close();
            is.close();
        }

        return s3Client.getUrl(bucketName, s3FileName).toString();

    }

    public void validateImageFileExtension(String fileName) throws S3ImageFormatException {
        int lastDotIndex = fileName.lastIndexOf('.');
        if(lastDotIndex == -1){
            throw new S3ImageFormatException("No image file extension provided");
        }
        String extension = fileName.substring(lastDotIndex+1).toLowerCase();
        if(!allowedImageExtentionList.contains(extension)){
            throw new S3ImageFormatException("Invalid image file extension");
        }
    }
}
