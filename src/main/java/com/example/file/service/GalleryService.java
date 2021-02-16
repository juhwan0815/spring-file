package com.example.file.service;

import com.example.file.entity.Gallery;
import com.example.file.model.GalleryDto;
import com.example.file.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public void savePost(GalleryDto galleryDto){
        galleryRepository.save(galleryDto.toEntity());
    }

    public List<GalleryDto> getList(){
        List<Gallery> galleries = galleryRepository.findAll();

        List<GalleryDto> galleryDtos = galleries.stream()
                .map(gallery -> convertEntityDto(gallery))
                .collect(Collectors.toList());

        return galleryDtos;
    }

    private GalleryDto convertEntityDto(Gallery gallery){
        return GalleryDto.builder()
                .id(gallery.getId())
                .title(gallery.getTitle())
                .filePath(gallery.getFilePath())
                .build();
    }
}
