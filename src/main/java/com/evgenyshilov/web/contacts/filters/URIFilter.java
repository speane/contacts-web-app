package com.evgenyshilov.web.contacts.filters;

import com.evgenyshilov.web.contacts.resources.ApplicationResources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class URIFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String STATIC_RESOURCE_REGEX = "/.+[.].*";
        String URI = ((HttpServletRequest) servletRequest).getRequestURI();

        if (URI.matches(STATIC_RESOURCE_REGEX)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute(ApplicationResources.PRIMARY_URI_ATTRIBUTE_NAME, URI);
            servletRequest.getRequestDispatcher(ApplicationResources.FRONT_CONTROLLER_PATH).forward(servletRequest,
                    servletResponse);
        }
    }

    public void destroy() {

    }
}
