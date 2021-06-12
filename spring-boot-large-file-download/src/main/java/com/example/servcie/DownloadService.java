package com.example.servcie;

import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

public interface DownloadService {
	Resource loadFileAsResource(String fileName);
	Resource loadFileAsResource(String fileName,String filePath);
	List<Resource> loadFileAsResource(List<String> fileNames);
	List<Resource> loadFileAsResource(Map<String,String> mapFile);
}
