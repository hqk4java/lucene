package com.bluedon.analyse.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.tika.exception.TikaException;
import org.springframework.web.util.HtmlUtils;

import com.bluedon.analyse.biz.search.Page;
import com.bluedon.analyse.model.Doc;
import com.bluedon.analyse.service.AnalServiceImpl;
import com.bluedon.analyse.service.IAnalService;
import com.bluedon.analyse.util.StringUtil;
import com.bluedon.analyse.vo.DocVo;




public class AnalyseSearchServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page page;
	/**
	 * Constructor of the object.
	 */
	public AnalyseSearchServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is request");
//		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String content=request.getParameter("contentStr");
		int page_cur = 0;
		int page_size= 10;
		
		try {
			page_cur = Integer.valueOf(request.getParameter("page_curPage"));
			page_size= request.getParameter("page_size")!=null?Integer.valueOf(request.getParameter("page_size")):10;
			//设置分页边界
			if(page_cur<0)
				page_cur = 0;
			if(page_size<0 || page_size > 30)
				page_size = 10;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String returnUrl = request.getParameter("returnUrl");
		returnUrl = null!=returnUrl ? returnUrl:"";
		//跨站脚本过滤验证 \b表示边界 \B表示非边界
//		String[] regx = {"(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(frame|<frame|iframe|<iframe|img|<img|JavaScript|<javascript|script|<script|<style|alert|select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|base64|Content-Type|Content-Transfer-Encoding|onmouseover|Function|=)\\b)",
//				"(\\B(frame|<frame|iframe|<iframe|img|<img|JavaScript|<javascript|script|<script|<style|style|alert|select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|base64|Content-Type|Content-Transfer-Encoding|onmouseover|Function|=)\\B)"};
//		
//		for ( String str : regx) {
//			
//			Pattern pattern = Pattern.compile(str);
//			Matcher matcher = pattern.matcher(cent);
//			if(matcher.find()) {//存在恶意脚本，则不查询数据
//				content = "";
//			}
//		}
		

		
		IAnalService as=new AnalServiceImpl();
		Page page=new Page(page_cur,page_size);
		try {
			List<DocVo> showList=new ArrayList<DocVo>();
			List<Doc> list;
			
			if(null == content || "".equals(content))
				list=new ArrayList<Doc>();
			else {
				String badStr = "\"|'|*|%|;|--|+|//|/|\\|#|<|>|&|=|(|)|{|}";
//				String cent = content.toLowerCase();
				//过滤掉的sql关键字，可以手动添加
				String[] badStrs = badStr.split("\\|");
				for (int i = 0; i < badStrs.length; i++) {
				    //循环检测，判断在请求参数当中是否包含SQL关键字
				    if (content.indexOf(badStrs[i]) >= 0) {
				    	//value = value.replaceAll(badStrs[i],"").trim();
				    	content=content.replace(badStrs[i], "");
				    }
				}
				System.out.println(content.trim());
				list=as.search(content.trim(),page);
			}
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i=0;i<list.size();i++){
				DocVo dv=new DocVo();
				dv.setDoc(list.get(i));
				dv.setCreate_date(df.format(new Date(list.get(i).getDate())));
				showList.add(dv);
			}
			request.setAttribute("curPage",page_cur);//当前页
			request.setAttribute("list",showList);//数据列表
			request.setAttribute("flag",1);//用来控制展示数据的列表
			request.setAttribute("content",content);//回显
			request.setAttribute("totalPages",page.getTotalPages());//总页数
			request.setAttribute("total",page.getTotal());//总记录数
//			request.setAttribute("returnUrl",returnUrl);//返回请求地址
			
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	
    
}
