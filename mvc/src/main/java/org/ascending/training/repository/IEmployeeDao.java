package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.ascending.training.model.Employee;

import java.util.List;

public interface IEmployeeDao {
    List<Employee> getEmployees();

    //Create
    boolean save(Employee employee);

    //Update
    void updateName(long id, String name);

    //Delete
    void delete(Employee employee);
}
