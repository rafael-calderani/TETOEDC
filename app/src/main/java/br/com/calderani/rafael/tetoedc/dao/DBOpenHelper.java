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

    private Context ctx;

    public DBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        this.ctx = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        DBOpenHelper.db = db;
        executeSQLScript(ctx , R.raw.local_db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: on database upgrade
    }

    /**
     * executeSQLScript: Executa script SQL no banco de dados informado
     * @param ctx: Contexto no qual a chamada está sendo executada (utilizada para obter os recursos do app)
     * @param sqlResId: ID do recurso específico que será executado
     */
    private void executeSQLScript(Context ctx, Integer sqlResId) {
        Resources res = ctx.getResources();
        try {
            InputStream stream = res.openRawResource(sqlResId);
            String sqlScript = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
            db.execSQL(sqlScript);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException( "Couldn't execute SQL script." , e);
        }
    }
    private void executeSQLScript(String sqlScript) {
        db.execSQL(sqlScript);
    }
}
