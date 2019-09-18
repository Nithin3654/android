package com.androidtutorialshub.loginregister.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;

public class issueActivity extends AppCompatActivity {
    private final AppCompatActivity activity = issueActivity.this;
    private TextInputLayout textInputLayoutBook;
    private EditText bookname;
    private Button btnissue;
    private Button info;
    private TextView message;
    private DatabaseHelper databaseHelper = new DatabaseHelper(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Bundle b;
        b= getIntent().getExtras();
        final String userid=b.getString("userid");
        bookname=(EditText) findViewById(R.id.book);
        Editable newTxt=(Editable)bookname.getText();

        btnissue=(Button) findViewById(R.id.issue);
        btnissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag=databaseHelper.issuebooks(bookname.getText().toString(),userid);
                message=(TextView) findViewById(R.id.message);
                if(flag==0)
                {
                    message.setText("cant be issued");
                }
                else if (flag==1)
                {
                    message.setText("issued successfully");
                }
                //message=(TextView) findViewById(R.id.message);
                //message.setText(bookname.getText().toString());
            }
        });
        info=(Button) findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentinfo = new Intent(getApplicationContext(), afterActivity.class);
                intentinfo.putExtra("userid",userid);
                startActivity(intentinfo);
            }
        });
    }
}
