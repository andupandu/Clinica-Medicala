package pkg.Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class AnuleazaProgramare
 */
public class AnuleazaProgramare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnuleazaProgramare() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String metoda=request.getParameter("verif");
		System.out.println(metoda);
		String pacient=request.getParameter("pacient");
		String medic=request.getParameter("medic");
		String msg=null;
		if(metoda!=null) {
			java.sql.Date data = null;
			System.out.println("dataaa"+request.getParameter("data"));
			try {
				data = DateUtil.getDateFromString(request.getParameter("data"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(metoda) {
			case "anulare":
				
				DbOperations.anuleazaConsultatie(pacient, medic, data);
				 msg="Programarea a fost anulata";
				//request.setAttribute("msg", msg);
				break;
			case "anularetotala":
				DbOperations.anuleazaToateConsultatiileDinZi(medic, data);
				 msg="Programarile au fost anulate";
			}
			
		}
		String dataFormatata=request.getParameter("data1");
		request.setAttribute("consultatii", DbOperations.getConsultatii(medic, dataFormatata));
		if(metoda!=null) {
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
		response.sendRedirect("AnulareProgramari.jsp");
		}else {
			request.getRequestDispatcher("AnulareProgramari.jsp").forward(request,response);
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
