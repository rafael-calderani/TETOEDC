package br.com.calderani.rafael.tetoedc.api;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.calderani.rafael.tetoedc.R;
import retrofit2.Retrofit;

/**
 * Created by Rafael on 09/07/2017.
 */

public class ApiUtils {
    private static final String HEROKU = "heroku";
    private static final String HEROKU_URL = "http://tetoedc.herokuapp.com";

    private static final String MOCKY = "mocky";
    private static final String MOCKY_URL = "http://www.mocky.io";

    public static CommunityAPI getCommunitiesAPI() {
        return RetrofitClient.getClient(HEROKU, HEROKU_URL).create(CommunityAPI.class);
    }

    public static UserAPI getUserAPI() {
        return RetrofitClient.getClient(MOCKY,MOCKY_URL).create(UserAPI.class);
    }

    public void ConfirmationDialog(Context ctx, int messageResource,
                                     DialogInterface.OnClickListener onClickYes,
                                     DialogInterface.OnClickListener onClickNo){

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(R.string.app_name);
        builder.setMessage(messageResource);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton(R.string.yes, onClickYes);
        if (onClickNo == null) {
            onClickNo = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            };
        }

        builder.setNegativeButton(R.string.no, onClickNo);
        AlertDialog alert = builder.create();
        alert.show();
    }
}