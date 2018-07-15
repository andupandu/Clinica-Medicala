package pkg.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.Entities.Categorie;
import pkg.Entities.Specialitate;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class CategoriiAnalize
 */
public class CategoriiAnalize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriiAnalize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation=request.getParameter("verif");
		System.out.println(operation);
		String msg=null;
		if(operation.equals("add")) {
			DbOperations.insertCategorieNoua(request.getParameter("categorieNoua"));
			msg="Categoria s-a adaugat cu succes!";
		}
		else {
			Categorie categorie=new Categorie();
			categorie.setCod(Long.valueOf(request.getParameter("categorieId")));
			categorie.setDenumire(request.getParameter("categorie"));
			DbOperations.modifyCategorie(categorie);
			msg="Categoria s-a modificat cu succes!";
		}
		if(!operation.equals("add")) {
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
		}
		else {
			request.setAttribute("msjInserareCategorie", msg);
			request.getRequestDispatcher("CategoriiAnalize.jsp").forward(request,response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
