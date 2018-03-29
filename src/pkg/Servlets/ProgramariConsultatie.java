package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ProgramariConsultatie
 */
public class ProgramariConsultatie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProgramariConsultatie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String cnp=request.getParameter("cnp");
		if(DbOperations.cautaPacientDupaCNP(cnp)==null){
		response.getWriter().append("{\"valid\":\"false\"}");
		} 
		else {
			response.getWriter().append("{\"valid\":true,"
					+ "\"nume\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getNume() +"\""
					+ ",\"prenume\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getPrenume() +"\""
					+ ",\"id\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getId() +"\""
					+ "}");
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
