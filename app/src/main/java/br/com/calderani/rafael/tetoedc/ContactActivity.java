package br.com.calderani.rafael.tetoedc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends AppCompatActivity {

    @BindView(R.id.etSubject)
    EditText etSubject;

    @BindView(R.id.etBody)
    EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btSend)
    public void send(){
        //TODO: implement method
    }

}
