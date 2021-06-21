package com.gazatem.ekip.service;

import com.gazatem.ekip.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface FileInfoService {
     void init();

     void save(MultipartFile file);
     Resource load(String filename);

     void deleteAll();

     Stream<Path> loadAll();
     FileInfo saveFile(FileInfo fileInfo);
     FileInfo store(MultipartFile file) throws IOException;
     FileInfo getFile(Integer id);
     Stream<FileInfo> getAllFiles();
}
