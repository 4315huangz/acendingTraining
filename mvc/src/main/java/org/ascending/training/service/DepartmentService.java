package org.ascending.training.service;

import org.ascending.training.model.Department;
import org.ascending.training.repository.IDepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private IDepartmentDAO departmentDAO;

    public void save(Department department) { departmentDAO.save(department); }

    public List<Department> getDepartments() throws SQLException {
        return departmentDAO.getDepartments();
    }

    public void delete(Department department) {
        departmentDAO.delete(department.getId());
    }

    public Department getDepartmentEager(long id) {
        return departmentDAO.getDepartmentEagerBy(id);
    }

    public Department getBy(long id) {
        return departmentDAO.getById(id);
    }

    public Department update(Department department) {
        department = departmentDAO.update(department);
        return department;
    }
}
