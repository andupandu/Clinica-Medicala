package pkg.Entities;

import java.sql.Date;

public class Consultatie {

	String oraInceput;
	String medic;
	String pacient;
	String tipConsutatie;
	Date data;
	public String getOraInceput() {
		return oraInceput;
	}
	public void setOraInceput(String oraInceput) {
		this.oraInceput = oraInceput;
	}
	public String getMedic() {
		return medic;
	}
	public void setMedic(String medic) {
		this.medic = medic;
	}
	public String getPacient() {
		return pacient;
	}
	public void setPacient(String pacient) {
		this.pacient = pacient;
	}
	public String getTipConsutatie() {
		return tipConsutatie;
	}
	public void setTipConsutatie(String tipConsutatie) {
		this.tipConsutatie = tipConsutatie;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
