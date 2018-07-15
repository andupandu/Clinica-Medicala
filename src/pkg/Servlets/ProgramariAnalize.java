package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
    public List<String> getOreDisp(List<String> oreIndisponibile){
    	List<String> oreLucru = Arrays.asList("08:00", "09:00", "10:00", "11:00", "12:00","13:00","14:00","15:00","16:00","17:00");
    	List<String> oreDisp=new ArrayList<String>();
    	for(String oraLucru:oreLucru) {
    		if(!oreIndisponibile.contains(oraLucru)) {
    			oreDisp.add(oraLucru);
    		}
    	}
    	return oreDisp;

    }
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
		case "ore":
			String date=request.getParameter("dataAnalize");
			
			try { List<String> oreDisp=getOreDisp(DbOperations.getOreIndisponibileAnaliza(date));
				if(oreDisp.isEmpty()) {
					response.getWriter().append(null);
				}else {
					
				}
				String json = new Gson().toJson(oreDisp);
				response.getWriter().append(json);
			} catch (Exception e) {
				System.out.println("Exceptie");
				e.printStackTrace();
			}
			break;
		}
		}else {
			List<String> analize=new ArrayList<String>();
			String verif=request.getParameter("analiza");
			if(verif!=null) {
			analize=Arrays.asList(request.getParameter("ListaAnalize").split("\\s*,\\s*"));
			}
			else {
				analize=Arrays.asList(request.getParameterValues("analize"));
			}
		 
		String nume=request.getParameter("nume");
		String prenume=request.getParameter("prenume");
		String telefon=request.getParameter("telefon");
		String email=request.getParameter("email");
		String cnpnou=request.getParameter("cnpNou");
		String ora=request.getParameter("ora");
		String data=request.getParameter("dataprog");
		String dataNasterii=request.getParameter("data1");
		System.out.println(request.getParameter("pacient"));
		Long codPacient=null;
		List<Consultatie> consultatii=new ArrayList<Consultatie>();
		if(verif==null && request.getParameter("pacient")=="") {
			try {Persoana pacientNou=new Persoana();
			pacientNou.setCnp(cnpnou);
			pacientNou.setEmail(email);
			pacientNou.setNume(nume);
			pacientNou.setPrenume(prenume);
			pacientNou.setTelefon(telefon);
			pacientNou.setData_nastere( DateUtil.getDateFromString(dataNasterii));
				DbOperations.insertPacient(pacientNou);
				DbOperations.insertPacientUser(email, SMTPHelper.generatePassword(),DbOperations.getPacient(email).getId().intValue());
				DbOperations.insertCodContIntoPacient(email, Long.valueOf(DbOperations.getCodCont(email)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			
			if(verif==null) {
				codPacient=Long.valueOf(request.getParameter("pacientcod"));
			}else {
				codPacient=Long.valueOf(request.getSession().getValue("idPacient").toString());
			}
			consultatii=DbOperations.cautaProgramareAnaliza(codPacient, analize);
			if(!consultatii.isEmpty()) {
				String msg="Pacientul are deja o programare pentru analiza/analizele: \\n";
				for(Consultatie cons:consultatii) {
					msg+=cons.getTipConsutatie()+",la data:"+cons.getData()+",ora:"+cons.getOraInceput()+"\\n"+"";
				}
				msg+="Daca ati selectat mai multe analize,incercati din nou ,programandu-va doar pentru analizele unde nu aveti deja o programare!";
				
				request.setAttribute("msg", msg);
				if(verif==null) {
					request.getRequestDispatcher("InformatiiAnalize.jsp").forward(request,response);
				}else {
					request.getRequestDispatcher("AfiseazaAnalize.jsp").forward(request,response);
				}
			}else {
				Long maxBuletin=DbOperations.getMaxBuletin();
				for(String codAnaliza:analize) {
					try { 
						if(verif==null && request.getParameter("pacient")=="") {
							DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", DbOperations.cautaPacientDupaCNP(cnpnou).getId(), Long.valueOf(codAnaliza),maxBuletin);
							request.setAttribute("msg", "Programarea a fost adaugata cu succes");
						}else {

							DbOperations.insertAnalize(ora, DateUtil.getDateFromString(data), "in curs", codPacient, Long.valueOf(codAnaliza),maxBuletin);
							request.setAttribute("msg", "Programarea a fost adaugata cu succes");

						} 
					}catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(verif==null) {
					request.getRequestDispatcher("InformatiiAnalize.jsp").forward(request,response);
				}else {
					request.getRequestDispatcher("AfiseazaAnalize.jsp").forward(request,response);
				}
			}
		}
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
