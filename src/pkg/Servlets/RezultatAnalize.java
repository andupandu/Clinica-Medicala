package pkg.Servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import pkg.Entities.*;
import pkg.Utils.*;


/**
 * Servlet implementation class RezultatAnalize
 */
public class RezultatAnalize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RezultatAnalize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String metoda = request.getParameter("metoda");
    	if(metoda!=null) {
    		switch(metoda) {
    		case "detalii":
    			String cnpCautat=request.getParameter("cnp");
    			System.out.println(cnpCautat);
    			Persoana pacient=DbOperations.cautaPacientDupaCNP(cnpCautat);
    			if(pacient.getId()==null){
    				response.getWriter().append("{\"valid\":\"false\"}");
    			} 
    			else {
    				response.getWriter().append("{\"valid\":true,"
    						+ "\"nume\":\"" + pacient.getNume() +"\""
    						+ ",\"prenume\":\"" + pacient.getPrenume() +"\""
    						+ ",\"id\":\"" + pacient.getId() +"\""
    						+ ",\"analize\":" +new Gson().toJson( DbOperations.getAnalizeleInCursAlePacientului(pacient.getId()))
    						+ "}");
    			}
    			break;
    	}
    	}
    	else {
    		Rezultat rezultat=new Rezultat();
    		rezultat.setPacient(request.getParameter("pacientcod"));
    		rezultat.setAnaliza(request.getParameter("analize"));
    		rezultat.setInterpretare(request.getParameter("interpretare"));
    		rezultat.setValoare(request.getParameter("valoare"));
    		rezultat.setData(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    		DbOperations.insertRezultat(rezultat);
    		DbOperations.changeAnalizaStatus(rezultat.getPacient(), rezultat.getAnaliza(), "finalizat");
    		request.setAttribute("msg", "Rezultatul a fost adaugat cu succes.");
    		request.getRequestDispatcher("RezultateAnalize.jsp").forward(request,response);
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
