package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Indicator;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class AdaugaIndicator
 */
public class AdaugaIndicator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdaugaIndicator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String intervalRefMin=request.getParameter("min");
		String intervalRefMax=request.getParameter("max");
		String tipPers=request.getParameter("tipPers");
		String sex=request.getParameter("sex");
		String codAnaliza=request.getParameter("analiza");
		Indicator indicator=new Indicator();
		indicator.setIntervalRefMax(intervalRefMax);
		indicator.setIntervalRefMin(intervalRefMin);
		indicator.setSex(sex);
		indicator.setTipPersoana(tipPers);
		indicator.setAnalizacod(codAnaliza);
		DbOperations.insertIndicator(indicator);
		request.setAttribute("msg", "Indicatorul a fost adaugat cu succes!");
		
		request.getRequestDispatcher("AdaugaIndicator.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
