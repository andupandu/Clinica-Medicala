package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Specialitate;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class EditeazaSpecialitati
 */
public class EditeazaSpecialitati extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditeazaSpecialitati() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation=request.getParameter("verif");
		System.out.println(operation);
		String msg=null;
		if(operation.equals("add")) {
			DbOperations.insertNewSpecializare(request.getParameter("specNoua"));
			msg="Specialitatea s a adaugat cu succes!";
		}else if(operation.equals("delete")) {
			DbOperations.deleteSpecialitate(request.getParameter("specId"));
			msg="Specialitatea s a sters cu succes!";
		}
		else {
			Specialitate spec=new Specialitate();
			spec.setCod(Long.valueOf(request.getParameter("specId")));
			spec.setDenumire(request.getParameter("spec"));
			DbOperations.modifySpecialitate(spec);
			msg="Specialitatea s a modificat cu succes!";
		}

		//request.getRequestDispatcher("InformatiiSpecialitati.jsp").forward(request,response);
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
		response.sendRedirect("InformatiiSpecialitati.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}