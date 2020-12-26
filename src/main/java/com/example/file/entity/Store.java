package com.example.file.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String storeName;

    private String fileOriginalName;

    private String fileContentType;

    private Long size;

    private String resourcePath;

    public Store(String storeName, String fileOriginalName, String fileContentType, Long size, String resourcePath) {
        this.storeName = storeName;
        this.fileOriginalName = fileOriginalName;
        this.fileContentType = fileContentType;
        this.size = size;
        this.resourcePath = resourcePath;
    }

}
