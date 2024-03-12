package com.hongchao.enkore.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hongchao.enkore.common.R;
import com.hongchao.enkore.entity.Employee;
import com.hongchao.enkore.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    //employee login
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee)
    {

        // get username and password
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // get password form database
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // check if password equal null
        if (emp == null)
        {
            return R.error(("Login failed ! Please enter password !"));
        }

        // compare password
        if (!emp.getPassword().equals(password))
        {
            return R.error(("Login failed ! Wrong password, please re-enter !"));
        }

        // check employee status
        if (emp.getStatus() == 0)
        {
            return R.error(("Account has been Blocked !"));
        }

        // login success and storage employee id to session and return
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    // employee logout
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("Logout Successful !");
    }

}
