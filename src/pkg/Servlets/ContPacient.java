package pkg.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;
import pkg.Utils.SMTPHelper;

/**
 * Servlet implementation class ContPacient
 */
public class ContPacient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContPacient() {
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
		String dataNasterii=request.getParameter("data1");
		String parola=SMTPHelper.generatePassword();
		System.out.println("DATAAAAAA"+dataNasterii);
		Persoana pacient=new Persoana();
		pacient.setNume(nume);
		pacient.setPrenume(prenume);
		pacient.setCnp(cnp);
		pacient.setEmail(email);
		pacient.setTelefon(telefon);
		try {
			pacient.setData_nastere(DateUtil.getDateFromString(dataNasterii));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		if(nume=="" ||prenume==""||telefon==""||cnp==""||email==""||dataNasterii=="") {
			request.setAttribute("msg", "Toate campurile sunt obligatorii");
		}else {
		if(DbOperations.isAccountInDB(email)!=null)
			request.setAttribute("msg", "Exista deja un cont cu acest email");
		else {
			try {
				DbOperations.insertPacient(pacient);
				DbOperations.insertPacientUser(email, parola,DbOperations.getPacient(email).getId().intValue());
				DbOperations.insertCodContIntoPacient(email, Long.valueOf(DbOperations.getCodCont(email)));
				request.setAttribute("msg", "Contul s a creat cu succes");
			} catch (SQLException e) {
				System.out.println("Exceptie"+e.getMessage());
				request.setAttribute("msg", "CNP-ul exista deja in baza de date");
			}
		}
		}
		request.getRequestDispatcher("ContPacient.jsp").forward(request,response);
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
