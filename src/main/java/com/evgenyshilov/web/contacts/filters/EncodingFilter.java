package com.evgenyshilov.web.contacts.filters;

import com.evgenyshilov.web.contacts.help.LogHelper;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Evgeny Shilov on 03.10.2016.
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            LogHelper.error("Unable to set character encoding to a request: ", e);
        }
    }

    @Override
    public void destroy() {

    }
}
