package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;

import java.util.Date;

public class afterActivity extends AppCompatActivity {
    private final AppCompatActivity activity = afterActivity.this;

    private TextView txtout1;
    private TextView txtout2;
    private TextView txtout3;
    private TextView txtout4;
    private TextView txtout5;
    private Button btn;
    private Button btn1;
    private Button btn2;
    private DatabaseHelper databaseHelper = new DatabaseHelper(activity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);
        int i=(int) new Date().getTime();

        Bundle b;
        b= getIntent().getExtras();
        final String str=b.getString("userid");
        databaseHelper.calfine(i,str);
        printdata(str);
        btn=(Button)findViewById(R.id.btnreturn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updatebooks(str);
                txtout4.setText("no book");
                txtout5.setText("no book");
            }
        });
        btn1=(Button)findViewById(R.id.issue);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentissue = new Intent(getApplicationContext(), issueActivity.class);
                intentissue.putExtra("userid",str);
                startActivity(intentissue);
            }
        });
        btn2=(Button)findViewById(R.id.logout);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intentlogin);
            }
        });
    }






    private void printdata(String str) {

        //txtout1 = (TextView) findViewById(R.id.Name);
        //txtout1.setText("anudeep");

        Cursor res = databaseHelper.getAllData();

        if(res.getCount() != 0)
        {

            while (res.moveToNext()) {
                if(res.getString(2).equals(str))
                {
                    txtout1 = (TextView) findViewById(R.id.Name);
                    txtout2 = (TextView) findViewById(R.id.Userid);
                    txtout3 = (TextView) findViewById(R.id.fine);
                    txtout4 = (TextView) findViewById(R.id.book1);
                    txtout5 = (TextView) findViewById(R.id.book2);
                    txtout1.setText(res.getString(1));
                    txtout2.setText(res.getString(2));
                    if(res.getString(4)==null)
                    {
                        txtout3.setText("0");
                    }
                    else{
                        txtout3.setText(res.getString(4));
                    }

                    if(res.getString(5)==null)
                    {
                        txtout4.setText("no book");
                    }
                    else{
                        txtout4.setText(res.getString(5));
                    }
                    if(res.getString(6)==null)
                    {
                        txtout5.setText("no book");
                    }
                    else{
                        txtout5.setText(res.getString(6));
                    }

                }



            }
        }
    }
}
