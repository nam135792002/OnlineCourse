package com.springboot.courses.payload.video;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private Integer id;

    private String url;

    private LocalTime duration;

    private String description;
}
