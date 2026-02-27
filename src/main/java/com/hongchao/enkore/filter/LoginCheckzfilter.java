package com.hongchao.enkore.filter;

import com.alibaba.fastjson.JSON;
import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;



// check if login
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckzfilter implements Filter
{

    // path matcher
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // get requestURI
        String requestURI = request.getRequestURI();
        log.info("Intercepted request: {}", requestURI);

        // define requests that do not need to be processed
        String[] urls = new String[] { "/employee/login", "/employee/logout", "/backend/**", "/front/**", "/common/**",
                "/user/sendMsg", "/user/login" };

        // check if need to process
        boolean check = check(urls, requestURI);

        // pass
        if (check)
        {
            log.info("Intercepted request: {} do not need to process", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // check if login - employee
        if (request.getSession().getAttribute("employee") != null)
        {
            log.info("Employee login, employee id is: {}", request.getSession().getAttribute("employee"));

            Long emId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(emId);

            filterChain.doFilter(request, response);
            return;
        }

        // check if login - user
        if (request.getSession().getAttribute("user") != null)
        {
            log.info("User login, user id is: {}", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("User not login");
        // not login
        String errorMessage = "NOTLOGIN";
        response.getWriter().write(JSON.toJSONString(R.error(errorMessage)));
        return;

    }

    // when path match check if pass
    public boolean check(String[] urls, String requestURI)
    {
        if (requestURI == null) {
            return false; // A null requestURI cannot match any URL
        }
        for (String url : urls)
        {
            boolean match = PATH_MATCHER.match(java.util.Objects.requireNonNull(url), java.util.Objects.requireNonNull(requestURI));
            if (match)
            {
                return true;
            }
        }

        return false;
    }
}
