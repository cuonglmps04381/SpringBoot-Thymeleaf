package com.gazatem.ekip.repository;

import com.gazatem.ekip.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {

    @Query ("select fileInfo from  FileInfo fileInfo " +
            "where fileInfo.id = :id")
    FileInfo findById(@Param("id") Integer id);
}
