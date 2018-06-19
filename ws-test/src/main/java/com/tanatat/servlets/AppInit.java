package com.tanatat.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tanatat.util.DBUtil;

@WebServlet("/AppInit")
public class AppInit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		String prefix = config.getServletContext().getRealPath("/");
		String file = config.getInitParameter("log4j-init-file");
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
			Logger log = Logger.getLogger(AppInit.class);
			log.info("========== ws-yourself running ==========");
			DBUtil.init();
		}
	}
}
