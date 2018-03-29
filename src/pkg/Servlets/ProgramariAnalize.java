package pkg.Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ProgramariAnalize
 */
public class ProgramariAnalize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProgramariAnalize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		List<String> analize=Arrays.asList(request.getParameterValues("analize"));
		String nume=request.getParameter("nume");
		String prenume=request.getParameter("prenume");
		String telefon=request.getParameter("telefon");
		String email=request.getParameter("email");
		String cnpnou=request.getParameter("cnpNou");
		String ora=request.getParameter("ora");
		String data=request.getParameter("data");
		String dataNasterii=request.getParameter("dataNasterii");
		System.out.println(request.getParameter("pacient"));
		for(String codAnaliza:analize) {
			try { 
				if(request.getParameter("pacient")=="") {
				Persoana pacientNou=new Persoana();
				pacientNou.setCnp(cnpnou);
				pacientNou.setEmail(email);
				pacientNou.setNume(nume);
				pacientNou.setPrenume(prenume);
				pacientNou.setTelefon(telefon);
				pacientNou.setData_nastere( DateUtil.getDateFromString(dataNasterii));
				DbOperations.insertPacient(pacientNou);
				DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", DbOperations.cautaPacientDupaCNP(cnpnou).getId(), Long.valueOf(codAnaliza));

				
			}else {	
				Long codPacient=Long.valueOf(request.getParameter("pacientcod"));
				DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", codPacient, Long.valueOf(codAnaliza));
			} }catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("InformatiiAnalize.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
