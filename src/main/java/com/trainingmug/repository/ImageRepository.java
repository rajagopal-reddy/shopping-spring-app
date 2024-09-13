package com.trainingmug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingmug.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByProductId(Long id);

}
