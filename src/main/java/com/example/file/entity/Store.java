package com.example.file.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String filePath;

    public static Store createStore(String fileName,String filePath){
        Store store = new Store();
        store.fileName = fileName;
        store.filePath = filePath;
        return store;
    }
}
