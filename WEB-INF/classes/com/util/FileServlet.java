package com.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@MultipartConfig(
		location = "c:/temp",					
		fileSizeThreshold = 1024*1024,		
		maxFileSize = 1024*1024*5,			
		maxRequestSize = 1024*1024*10
	)
public abstract class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected Map<String, String> fileUpload(Part p, String pathname) throws ServletException, IOException {
		Map<String, String> map = null;

		try {
			File f = new File(pathname);
			if (!f.exists()) {
				f.mkdirs();
			}
			String ogFilename = getogFilename(p);
			if (ogFilename == null || ogFilename.length() == 0) {
					return null;
			}
			
			String fileExt = ogFilename.substring(ogFilename.lastIndexOf("."));
			String fileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
			fileName += System.nanoTime();
			fileName += fileExt;
			
			String fullpath = pathname+File.separator+fileName;
			p.write(fullpath);
			
			map = new HashMap<String, String>();
			map.put("ogFilename", ogFilename);
			map.put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private String getogFilename(Part p) {
		try {
			for (String s : p.getHeader("content-disposition").split(";")) {
				if (s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=") + 1).trim().replace("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}