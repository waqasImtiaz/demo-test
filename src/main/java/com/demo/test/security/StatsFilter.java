package com.demo.test.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter("/*")
public class StatsFilter implements Filter {

  private static final Logger logger = LogManager.getLogger(StatsFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException { }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    long startTime = System.nanoTime();
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      // print processing time
      logger.info("Processing time: " + String.valueOf((System.nanoTime()-startTime)/1000000) + " MilliSecs");
    }
  }

  @Override
  public void destroy() { }

}
