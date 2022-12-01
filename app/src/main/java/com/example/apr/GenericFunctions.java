package com.example.apr;
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.activation.*;
//import javax.mail.Session;
//import javax.mail.Transport;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class GenericFunctions {

    public static <T> HashMap<String, String> ConvertClassIntoHashMap(T cls){
        if (cls == null)
            return null;
        HashMap<String, String> hm = new HashMap<>();
        Field[] fileds = cls.getClass().getDeclaredFields();
        for(Field field: fileds){
            try {
                Object ab = field.get(cls);
                hm.put(field.getName(), ab.toString());
            }
            catch (Exception e) {
                System.out.println("Error");
            }
        }
        return hm;
    }

    // ActionType = 1 = INSERT
    // ActionType = 2 = UPDATE
    public static <T> ContentValues ConvertClassIntoContentValues(T cls, int ActionType){
        if (cls == null)
            return null;
        ContentValues hm = new ContentValues();
        Field[] fileds = cls.getClass().getDeclaredFields();
        for(Field field: fileds){
            if (field.getName() != "Id"){
                if (ActionType == EntityActionType.UPDATE_ACTION && field.getName() == "CreatedOn"){
                    continue;
                }
                try {
                    Object ab = field.get(cls);
                    hm.put(field.getName(), ab.toString());
                }
                catch (Exception e) {
                    System.out.println("Error");
                }
            }
        }
        return hm;
    }

//    public static void SendEmail(Context context){
//        // email ID of Recipient.
//        String recipient = "m.arsalan@aptechnorth.edu.pk";
//
//        Toast.makeText(context, "recipient passed", Toast.LENGTH_LONG).show();
//        // email ID of Sender.
//        String sender = "marcsiddiqui@gmail.com";
//        Toast.makeText(context, "sender passed", Toast.LENGTH_LONG).show();
//
//        // using host as localhost
//        String host = "localhost";
//        Toast.makeText(context, "host passed", Toast.LENGTH_LONG).show();
//
//        // Getting system properties
//        Properties properties = System.getProperties();
//        Toast.makeText(context, "properties passed", Toast.LENGTH_LONG).show();
//
//        // Setting up mail server
//        properties.setProperty("mail.smtp.host", host);
//        Toast.makeText(context, "properties.setProperty passed", Toast.LENGTH_LONG).show();
//
//        // creating session object to get properties
//        Session session = Session.getDefaultInstance(properties);
//        Toast.makeText(context, "session passed", Toast.LENGTH_LONG).show();
//
////        try
////        {
////            // MimeMessage object.
////           MimeMessage message = new MimeMessage(session);
//////            Toast.makeText(context, "message passed", Toast.LENGTH_LONG).show();
//////
//////            // Set From Field: adding senders email to from field.
//////            message.setFrom(new InternetAddress(sender));
//////            Toast.makeText(context, "setFrom passed", Toast.LENGTH_LONG).show();
//////
//////            // Set To Field: adding recipient's email to from field.
//////            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//////
//////            // Set Subject: subject of the email
//////            message.setSubject("This is Subject");
//////            Toast.makeText(context, "setSubject passed", Toast.LENGTH_LONG).show();
//////
//////            // creating first MimeBodyPart object
//////            BodyPart messageBodyPart1 = new MimeBodyPart();
//////            messageBodyPart1.setText("This is body of the mail");
//////            Toast.makeText(context, "setText passed", Toast.LENGTH_LONG).show();
//////
//////            // creating second MimeBodyPart object
//////            BodyPart messageBodyPart2 = new MimeBodyPart();
//////            String filename = "attachment.txt";
//////            DataSource source = new FileDataSource(filename);
//////            messageBodyPart2.setDataHandler(new DataHandler(source));
//////            messageBodyPart2.setFileName(filename);
//////            Toast.makeText(context, "messageBodyPart2 passed", Toast.LENGTH_LONG).show();
//////
//////            // creating MultiPart object
//////            Multipart multipartObject = new MimeMultipart();
//////            multipartObject.addBodyPart(messageBodyPart1);
//////            multipartObject.addBodyPart(messageBodyPart2);
//////            Toast.makeText(context, "addBodyPart passed", Toast.LENGTH_LONG).show();
//////
//////
//////
//////            // set body of the email.
//////            message.setContent(multipartObject);
//////            Toast.makeText(context, "setContent passed", Toast.LENGTH_LONG).show();
//////
//////            // Send email.
//////            Transport.send(message);
//////            Toast.makeText(context, "send passed", Toast.LENGTH_LONG).show();
////            System.out.println("Mail successfully sent");
////        }
////        catch (MessagingException mex)
////        {
////            mex.printStackTrace();
////        }
//    }

}
