package com.example.file.model;

import com.example.file.entity.Gallery;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GalleryDto {

    private Long id;
    private String title;
    private String filePath;
    private String imgFullPath;

    public Gallery toEntity(){
        Gallery build = Gallery.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public GalleryDto(Long id,String title,String filePath,String imgFullPath){
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.imgFullPath = imgFullPath;
    }
}
