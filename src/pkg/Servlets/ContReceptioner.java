package pkg.Servlets;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			DbOperations.insertReceptionerNou(receptioner);
			DbOperations.insertReceptionerUser(email, parola,DbOperations.getReceptioner(receptioner).getId().intValue());
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
