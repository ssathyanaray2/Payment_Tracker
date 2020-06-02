package mail;

import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class JavaEmail
{
    Session mailSession;
    Scanner in = new Scanner(System.in); 
    public static void main(String args[]) throws AddressException, MessagingException
    {
    	JavaEmail javaEmail = new JavaEmail();
        javaEmail.setMailServerProperties();
        try {
            javaEmail.sendEmail();
        	}
            
        catch(Exception e) { 
            System.out.println("Email or Password incorrect. Re-Enter credentials.");
            javaEmail.sendEmail();
            }
    }
 
    private void setMailServerProperties()
    {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        mailSession = Session.getDefaultInstance(emailProperties, null);
    }
 
    private MimeMessage draftEmailMessage() throws AddressException, MessagingException
    {
    	System.out.println("Enter receiver email id :\n");
        String email= in.nextLine();
        String[] toEmails = { email };
        System.out.println("Enter mail subject :\n");
        String emailSubject = in.nextLine();
        System.out.println("Enter mail body :\n");
        String emailBody = in.nextLine();
        
        MimeMessage emailMessage = new MimeMessage(mailSession);
        
        for (int i = 0; i < toEmails.length; i++)
        {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }
        emailMessage.setSubject(emailSubject);
        /**If sending HTML mail**/
        //emailMessage.setContent(emailBody, "text/html");
        /**If sending only text mail **/
        emailMessage.setText(emailBody);
        return emailMessage;
    }
 
    private void sendEmail() throws AddressException, MessagingException
    {  
        System.out.println("Enter your mail id :\n");
        String fromUser = in.nextLine();
        System.out.println("Enter password :\n");
        String fromUserEmailPassword = in.nextLine();
 
        String emailHost = "smtp.gmail.com";
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        MimeMessage emailMessage = draftEmailMessage();
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}