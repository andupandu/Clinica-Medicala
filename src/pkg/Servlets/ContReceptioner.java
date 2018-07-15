package pkg.Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import pkg.Entities.Medic;
import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

/**
 * Servlet implementation class ContReceptioner
 */
public class ContReceptioner extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContReceptioner() {
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
		String parola=SMTPHelper.generatePassword();
		Persoana receptioner=new Persoana();
		receptioner.setNume(nume);
		receptioner.setPrenume(prenume);
		receptioner.setEmail(email);
		if(DbOperations.isAccountInDB(email,null)!=null)
			request.setAttribute("msg", "Exista deja un cont cu acest email");
		else {
			try {
			DbOperations.insertReceptionerNou(receptioner);
			DbOperations.insertReceptionerUser(email, parola,DbOperations.getReceptioner(receptioner).getId().intValue());
			DbOperations.insertCodContIntoReceptioner(DbOperations.getReceptioner(receptioner).getId().intValue(), Long.valueOf(DbOperations.getCodCont(email)));
			String continut="Buna ziua,\n" + 
					"Multumim ca v ati inregistrat pe site ul nostru.\n" + 
					"Datele dumneavoastra de logare sunt urmatoarele:\n" + 
					"Username: " + email+ "\n" + 
					"Parola:"+parola;
				SMTPHelper.SendEmail(Arrays.asList(email),continut,"Bun venit!");
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("msg", "Contul s-a creat cu succes");
		}
		
		request.getRequestDispatcher("ContReceptioner.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
