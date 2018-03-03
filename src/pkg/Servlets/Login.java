package pkg.Servlets;

import pkg.Utils.*;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//PrintWriter msj=response.getWriter();
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		if(DbOperations.isAccountInDB(email)!=null) {
		if(DbOperations.isAccountInDB(email).equals("pacient")) {
		
			request.getSession().putValue("persoanaLogata",DbOperations.getPacient(email));
			request.getRequestDispatcher("Login.jsp").forward(request,response);
		}
		if(DbOperations.isAccountInDB(email).equals("medic")) {
			
			request.getSession().putValue("persoanaLogata",DbOperations.getMedic(email));
			request.getRequestDispatcher("Login.jsp").forward(request,response);
		}
		if(DbOperations.isAccountInDB(email).equals("admin")) {
			
			//request.getSession().putValue("persoanaLogata",DbOperations.getNumePrenumePacient(email));
			 request.getRequestDispatcher("indexAdmin.jsp").forward(request,response);
		}
		}
		else { 
		request.setAttribute("msg","Nu s a gasit userul in db");
		request.getRequestDispatcher("Login.jsp").forward(request,response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
