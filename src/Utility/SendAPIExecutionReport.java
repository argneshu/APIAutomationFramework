package Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import testCaseReporting.SuiteReporting;

public class SendAPIExecutionReport {

	private String SMTP_HOST = "smtp.gmail.com";
	private static String FROM_ADDRESS = "zopper.test@gmail.com";
	private static String PASSWORD = "qa@zopper";
	private String FROM_NAME = "API Automation Report";
	
	public boolean sendMail() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "false");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.port", "localhost");
			props.put("mail.smtp.starttls.enable", "true");
			String[] recipients = new String[] { "mohit@zopper.com" };
			String[] bccRecipients = new String[] { "mohit@zopper.com" };
//			String[] bccRecipients = new String[] { "vinay.kumar@zopper.com", "shashank.mishra@zopper.com",
//					"argneshu@zopper.com", "rajanikant@zopper.com" };
			String subject = "API Suite Execution Report";

			Session session = Session.getInstance(props, new SocialAuth());
			Message msg = new MimeMessage(session);

			InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
			msg.setFrom(from);

			InternetAddress[] toAddresses = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				toAddresses[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, toAddresses);

			InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];
			for (int j = 0; j < bccRecipients.length; j++) {
				bccAddresses[j] = new InternetAddress(bccRecipients[j]);
			}
			msg.setRecipients(Message.RecipientType.BCC, bccAddresses);

			msg.setSubject(subject);

			FolderZip.CompressResultFolder();
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Hi," + "" + "Please find attached API suite execution report Zip");

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			DataSource source = new FileDataSource(SuiteReporting.pathToSuiteFolder+"/TestCase.zip");
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName("APIResultSuit.zip");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			msg.setContent(multipart);

			Transport.send(msg);
			return true;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(SendAPIExecutionReport.class.getName()).log(Level.SEVERE, null, ex);
			return false;

		} catch (MessagingException ex) {
			Logger.getLogger(SendAPIExecutionReport.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	class SocialAuth extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

		}
	}

}