package pkg.Servlets;
import pkg.Entities.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;

import pkg.Utils.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * Servlet implementation class ContNou
 */
public class ContNou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContNou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
		String nume=request.getParameter("nume");
		String prenume=request.getParameter("prenume");
		String telefon=request.getParameter("telefon");
		String cnp=request.getParameter("cnp");
		String dataNasterii=request.getParameter("dataNasterii");
		String parola=SMTPHelper.generatePassword();
		Persoana pacient=new Persoana();
		pacient.setNume(nume);
		pacient.setPrenume(prenume);
		pacient.setCnp(cnp);
		try {
			pacient.setData_nastere(DateUtil.getDateFromString(dataNasterii));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		pacient.setEmail(email);
		pacient.setTelefon(telefon);
		
		try { if(DbOperations.isAccountInDB(email,null)!=null)
			request.setAttribute("msg", "Exista deja un cont cu acest email");
		else {
			try {
				DbOperations.insertPacient(pacient);
			} catch (SQLException e) {
				request.setAttribute("msg", "Cnp deja existent");
				e.printStackTrace();
			}
			DbOperations.insertPacientUser(email, parola,DbOperations.getPacient(email).getId().intValue());
			DbOperations.insertCodContIntoPacient(email, Long.valueOf(DbOperations.getCodCont(email)));
			String subiect="Bun venit";
			String continut="Buna ziua,\n" + 
					"Multumim ca v ati inregistrat pe site ul nostru.\n" + 
					"Datele dumneavoastra de logare sunt urmatoarele:\n" + 
					"Username: " + email+ "\n" + 
					"Parola:"+parola;
			SMTPHelper.SendEmail(Arrays.asList(email),continut,subiect);
			request.setAttribute("msg", "Contul s a creat cu succes");
		}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("ContNou.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
