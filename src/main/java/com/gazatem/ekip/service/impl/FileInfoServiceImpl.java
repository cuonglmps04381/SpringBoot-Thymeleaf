package com.gazatem.ekip.service.impl;

import com.gazatem.ekip.model.FileInfo;
import com.gazatem.ekip.repository.FileInfoRepository;
import com.gazatem.ekip.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    private final Path root = Paths.get("uploads");

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public FileInfo saveFile(FileInfo fileInfo) {
        return fileInfoRepository.save(fileInfo);
    }

    @Override
    public FileInfo store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileInfo FileDB = new FileInfo(fileName, file.getContentType(), file.getBytes());

        return fileInfoRepository.save(FileDB);
    }

    @Override
    public FileInfo getFile(Integer id) {
        return fileInfoRepository.findById(id);
    }

    @Override
    public Stream<FileInfo> getAllFiles() {
        return fileInfoRepository.findAll().stream();
    }
}
