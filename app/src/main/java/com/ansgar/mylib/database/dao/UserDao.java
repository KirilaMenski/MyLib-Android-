package com.ansgar.mylib.database.dao;

import com.ansgar.mylib.database.entity.User;

/**
 * Created by kirill on 24.1.17.
 */

public interface UserDao {

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    User getUserById(long id);

    User getUserByEmail(String email);

    User getUserByUuid(String uuid);

}
