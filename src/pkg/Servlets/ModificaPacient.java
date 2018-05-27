package pkg.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Medic;
import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ModificaPacient
 */
public class ModificaPacient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaPacient() {
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
		String metoda=request.getParameter("verif");
		String msg=null;
		if(metoda!=null) {
			switch (metoda) {
			case "modif": 
				Persoana pacient=new Persoana();
				pacient.setNume(request.getParameter("nume"));
				pacient.setPrenume(request.getParameter("prenume"));
				pacient.setTelefon(request.getParameter("telefon"));
				pacient.setEmail(request.getParameter("email"));
				pacient.setId(Long.valueOf(request.getParameter("pacientId")));
				pacient.setCnp(request.getParameter("cnp"));
				try {
					pacient.setData_nastere(DateUtil.getDateFromString(request.getParameter("dataNasterii")));
				} catch (ParseException e) {

					e.printStackTrace();
				}
				DbOperations.modifyPacient(pacient);
				DbOperations.modifyEmailInCont(pacient);
				msg="Datele au fost modificate";
				break;
			}
			response.getWriter().write(msg);
			response.getWriter().flush();
			response.getWriter().close();
			response.sendRedirect("InformatiiPacient.jsp");
		}
			else {
				String cnpDeCautat=request.getParameter("cnpintrodus");
				if(cnpDeCautat==null||cnpDeCautat=="") {
					try {
						request.setAttribute("pacienti", DbOperations.getPacienti());
					} catch (SQLException e) {

						e.printStackTrace();
					}
				}
				else {
					request.setAttribute("pacienti", Arrays.asList(DbOperations.cautaPacientDupaCNP(cnpDeCautat)));
				}
				request.getRequestDispatcher("InformatiiPacient.jsp").forward(request,response);
			}
		
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
