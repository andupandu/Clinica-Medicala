package pkg.Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pkg.Entities.Interval;
import pkg.Utils.DateUtil;
import pkg.Utils.DbOperations;

/**
 * Servlet implementation class ProgramariConsultatie
 */

public class ProgramariConsultatie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws ParseException 
     * @see HttpServlet#HttpServlet()
     */
	public String validDate(String data,String codMedic,String codServiciu) throws Exception {
		Date date=new Date(data);
		boolean hasFreeHours=false;

		if(DbOperations.hasProgramInThatDay(DateUtil.getSqlDateFromUtilDate(date), Long.valueOf(codMedic))) {

			if(!getOreDisp(codMedic, data, codServiciu).isEmpty())
				hasFreeHours=true;

			if(hasFreeHours)
				return "{\"valid\":true,\"color\":\"green\"}";
			else 
				return "{\"valid\":false,\"color\":\"red\"}";
		}

		else
			return "{\"valid\":false,\"color\":\"red\"}";

	}
	
	public String getStartingHour(String inceput,List<Interval> consultatii) throws ParseException {
		int i=0;
		
		while(i<consultatii.size() && DateUtil.getDateHourFromString(inceput).equals(DateUtil.getDateHourFromString(consultatii.get(i).getInceput()))){
			inceput=consultatii.get(i).getSfarsit();
			i++;
			
		}
		return inceput;
	}
	public List<String> getOreDisp(String codMedic,String data,String codServiciu) throws Exception{
		List<String> oreDisp=new ArrayList<String>();
		for(Interval ora:getListaIntervalePosibile(codMedic, data))
			oreDisp.addAll(getOreDisponibileConsultatie(ora, DbOperations.getTimpServiciu(Long.valueOf(codMedic), Long.valueOf(codServiciu))));
		return oreDisp;
	}
	
	public List<Interval> getListaIntervalePosibile(String codMedic,String data){
		System.out.println(new Date(data));
		List<String> oreLucruMedic=DbOperations.getPogramMedic(Long.valueOf(codMedic),DateUtil.getSqlDateFromUtilDate(new Date(data)));
		List<Interval> intervalePosibile=new ArrayList<Interval>();
		String inceput=oreLucruMedic.get(0).substring(0, oreLucruMedic.get(0).length() - 3);
		String sfarsit=oreLucruMedic.get(1).substring(0, oreLucruMedic.get(1).length() - 3);

		try {
			if(!DbOperations.getBusyHoursConsultatie(DateUtil.getSqlDateFromUtilDate(new Date(data)), Long.valueOf(codMedic)).isEmpty()) {
				for(Interval ora:DbOperations.getBusyHoursConsultatie(DateUtil.getSqlDateFromUtilDate(new Date(data)), Long.valueOf(codMedic)))
				{ System.out.println("OREEEEE"+DateUtil.getDateHourFromString(inceput)+DateUtil.getDateHourFromString(ora.getInceput()));
					if(DateUtil.getDateHourFromString(inceput).before(DateUtil.getDateHourFromString(ora.getInceput()))) {
						Interval interval=new Interval();
						interval.setInceput(inceput);
						//System.out.println("INCEPUT"+inceput);
						interval.setSfarsit(ora.getInceput());
						intervalePosibile.add(interval);
						inceput=ora.getSfarsit();
					}
					else {
						inceput=getStartingHour(inceput,DbOperations.getBusyHoursConsultatie(DateUtil.getSqlDateFromUtilDate(new Date(data)), Long.valueOf(codMedic)));
					}
				}
			}
			if(DateUtil.getDateHourFromString(inceput).before(DateUtil.getDateHourFromString(sfarsit))) {
				Interval interval=new Interval();
				interval.setInceput(inceput);
				interval.setSfarsit(sfarsit);
				intervalePosibile.add(interval);
			}
		} catch (ParseException e) {
			System.out.println("eeeee"+e.getMessage());
		}
		return intervalePosibile;

	}
	
	public List<String> getOreDisponibileConsultatie(Interval intervalDisponibil,String timpConsultatie){
		List<String> oreDisponibile=new ArrayList<String>();
		String oraInceput=intervalDisponibil.getInceput();
		try {
			while(DateUtil.getDateHourFromString(DateUtil.addMinutesToHours(oraInceput, timpConsultatie)).before(DateUtil.getDateHourFromString(intervalDisponibil.getSfarsit()))||
					DateUtil.getDateHourFromString(DateUtil.addMinutesToHours(oraInceput, timpConsultatie)).equals(DateUtil.getDateHourFromString(intervalDisponibil.getSfarsit()))) {
				oreDisponibile.add(oraInceput);
				oraInceput=DateUtil.addMinutesToHours(oraInceput, timpConsultatie);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return oreDisponibile;
	}
	
    public ProgramariConsultatie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String metoda = request.getParameter("metoda");
		if(metoda!=null) {
		switch(metoda) {
		case "data":
			String data=request.getParameter("data");
			String codMedic=request.getParameter("codMedic");
			String codServiciu=request.getParameter("serviciu");
			try {
				response.getWriter().append(validDate(data,codMedic,codServiciu));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "detalii":
			String cnp=request.getParameter("cnp");
			if(DbOperations.cautaPacientDupaCNP(cnp)==null){
				response.getWriter().append("{\"valid\":\"false\"}");
			} 
			else {
				response.getWriter().append("{\"valid\":true,"
						+ "\"nume\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getNume() +"\""
						+ ",\"prenume\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getPrenume() +"\""
						+ ",\"id\":\"" + DbOperations.cautaPacientDupaCNP(cnp).getId() +"\""
						+ "}");
			}
			break;
		case "ora":
			String date=request.getParameter("data");
			String codMed=request.getParameter("codMedic");
			String codServ=request.getParameter("serviciu");
			try {
				String json = new Gson().toJson(getOreDisp(codMed, date, codServ));
				response.getWriter().append(json);
			} catch (Exception e) {
				System.out.println("sssssssssssssssssssssssssssssss");
				e.printStackTrace();
			}
			break;
					
		}
		}
		else {
		String medic=request.getParameter("medic");
		String pacient=request.getParameter("pacientcod");
		String dataCons=request.getParameter("data");
		String serviciu=request.getParameter("serviciu");
		String ora=request.getParameter("ora");
		String detalii=request.getParameter("detalii");
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date parsed;
		try {
			parsed = new SimpleDateFormat("dd/MM/yyyy").parse(dataCons);
			DbOperations.insertConsultatie(detalii, "in curs",serviciu, ora,new java.sql.Date(parsed.getTime()), medic, pacient);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("InformatiiConsultatie.jsp");
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
