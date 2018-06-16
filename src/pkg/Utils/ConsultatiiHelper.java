package pkg.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pkg.Entities.Interval;

public class ConsultatiiHelper {
	
	public static String getStartingHour(String inceput,List<Interval> consultatii) throws ParseException {
		int i=0;
		while(i<consultatii.size() && DateUtil.getDateHourFromString(inceput).equals(DateUtil.getDateHourFromString(consultatii.get(i).getInceput()))){
			inceput=consultatii.get(i).getSfarsit();
			i++;
		}
		return inceput;
	}
	
	public static List<String> getOreDisp(String codMedic,String data,String codServiciu) throws Exception{
		List<String> oreDisp=new ArrayList<String>();
		for(Interval ora:getListaIntervalePosibile(codMedic, data))
			oreDisp.addAll(getOreDisponibileConsultatie(ora, DbOperations.getTimpServiciu(Long.valueOf(codMedic), Long.valueOf(codServiciu))));
		return oreDisp;
	}

	public static List<Interval> getListaIntervalePosibile(String codMedic,String data){
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

	public static List<String> getOreDisponibileConsultatie(Interval intervalDisponibil,String timpConsultatie){
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

}
