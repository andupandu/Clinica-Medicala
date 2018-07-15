package pkg.Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Consultatie;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class VizualizareProgramari
 */
public class VizualizareProgramari extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VizualizareProgramari() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataInceput=request.getParameter("dataInceput");
		String dataSfarsit=request.getParameter("dataSfarsit");
		System.out.println(dataInceput +""+dataSfarsit);
		List<Consultatie> consultatii=DbOperations.getConsultatiiInFunctieDeData(dataInceput, dataSfarsit);
		request.setAttribute("consultatii", consultatii);
		request.getRequestDispatcher("VizualizareProgramari.jsp").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
