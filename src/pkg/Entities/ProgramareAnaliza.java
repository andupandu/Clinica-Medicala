package pkg.Entities;

import java.sql.Date;

public class ProgramareAnaliza {
	String codAnaliza;
	String denumireAnaliza;
	Date data;
	String ora;
	String categorieAnaliza;
	public String getCodAnaliza() {
		return codAnaliza;
	}
	public void setCodAnaliza(String codAnaliza) {
		this.codAnaliza = codAnaliza;
	}
	public String getDenumireAnaliza() {
		return denumireAnaliza;
	}
	public void setDenumireAnaliza(String denumireAnaliza) {
		this.denumireAnaliza = denumireAnaliza;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public String getCategorieAnaliza() {
		return categorieAnaliza;
	}
	public void setCategorieAnaliza(String categorieAnaliza) {
		this.categorieAnaliza = categorieAnaliza;
	}
	

}
