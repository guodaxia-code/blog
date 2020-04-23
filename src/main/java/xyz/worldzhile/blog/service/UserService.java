package xyz.worldzhile.blog.service;

import xyz.worldzhile.blog.domain.User;

public interface UserService {
    User checkUser(String username, String password);
}
