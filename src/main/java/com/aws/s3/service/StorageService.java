/**
 * 
 */
package com.aws.s3.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Logu
 *
 */
public interface StorageService {

	void uploadFile(MultipartFile multipartFile);

}
