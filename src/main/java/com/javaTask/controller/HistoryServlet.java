package main.java.com.javaTask.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.javaTask.exceptions.NoTimeException;
import main.java.com.javaTask.service.ProductService;
import main.java.com.javaTask.service.impl.ProductServiceImpl;
import main.java.com.javaTask.utilities.ProductTO;

/**
 * Servlet implementation class HistoryServlet
 */
//@WebServlet(name="/HistoryServlet", urlPatterns="/history")
public class HistoryServlet extends HttpServlet {
	
	private static final Logger LOG = Logger.getLogger(HistoryServlet.class.getName());
	private static ProductService productService = new ProductServiceImpl();
	private String from = null;
	private String till = null;
	private long timeFrom = 0l;
	private long timeTill = 0l;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String userId = req.getParameter("userid");
		String cartId = req.getParameter("cartid");
		from = req.getParameter("from");
		till = req.getParameter("till");
		
		try {
			convertToMilliseconds();
		} catch (NoTimeException e) {
			LOG.info("Exception occured in convertToMilliseconds() within HistoryServlet");
			
			req.setAttribute("cartId", cartId);
			req.setAttribute("userId", userId);
			RequestDispatcher view = req.getRequestDispatcher("jsp/success.jsp");
			view.forward(req, resp);
		}
		
		List<ProductTO> results = productService.getProductsHistoryByTimeAndUserId(Integer.valueOf(userId), timeFrom, timeTill);
		
		req.setAttribute("results", results);
		req.setAttribute("userId", userId);
		req.setAttribute("cartId", cartId);
		
		if (userId == null || cartId == null || results == null) {
			resp.setStatus(400);
		}

		RequestDispatcher view = req.getRequestDispatcher("jsp/history.jsp");
		view.forward(req, resp);
	}
	
	private void convertToMilliseconds() throws NoTimeException {
		if (from.isEmpty() || till.isEmpty()) {
			throw new NoTimeException("Dates were not sent to HistoryServlet");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateFrom = from + " 00:00:00";
		LOG.info(dateFrom);
		String dateTill = till + " 23:59:59";
		LOG.info(dateTill);

		try {
			timeFrom = sdf.parse(dateFrom).getTime();
			LOG.info("Date from: " + timeFrom);
			timeTill = sdf.parse(dateTill).getTime();
			LOG.info("Date till: " + timeTill);
		} catch (ParseException e1) {
			LOG.info("An exception occured when converting date from string to long timestamp");
			e1.printStackTrace();
		}
	}
}
