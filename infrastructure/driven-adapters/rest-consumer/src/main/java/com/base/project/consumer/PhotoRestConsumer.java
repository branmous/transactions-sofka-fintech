package com.base.project.consumer;

import com.base.project.consumer.photos.PhotoMapper;
import com.base.project.consumer.photos.PhotoResponse;
import com.base.project.model.photo.Photo;
import com.base.project.model.photo.gateways.PhotoRestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoRestConsumer implements PhotoRestRepository {
    private final WebClient client;
    private final PhotoMapper photoMapper;

    @Override
    public Flux<Photo> getPhotos() {
        return client.get()
                .uri("/photos")
                .retrieve()
                .bodyToFlux(PhotoResponse.class)
                .map(photoMapper::toModel);
    }




    private List<PhotoResponse> entityToResponse(List<Photo> photos){
        List<PhotoResponse> photoResponses = new ArrayList<>();
        for (Photo photo:photos) {
            PhotoResponse photoResponse = PhotoResponse.builder()
                    .id(photo.getId())
                    .albumId(photo.getAlbumId())
                    .title(photo.getTitle())
                    .build();
            photoResponses.add(photoResponse);
        }

        return photoResponses;
    }

}
