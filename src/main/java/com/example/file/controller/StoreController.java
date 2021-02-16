package com.example.file.controller;

import com.example.file.entity.Store;
import com.example.file.service.StorageService;
import com.example.file.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StorageService storageService;
    private final StoreService storeService;

    @PostMapping("/store")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        String filePath = storageService.upload(multipartFile);
        storeService.saveStore(multipartFile.getOriginalFilename(),filePath);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/store")
    public ResponseEntity delete(@RequestParam("fileName") String fileName){

        Store store = storeService.delete(fileName);
        storageService.delete(store);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/store")
    public String get(@RequestParam("fileName") String fileName){

        Store store = storeService.findOne(fileName);
        return "redirect:"+store.getFilePath();
    }

}
