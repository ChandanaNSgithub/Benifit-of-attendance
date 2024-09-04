package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "EmailService";

    private String recipientEmail;
    private String subject;
    private String messageBody;

    public EmailService(String recipientEmail, String subject, String messageBody) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.messageBody = messageBody;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // Configure email sending
        final String username = "chandumegha93@gmail.com"; // Replace with your email address
        final String password = "lked uewe wkel nxta"; // Replace with your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP server host
        props.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            Log.d(TAG, "Email sent successfully to " + recipientEmail);
            return true;

        } catch (MessagingException e) {
            Log.e(TAG, "Error sending email", e);
            // Log the full stack trace
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            // Email sent successfully
            Log.d(TAG, "Email sent successfully.");
        } else {
            // Failed to send email
            Log.e(TAG, "Failed to send email.");
        }
    }
}
