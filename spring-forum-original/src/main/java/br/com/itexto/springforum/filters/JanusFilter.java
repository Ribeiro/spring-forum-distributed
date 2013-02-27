package br.com.itexto.springforum.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import br.com.itexto.springforum.requestwrapers.JanusHttpServletRequestWrapper;

public class JanusFilter implements Filter {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(Log4JLogger.class);

	public void destroy() {
		//Do any implementation here if necessary

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		log.info("JanusFilter - doFilter()");

		JanusHttpServletRequestWrapper httpReq = new JanusHttpServletRequestWrapper(
				(HttpServletRequest) req);
		HttpServletResponse httpRes = (HttpServletResponse) res;

		httpReq.addHeader("MY-HEADER-X1", "header 1");
		httpReq.addHeader("MY-HEADER-X2", "header 2");

		filterChain.doFilter(httpReq, httpRes);
	}

	public void init(FilterConfig arg0) throws ServletException {
		//Do any implementation here if necessary
	}

}
