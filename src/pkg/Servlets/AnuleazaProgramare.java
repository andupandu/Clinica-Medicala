package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

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
		System.out.println("Metoda:"+metoda);
		String pacient=request.getParameter("pacient");
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		String idMedic=null;
		if(tipUser=="medic") {
			idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		}
		else {
		 idMedic=request.getParameter("medic");
		}
		System.out.println("IDMEDIC"+idMedic);
		String msg=null;
		String continut=null;
		if(metoda!=null) {
			java.sql.Date data = null;
			System.out.println("dataaa"+request.getParameter("data"));
			
			try {
				data = DateUtil.getDateFromString(request.getParameter("data"));
				 continut="Buna ziua,\n" + 
						"Va anuntam ca programarea din data de "+data+"la medicul "+DbOperations.getNumePrenumeMedic(Long.valueOf(idMedic))+" a fost anulata" + 
						"Va rugam sa reveniti cu un telefon la 07555555555 pentru a muta consultatia .\n" + 
						"Va multumim pentru intelegere!";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}try {
			switch(metoda) {
			case "anulare":
				
				DbOperations.changeConsultatieStatus(pacient, idMedic, data,"anulat");
				
				SMTPHelper.SendEmail(Arrays.asList(DbOperations.getPacientEmail(pacient)), continut, "Anulare consultatie");
				 msg="Programarea a fost anulata";
				//request.setAttribute("msg", msg);
				break;
			case "anularetotala":
				DbOperations.anuleazaToateConsultatiileDinZi(idMedic, data);
				SMTPHelper.SendEmail(DbOperations.getEmailuriPacienti(idMedic,data), continut, "Anulare consultatie");
				 msg="Programarile au fost anulate";
			}
			}catch(Exception e) {
				System.out.println("Exceptieee: "+e.getMessage());
				e.printStackTrace();
			}
			
		}
		String dataFormatata=request.getParameter("data1");
		System.out.println(dataFormatata);
		request.setAttribute("consultatii", DbOperations.getConsultatii(idMedic, dataFormatata));
		if(metoda!=null) {
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
	
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
