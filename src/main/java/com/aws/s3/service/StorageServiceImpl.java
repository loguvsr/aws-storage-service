/**
 * 
 */
package com.aws.s3.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author Logu
 *
 */
@Service
public class StorageServiceImpl implements StorageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

	@Autowired
	private AmazonS3 amazons3;
	
	@Value("${aws.s3.bucket_name")
	private String bucketName;

	@Override
	@Async
	public void uploadFile(MultipartFile multipartFile) {
		try {
			File file = convertMultiPartFileToFile(multipartFile);
			uploadFileToS3Bucket(bucketName, file);
			LOGGER.info("File upload is completed.");
			file.delete(); // To remove the file locally created in the project folder.
		} catch (AmazonServiceException ex) {
			ex.getMessage();
		}
	}

	private File convertMultiPartFileToFile(MultipartFile multipartFile) {
		File file = new File(multipartFile.getOriginalFilename());
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch ( IOException ex) {
		}
		return file;
	}

	private void uploadFileToS3Bucket(String bucketName, File file) {
        String fileName = file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        amazons3.putObject(putObjectRequest);
    }
}
