package com.example.file.service;

import com.example.file.entity.Store;
import com.example.file.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public void saveStore(String fileName,String filePath){

        Store store = Store.createStore(fileName, filePath);

        storeRepository.save(store);
    }


    @Transactional
    public Store delete(String fileName) {

        Store findStore = storeRepository.findByFileName(fileName)
                .orElseThrow(() -> new IllegalStateException());

        storeRepository.delete(findStore);

        return findStore;
    }


    public Store findOne(String fileName) {
        return storeRepository.findByFileName(fileName)
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
