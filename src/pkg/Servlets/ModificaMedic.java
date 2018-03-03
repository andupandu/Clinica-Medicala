package pkg.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ModificaMedic
 */
public class ModificaMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		 response.setContentType("text/plain");
		System.out.println(request.getParameter("nume"));
		System.out.println(request.getParameter("prenume"));
		System.out.println(request.getParameter("medicId"));
		if(request.getParameter("verif").equals("delete")){
		DbOperations.deleteMedic(request.getParameter("medicId"));
		}
		else if(request.getParameter("verif").equals("modif")) {
			System.out.println("modificaaaa");
		}
		request.getRequestDispatcher("InformatiiMedic.jsp").forward(request,response);
	out.close();
		}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
