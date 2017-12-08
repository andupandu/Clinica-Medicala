package pkg;

import java.util.Date;

public class Persoana {
	Long id;
	String nume;
	String prenume;
	Date data_nastere;
	String email;
	String cnp;
	String telefon;
	public String getCnp() {
		return cnp;
	}
	public void setCnp(String cnp) {
		this.cnp = cnp;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getPrenume() {
		return prenume;
	}
	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}
	public Date getData_nastere() {
		return data_nastere;
	}
	public void setData_nastere(Date data_nastere) {
		this.data_nastere = data_nastere;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
