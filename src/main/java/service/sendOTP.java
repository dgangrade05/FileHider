package service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class sendOTP {
    public static void sendOTP(String email, String OTP){
        String mailTO = email;

        String mailFrom = "dhanrajloginemails@gmail.com";

        String host = "smtp.gmail.com";

        Properties prop = System.getProperties();

        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(mailFrom, "cerfrsolkwtvjelr");

            }

        });

        session.setDebug(true);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTO));
            message.setSubject("THE OTP");
            message.setText("YOUR ONE TIME PASSWORD IS "+ OTP + " Please do not share this with anyone.");
            System.out.println("SENDING ... ");
            Transport.send(message);
            System.out.println("SENT SUCCESSFULLY");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
