package pkg.Servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import pkg.Entities.Persoana;
import pkg.Entities.ProgramareAnaliza;

import pkg.Utils.CreatePDF;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class CreazaPDFRezultatAnaliza
 */
public class CreazaPDFRezultatAnaliza extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreazaPDFRezultatAnaliza() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPacient=(String)request.getSession().getValue("idPacient");
		System.out.println(idPacient);
		String codBuletin=request.getParameter("buletin");
		List<String> categoriiAnalize=DbOperations.getCategoriiAnalizeBuletin(codBuletin, idPacient);
		if(idPacient==""||idPacient==null) {
			String msg="Autentificati-va pentru a avea acces la istoricul medical al dvs!";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("GenerareRezultatAnaliza.jsp").forward(request,response);
		}else if(categoriiAnalize.isEmpty()) {
			String msg="Cod buletin gresit!";
			request.setAttribute("msj", msg);
			request.getRequestDispatcher("GenerareRezultatAnaliza.jsp").forward(request,response);	
		}
		else {
			try {
				Persoana pacient=DbOperations.getPacientFromCod(idPacient);
				CreatePDF.createPdfRezultatAnalize(pacient,codBuletin,categoriiAnalize);
				File pdfFile = new File("C:\\Users\\Andreea\\eclipse-workspace\\ClinicaMedicala\\WebContent\\resources\\RezultatAnalize.pdf");

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "inline; filename=RezultatAnalize.pdf");
				response.setContentLength((int) pdfFile.length());

				FileInputStream fileInputStream = new FileInputStream(pdfFile);
				OutputStream responseOutputStream = response.getOutputStream();
				int bytes;
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes);
				}
				
				fileInputStream.close();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				request.setAttribute("msg","Eroare la generarea PDF-ului");
				request.getRequestDispatcher("GenerareRezultatAnaliza.jsp").forward(request,response);

			}
			
			
		
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
