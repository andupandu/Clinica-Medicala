package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Analiza;
import pkg.Entities.Indicator;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class AdaugaAnalize
 */
public class AdaugaAnalize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdaugaAnalize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String denumire=request.getParameter("denumire");
		String pret=request.getParameter("pret");
		String durata=request.getParameter("durata");
		String tip=request.getParameter("tip");
		String categorie=request.getParameter("categorie");
		
		Analiza analiza=new Analiza();
		analiza.setDenumire(denumire);
		analiza.setDurata(durata);
		analiza.setPret(Long.valueOf(pret));
		analiza.setTip(tip);
		analiza.setCategorie(categorie);
		
		try {
			if(DbOperations.esteAnalizaInDb(denumire)) {
				request.setAttribute("msg", "Aceasta analiza exista in baza de date!");
			}else {
				DbOperations.insertAnaliza(analiza);
				request.setAttribute("msg", "Analiza a fost adaugata cu succes!");
			}
		} catch (SQLException e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("AdaugaAnalize.jsp").forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
