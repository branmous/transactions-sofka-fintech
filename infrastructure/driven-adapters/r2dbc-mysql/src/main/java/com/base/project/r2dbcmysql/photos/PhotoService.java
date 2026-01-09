package com.base.project.r2dbcmysql.photos;

import com.base.project.model.photo.Photo;
import com.base.project.model.photo.gateways.PhotoRepository;
import com.base.project.r2dbcmysql.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class PhotoService extends ReactiveAdapterOperations<Photo, PhotoData, Long, PhotoDataRepository>
        implements PhotoRepository {
    protected PhotoService(PhotoDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, data -> mapper.map(data, Photo.class));
    }

    @Override
    public Flux<Photo> saveAll(Flux<Photo> photos) {
        return this.saveData(photos.map(this::toData))
                .map(this::toEntity);
    }
}
