package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.ascending.training.repository.exception.UserNotFoundException;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserHibernateDao implements IUserDao {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDAO.class);

    @Override
    public User getUserById(long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM User d where id = :Id";
                try {
                    Query<User> query = session.createQuery(hql);
                    query.setParameter("Id", id);
                    User result = query.uniqueResult();
                    session.close();
                    return result;
                } catch (HibernateException e) {
                    logger.error("Session close exception try again" , e);
                    session.close();
                    return null;
                }
    }

    @Override
    public User getUserByCredentials(String email, String password) throws Exception {
        String hql = "From User as u where lower(u.email) = :email and u.password = :password";
        logger.info(String.format("User email: %s, password: %s", email, password));

        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            logger.info("Get user {}", query.uniqueResult());
            return query.uniqueResult();
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage());
            throw new UserNotFoundException("Can't find user record with email = " + email + ", and password = " + password);
        }
    }
}
