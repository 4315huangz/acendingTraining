package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentDAO {
    List<Department> getDepartments();

    //Create
    boolean save(Department department);

    //Update
    Department update(Department department);

    //Delete
    void delete(long id);

    Department getDepartmentEagerBy(Long id);

    Department getById(Long id);
}
