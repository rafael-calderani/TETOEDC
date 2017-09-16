package br.com.calderani.rafael.tetoedc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.api.Validation;
import br.com.calderani.rafael.tetoedc.dao.ProjectDAO;
import br.com.calderani.rafael.tetoedc.model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class ProjectManagementActivity extends AppCompatActivity {

    @BindView(R.id.etProjectName)
    EditText etProjectName;

    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.etManagersFromTeam)
    EditText etManagersFromTeam;

    @BindView(R.id.etManagersFromCommunity)
    EditText etManagersFromCommunity;

    @BindView(R.id.ddlStatus)
    Spinner ddlStatus;

    @BindView(R.id.tvCreatedOn)
    TextView tvCreatedOn;

    @BindView(R.id.tvModifiedOn)
    TextView tvModifiedOn;

    @BindView(R.id.tvCompletedOn)
    TextView tvCompletedOn;

    @BindView(R.id.btDeleteProject)
    Button btDeleteProject;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_management);
        ButterKnife.bind(this);

        project = this.getIntent().getParcelableExtra("PROJECT");
        if (project != null) { // Existing project, must prepare edit screen
            etProjectName.setText(project.getName());
            etProjectName.setEnabled(false);
            etDescription.setText(project.getDescription());
            etManagersFromTeam.setText(project.getManagersFromTeam());
            etManagersFromCommunity.setText(project.getManagersFromCommunity());
            String status = project.getStatus();
            String[] statuses = this .getResources().getStringArray(R.array.status_array);
            int statusPos = Arrays.asList(statuses).indexOf(status);
            ddlStatus.setSelection(statusPos);
            tvCreatedOn.setText(project.getCreatedOn());
            tvModifiedOn.setText(project.getModifiedOn());
            if (!project.getCompletedOn().isEmpty()) {
                tvCompletedOn.setText(project.getCompletedOn());
            }
            btDeleteProject.setVisibility(View.VISIBLE);
        }
        else { // New project
            project = new Project();
        }
    }

    @OnClick(R.id.btSaveProject)
    public void saveProject() {
        if (!validateRecord()) {
            Toast.makeText(this, "Please fill out every mandatory field.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String pName = etProjectName.getText().toString();
        final String pDescription = etDescription.getText().toString();
        final String pManagersTeam = etManagersFromTeam.getText().toString();
        final String pManagersCommunity = etManagersFromCommunity.getText().toString();
        final String pStatus = ddlStatus.getSelectedItem().toString();

        project.setName(pName);
        project.setDescription(pDescription);
        project.setManagersFromTeam(pManagersTeam);
        project.setManagersFromCommunity(pManagersCommunity);
        project.setStatus(pStatus);

        if(new ProjectDAO(this).save(project)) {
            Toast.makeText(this, R.string.project_creation_success,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.project_creation_error,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        setResult(Activity.RESULT_OK, null);
        finish();
    }

    @OnClick(R.id.btDeleteProject)
    public void deleteProject() {
        (new ApiUtils()).ConfirmationDialog(this, R.string.project_deletion_confirm,
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ProjectDAO projectDAO = new ProjectDAO(ProjectManagementActivity.this);
                int message = R.string.project_deletion;
                if (!projectDAO.delete(project.getName())) {
                    message = R.string.project_deletion_error;
                }

                Toast.makeText(ProjectManagementActivity.this, message, Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK, null);
                finish();
            }
        }, null);
    }

    @OnFocusChange({R.id.etProjectName, R.id.etDescription, R.id.etManagersFromTeam, R.id.etManagersFromCommunity})
    void validateRequiredFields(View v, boolean hasFocus){
        if (hasFocus) return; // validate only on lost focus
        EditText etCaller = (EditText)v;
        Validation.hasText(etCaller,
                getResources().getString(R.string.validation_required_field));
    }

    boolean validateRecord() {
        return etProjectName.getError() == null &&
                etDescription.getError() == null &&
                etManagersFromTeam.getError() == null &&
                etManagersFromCommunity.getError() == null;
    }
}
