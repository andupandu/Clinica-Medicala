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
		String medic=request.getParameter("medic");
		String data=request.getParameter("data1");
		request.setAttribute("consultatii", DbOperations.getConsultatii(medic, data));
		request.getRequestDispatcher("AnulareProgramari.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
