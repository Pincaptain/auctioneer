package com.gjorovski.auctioneer.auth.filter;

import com.gjorovski.auctioneer.auth.Authentication;
import com.gjorovski.auctioneer.auth.TokenService;
import com.gjorovski.auctioneer.user.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (tokenService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

            if (webApplicationContext != null) {
                tokenService = webApplicationContext.getBean(TokenService.class);
            }
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tokenValue = httpServletRequest.getHeader("Authorization");

        User user = tokenService.getUserByTokenValue(tokenValue);
        Authentication authentication = new Authentication(user);

        httpServletRequest.setAttribute("authentication", authentication);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
