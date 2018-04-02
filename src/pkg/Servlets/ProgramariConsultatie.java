package pkg.Servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ProgramariConsultatie
 */

public class ProgramariConsultatie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public String validDate(Date data) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        System.out.println(cal.getTime());
		if(data.before(new Date())||data.after(cal.getTime()))
			return "{\"valid\":false,\"color\":\"\"}";
			else 
				if(DbOperations.hasProgramInThatDay(DateUtil.getSqlDateFromUtilDate(data), Long.valueOf(4)))
					return "{\"valid\":true,\"color\":\"green\"}";
				else
					return "{\"valid\":false,\"color\":\"\"}";
		
	}
    public ProgramariConsultatie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String metoda = request.getParameter("metoda");
		
		switch(metoda) {
		case "data":
			System.out.println(validDate(new Date(request.getParameter("data"))));
		
			response.getWriter().append(validDate(new Date(request.getParameter("data"))));
			break;
		case "detalii":
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
			break;
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
