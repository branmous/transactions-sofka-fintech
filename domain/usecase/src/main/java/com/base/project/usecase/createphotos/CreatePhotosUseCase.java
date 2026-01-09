package com.base.project.usecase.createphotos;

import com.base.project.model.photo.Photo;
import com.base.project.model.photo.gateways.PhotoRepository;
import com.base.project.model.photo.gateways.PhotoRestRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class CreatePhotosUseCase {
    private final PhotoRestRepository photoRestRepository;
    private final PhotoRepository photoRepository;

    public Flux<Photo> createPhotos() {
        return photoRestRepository.getPhotos()
                .buffer(20)
                .flatMap(photos ->
                        photoRepository.saveAll(Flux.fromIterable(photos)));
    }

}
