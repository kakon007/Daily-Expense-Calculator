package com.jahid.earncostcal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.jahid.earncostcal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private AdView mAdView;


    Button badd,bsub,historyearn,historycost;
    EditText edamount,edpurpose;
    DatabaseHelper db;
    TextView tv;
    private String table_name;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);




     badd=(Button) findViewById(R.id.addearning);
     bsub=(Button) findViewById(R.id.addcost);
     edamount=(EditText) findViewById(R.id.edamount);
     edpurpose=(EditText) findViewById(R.id.edpurpose);
     tv=(TextView) findViewById(R.id.tvDisplay);
     historyearn=(Button) findViewById(R.id.historyEarn);
     historycost=(Button) findViewById(R.id.historyCost);
     db= new DatabaseHelper(this);




     int balance = db.mainBalance();
     tv.setText(""+balance );



        MobileAds.initialize(this, "ca-app-pub-5820051120284844~4697520037");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





     badd.setOnClickListener(new View.OnClickListener()
     {
         @Override
         public void onClick(View view)
         {


             String amount=edamount.getText().toString();

             String purpose=edpurpose.getText().toString();

          if(amount.length()>0 &&  purpose.length()>0)
          {

                             int intAmount = Integer.parseInt(amount);

                      //*********************************************************************************
                             String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                     //*****************************************************************************
                             db.addEarn(intAmount,purpose,date);
                             edamount.setText("");
                             edpurpose.setText("");

                            int balance = db.mainBalance();
                            String SS_balance = addCommas(""+balance);
                            tv.setText(""+SS_balance);

          }
            else {

              Toast.makeText(getApplicationContext(),"Please Enter a value", Toast.LENGTH_SHORT).show();
          }






         }
     });

     bsub.setOnClickListener(new View.OnClickListener()
     {
         @Override
         public void onClick(View view)
         {
             String amount=edamount.getText().toString();

             String purpose=edpurpose.getText().toString();

             if(amount.length()>0 &&  purpose.length()>0){

                 int intAmount = Integer.parseInt(amount);
                 //****************************
                 String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                 //****************************
                 db.addCost(intAmount,purpose,date);
                 edamount.setText("");  edpurpose.setText("");


                 int balance = db.mainBalance();
                 String SS_balance = addCommas(""+balance);
                 tv.setText(""+SS_balance);

             }

             else {

                 Toast.makeText(getApplicationContext(),"Please Enter a purpose", Toast.LENGTH_SHORT).show();
             }



         }
     });



historycost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myInt1 = new Intent(MainActivity.this,Cost.class);
        startActivity(myInt1);

    }
});

historyearn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent myInt2=new Intent(MainActivity.this, com.jahid.earncostcal.Earn.class);
        startActivity(myInt2);
    }
});



    }


  //*******************************************************


    @Override
    protected void onResume() {
        super.onResume();
        if (tv !=null && db != null ){
            int balance =  db.mainBalance();
            String SS_balance = addCommas(""+balance);
            tv.setText(""+SS_balance);
        }
    }




//************************************************************************************


    private String addCommas(String digits) {

        int len = digits.length();

        String result = digits;

        if (digits.length() <= 3)
            return digits; // If the original value has 3 digits or  less it returns that value

        for (int i = 0; i < (len - 1) / 3; i++) {

            int commaPos = len - 3 - (3 * i); // comma position in each cicle

            result = result.substring(0, commaPos) + "," + result.substring(commaPos);
        }
        return result;
    }


    //########################################################################







}
