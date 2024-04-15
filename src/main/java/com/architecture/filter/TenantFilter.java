package com.architecture.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.access.AccessDeniedException;
import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final String tenantId = "22";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader("X-Tenant-id");
        boolean hasAccess = isUserAllowed(tenantId);
        if (hasAccess){
            filterChain.doFilter(request, response); // invoke the rest of the filters in the chain.
            return;
        }
        throw new AccessDeniedException("Access Denied");
    }

    private boolean isUserAllowed(String tenantId) {
        return tenantId.equals(this.tenantId);
    }
}
