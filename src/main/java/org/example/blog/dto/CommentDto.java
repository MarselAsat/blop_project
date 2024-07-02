package org.example.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private String user;

    @JsonProperty("post_id")
    private Long postId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss[.SSS]")
    @JsonProperty("dt_created")
    LocalDateTime dtCreated;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss[.SSS]")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dt_updated")
    LocalDateTime dtUpdated;

}
