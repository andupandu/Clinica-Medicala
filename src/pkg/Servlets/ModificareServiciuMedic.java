package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Persoana;
import pkg.Entities.Serviciu;
import pkg.Entities.Specialitate;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ModificareServiciuMedic
 */
public class ModificareServiciuMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificareServiciuMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation=request.getParameter("verif");
		String serviciuCod=request.getParameter("servId");
		String timp=request.getParameter("timp");
		String pret=request.getParameter("pret");
		Persoana user=(Persoana) request.getSession().getValue("persoanaLogata");
		String tipUser=(String)request.getSession().getValue("tipUser");
		 String idMedic=DbOperations.getUserCodFromPassword(user,tipUser).toString();
		System.out.println(operation);
		String msg=null;
		 if(operation.equals("modify")) {
			Serviciu serviciu=new Serviciu();
			serviciu.setCod(Long.valueOf(serviciuCod));
			serviciu.setTimp(timp);
			serviciu.setPret(Long.valueOf(pret));
			
			DbOperations.modifyServiciu(serviciu,idMedic);
			msg="Serviciul s-a modificat cu succes!";
		}
		
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
