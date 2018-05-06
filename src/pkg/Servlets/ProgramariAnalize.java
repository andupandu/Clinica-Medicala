package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Consultatie;
import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

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
		
		
		String metoda = request.getParameter("metoda");
		System.out.println(metoda);
		if(metoda!=null) {
		switch(metoda) {
		case "detalii":
			String cnpCautat=request.getParameter("cnp");
			System.out.println("cnpppp"+cnpCautat);
			System.out.println(DbOperations.cautaPacientDupaCNP(cnpCautat).getId());
		if(DbOperations.cautaPacientDupaCNP(cnpCautat).getId()==null){
			
		response.getWriter().append("{\"valid\":\"false\"}");
		} 
		else {
			response.getWriter().append("{\"valid\":true,"
					+ "\"nume\":\"" + DbOperations.cautaPacientDupaCNP(cnpCautat).getNume() +"\""
					+ ",\"prenume\":\"" + DbOperations.cautaPacientDupaCNP(cnpCautat).getPrenume() +"\""
					+ ",\"id\":\"" + DbOperations.cautaPacientDupaCNP(cnpCautat).getId() +"\""
					+ "}");
		}
		break;
		}
		}else {
		List<String> analize=Arrays.asList(request.getParameterValues("analize"));
		String nume=request.getParameter("nume");
		String prenume=request.getParameter("prenume");
		String telefon=request.getParameter("telefon");
		String email=request.getParameter("email");
		String cnpnou=request.getParameter("cnpNou");
		String ora=request.getParameter("ora");
		String data=request.getParameter("dataprog");
		String dataNasterii=request.getParameter("data1");
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
				try {
					DbOperations.insertPacient(pacientNou);
					DbOperations.insertPacientUser(email, SMTPHelper.generatePassword(),DbOperations.getPacient(email).getId().intValue());
					DbOperations.insertCodContIntoPacient(email, Long.valueOf(DbOperations.getCodCont(email)));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", DbOperations.cautaPacientDupaCNP(cnpnou).getId(), Long.valueOf(codAnaliza));

				request.setAttribute("msg", "Programarea a fost adaugata cu succes");
			}else {	
				Long codPacient=Long.valueOf(request.getParameter("pacientcod"));
				List<Consultatie> consultatii=DbOperations.cautaProgramareAnaliza(codPacient, analize);
				if(consultatii.isEmpty()) {
				DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", codPacient, Long.valueOf(codAnaliza));
				request.setAttribute("msg", "Programarea a fost adaugata cu succes");
				}
				else {
					String msg="Pacientul are deja o programare pentru analiza/analizele: \\n";
					for(Consultatie cons:consultatii) {
						msg+=cons.getTipConsutatie()+",la data:"+cons.getData()+",ora:"+cons.getOraInceput()+"\\n"+"";
					}
					msg+="Daca a-ti selectat mai multe analize,incercati din nou ,programandu-va doar pentru analizele unde nu aveti deja o programare!";
					
					request.setAttribute("msg", msg);
				}
			} 
				
				}catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("InformatiiAnalize.jsp").forward(request,response);
		}
		
		//response.sendRedirect("InformatiiAnalize.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
