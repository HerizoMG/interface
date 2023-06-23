package com.example.affectation.Util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class MailSender {
    public static void EnvoyeMail(String mail, String messageText) {
        final String username = "valiavoandriantsoa30@gmail.com";
        final String password = "zxulcjrbzzzicpla";

        // Configuration des propriétés
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable", "true");


        // Création de la session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Notification d'affectation");
            message.setText("Contenu du mail");

            // Envoi du message
            Transport.send(message);

            System.out.println("Le mail a été envoyé avec succès.");
        } catch (MessagingException e) {
            System.out.println("Une erreur s'est produite lors de l'envoi du mail : " + e.getMessage());
        }
    }
}
