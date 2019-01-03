package com.bluedon.analyse.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class WebServicesFilter implements Filter {
	//有权限访问的IP地址
    static final String[] deniedIPList=new String[]{
                       "127.0.0.1"
                    };
    
    public boolean isIPDenied(String ipAddr) {
    	for (String ip : deniedIPList) {
			if(ip.equals(ipAddr))
				return true;
		}
		return false;
    }


	@Override
	public void doFilter(final ServletRequest req,final ServletResponse res,FilterChain chain) throws IOException, ServletException {
		String ipAddr = req.getRemoteAddr();
		System.out.println(ipAddr);
		if(isIPDenied(ipAddr)) {
			chain.doFilter(req, res);
		}else {
            throw new ServletException("无权限访问此Web服务！");
		}
		
	}


	@Override
	public void destroy() {
		
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
