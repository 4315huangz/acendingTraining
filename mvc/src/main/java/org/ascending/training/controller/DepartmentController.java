package org.ascending.training.controller;

import org.ascending.training.model.Department;
import org.ascending.training.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController //controller+rest, which is response body,完成java到json转变，client可以理解
@RequestMapping(value = "/department")
public class DepartmentController {
    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Department> getDepartments() throws SQLException {
        logger.info("I am in getDepartments controller.");
        List<Department> departments = departmentService.getDepartments();
        return departments;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Department getDepartmentById(@PathVariable(name = "id") long id) {
        logger.info("I am in getDepartmentById controller.");
        return departmentService.getBy(id);
    }

//patch更新局部资源比如只有name of department，用put也是更新，但会整个department暴露给client，可以修改其他attribute
    @RequestMapping(value = "/{id}", params = { "name"}, method = RequestMethod.PATCH)
    public Department updateDepartmentName(@PathVariable(name = "id") long id, @RequestParam(name = "name") String name) {
        logger.info("Pass in variable id: {} and name {}.", id, name);
         Department d = departmentService.getBy(id);
         d.setName(name);
         d = departmentService.update(d);
        return d;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Department department) {
        logger.info("Post a new department object {}", department.getName());
        departmentService.save(department);
    }
}
