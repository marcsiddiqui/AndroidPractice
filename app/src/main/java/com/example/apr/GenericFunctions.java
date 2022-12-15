package com.example.apr;
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.activation.*;
//import javax.mail.Session;
//import javax.mail.Transport;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
                    if (cls.getClass().getSimpleName().equals("Notification") && field.getName().equals("IsRead")){
                        hm.put(field.getName(), false);
                    }
                    else if (cls.getClass().getSimpleName().equals("User") && field.getName().equals("ProfileImage")){
                        Object ab = field.get(cls);
                        hm.put(field.getName(), (byte[]) ab);
                    }
                    else {
                        Object ab = field.get(cls);
                        hm.put(field.getName(), ab.toString());
                    }
                }
                catch (Exception e) {
                    System.out.println("Error");
                }
            }
        }
        return hm;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //region Extra Code

    //    public static <T> ArrayList<T> ConvertCursorToClassCollection(Cursor cursor, Class<T> cls) {
//        ArrayList<T> li = new ArrayList<>();
//        if (cursor != null && cursor.getCount() > 0) {
//            Field[] fields = cls.getDeclaredFields();
//            String[] columns = cursor.getColumnNames();
//            //Toast.makeText(context, columns.length+"", Toast.LENGTH_SHORT).show();
//            try {
//                while (cursor.moveToNext()) {
//                    T bean = cls.newInstance();
//
//                    for (String column : columns) {
//                        //Toast.makeText(context, column, Toast.LENGTH_SHORT).show();
//                        Field field = findFieldByName(fields, column);
//
//                        field.set(bean, getValueByField(cursor, column, field));
////                        String letter = column.substring(0, 1)
////                                .toUpperCase();
////
////                        Toast.makeText(context, letter, Toast.LENGTH_SHORT).show();
////                        String setter = "set" + letter
////                                + column.substring(1);
////                        Method setMethod = cls.getMethod(setter,
////                                new Class[] { field.getType() });
////                        setMethod.invoke(bean,
////                                getValueByField(cursor, column, field));
//                    }
//                    li.add(bean);
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            }
//        }
//        return li;
//    }

//    private static Field findFieldByName(Field[] fields, String name) {
//        for (Field field : fields) {
//            if (field.getName().equals(name)) {
//                return field;
//            }
//        }
//        return null;
//    }

//    private static Object getValueByField(Cursor cursor, String columnName, Field field) {
//        int index = cursor.getColumnIndex(columnName);
//        if (index == -1)
//            return null;
//        Class fieldClass = field.getType();
//
//        String test1 = field.getType().getSimpleName().toLowerCase();
//        String test2 = Byte[].class.getSimpleName().toLowerCase();
//
//        if (fieldClass == String.class)
//            return cursor.getString(index);
//
//        else if (fieldClass == int.class || fieldClass == Integer.class)
//            return cursor.getInt(index);
//        else if (fieldClass == Long.class)
//            return cursor.getLong(index);
//
//        else if (fieldClass == Double.class)
//            return cursor.getDouble(index);
//
//        else if (fieldClass == Float.class)
//            return cursor.getFloat(index);
//
//        else if (fieldClass == Short.class)
//            return cursor.getShort(index);
//
//        else if (test1 == test2)
//            return cursor.getBlob(index);
//
//        return null;
//    }

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

    //endregion
}
