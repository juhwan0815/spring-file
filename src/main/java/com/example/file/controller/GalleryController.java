package com.example.file.controller;

import com.example.file.model.GalleryDto;
import com.example.file.service.GalleryService;
import com.example.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final S3Service s3Service;
    private final GalleryService galleryService;

    @GetMapping("/gallery")
    public String displayWrite(Model model){

        List<GalleryDto> galleryDtoList = galleryService.getList();

        model.addAttribute("galleryList",galleryDtoList);

        return "/gallery";
    }

    @PostMapping("/gallery")
    public String execWrite(GalleryDto galleryDto, MultipartFile file) throws Exception{
        String imgPath = s3Service.upload(file);
        galleryDto.setFilePath(imgPath);

        galleryService.savePost(galleryDto);

        return "redirect:/gallery";
    }

}
