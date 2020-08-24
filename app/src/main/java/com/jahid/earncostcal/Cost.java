package com.jahid.earncostcal;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Cost extends AppCompatActivity {

    ListView listCost;
    DatabaseHelper db;

    TextView tvCost;

    HashMap<String, String> map =  new HashMap<String, String>();
    ArrayList<HashMap<String, String> > arrayList = new ArrayList< HashMap<String, String>  >();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        listCost=(ListView) findViewById(R.id.listCost);
        db = new DatabaseHelper(this);


        tvCost = (TextView) findViewById(R.id.tvCost);

        int earn = db.Total("table_cost");
        String ss_earn = addCommas(""+earn);
        tvCost.setText("Total: "+ss_earn);





        Cursor cursor=db.getCost();
        while (cursor.moveToNext())
        {
            int amount=cursor.getInt(1);
            int id =cursor.getInt(0);
            String purpose=cursor.getString(2);
            String date=cursor.getString(3);
            // mylist.add(myString);

            map = new HashMap< String, String >();
            map.put("id", ""+id);
            map.put("amount", ""+amount);
            map.put("purpose", purpose );
            map.put("date", date);
            arrayList.add(map);

        }



        adapter = new CustomAdapter(Cost.this, arrayList);
        listCost.setAdapter(adapter);

    }


    //**********************************************

    public  class CustomAdapter extends BaseAdapter {

        ArrayList<HashMap<String, String> > myArrayList;
        Context myContext;



        public  CustomAdapter (Context context, ArrayList<HashMap<String, String> > arrList ){
            myArrayList = arrList;
            myContext = context;

        }


        @Override
        public int getCount() {
            return myArrayList.size() ;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {



            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);

            HashMap<String, String> map = new HashMap<String, String>();
            map = myArrayList.get(position);
            String amount = map.get("amount");
            String purpose = map.get("purpose");
            String date = map.get("date");
            String id = map.get("id");

            final TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
            TextView tvPurpose = (TextView) view.findViewById(R.id.tvPurpose);
            final TextView tvID = (TextView) view.findViewById(R.id.tvID);

            Button bDelete = (Button) view.findViewById(R.id.bDelete);

            //final TextView tvDisplay=(TextView) view.findViewById(R.id.tvDisplay);

            tvAmount.setText(""+amount+" /=");
            tvDate.setText(date);
            tvPurpose.setText(purpose);
            tvID.setText(id);





            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String id = tvID.getText().toString();

                    db = new DatabaseHelper(Cost.this);

                    db.deleteFromTable("table_cost", ""+id);
                    Toast.makeText(getApplicationContext(), "Deleted!",  Toast.LENGTH_LONG).show();
                    myArrayList.remove(position);
                    adapter.notifyDataSetChanged();
                    listCost.invalidateViews();


                    int earn = db.Total("table_cost");
                    String ss_earn = addCommas(""+earn);
                    tvCost.setText("Total: "+ss_earn);






                }
            });

            return view;
        }



    }

    //*******************************************************



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
