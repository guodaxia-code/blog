package xyz.worldzhile.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String id;
    private String name;

    public Tag(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer count;
    private List<Blog> blogs;
}
