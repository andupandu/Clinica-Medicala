package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class DateContReceptioner
 */
public class DateCont extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DateCont() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String parola=request.getParameter("parolanoua");
	String email=request.getParameter("email");
	Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
	String tipUser=(String)request.getSession().getValue("tipUser");
	System.out.println("email "+email);
	Long userCod=DbOperations.getUserCodFromPassword(user,tipUser);
	if(parola!="" && parola!=null){
		if(userCod!=null) {
		DbOperations.modifyUserParola(userCod, parola,tipUser);
		request.setAttribute("msg", "Parola a fost modificata cu succes");
		}
		else {
			request.setAttribute("msg", "Parola gresita! Nu exista utilizator cu userul si parola aceasta.");
		}
	}else if(email!=null) {
		DbOperations.modifyUserEmailInCont(userCod,tipUser,email);
		if(tipUser=="medic") {
			DbOperations.changeMedicEmail(email, userCod);
		}
		request.setAttribute("msg", "Emailul a fost modificat cu succes");

	}
	request.getRequestDispatcher("DateCont.jsp").forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
