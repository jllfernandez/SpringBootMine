package com.example.demo.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {

		log.info("SimpleCORSFilter Init");
	}

	// @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		res.setHeader("Access-Control-Allow-Origin", req.getHeader("origin"));
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Methods, Content-Type, Accept, X-Requested-With, remember-me");
		
		try {
			chain.doFilter(req, res);
		} catch (IOException ioe) {
			throw new ServletException();
		}

	}

	// @Override
	public void init(FilterConfig filterConfig) {
	}

	// @Override
	public void destroy(FilterConfig filterConfig) {
	}

}
