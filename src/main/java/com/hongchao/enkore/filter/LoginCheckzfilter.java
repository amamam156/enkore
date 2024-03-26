package com.hongchao.enkore.filter;

import com.alibaba.fastjson.JSON;
import com.hongchao.enkore.common.BaseContext;
import com.hongchao.enkore.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String[] urls = new String[] { "/employee/login", "/employee/logout", "/backend/**", "/front/**",
                "/common/**" };

        // check if need to process
        boolean check = check(urls, requestURI);

        // pass
        if (check)
        {
            log.info("Intercepted request: {} do not need to process", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // check if login
        if (request.getSession().getAttribute("employee") != null)
        {
            log.info("User login, user id is : {} ", request.getSession().getAttribute("employee"));

            Long emId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(emId);

            filterChain.doFilter(request, response);
            return;
        }
        log.info("User not login");
        // not login
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    // when path match check if pass
    public boolean check(String[] urls, String requestURI)
    {
        for (String url : urls)
        {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match)
            {
                return true;
            }
        }

        return false;
    }
}
