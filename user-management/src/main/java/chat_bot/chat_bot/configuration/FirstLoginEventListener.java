package chat_bot.chat_bot.configuration;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class FirstLoginEventListener implements EventListenerProvider {
    private final KeycloakSession session;

    public FirstLoginEventListener(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("Event received: " + event.getType() + " for user: " + event.getUserId());

        if ("LOGIN".equals(event.getType().toString())) {
            String userId = event.getUserId();
            RealmModel realm = session.getContext().getRealm();
            UserModel user = session.users().getUserById(realm, userId);

            if (user != null) {
                boolean isFirstLogin = isFirstLogin(user);

                System.out.println("Is first login: " + isFirstLogin);  // Add logging here

                if (isFirstLogin) {
                    System.out.println("Sending email to admin...");  // Confirm this line
                    sendEmailToAdmin(userId);
                }
            }
        }
    }


    @Override
    public void onEvent(AdminEvent event, boolean isNew) {
        // Can be left empty if not handling admin events
    }

    // Function to check if this is the first login based on a custom attribute
    private boolean isFirstLogin(UserModel user) {
        String firstLogin = user.getFirstAttribute("firstLogin");
        System.out.println("firstLogin attribute value: " + firstLogin);

        if (firstLogin == null) {
            // Mark user as having logged in for the first time
            user.setAttribute("firstLogin", java.util.Collections.singletonList("true"));
            System.out.println("User marked as first login: " + user.getUsername());
            return true;
        }

        return false;  // It's not the first login if the attribute exists
    }

    private void sendEmailToAdmin(String userId) {
        // Admin email address
        String adminEmail = "ghribiislem19@gmail.com";
        String subject = "New User First Login Alert";
        String body = "User with ID " + userId + " has logged in for the first time or their account was unlocked.";

        // Send email using SMTP or other email service
        sendEmail(adminEmail, subject, body);
    }

    // Method to send email using SMTP
    private void sendEmail(String to, String subject, String body) {
        String from = "ghribiislem19@gmail.com";  // Your email address
        String host = "smtp.gmail.com";  // Your SMTP server

        // Log the email details to confirm it's being triggered
        System.out.println("Sending email to: " + to);
        System.out.println("Email Subject: " + subject);
        System.out.println("Email Body: " + body);

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, "tbyc pyut gwsl vcjm");
            }
        });

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // Log before sending
            System.out.println("Sending email...");

            Transport.send(message);

            // Log after sending
            System.out.println("Email sent successfully to: " + to);
        } catch (MessagingException e) {
            System.out.println("Failed to send email.");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        // Cleanup resources if needed
    }
}
