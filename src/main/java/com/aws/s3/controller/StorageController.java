package com.aws.s3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.s3.service.StorageServiceImpl;

@RestController
public class StorageController {
	
	@Autowired
	private StorageServiceImpl storageService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
		storageService.uploadFile(multipartFile);
		final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
		return new ResponseEntity<String>(response, HttpStatus.OK);	
	}

}
