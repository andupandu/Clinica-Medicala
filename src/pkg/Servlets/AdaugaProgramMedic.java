package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class AdaugaProgramMedic
 */
public class AdaugaProgramMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdaugaProgramMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		 String idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		 String ziId=(String)request.getParameter("zi");
		 String oraInceput=(String)request.getParameter("orainceput");
		 String oraSfarsit=(String)request.getParameter("orasfarsit");
		 DbOperations.insertProgramLucru(idMedic, oraInceput, oraSfarsit, Long.valueOf(ziId));
		 request.setAttribute("msg", "Programul de lucru a fost adaugat!");
		 request.getRequestDispatcher("/ZileProgramMedic").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
