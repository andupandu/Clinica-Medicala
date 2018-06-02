package pkg.Servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

/**
 * Servlet implementation class FinalizareProgCons
 */
public class FinalizareProgCons extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizareProgCons() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String metoda=request.getParameter("verif");
		System.out.println("Metoda:"+metoda);
		String pacient=request.getParameter("pacient");
		String idMedic=request.getParameter("medic");
		System.out.println("IDMEDIC"+idMedic);
		String msg=null;
		String cnp=request.getParameter("cnp");
		if(metoda!=null) {
			java.sql.Date data = null;
			System.out.println("dataaa"+request.getParameter("data"));
			
			try {
				data = DateUtil.getDateFromString(request.getParameter("data"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(metoda) {
			case "anulare":
				
				DbOperations.changeConsultatieStatus(pacient, idMedic, data,"finalizat");
				 msg="Programarea a fost finalizata";
					response.getWriter().write(msg);
					response.getWriter().flush();
					response.getWriter().close();
				break;
			}	
		}
		else {
		String dataFormatata=request.getParameter("data1");
		System.out.println(dataFormatata);		
		request.setAttribute("consultatii", DbOperations.getConsultatii(idMedic, dataFormatata,cnp));
		request.getRequestDispatcher("StatusProgCons.jsp").forward(request,response);
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
