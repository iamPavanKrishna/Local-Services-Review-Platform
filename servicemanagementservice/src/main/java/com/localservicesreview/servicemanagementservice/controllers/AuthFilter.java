package com.localservicesreview.servicemanagementservice.controllers;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.localservicesreview.servicemanagementservice.thirdPartyClients.user.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter implements Filter {
    @Autowired
    private UserService userService;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        if(uri.equals("/api/v1/auth/login") || uri.equals("/api/v1/auth/signup") || uri.equals("/api/v1/services/hello")){
            filterchain.doFilter(request, response);
            return;
        }
        Enumeration<String> headerNames = httpRequest.getHeaderNames();

        if (headerNames != null) {
            boolean authTokenFound = false;
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                if(header.equals("authorization")){
                    authTokenFound = true;
                    String token = httpRequest.getHeader(header).substring(7);
                    boolean tokenValidated = userService.validateToken(token);
                    if(!tokenValidated){
                        httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid auth token");
                    }
                    break;
                }
            }
            if(!authTokenFound){
                httpResponse.sendError(HttpStatus.BAD_REQUEST.value(), "Missing auth header");
            }
        } else {
            httpResponse.sendError(HttpStatus.BAD_REQUEST.value(), "Missing request headers");
        }

        filterchain.doFilter(httpRequest, response);
    }

}
