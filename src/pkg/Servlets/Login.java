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
		System.out.println(email+" "+password);
		String user=DbOperations.isAccountInDB(email,password);
		if(user!=null) {
			switch(user) {
			case "pacient":{

				request.getSession().putValue("persoanaLogata",DbOperations.getPacient(email));
				request.getSession().putValue("tipUser","pacient");	
				request.getRequestDispatcher("index.jsp").forward(request,response);
				break;

			}
			case "medic":{

				request.getSession().putValue("persoanaLogata",DbOperations.getMedic(email));
				request.getSession().putValue("tipUser","medic");
				request.getRequestDispatcher("indexMedic.jsp").forward(request,response);
				break;
			}
			case "admin":{
				request.getSession().putValue("tipUser","admin");
				request.getRequestDispatcher("indexAdmin.jsp").forward(request,response);
				break;
			}
			case "receptioner": {
				request.getSession().putValue("persoanaLogata",DbOperations.getReceptioner(email));
				request.getSession().putValue("tipUser","receptioner");
				request.getRequestDispatcher("indexReceptioner.jsp").forward(request,response);
				break;
			}
		}
		}
		else { 
		request.setAttribute("msg","Email/Parola gresita !");
		request.getRequestDispatcher("index.jsp").forward(request,response);
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
