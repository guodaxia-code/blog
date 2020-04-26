package xyz.worldzhile.blog.service;

import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(String id);

    Type getTypeByName(String name);

    List<Type> listType(PageBean<Type> page);

    List<Type> getAll();

    Type updateType(Type type);

    void deleteType(String id);

    List<Type> indexShow(int typeShowCount);
}
