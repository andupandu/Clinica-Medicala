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
 * Servlet implementation class ContMedic
 */
public class ContMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContMedic() {
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
		String dataNasterii=request.getParameter("data1");
		String codSpec=request.getParameter("spec");
		String parola=SMTPHelper.generatePassword();
		Medic medic=new Medic();
		medic.setNume(nume);
		medic.setPrenume(prenume);
		medic.setEmail(email);
		medic.setTelefon(telefon);
		medic.setCodSpec(Long.valueOf(codSpec));
		try {
			medic.setData_nastere(DateUtil.getDateFromString(dataNasterii));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
		if(nume=="" ||prenume==""||telefon==""||email==""||dataNasterii=="") {
			request.setAttribute("msg", "Toate campurile sunt obligatorii");
		}else {
		if(DbOperations.isAccountInDB(email,null)!=null)
			request.setAttribute("msg", "Exista deja un cont cu acest email");
		else {
			DbOperations.insertMedicNou(medic);
			DbOperations.insertMedicUser(email, parola,DbOperations.getMedic(email).getId().intValue());
			DbOperations.insertCodContIntoMedic(email, Long.valueOf(DbOperations.getCodCont(email)));
			String continut="Buna ziua,\n" + 
					"Multumim ca v ati inregistrat pe site ul nostru.\n" + 
					"Datele dumneavoastra de logare sunt urmatoarele:\n" + 
					"Username: " + email+ "\n" + 
					"Parola:"+parola;
			try {
				SMTPHelper.SendEmail(Arrays.asList(email),continut,"Bun venit!");
			} catch (JAXBException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("msg", "Contul s-a creat cu succes");
		}
		}
		request.getRequestDispatcher("ContMedic.jsp").forward(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
