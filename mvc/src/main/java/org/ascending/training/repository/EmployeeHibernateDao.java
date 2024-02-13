package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.model.Employee;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeHibernateDao implements IEmployeeDao {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final Logger logger = LoggerFactory.getLogger(DepartmentHibernateDAO.class);

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees;
        try {
            Session session = sessionFactory.openSession();
            String hql = "from Employee"; //select * from department;
            Query<Employee> query = session.createQuery(hql);
            employees = query.list();
            session.close();
            return employees;
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            throw e;
        }
    }

    @Override
    public boolean save(Employee employee) {
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Save transaction failed, rollback");
                transaction.rollback();
            }
            logger.error("Failed to save department ${}", employee);
            throw e;
        }
    }

    @Override
    public void updateName(long id, String name) {

    }

    @Override
    public void delete(Employee employee) {
        Transaction transaction = null;

        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Delete employee failed, rollback", e);
                transaction.rollback();
            }
            logger.error("Fail to delete employee ${}", employee);
            throw e;
        }

    }
}
