package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentHibernateDaoTest {
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private Session mockSession;
    @Mock
    private Query mockQuery;
    @Mock
    private Transaction mockTransaction;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void getDepartments_happyPass() {
        //Step1: prepare
        IDepartmentDAO departmentDao = new DepartmentHibernateDAO();
        Department department = new Department(1,"Zhang3", "random", "US");
        List<Department> res = List.of(department);

        try (MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
            //when 里一定是假的， then可以假可以真.因为我的mocktory是假的所以我的then后面也是假的。
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            //这里放department还是account不重要
            when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
            //最关键的return value一定是真实的。
            when(mockQuery.list()).thenReturn(res);
            doNothing().when(mockSession).close();

            //Step2: call test method
            List<Department> actualRes = departmentDao.getDepartments();
            //Step3: result validation
            assertEquals(res, actualRes);
        }
    }

    //取名三段论，针对测试的方法_situation是什么，比如get exception_结论
    @Test
    public void getDepartmentsTest_getHibernateException_throwHibernateException() {
        IDepartmentDAO departmentDao = new DepartmentHibernateDAO();
        Department department = new Department(1, "Zhang3", "random", "US");
        List<Department> res = List.of(department);

        try (MockedStatic mockedStatic = mockStatic(HibernateUtil.class)) {
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
            //when 里一定是假的， then可以假可以真.因为我的mocktory是假的所以我的then后面也是假的。
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
            when(mockQuery.list()).thenReturn(res);
            doThrow(HibernateException.class).when(mockSession).close();

            //Step2&3: call test method and do result validation
            assertThrows(HibernateException.class, () -> departmentDao.getDepartments());
        }
    }

    @Test
    public void save_happyPass() {
        try (MockedStatic mockedStatic = mockStatic(HibernateUtil.class)){
            mockedStatic.when(HibernateUtil::getSessionFactory).thenReturn(mockSessionFactory);
            when(mockSessionFactory.openSession()).thenReturn(mockSession);
            when(mockSession.beginTransaction()).thenReturn(mockTransaction);


        }
    }
}
