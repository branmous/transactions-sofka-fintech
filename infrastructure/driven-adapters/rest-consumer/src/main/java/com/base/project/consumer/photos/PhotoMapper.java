package com.base.project.consumer.photos;

import com.base.project.model.photo.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "albumId", source = "albumId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "thumbnailUrl", source = "thumbnailUrl")
    Photo toModel(PhotoResponse photoResponse);
}
