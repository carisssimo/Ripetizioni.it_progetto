package Filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;

import java.io.IOException;

public class CORSFilter implements Filter {

    @Override    public void init(FilterConfig filterConfig) throws ServletException {
        /*Filter.super.init(filterConfig);*/
           }

        @Override public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain
        filterChain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers",
                    "Access-Control-Allow-Headers,Origin,Accept,Authorization,X-Request-With,Content-Type,Access-Control-Request-Methods,Access-Control-Request-Headers");

            filterChain.doFilter(servletRequest, servletResponse);

        }

        @Override public void destroy () {
           /* Filter.super.destroy();*/
        }

    }