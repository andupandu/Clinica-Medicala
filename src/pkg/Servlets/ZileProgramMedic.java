package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import pkg.Entities.Persoana;
import pkg.Utils.*;
/**
 * Servlet implementation class ZileProgramMedic
 */
public class ZileProgramMedic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZileProgramMedic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String idMedic=(String)request.getSession().getValue("idMedic");
		 Map<Long,String> zile =  DbOperations.getZileNelucrateMedic(idMedic);
		 request.setAttribute("zile", zile);
		 request.setAttribute("msg", request.getAttribute("msg"));
		 request.getRequestDispatcher("AdaugaProgramMedic.jsp").forward(request,response);
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
