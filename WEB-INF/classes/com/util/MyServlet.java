package com.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
	location = "c:/temp",						// ������ �ӽ÷� ������ ���(�⺻�� ""), c:\temp ��ΰ� ������ ���ε尡 �ȵ�
	fileSizeThreshold = 1024*1024,		// ���ε�� ������ �ӽ÷� ������ ������� �ʰ� �޸𸮿��� ��Ʈ������ �ٷ� ���޵Ǵ� ũ��
	maxFileSize = 1024*1024*5,			// ���ε�� �ϳ��� ���� ũ��. �⺻ �뷮 ���� ����
	maxRequestSize = 1024*1024*10	// �� ��ü �뷮
)
public abstract class MyServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected Map<String, String> doFileUpload(Part p, String pathname) throws ServletException, IOException {
		Map<String, String> map = null;
		
		try {
			File f=new File(pathname);
			if(! f.exists()) { 
				f.mkdirs();
			}
			
			String bfilename=getbfilename(p);
			if(bfilename==null || bfilename.length()==0) return null;
			
			String fileExt = bfilename.substring(bfilename.lastIndexOf("."));
			String afilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
				 Calendar.getInstance());
			afilename += System.nanoTime();
			afilename += fileExt;
			
			String fullpath = pathname+File.separator+afilename;
			p.write(fullpath);
			
			map = new HashMap<>();
			map.put("bfilename", bfilename);
			map.put("afilename", afilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	protected Map<String, String[]> doFileUpload(Collection<Part> parts, String pathname) throws ServletException, IOException {
		Map<String, String[]> map = null;
		try {
			File f=new File(pathname);
			if(! f.exists()) { // ������ �������� ������
				f.mkdirs();
			}
			
			String original, save, ext;
			List<String> listOriginal=new ArrayList<String>();
			List<String> listSave=new ArrayList<String>();
			
			for(Part p : parts) {
				String contentType = p.getContentType();

				if(contentType != null) { 
					original = getbfilename(p);
					if(original == null || original.length() == 0 ) continue;
					
					ext = original.substring(original.lastIndexOf("."));
					save = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
						 Calendar.getInstance());
					save += System.nanoTime();
					save += ext;
					
					String fullpath = pathname+File.separator+save;
					p.write(fullpath);
					
					listOriginal.add(original);
					listSave.add(save);
					
				}
			}		
			
			if(listOriginal.size() != 0) {
				String [] originals = listOriginal.toArray(new String[listOriginal.size()]);
				String [] saves = listSave.toArray(new String[listSave.size()]);
				
				map = new HashMap<>();
				
				map.put("bfilename", originals);
				map.put("afilename", saves);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private String getbfilename(Part p) {
		try {
			for(String s: p.getHeader("content-disposition").split(";")) {
				if(s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=")+1).trim().replace("\"","");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
