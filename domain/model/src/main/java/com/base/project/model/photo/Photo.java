package com.base.project.model.photo;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Photo {
    private Long id;
    private Long albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}
