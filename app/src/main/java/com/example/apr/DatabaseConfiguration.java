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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("alter table User ADD UserType INT NOt NULL DEFAULT 0");
        }
    }

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
        return GenericFunctions.ConvertCursorToClassCollection(cursor, User.class);
    }

    public ArrayList<Product> GetAllProducts(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Product", null);
        return GenericFunctions.ConvertCursorToClassCollection(cursor, Product.class);
    }

    public ArrayList<Order> GetAllOrders(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM [Order]", null);
        return GenericFunctions.ConvertCursorToClassCollection(cursor, Order.class);
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
                    user.Id = Integer.parseInt(cursor.getString(0).toString());
                    user.FirstName = cursor.getString(1).toString();
                    user.LastName = cursor.getString(2).toString();
                    user.Email = cursor.getString(3).toString();
                    user.Username = cursor.getString(4).toString();
                    user.Password = cursor.getString(5).toString();
                    //user.CreatedOn = cursor.getString(6).toString();
                    user.UserType = Integer.parseInt(cursor.getString(7).toString());
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
        // slacsqLiteDatabase.execSQL("insert into User (firstname, lastname, email, username, password, createdon, usertype) values ('ali','haider','ali@gmail.com','ali','123456',date(), 1)");
    }


}
