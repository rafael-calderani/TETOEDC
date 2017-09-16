package br.com.calderani.rafael.tetoedc;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.calderani.rafael.tetoedc.adapter.CommunitiesAdapter;
import br.com.calderani.rafael.tetoedc.adapter.OnItemClickListener;
import br.com.calderani.rafael.tetoedc.api.ApiUtils;
import br.com.calderani.rafael.tetoedc.api.CommunityAPI;
import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.Project;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.GPS_FINE_PERMISSION;

public class CommunitiesActivity extends AppCompatActivity {
    @BindView(R.id.rvCommunities)
    RecyclerView rvCommunities;
    private CommunitiesAdapter cAdapter;
    private CommunityAPI cService;
    private Community community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);

        ButterKnife.bind(this);

        cService = ApiUtils.getCommunitiesAPI();

        cAdapter = new CommunitiesAdapter(new ArrayList<Community>(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(Community community) {
                        CommunitiesActivity.this.community = community;
                        showMap();
                    }
                    @Override
                    public void onItemClick(Project item) {}
                });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvCommunities.setLayoutManager(layoutManager);
        rvCommunities.setAdapter(cAdapter);
        rvCommunities.setHasFixedSize(true);
        rvCommunities.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });

        loadCommunities();
    }


    @AfterPermissionGranted(GPS_FINE_PERMISSION)
    private void showMap() {
        if(EasyPermissions.hasPermissions(CommunitiesActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Intent communityDetails = new Intent(
                    CommunitiesActivity.this,
                    CommunityMapsActivity.class
            );

            communityDetails.putExtra("COMMUNITY", community);

            startActivity(communityDetails);
        }
        else {
            EasyPermissions.requestPermissions(CommunitiesActivity.this,
                    getString(R.string.gpsPermissionRequest), GPS_FINE_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    public void loadCommunities() {
        cService.getCommunities()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Community>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),
                                String.format(getString(R.string.service_error_message), e.getMessage()),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Community> communityList) {
                        cAdapter.update(communityList);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, results, this);
    }
}
