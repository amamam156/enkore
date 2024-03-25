package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.Employee;
import com.hongchao.enkore.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //employee login
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        // get username and password
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // get password form database
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // check if password equal null
        if (emp == null) {
            return R.error(("Login failed ! Please enter password !"));
        }

        // compare password
        if (!emp.getPassword().equals(password)) {
            return R.error(("Login failed ! Wrong password, please re-enter !"));
        }

        // check employee status
        if (emp.getStatus() == 0) {
            return R.error(("Account has been Blocked !"));
        }

        // login success and storage employee id to session and return
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    // employee logout
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("Logout Successful!");
    }

    // add employee
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("Add employee, employee information: {}", employee.toString());

        // set default password (MD5)
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        // get login uesr id
        // Long empId = (Long) request.getSession().getAttribute("employee");

        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("Add employee successful!");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        // page creator
        Page pageInfo = new Page(page, pageSize);

        // create filter
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        // Add filter criteria
        queryWrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);

        // Add sorting criteria
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // process search
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    // edit employee information base on employee id
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        Long id = Thread.currentThread().getId();
        log.info("The thread ID is {}", id);

        // Long empId = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateUser(empId);
        // employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);

        return R.success("Employee information update successful!");
    }

    // search employee information
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("search employee information base on id...");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("Employee information does not exist!");
    }
}
