package org.ascending.training.service;

import org.ascending.training.model.User;
import org.ascending.training.repository.UserHibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserHibernateDao userHibernateDao;

    public User getUserByCredentials(String email, String password) throws Exception {
        return userHibernateDao.getUserByCredentials(email, password);
    }

    public User getUserById(long id) {
        return userHibernateDao.getUserById(id);
    }
}
