package br.com.calderani.rafael.tetoedc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.model.Project;
import br.com.calderani.rafael.tetoedc.model.User;

/**
 * Created by Rafael on 05/08/2017.
 */

public class ProjectDAO {
    private DBOpenHelper dbHelper;
    public static final String TABLE_NAME = "project";
    public static final String SQL_LIST_PROJECTS =
            "SELECT name, description, status, createdOn, modifiedOn, completedOn FROM project";
    public static final String SQL_GET_PROJECT_BY_NAME =
            "SELECT description, status, createdOn, modifiedOn, completedOn FROM user WHERE name = ?";
    public static final String SQL_EXISTS_PROJECT = "SELECT 1 FROM user WHERE name = ?";


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
        ContentValues values = new ContentValues();
        values.put("name", project.getName());
        values.put("description", project.getDescription());
        values.put("status", project.getStatus());
        //values.put("createdOn", project.getCreatedOn());
        values.put("completedOn", project.getCompletedOn());

        long dbResult = db.insert(TABLE_NAME, null, values);

        return dbResult == 1;
    }

    public boolean update(Project project){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description", project.getDescription());
        values.put("status", project.getStatus());
        values.put("modifiedOn", ""); //TODO: get current time and date
        values.put("completedOn", project.getCompletedOn());

        long dbResult = db.update(TABLE_NAME, values, "name = ?", new String[] { project.getName() });

        return dbResult == 1;
    }

    public  boolean save(Project project) {
        if (exists(project.getName()))
            return update(project);
        else return insert(project);
    }

    public boolean delete(String name){
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            long dbResult = db.delete(TABLE_NAME, "name = ?", new String[]{ name });

            return dbResult == 1;
        }
    }

    public Project getProjectByName(String name){
        Project project = null;
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            if(exists(name)){
                try (Cursor cursor = db.rawQuery(SQL_GET_PROJECT_BY_NAME, new String[] { name })) {
                    cursor.moveToNext();
                    project = new Project();
                    project.setName(name);
                    project.setDescription(cursor.getString(1));
                    project.setStatus(cursor.getString(2));
                    project.setCreatedOn(cursor.getString(3));
                    project.setModifiedOn(cursor.getString(4));
                    project.setCompletedOn(cursor.getString(5));
                }
            }
        }

        return project;
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
                    project.setStatus(cursor.getString(2));
                    project.setCreatedOn(cursor.getString(3));
                    project.setModifiedOn(cursor.getString(4));
                    project.setCompletedOn(cursor.getString(5));

                    projects.add(project);
                }
            }
        }
        return projects;
    }
}
