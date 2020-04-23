package xyz.worldzhile.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogTag {

    private String id;
    private String blogid;
    private String tagid;


}
