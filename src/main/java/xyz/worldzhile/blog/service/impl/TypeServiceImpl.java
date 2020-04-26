package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.mapper.TypeMapper;
import xyz.worldzhile.blog.service.TypeService;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;


    @Override
    public Type saveType(Type type) {
         typeMapper.save(type);
         return  typeMapper.get(type.getId());
    }

    @Override
    public Type getType(String id) {
        return typeMapper.get(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public List<Type> listType(PageBean<Type> pageBean) {
        Integer count = typeMapper.count();
        pageBean.setTotalCount(count);
        return typeMapper.list(pageBean);
    }

    @Override
    public List<Type> getAll() {
        return typeMapper.all();
    }

    @Override
    public Type updateType(Type type) {
         typeMapper.update(type);
        return typeMapper.get(type.getId());
    }

    @Override
    public void deleteType(String id) {
        typeMapper.deleteType(id);
    }

    @Override
    public List<Type> indexShow(int typeShowCount) {
        return typeMapper.indexShow(typeShowCount);
    }
}
