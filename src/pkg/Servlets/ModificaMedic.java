package pkg.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Medic;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ModificaMedic
 */
public class ModificaMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		String metoda=request.getParameter("verif");
		Medic medic=new Medic();
		String msg=null;
		if(metoda!=null) {
			switch(metoda) {
			case "delete":
				DbOperations.deleteMedic((String)request.getParameter("medicId"));
				msg="Operatiune reusita! Medicul selectat nu mai este disponibil";
				break;
			case "modif":
			{
				
				medic.setNume(request.getParameter("nume"));
				medic.setPrenume(request.getParameter("prenume"));
				medic.setTelefon(request.getParameter("telefon"));
				medic.setEmail(request.getParameter("email"));
				medic.setId(Long.valueOf(request.getParameter("medicId")));
				try {
					medic.setCodSpec(DbOperations.getCodSpecFromDenSpec(request.getParameter("spec")));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DbOperations.modifyMedic(medic);
				msg="Datele au fost modificate";
				break;
			}
			}
			
			response.getWriter().write(msg);
			response.getWriter().flush();
			response.getWriter().close();
			response.sendRedirect("InformatiiMedic.jsp");
		}else {
			String spec=request.getParameter("specialitateIntrodusa");
			System.out.println("aaaaaaaaaaaaaaaaaa"+request.getParameter("specialitateIntrodusa"));
			Long specialitateDeCautat=spec==null?0:Long.valueOf(spec);
			if(specialitateDeCautat==0) {
				request.setAttribute("medici", DbOperations.getListaMedici());
			}
			else {
				try {
					request.setAttribute("medici",DbOperations.getMedicFromCodSpec(specialitateDeCautat));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			request.getRequestDispatcher("InformatiiMedic.jsp").forward(request,response);
		}



		
		out.close();
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
