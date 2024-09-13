package com.trainingmug.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trainingmug.dto.ImageDto;
import com.trainingmug.model.Image;

public interface IImageService {
	
	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
	void updateImage(MultipartFile file, Long imageId);

}
