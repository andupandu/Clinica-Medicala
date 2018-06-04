package pkg.Entities;

public class Serviciu {
Long cod;
String Timp;
Long pret;
Long codMedic;
String denumire;
public Long getCodMedic() {
	return codMedic;
}
public void setCodMedic(Long codMedic) {
	this.codMedic = codMedic;
}
public Long getCod() {
	return cod;
}
public void setCod(Long cod) {
	this.cod = cod;
}
public String getTimp() {
	return Timp;
}
public void setTimp(String timp) {
	Timp = timp;
}
public Long getPret() {
	return pret;
}
public void setPret(Long pret) {
	this.pret = pret;
}
public String getDenumire() {
	return denumire;
}
public void setDenumire(String denumire) {
	this.denumire = denumire;
}

}
