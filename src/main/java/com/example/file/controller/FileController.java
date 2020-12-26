package com.example.file.controller;

import com.example.file.entity.Store;
import com.example.file.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class FileController {

    @Value("${spring.servlet.multipart.location}")
    private String fileLocation;
    private final StoreRepository storeRepository;

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam MultipartFile file) {

        String storeName = LocalDateTime.now() + file.getOriginalFilename();
        String resourcePath = fileLocation + storeName;

        try {
            File store = new File(resourcePath);

            if(!store.exists()){
                store.mkdirs();
            }

            file.transferTo(store);

            Store store1 = new Store(
                    storeName,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getSize(),
                    resourcePath);

            storeRepository.save(store1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://localhost:8080/file/" + storeName;
    }

    @PostMapping("/filesUpload")
    public String filesUpload(@RequestParam("files") List<MultipartFile> files){
        System.out.println("files = " + files.size());

        for (MultipartFile file : files) {

            String storeName = LocalDateTime.now() + file.getOriginalFilename();
            String resourcePath = fileLocation + storeName;

            try {
                File store = new File(resourcePath);

                if(!store.exists()){
                    store.mkdirs();
                }

                file.transferTo(store);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "성공";
    }

    @PostMapping("/multiUpload")
    public String multiUpload(@RequestParam Map<String,MultipartFile> fileMap){

        Set<String> keys = fileMap.keySet();

        for (String key : keys) {
            MultipartFile file = fileMap.get(key);

            String storeName = LocalDateTime.now() + file.getOriginalFilename();
            String resourcePath = fileLocation + storeName;

            try {
                File store = new File(resourcePath);

                if(!store.exists()){
                    store.mkdirs();
                }

                file.transferTo(store);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "성공";
    }


    @GetMapping("/file/{downloadFile}")
    public ResponseEntity<Resource> download(@PathVariable("downloadFile") String downloadFile) throws IOException {

        //  만약 없다면 에러처리를 해주자
        Store store = storeRepository.findByStoreName(downloadFile).get();
        // 에러 컨트롤을 한다면 전역 컨트롤러에 의해 여기서 끝

        Path path = Paths.get(fileLocation + store.getStoreName());
        System.out.println("store.getStoreName() = " + store.getStoreName());
        String contentType = Files.probeContentType(path);

        String fileOriginalName = store.getFileOriginalName();

        String encordedFilename = URLEncoder.encode(fileOriginalName,"UTF-8").replace("+", "%20");

        System.out.println("contentType = " + contentType);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encordedFilename + "\"");

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/file/{storeName}")
    public String deleteFile(@PathVariable("storeName") String storeName){

        Store store = storeRepository.findByStoreName(storeName).get();
        String resourcePath = store.getResourcePath();

        File file = new File(resourcePath);

        if(file.exists()){
            file.delete();
        }

        return "성공";
    }
}
