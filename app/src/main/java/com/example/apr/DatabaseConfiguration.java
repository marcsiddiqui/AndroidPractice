package com.example.apr;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseConfiguration extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Calculator_DB";
    Context context;

    public DatabaseConfiguration(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (Id INTEGER PRIMARY KEY AUTOINCREMENT, FirstName TEXT, LastName TEXT, Email Text, Username TEXT, Password TEXT, CreatedOn DATETIME)");

        if (!IsColumnExists("User", "UserType")){
            db.execSQL("ALTER TABLE User ADD UserType INTEGER DEFAULT 0 NOT NULL");
        }

        db.execSQL("insert into User (firstname, lastname, email, username, password, createdon, usertype) values ('ali','haider','ali@gmail.com','ali','123456',date(), 1)");
        db.execSQL("CREATE TABLE Notification (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT, CreatedOn DATETIME, IsRead BIT)");

        if (!IsColumnExists("User", "ProfileImage")){
            db.execSQL("ALTER TABLE User ADD ProfileImage BLOG");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion > oldVersion){
//            db.execSQL("alter table User ADD UserType INT NOt NULL DEFAULT 0");
//        }
    }

    //region User Related Methods

    public boolean Insert(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = GenericFunctions.ConvertClassIntoContentValues(user, EntityActionType.INSERT_ACTION);
        long result = sqLiteDatabase.insert("User", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean Update(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = GenericFunctions.ConvertClassIntoContentValues(user,EntityActionType.UPDATE_ACTION);
        long result = sqLiteDatabase.update("User", contentValues, "Id = "+ user.Id, null);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public User Login(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * FROM User where (username = ? OR email = ?) and password = ?", new String[]{username, username, password});
        if(cursor.getCount() > 0){
            User user = new User();
            while(cursor.moveToNext()){
                user = UserBinder(cursor);
            }
            return user;
        }else{
            return null;
        }
    }

    public User GetUserById(int id){
        if (id <= 0){
            return null;
        }
        else {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE Id=" + id, null);
            if (cursor.getCount() > 0){
                User user = new User();
                while (cursor.moveToNext()){
                    user = UserBinder(cursor);
                }
                return user;
            }
            else {
                return null;
            }
        }
    }

    public Integer DeleteUser(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("User", "Id = " + id, null);
    }

    public ArrayList<User> GetAllUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM User", null);

        if (cursor.getCount() > 0){

            ArrayList<User> li = new ArrayList<>();

            while (cursor.moveToNext()){
                li.add(UserBinder(cursor));
            }

            return li;
        }
        else {
            return null;
        }
    }

    public User UserBinder(Cursor cursor){
        User user = new User();
        user.Id = Integer.parseInt(cursor.getString(0).toString());
        user.FirstName = cursor.getString(1).toString();
        user.LastName = cursor.getString(2).toString();
        user.Email = cursor.getString(3).toString();
        user.Username = cursor.getString(4).toString();
        user.Password = cursor.getString(5).toString();
        //user.CreatedOn = cursor.getString(6).toString();
        user.UserType = Integer.parseInt(cursor.getString(7).toString());
        user.ProfileImage = cursor.getBlob(8);
        return user;
    }

    //endregion

    //region Notification Related Methods

    public boolean InsertNotification(Notification notification){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = GenericFunctions.ConvertClassIntoContentValues(notification, EntityActionType.INSERT_ACTION);
        long result = sqLiteDatabase.insert("Notification", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean UpdateNotification(Notification notification){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = GenericFunctions.ConvertClassIntoContentValues(notification,EntityActionType.UPDATE_ACTION);
        long result = sqLiteDatabase.update("Notification", contentValues, "Id = "+ notification.Id, null);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Notification GetNotificationById(int id){
        if (id <= 0){
            return null;
        }
        else {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Notification WHERE Id=" + id, null);
            if (cursor.getCount() > 0){
                Notification notification = new Notification();
                while (cursor.moveToNext()){

                    notification = NotificationBinder(cursor);

                }
                return notification;
            }
            else {
                return null;
            }
        }
    }

    public ArrayList<Notification> GetAllUnReadNotifications(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Notification WHERE IsRead = 0", null);
        if (cursor.getCount() > 0){
            ArrayList<Notification> notifications = new ArrayList<>();
            while (cursor.moveToNext()){
                notifications.add(NotificationBinder(cursor));
            }
            return notifications;
        }
        return null;
    }

    public void ReadAllNotificaitons(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE Notification SET IsRead = 1 WHERE IsRead = 0");
    }

    public void MarkSpecificNotificationAsRead(int id){
        if (id > 0){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("UPDATE Notification SET IsRead = 1 WHERE Id = " + id);
        }
    }

    public Notification NotificationBinder(Cursor cursor){
        Notification notification = new Notification();

        notification.Id = cursor.getInt(0);
        notification.Title = cursor.getString(1).toString();
        notification.Description = cursor.getString(2).toString();

        return notification;
    }

    //endregion

    //region General Methods

    public boolean IsColumnExists(String tablename, String columnname){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM pragma_table_info('"+tablename+"') WHERE name = '"+columnname+"'", null);
        if (cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void ExecuteQuery(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        if (!IsColumnExists("User", "UserType")){
//            sqLiteDatabase.execSQL("ALTER TABLE User ADD UserType INTEGER DEFAULT 0 NOT NULL");
//        }
//         sqLiteDatabase.execSQL("DELETE FROM User");
//        sqLiteDatabase.execSQL("insert into User (firstname, lastname, email, username, password, createdon, usertype) values ('ali','haider','ali@gmail.com','ali','123456',date(), 1)");
//        sqLiteDatabase.execSQL("CREATE TABLE Notification (Id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT, CreatedOn DATETIME, IsRead BIT)");
//
//        sqLiteDatabase.execSQL("UPDATE Notification SET IsRead = 0");
//
            //sqLiteDatabase.execSQL("alter table User drop column ProfileImage");

//        if (!IsColumnExists("User", "ProfileImage")){
//            sqLiteDatabase.execSQL("ALTER TABLE User ADD ProfileImage BLOG");
//        }
    }

    //endregion
}
