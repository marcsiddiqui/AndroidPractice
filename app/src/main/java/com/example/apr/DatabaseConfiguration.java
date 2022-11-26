package com.example.apr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("alter table User ADD UserType INT NOt NULL DEFAULT 0");
        }
    }

    public boolean Insert(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = GenericFunctions.ConvertClassIntoContentValues(user);
        long result = sqLiteDatabase.insert("User", null, contentValues);
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
                user.FirstName = cursor.getString(1).toString();
                user.LastName = cursor.getString(2).toString();
                user.Email = cursor.getString(3).toString();
                user.Username = cursor.getString(4).toString();
                user.Password = cursor.getString(5).toString();
                user.UserType = Integer.parseInt(cursor.getString(7).toString());
                //user.CreatedOn = cursor.getString(6).toString();
            }
            return user;
        }else{
            return null;
        }
    }

    public ArrayList<User> GetAllUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM User", null);
        ArrayList<User> allUsers = new ArrayList<>();
        while(cursor.moveToNext()){
            User user = new User();
            user.FirstName = cursor.getString(1).toString();
            user.LastName = cursor.getString(2).toString();
            user.Email = cursor.getString(3).toString();
            user.Username = cursor.getString(4).toString();
            allUsers.add(user);
        }
        return allUsers;
    }

    public Cursor ViewAllUsers(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        if (id > 0){
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE Id=" + id, null);
            return cursor;
        }
        else {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user", null);
            return cursor;
        }
    }

    public Integer DeleteUser(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("User", "Id = " + id, null);
    }

    public boolean UpdateUser(int id, String username, String password, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", username);
        contentValues.put("Password", password);
        contentValues.put("Email", email);
        long result = sqLiteDatabase.update("User", contentValues, "Id = " + id, null);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

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
        if (!IsColumnExists("User", "UserType")){
            sqLiteDatabase.execSQL("ALTER TABLE User ADD UserType INTEGER DEFAULT 0 NOT NULL");
        }
        // sqLiteDatabase.execSQL("DELETE FROM User");
        // sqLiteDatabase.execSQL("insert into User (firstname, lastname, email, username, password, createdon, usertype) values ('ali','haider','ali@gmail.com','ali','123456',date(), 1)");
    }
}
