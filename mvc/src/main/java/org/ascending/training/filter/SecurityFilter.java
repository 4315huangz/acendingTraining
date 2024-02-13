package org.ascending.training.filter;

import io.jsonwebtoken.Claims;
import org.ascending.training.model.User;
import org.ascending.training.service.JWTService;
import org.ascending.training.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    private static final Set<String> ALLOWED_PATH = new HashSet<>(Arrays.asList("", "/login","logout"));
    private static final Set<String> IGNORED_PATH = new HashSet<>(Arrays.asList("/auth"));

    @Autowired
    UserService userService;
    @Autowired
    JWTService jwtService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Check null or not before use. Initialize dependency first then inject.
        if (jwtService == null) {
            // Once injected, it will inject all dependencies required by various filter instance.
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletRequest.getServletContext());
        }
        logger.info("Start to do authorization");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        int statusCode = authorization(req);
        logger.info("Status Coded: {}", statusCode);
        if(statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("1");
        } else {
            logger.info("2");
            ((HttpServletResponse)servletResponse).sendError(statusCode);
        }
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        if (IGNORED_PATH.contains(uri)) {
            logger.info("uri: {}", uri);
            return HttpServletResponse.SC_ACCEPTED;
        }

        try {
            logger.info("request is: {}", req.getHeader("Authorization"));
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            logger.info("token is: ", token);
            if (token == null || token.isEmpty())
                return statusCode;

            logger.info("Before jwt decrypt.");
            Claims claims = jwtService.decryptToken(token);
            logger.info("===== after parsing JWT token, claims.getId()={}", claims.getId());
            if (claims.getId() != null) {
                User u = userService.getUserById(Long.valueOf(claims.getId()));
                logger.info("after get user id.");
                if (u != null) {
                    return HttpServletResponse.SC_ACCEPTED;
                }
            }
            String verb = req.getMethod();
            String allowedResources = "/";
            logger.info("###");
            switch (verb) {
                case "GET":
                    allowedResources = (String) claims.get("allowedResources");
                    break;
                case "POST":
                    allowedResources = (String) claims.get("allowedCreateResources");
                    break;
                case "PUT":
                    allowedResources = (String) claims.get("allowedUpdateResources");
                    break;
                case "DELETE":
                    allowedResources = (String) claims.get("allowedDeleteResources");
            }

            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    logger.info("$$$$");
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
        } catch (Exception e) {
            logger.info("Cannot get token");
        }
        logger.info("%%%%");
        return statusCode;
    }
}
