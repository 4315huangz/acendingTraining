package org.ascending.training.integration;

import org.ascending.training.ApplicationBootstrap;
import org.ascending.training.model.Department;
import org.ascending.training.model.Employee;
import org.ascending.training.repository.DepartmentHibernateDAO;
import org.ascending.training.repository.EmployeeHibernateDao;
import org.ascending.training.repository.IDepartmentDAO;
import org.ascending.training.repository.IEmployeeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class DepartmentDaoTest {
    @Autowired
    IDepartmentDAO departmentDAO;
    @Autowired
    IEmployeeDao employeeDao;
    Department department;
    Employee employee1;
    Employee employee2;

    @Before
    public void setup() {
       // departmentDAO = new DepartmentHibernateDAO();
      //  employeeDao = new EmployeeHibernateDao();
        department = new Department();
        department.setName("HR");
        department.setDescription("random description");
        department.setLocation("US");
        departmentDAO.save(department);

        employee1 = new Employee();
        employee1.setAddress("US");
        employee1.setName("Joe");
        employee1.setDepartment(department);
        employeeDao.save(employee1);

        employee2 = new Employee();
        employee2.setName("May");
        employee2.setAddress("US");
        employee2.setDepartment(department);
        employeeDao.save(employee2);

    }

    @After
    public void teardown() {
        employeeDao.delete(employee1);
        employeeDao.delete(employee2);
        departmentDAO.delete(department.getId());
    }

    @Test
    public void getDepartmentsTest(){
        List<Department> departmentList = departmentDAO.getDepartments();
        assertEquals(3, departmentList.size());
    }

}
