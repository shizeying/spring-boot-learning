package com.example.service.impl;

import com.example.domain.Namespace;
import com.example.service.StringJoinService;
import com.example.utils.XmlFormatter;
import io.vavr.control.Try;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class StringJoinServiceImpl implements StringJoinService {
	@Autowired
	private XmlFormatter xmlFormatter;
	
	@Override
	public String setAddJoin(Namespace namespace) {
		return xmlFormatter.format(namespace.toString());
	}
	
	/**
	 * 写文件
	 *
	 * @param namespace
	 * 		名称空间
	 * @param fileName
	 */
	@Override
	public void writeFile(String namespace, String fileName) {
		File file = new File(fileName);
		file.deleteOnExit();
		Try.of(file::createNewFile)
		   .onFailure(Throwable::printStackTrace)
		   .isSuccess();
		Try
				.run(() -> FileUtils.writeStringToFile(file, namespace, StandardCharsets.UTF_8.name()))
				.onFailure(Throwable::printStackTrace)
				.get();
		
	}
}
