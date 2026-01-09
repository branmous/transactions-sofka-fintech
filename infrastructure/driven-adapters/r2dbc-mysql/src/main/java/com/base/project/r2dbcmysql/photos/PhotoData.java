package com.base.project.r2dbcmysql.photos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "photos")
@AllArgsConstructor
@NoArgsConstructor
public class PhotoData {
    @Id
    private Long id;
    @Column(value = "album_id")
    private Long albumId;
    @Column(value = "title")
    private String title;
    @Column(value = "url")
    private String url;
    @Column(value = "thumbnail_url")
    private String thumbnailUrl;
}
