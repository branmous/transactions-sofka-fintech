package com.base.project.model.photo.gateways;

import com.base.project.model.photo.Photo;
import reactor.core.publisher.Flux;

public interface PhotoRepository {
    Flux<Photo> saveAll(Flux<Photo> photos);
}
