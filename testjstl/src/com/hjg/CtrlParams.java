package com.hjg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CtrlParams
 */
@WebServlet("/CtrlParams")
public class CtrlParams extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CtrlParams() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//包装数据
		List<String> list =new ArrayList<String>();
		list.add("hello1");
		list.add("hello2");
		list.add("hello3");
		list.add("hello4");
		request.setAttribute("list", list);
		request.setAttribute("hello", "world");
		String   url="/param.jsp";   
		ServletContext   sc   =   getServletContext();   
		RequestDispatcher   rd   =   sc.getRequestDispatcher(url);   
		rd.forward(request,   response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
