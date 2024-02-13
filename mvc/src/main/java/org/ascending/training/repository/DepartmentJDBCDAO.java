package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DepartmentJDBCDAO implements IDepartmentDAO{
    private static final String DB_URL = "jdbc:postgresql://localhost:5431/training_db";
    private static final String USER = "admin";
    private static final String PASS = "Training123!";
    private static final Logger logger = LoggerFactory.getLogger(DepartmentJDBCDAO.class);

    public List<Department> getDepartments()  {
        List<Department> departments = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM departments";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");

                Department department = new Department();
                department.setId(id);
                department.setName(name);
                department.setDescription(description);
                department.setLocation(location);

                departments.add(department);
            }
        } catch(SQLException e) {
            logger.error("Unable to connect to postgres", e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                //log.error
            }
        }

        return departments;
    }

    @Override
    public boolean save(Department department) {
        return false;
    }

    @Override
    public Department update(Department department) {
        return department;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        return null;
    }

    @Override
    public Department getById(Long id) {
        return null;
    }
}
