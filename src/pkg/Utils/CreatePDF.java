package pkg.Utils;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import pkg.Entities.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CreatePDF {

	    public static final String DEST = "C:/Users/Andreea/eclipse-workspace/ClinicaMedicala/WebContent/resources/IstoricMedical.pdf";
	    public static final String DESTREZ = "C:/Users/Andreea/eclipse-workspace/ClinicaMedicala/WebContent/resources/RezultatAnalize.pdf";

	    public static void createPdfIstoricAnalize(List<Consultatie> consultatii,List<Rezultat> analize) throws IOException, DocumentException {
	    	try {
	    	File file = new File(DEST);
	        file.getParentFile().mkdirs();
	        Font smallfont = new Font(FontFamily.HELVETICA, 10);
	        Font largefont = new Font(FontFamily.HELVETICA, 16);
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
	        document.open();
	        PdfPTable tableConsultatii = new PdfPTable(5);
	        PdfPTable tableAnalize = new PdfPTable(4);

	        tableConsultatii.setTotalWidth(900F);
	        tableAnalize.setTotalWidth(900F);
	        // first row
	        Paragraph p = new Paragraph("Istoric medical",largefont);
	        p.setAlignment(Element.ALIGN_CENTER);
	        document.add(p);
	        // second row
	        p = new Paragraph("Consultatii", smallfont);
	        
	        p.setAlignment(Element.ALIGN_LEFT);
	        document.add(p);
	        document.add( Chunk.NEWLINE );
	        tableConsultatii.addCell("Serviciu");
	        tableConsultatii.addCell("Data");
	        tableConsultatii.addCell("Ora");
	        tableConsultatii.addCell("Status");
	        tableConsultatii.addCell("Medic");
	        for(Consultatie c:consultatii) {
	        	tableConsultatii.addCell(c.getTipConsutatie());
	        	tableConsultatii.addCell(c.getData().toString());
	        	tableConsultatii.addCell(c.getOraInceput());
	        	tableConsultatii.addCell(c.getStatus());
	        	tableConsultatii.addCell(c.getMedic());
	        	
	        }
	        document.add(tableConsultatii);
	        document.add( Chunk.NEWLINE );
	        p = new Paragraph("Analize", smallfont);
	        p.setAlignment(Element.ALIGN_LEFT);
	        document.add(p);
	        document.add( Chunk.NEWLINE );
	        tableAnalize.addCell("Denumire");
	        tableAnalize.addCell("Valoare");
	        tableAnalize.addCell("Interpretare");
	        tableAnalize.addCell("Data rezultat");
	        for(Rezultat r:analize) {
	        	tableAnalize.addCell(r.getAnaliza());
	        	tableAnalize.addCell(r.getValoare());
	        	tableAnalize.addCell(r.getInterpretare());
	        	tableAnalize.addCell(r.getData());
	        	
	        	
	        }
	        document.add(tableAnalize);
	        document.close();
	        
	    	}catch(Exception e)
	    	{
	    		System.out.println(e.getMessage());
	    	}
	    	System.out.println("Fisier creat!");
	    }
	  public static void createPdfRezultatAnalize(Persoana pacient,String codBuletin,List<String> categoriiAnalize) {
		  try {
		    	File file = new File(DESTREZ);
		        file.getParentFile().mkdirs();
		        Font smallfont = new Font(FontFamily.HELVETICA, 10);
		        Font boldFont = new Font(FontFamily.HELVETICA, 10, Font.BOLD);
		        Document document = new Document();
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DESTREZ));
		        document.open();
		        PdfPTable tabelDetalii = new PdfPTable(3);
		        PdfPTable tabelRezultat = new PdfPTable(4);
		        String tipPersoana="";
		        if(pacient.getVarsta()>18) {
		        	tipPersoana="adult";
		        }
		        else {
		        	tipPersoana="copil";
		        }
		        tabelRezultat.setTotalWidth(1000F);
		        tabelDetalii.setTotalWidth(1000F);
		        Paragraph p = new Paragraph("Clinica medicala",smallfont);
		        p.setAlignment(Element.ALIGN_CENTER);
		        document.add(p);
		        Chunk linebreak = new Chunk(new LineSeparator());
		        document.add(linebreak);  
		        p=new Paragraph("Buletin de analize medicale nr."+codBuletin,boldFont);
		        p.setAlignment(Element.ALIGN_CENTER);
		        document.add(p);
		      ProgramareAnaliza prog= DbOperations.getAnalizeleBuletinului(codBuletin, pacient.getId().toString(),categoriiAnalize.get(0)).get(0);
		        p=new Paragraph("Data si ora recoltare:"+DateUtil.changeDateFormat(prog.getData())+" "+prog.getOra(),smallfont);
		        p.setAlignment(Element.ALIGN_CENTER);
		        document.add(p);
		        document.add( Chunk.NEWLINE );
		        tabelDetalii.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		        tabelDetalii.addCell("Pacient:"+pacient.getNume()+" "+pacient.getPrenume());
		        tabelDetalii.addCell("Varsta:"+pacient.getVarsta());
		        tabelDetalii.addCell("Sex:"+pacient.getSex());
		        tabelDetalii.addCell("CNP:"+pacient.getCnp());
		        tabelDetalii.addCell("Localitate:Galati");
		        tabelDetalii.addCell("Data:"+DateUtil.changeDateFormat(new Date()));
		        tabelDetalii.setTableEvent(new BorderEvent());
		        document.add(tabelDetalii);
		        document.add( Chunk.NEWLINE );
		        for(String categorie:categoriiAnalize) {
		        	p=new Paragraph(categorie,smallfont);
		        	document.add(p);
		        	 document.add( Chunk.NEWLINE );
			        tabelRezultat.addCell("Analiza");
			        tabelRezultat.addCell("Valoare");
			        tabelRezultat.addCell("Interpretare");
			        tabelRezultat.addCell("Interval biologie de referinta/UM");
			        for(ProgramareAnaliza programare:DbOperations.getAnalizeleBuletinului(codBuletin, pacient.getId().toString(),categorie)) {
			        	Rezultat rezultatAnaliza=DbOperations.getRezultatAnalize(programare.getCodAnaliza(), codBuletin);
			        	tabelRezultat.addCell(programare.getDenumireAnaliza());
			        	tabelRezultat.addCell(rezultatAnaliza.getValoare());
			        	tabelRezultat.addCell(rezultatAnaliza.getInterpretare());
			        	Indicator indicator=DbOperations.getIndicator(programare.getCodAnaliza(), pacient.getSex(), tipPersoana);
			        	if(indicator.getIntervalRefMin().equals("0") && !(indicator.getIntervalRefMax().equals(indicator.getIntervalRefMin())))
			        	tabelRezultat.addCell("<"+indicator.getIntervalRefMax());
			        	else if(indicator.getIntervalRefMax().equals(indicator.getIntervalRefMin()))
			        		tabelRezultat.addCell("Negativ");
			        	else
			        		tabelRezultat.addCell(indicator.getIntervalRefMin()+"-"+indicator.getIntervalRefMax());
			        }
			        document.add(tabelRezultat);
			        tabelRezultat.flushContent(); 
		        	 document.add( Chunk.NEWLINE );


		        }
		        document.close();
		  }
		  catch(Exception e) {
			  System.out.println(e.getMessage());
		  }
		  System.out.println("Fisier creat!");
	  }
	}

