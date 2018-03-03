package com.svs.krishi.grainservice.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * This is the servlet filter for assigning request id with each request
 */
@Slf4j
public class RequestIdFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
        throws IOException, ServletException {
        final String requestId = UUID.randomUUID().toString();
        request.setAttribute("RequestId", requestId);
        log.info("Request ID Generated: {}", requestId);
        try {
            MDC.put("RequestId", requestId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove("RequestId");
        }
    }

    @Override
    public void destroy() {

    }
}
