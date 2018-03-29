package pkg.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Medic;
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
		if(request.getParameter("verif").equals("delete")){
			DbOperations.deleteMedic((String)request.getParameter("medicId"));
		}
		else if(request.getParameter("verif").equals("modif")) {
			Medic medic=new Medic();
			medic.setNume(request.getParameter("nume"));
			medic.setPrenume(request.getParameter("prenume"));
			medic.setTelefon(request.getParameter("telefon"));
			medic.setEmail(request.getParameter("email"));
			medic.setId(Long.valueOf(request.getParameter("medicId")));
			try {
				medic.setCodSpec(DbOperations.getCodSpecFromDenSpec(request.getParameter("spec")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbOperations.modifyMedic(medic);
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
