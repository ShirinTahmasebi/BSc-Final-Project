package shirin.tahmasebi.mscfinalproject.util.mail.gmail;


import android.util.Log;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class GMailSender {
    private Session session;


    public SMTPTransport connectToSmtp(String host, int port, String userEmail,
                                       String oauthToken, boolean debug) throws Exception {
        Log.v("mscFinalProject", " شروع تلاش برای برقراری ارتباط با SMTP ");
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.sasl.enable", "false");
        session = Session.getInstance(props);
        session.setDebug(debug);
        final URLName unusedUrlName = null;
        SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
        final String emptyPassword = null;
        transport.connect(host, port, userEmail, emptyPassword);
        Log.v("mscFinalProject", "  قبل از تولید جواب ");
        byte[] response = String.format("user=%s\1auth=Bearer %s\1\1", userEmail, oauthToken)
                .getBytes();
        response = BASE64EncoderStream.encode(response);
        Log.v("mscFinalProject", "چک کردن وصل بودن ترنسپورت:  " + transport.isConnected());
        Log.v("mscFinalProject", " جواب تبدیل شده است:   " + new String(response));
        transport.issueCommand("AUTH XOAUTH2 " + new String(response), 235);
        Log.v("mscFinalProject", "بعد از متد ایشوکامند");
        return transport;
    }


    public synchronized boolean sendMail(String subject, String body, String user,
                                         String oauthToken, String recipients) {
        try {
            SMTPTransport smtpTransport = connectToSmtp(
                    "smtp.gmail.com",
                    587,
                    user,
                    oauthToken,
                    true
            );

            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(
                    body.getBytes(),
                    "text/plain")
            );
            // ست کردن داده‌های مربوط به ایمیل مانند آدرس و موضوع و متن و ...
            message.setSender(new InternetAddress(user));
            message.setSubject(subject);
            message.setDataHandler(handler);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            // ارسال ایمیل
            smtpTransport.sendMessage(message, message.getAllRecipients());
            return true;
        } catch (MessagingException e) {
            // ارسال پیام با شکست مواجه شد
            Log.w("mscFinalProject",
                    "ارسال پیام با شکست رو برو شد. علت میتواند بدلیل منقضی شدن توکن باشد");

        } catch (Exception e) {
            e.printStackTrace();
            // ارسال پیام با شکست مواجه شد
            Log.w("mscFinalProject",
                    "ارسال پیام با شکست مواجه شد.");
        }
        return false;
    }
}