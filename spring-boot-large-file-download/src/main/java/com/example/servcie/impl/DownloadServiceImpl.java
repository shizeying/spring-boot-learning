package com.example.servcie.impl;

import com.example.servcie.DownloadService;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DownloadServiceImpl implements DownloadService {
	@Autowired
	private Environment env;
	
	@Override
	public Resource loadFileAsResource(String fileName) {
		//env.getProperty("file.upload-dir")
		Path file = Try.of(() -> Paths.get(Objects.requireNonNull("/Users/shizeying/Desktop/")).toAbsolutePath().normalize())
		               .mapTry(a -> a.resolve(fileName).normalize()).onFailure(Throwable::printStackTrace).get();
		
		UrlResource urlResource = Try.of(() -> new UrlResource(file.toUri()))
		                             .onFailure(Throwable::printStackTrace).get();
		return Optional.ofNullable(urlResource).filter(Resource::exists).orElse(null);
	}
	
	@Override
	public Resource loadFileAsResource(String fileName, String filePath) {
		Path file = Try.of(Paths.get(filePath).toAbsolutePath()::normalize)
		               .mapTry(a -> a.resolve(fileName).normalize()).onFailure(Throwable::printStackTrace).get();
		
		UrlResource urlResource = Try.of(() -> new UrlResource(file.toUri()))
		                             .onFailure(Throwable::printStackTrace).get();
		return Optional.ofNullable(urlResource).filter(Resource::exists).orElse(null);
	}
	
	@Override
	public List<Resource> loadFileAsResource(List<String> fileNames) {
		return fileNames.stream().filter(Objects::nonNull).map(this::loadFileAsResource).collect(Collectors.toList());
		
	}
	
	@Override
	public List<Resource> loadFileAsResource(Map<String, String> mapFile) {
		return mapFile.entrySet().stream().filter(Objects::nonNull).map(map -> this.loadFileAsResource(map.getKey(), map.getValue()))
		              .collect(Collectors.toList());
	}
	
	
}
