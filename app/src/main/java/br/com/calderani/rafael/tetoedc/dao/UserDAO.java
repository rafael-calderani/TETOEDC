package br.com.calderani.rafael.tetoedc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.model.User;

/**
 * Created by Rafael on 05/08/2017.
 */

public class UserDAO {
    private DBOpenHelper dbHelper;
    public static final String TABLE_NAME = "user";
    public static final String SQL_LIST_USERS =
            "SELECT email, password, name, function, phone, communityName FROM user";
    public static final String SQL_GET_USER_BY_LOGIN =
            "SELECT email, password, name, function, phone, communityName FROM user WHERE email = ?";
    public static final String SQL_EXISTS_USER = "SELECT 1 FROM user WHERE email = ?";
    public static final String SQL_AUTHENTICATE_USER =
            "SELECT 1 FROM user WHERE email = ? AND password = ?";


    public UserDAO(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public boolean exists(String user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try (Cursor c = db.rawQuery(SQL_EXISTS_USER, new String[] { user })) {

            return c != null && c.getCount() > 0;
        }
    }

    public boolean insert(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("name", user.getName());
        values.put("function", user.getFunction());
        values.put("phone", user.getPhone());
        values.put("communityName", user.getCommunityName());

        long dbResult = db.insert(TABLE_NAME, null, values);

        return dbResult == 1;
    }

    public boolean update(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("name", user.getName());
        values.put("function", user.getFunction());
        values.put("phone", user.getPhone());
        values.put("communityName", user.getCommunityName());

        long dbResult = db.update(TABLE_NAME, values, "email = ?", new String[] { user.getEmail() });

        return dbResult == 1;
    }

    public  boolean save(User user) {
        if (exists(user.getEmail()))
            return update(user);
        else return insert(user);
    }

    public boolean delete(String login){
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            long dbResult = db.delete(TABLE_NAME, "email = ?", new String[]{ login });

            return dbResult == 1;
        }
    }

    public User authenticateUser(String email, String password){
        User user = null;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            Cursor c = db.rawQuery(SQL_AUTHENTICATE_USER, new String[] { email, password });
            if(c != null && c.getCount() > 0){
                c.close();
                try (Cursor cursor = db.rawQuery(SQL_GET_USER_BY_LOGIN, new String[] { email })) {
                    cursor.moveToNext();
                    user = new User();
                    user.setEmail(cursor.getString(0));
                    user.setPassword(cursor.getString(1));
                    user.setName(cursor.getString(2));
                    user.setFunction(cursor.getString(3));
                    user.setPhone(cursor.getString(4));
                    user.setCommunityName(cursor.getString(5));
                }
            }
        }

        return user;
    }

    public List<User> listUsers() {
        List<User> users = new LinkedList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery(SQL_LIST_USERS, null)) {
                User user;
                while (cursor.moveToNext()) {
                    user = new User();
                    user.setEmail(cursor.getString(0));
                    user.setPassword(cursor.getString(1));
                    user.setName(cursor.getString(2));
                    user.setFunction(cursor.getString(3));
                    user.setPhone(cursor.getString(4));
                    user.setCommunityName(cursor.getString(5));

                    users.add(user);
                }
            }
        }
        return users;
    }
}
