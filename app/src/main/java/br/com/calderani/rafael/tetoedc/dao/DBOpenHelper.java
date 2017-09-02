package br.com.calderani.rafael.tetoedc.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.io.CharStreams;

import br.com.calderani.rafael.tetoedc.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rafael on 05/08/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tetoedc.db";
    private static final int DB_VERSION = 1;
    private static SQLiteDatabase db;

    // Table User Data
    static final String TABLE_USER = "user";
    private static final String CREATE_TBL_USER = "CREATE TABLE " + TABLE_USER + " (" +
            " id INTEGER PRIMARY KEY NOT NULL," +
            " email varchar ( 100 ) NOT NULL," +
            " password varchar ( 20 )  NOT NULL," +
            " name varchar ( 255 )  NULL," +
            " function varchar ( 20 ) NOT NULL," +
            " communityName varchar ( 50 ) NOT NULL," +
            " phone varchar ( 20 ) NULL," +
            " unique(email) ON CONFLICT replace)";

    // Table Project Data
    static final String TABLE_PROJECT = "project";
    private static final String CREATE_TBL_PROJECT = "CREATE TABLE " + TABLE_PROJECT + " (" +
            " name varchar ( 100 ) NOT NULL," +
            " description varchar ( 255 )  NULL," +
            " managersFromTeam varchar ( 255 )  NULL," +
            " managersFromCommunity varchar ( 255 )  NULL," +
            " status varchar ( 20 ) NOT NULL," +
            " createdOn varchar ( 20 ) NOT NULL," +
            " modifiedOn varchar ( 20 ) NOT NULL," +
            " completedOn varchar ( 20 ) NULL," +
            " unique(name) ON CONFLICT replace)";

    public DBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBOpenHelper.db = db;

        executeSQLScript(CREATE_TBL_USER);
        executeSQLScript(CREATE_TBL_PROJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: on database upgrade
    }

    /**
     * executeSQLScript: Executa script SQL no banco de dados informado
     * @param sqlScript: Script SQL a ser executado
     */
    private void executeSQLScript(String sqlScript) {
        db.execSQL(sqlScript);
    }
}
