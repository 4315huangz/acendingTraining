package org.ascending.training.repository;

import org.ascending.training.model.User;

public interface IUserDao {
    User getUserByCredentials(String email, String password) throws Exception;
    User getUserById(long id);
}
