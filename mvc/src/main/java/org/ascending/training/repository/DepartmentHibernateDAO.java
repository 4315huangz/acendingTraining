package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.repository.exception.DatabaseException;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentHibernateDAO implements IDepartmentDAO{
    private static final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDAO.class);

    @Override
    public List<Department> getDepartments() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        List<Department> departments;
        try {
            Session session = sessionFactory.openSession();
            String hql = "from Department"; //select * from department;
            Query<Department> query = session.createQuery(hql);
            departments = query.list();
            session.close();
            return departments;
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            throw e;
        }
    }

    @Override
    public boolean save(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Save transaction failed, rollback");
                transaction.rollback();
            }
            logger.error("Failed to save department ${}", department);
            throw e;
        }
    }

    @Override
    public Department update(Department department) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(department);
            transaction.commit();
            Department d = getById((department.getId()));
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("update transaction failed, rollback", e);
                transaction.rollback();
            }
            logger.error("Failed to update department ${}", department);
            throw e;
        }
        return department;
    }

    @Override
    public void delete(long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        String hql = "delete Department as d where d.id = :Id";

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Delete transaction failed, rollback", e);
                transaction.rollback();
            }
            logger.error("Delete department failed where id = ${}", id, e);
            throw e;
        }
    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        String hql = "From Department as d LEFT JOIN FETCH d.employees where d.id = :Id";
        Session session = sessionFactory.openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Department result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("failed to retrieve data record", e);
            session.close();
            return null;
        }
    }

    @Override
    public Department getById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        String hql = "FROM Department d where id= :ID";
        try {
            Query<Department> query = s.createQuery(hql);
            query.setParameter("ID", id);
            Department result = query.uniqueResult();
            s.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            s.close();
            return null;
        }
    }
}
