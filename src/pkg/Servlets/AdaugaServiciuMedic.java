package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Entities.Serviciu;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class AdaugaServiciuMedic
 */
public class AdaugaServiciuMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdaugaServiciuMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String denserviciu=request.getParameter("serviciu");
		String timp=request.getParameter("timp");
		String pret=request.getParameter("pret");
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		 String idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		
		try {
			if(!DbOperations.isServiciu(denserviciu)) {
				DbOperations.insertServiciuNou(denserviciu);
			}
			if(!DbOperations.mediculOferaServiciul(denserviciu, idMedic)) {
			DbOperations.alocaServiciuNou(denserviciu,timp,pret,idMedic);
			request.setAttribute("msg", "Serviciul a fost adaugat");
			}
			else {
				request.setAttribute("msg", "Oferiti deja acest serviciu!");
			}
			
		} catch (SQLException e) {
			System.out.println("Exceptie:"+e.getMessage());
			e.printStackTrace();
		}
		request.getRequestDispatcher("ServiciiNoiMedic.jsp").forward(request,response);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
