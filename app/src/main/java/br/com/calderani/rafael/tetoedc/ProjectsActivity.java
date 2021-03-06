package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.adapter.OnItemClickListener;
import br.com.calderani.rafael.tetoedc.adapter.OnItemSwipeListener;
import br.com.calderani.rafael.tetoedc.adapter.OnSwipeTouchListener;
import br.com.calderani.rafael.tetoedc.adapter.ProjectsAdapter;
import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.dao.ProjectDAO;
import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectsActivity extends AppCompatActivity {
    @BindView(R.id.rvProjects)
    RecyclerView rvProjects;
    private ProjectsAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        ButterKnife.bind(this);

        pAdapter = new ProjectsAdapter(new ArrayList<Project>(), new OnItemClickListener() {
            @Override
            public void onItemClick(Community item) {}

            @Override
            public void onItemClick(Project item) {
                startProjectActivity(item);
            }
        } /*, new OnItemSwipeListener() {
            @Override
            public void onSwipeLeft(Community item) {}

            @Override
            public void onSwipeLeft(final Project item) {
                (new ApiUtils()).ConfirmationDialog(ProjectsActivity.this,
                        R.string.project_deletion_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProjectDAO projectDAO = new ProjectDAO(ProjectsActivity.this);
                        int message = R.string.project_deletion;
                        if (!projectDAO.delete(item.getName())) {
                            message = R.string.project_deletion_error;
                        }

                        Toast.makeText(ProjectsActivity.this, message, Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK, null);
                        finish();
                    }
                }, null);
            }
        }*/);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProjects.setLayoutManager(layoutManager);
        rvProjects.setAdapter(pAdapter);
        rvProjects.setHasFixedSize(true);
        rvProjects.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });

        loadProjects();
    }

    public void loadProjects() {
        List<Project> projectList = (new ProjectDAO(this)).listProjects();
        if (projectList.size() == 0) {
            Toast.makeText(this, R.string.emptyProjectsList, Toast.LENGTH_SHORT).show();
        }
        pAdapter.update(projectList);

    }

    @OnClick(R.id.fabAddProject)
    public void addProjectClick(){
        startProjectActivity(null);
    }

    public void startProjectActivity(Project project){
        Intent projectDetails = new Intent(
                ProjectsActivity.this,
                ProjectManagementActivity.class
        );

        projectDetails.putExtra("PROJECT", project);

        startActivityForResult(projectDetails, 0, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadProjects();
        }
    }
}
