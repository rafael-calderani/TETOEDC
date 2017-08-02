package br.com.calderani.rafael.tetoedc;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static br.com.calderani.rafael.tetoedc.api.PermissionRequestCodes.GPS_FINE_PERMISSION;

public class CommunitiesActivity extends AppCompatActivity {
    @BindView(R.id.rvCommunities)
    RecyclerView rvCommunities;
    private CommunitiesAdapter cAdapter;
    private CommunityAPI cService;

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
                        if(ContextCompat.checkSelfPermission(CommunitiesActivity.this,
                                ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                            Toast.makeText(
                                    CommunitiesActivity.this,
                                    R.string.gpsPermissionRequest,
                                    Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(CommunitiesActivity.this,
                                    new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                    GPS_FINE_PERMISSION);

                        }
                        iniciarMapa(community);
                    }
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


    private void iniciarMapa(Community community) {
        Intent communityDetails = new Intent(
                CommunitiesActivity.this,
                CommunityMapsActivity.class
        );

        communityDetails.putExtra("COMMUNITY", community);

        startActivity(communityDetails);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case GPS_FINE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    // Sucesso!
                }
                break;
            }

        }
    }
}
