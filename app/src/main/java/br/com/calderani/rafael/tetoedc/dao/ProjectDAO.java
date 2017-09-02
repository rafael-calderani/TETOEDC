package br.com.calderani.rafael.tetoedc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.model.Project;

/**
 * Created by Rafael on 05/08/2017.
 */

public class ProjectDAO {
    private DBOpenHelper dbHelper;
    private final String SQL_LIST_PROJECTS =
            "SELECT name, description, managersFromTeam, managersFromCommunity, status, createdOn, modifiedOn, completedOn FROM project";
    private final String SQL_EXISTS_PROJECT = "SELECT 1 FROM project WHERE name = ?";

    public ProjectDAO(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public boolean exists(String projectName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try (Cursor c = db.rawQuery(SQL_EXISTS_PROJECT, new String[] { projectName })) {

            return c != null && c.getCount() > 0;
        }
    }

    public boolean insert(Project project){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String date = df.format(c.getTime());

        ContentValues values = new ContentValues();
        values.put("name", project.getName());
        values.put("description", project.getDescription());
        values.put("managersFromTeam", project.getManagersFromTeam());
        values.put("managersFromCommunity", project.getManagersFromCommunity());
        values.put("status", project.getStatus());
        values.put("createdOn", date);
        values.put("modifiedOn", date);
        if (project.getStatus() == "Completed" || project.getStatus() == "Concluído") {
            values.put("completedOn", date);
        }

        long dbResult = db.insert(DBOpenHelper.TABLE_PROJECT, null, values);

        return dbResult == 1;
    }

    public boolean update(Project project){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        String date = df.format(c.getTime());

        ContentValues values = new ContentValues();
        values.put("description", project.getDescription());
        values.put("managersFromTeam", project.getManagersFromTeam());
        values.put("managersFromCommunity", project.getManagersFromCommunity());
        values.put("status", project.getStatus());
        values.put("modifiedOn", date);
        if (project.getStatus() == "completed") {
            values.put("completedOn", date);
        }

        long dbResult = db.update(DBOpenHelper.TABLE_PROJECT, values, "name = ?", new String[] { project.getName() });

        return dbResult == 1;
    }

    public boolean save(Project project) {
        if (exists(project.getName()))
            return update(project);
        else return insert(project);
    }

    public boolean delete(String name){
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            long dbResult = db.delete(DBOpenHelper.TABLE_PROJECT, "name = ?", new String[]{ name });

            return dbResult == 1;
        }
    }

    public List<Project> listProjects() {
        List<Project> projects = new LinkedList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery(SQL_LIST_PROJECTS, null)) {
                Project project;
                while (cursor.moveToNext()) {
                    project = new Project();
                    project.setName(cursor.getString(0));
                    project.setDescription(cursor.getString(1));
                    project.setManagersFromTeam(cursor.getString(2));
                    project.setManagersFromCommunity(cursor.getString(3));
                    project.setStatus(cursor.getString(4));
                    project.setCreatedOn(cursor.getString(5));
                    project.setModifiedOn(cursor.getString(6));
                    project.setCompletedOn(cursor.getString(7));

                    projects.add(project);
                }
            }
        }
        return projects;
    }
}
