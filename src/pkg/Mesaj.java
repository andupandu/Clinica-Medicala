package pkg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

public class Mesaj {
	public static Map<String, String> mesaje = new HashMap<String, String>();
	private static final String fisierMesaje = "Mesaje.xml";
	
	private String nume;
	private String continut;
	
	public String getNume() {
		return nume;
	}
	@XmlElement
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getContinut() {
		return continut;
	}
	@XmlElement
	public void setContinut(String continut) {
		this.continut = continut;
	}

	public String IncarcaMesaje() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Mesaj.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fisierMesaje).getFile());
		Mesaj mesaj = (Mesaj) jaxbUnmarshaller.unmarshal(file);
		return mesaj.getContinut();
	}
}
