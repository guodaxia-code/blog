package xyz.worldzhile.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private String id;
    private String name;



    private List<Blog> blogs;
}
