package com.example.demo.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Upload;

public interface UploadRepo extends JpaRepository<Upload,Long>{

	List<Upload> findBySpecialistContainingIgnoreCase(
	        String specialist
	);
}
