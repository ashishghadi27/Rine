package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.asg.ashish.rine.R.drawable.malewhite;

public class BmiActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private EditText age, foot, inch, weigh;
    int malecount = 1, femalecount = 0;
    String gender;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        final Button male = (Button)findViewById(R.id.Male);
        final Button female = (Button)findViewById(R.id.Female);
        final Button submit = (Button)findViewById(R.id.submitdata);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = (EditText)findViewById(R.id.edittextage);
                foot = (EditText)findViewById(R.id.foot);
                inch = (EditText)findViewById(R.id.inch);
                weigh = (EditText)findViewById(R.id.edittextweight);
                String Age = age.getText().toString();
                String feet = foot.getText().toString();
                String in = inch.getText().toString();
                String kg = weigh.getText().toString();
                if(!Age.equals("")&&!feet.equals("")&&!in.equals("")&&!kg.equals(""))
                {
                    Intent i = new Intent(BmiActivity.this, Bmicalulate.class);
                    i.putExtra("age", Age);
                    i.putExtra("feet", feet);
                    i.putExtra("inch", in);
                    i.putExtra("weight", kg);
                    startActivity(i);
                    sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("age", Age);
                    editor.putString("height", feet+"' "+in+'"');
                    editor.putString("weight", kg);
                    if(malecount == 1)
                        gender = "Male";
                    else
                        gender = "Female";
                    editor.putString("gender", gender);
                    editor.apply();


                }
                else{
                    Toast.makeText(BmiActivity.this,"Enter Complete Information", Toast.LENGTH_SHORT).show();
                }



            }
        });


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malecount = 1;
                Drawable maledraw = getResources().getDrawable(R.drawable.malewhite);
                male.setBackground(maledraw);
                Drawable femaledraw = getResources().getDrawable(R.drawable.female);
                female.setBackground(femaledraw);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femalecount = 1;
                Drawable femaledraw = getResources().getDrawable(R.drawable.femalewhite);
                female.setBackground(femaledraw);
                Drawable maledraw = getResources().getDrawable(R.drawable.male);
                male.setBackground(maledraw);
            }
        });

    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }

    public void back(View view){
        this.onBackPressed();
    }


}

