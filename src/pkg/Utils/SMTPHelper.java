package pkg.Utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBException;

import pkg.Entities.Mesaj;

public class SMTPHelper {
	public static void SendEmail(List<String> emails,String continut,String subiect) throws JAXBException {
		Properties props = new Properties();
	
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		InternetAddress[] recipientAdress=new InternetAddress [emails.size()] ;
		Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(
            		   "andreea.limba96@gmail.com", "andutupandutud");
            }
         });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("clinicaMedicala@yahoo.com"));
			int i=0;
			for(String email:emails) {
				recipientAdress[i]=new InternetAddress(email);
				i++;
			}
			message.setRecipients(Message.RecipientType.CC, recipientAdress);
			message.setSubject(subiect);
			message.setText(continut);
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	public static String generatePassword() {
		 SecureRandom random = new SecureRandom();
		  final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		  final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		  final String NUMERIC = "0123456789";
		    String result = "";
		    for (int i = 0; i < 6; i++) {
		        int index = random.nextInt((ALPHA_CAPS+ALPHA+NUMERIC).length());
		        result += (ALPHA_CAPS+ALPHA+NUMERIC).charAt(index);
		    }
		    return result;
		    }
	}


