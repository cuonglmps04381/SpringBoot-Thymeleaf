package com.gazatem.ekip.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "file_info")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String type;

    @Lob()
    @Column(length=100000)
    private byte[] data;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "fileInfo")
    @ToString.Exclude
    private User user;

    public FileInfo(String fileName, String contentType, byte[] bytes) {
        this.name = fileName;
        this.type = contentType;
        this.data = bytes;
    }

    public FileInfo(String filename, String url) {
        this.name = filename;
    }
}
