package ru.MarkMoskvitin.Timedome.back.service;

import ru.MarkMoskvitin.Timedome.back.entity.User;

public interface UserService {
    void deleteUser(String username);
    void addUser(User user);
    boolean isFree(String username);
}
