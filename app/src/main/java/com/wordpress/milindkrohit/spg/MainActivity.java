package com.wordpress.milindkrohit.spg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    DBHelper mydb;
    Button srating,ssetup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);
        srating = (Button) findViewById(R.id.button2);
        ssetup = (Button)findViewById(R.id.button);
        srating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onattachAction(1);
            }
        });
        ssetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onattachAction(2);
            }
        });
        new BackgroundTask();

        fragment_selector(1);

    }

    public void fragment_selector(int fragment){
        Fragment new_fragment;
      //  fragment_id = fragment;
        String frag_tag ="";
        switch (fragment){

            case 1:
                new_fragment = new ListScore();
                frag_tag = "list";
                break;
            default:
                new_fragment = new ListScore();
        }


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= manager.beginTransaction();
        fragmentTransaction.replace(R.id.list, new_fragment, frag_tag);
        fragmentTransaction.commit();
    }

    public void onattachAction(int a) {
        FragmentManager manager = getSupportFragmentManager();
        ListScore Mplay = (ListScore) manager.findFragmentByTag("list");

        if(a == 1){
            Mplay.sortby(1);
        }else{
            Mplay.sortby(2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class BackgroundTask extends AsyncTask<Void ,Void,String> {
        String my_url,JSON_STRING;
        @Override
        protected void onPreExecute() {

            my_url = "http://hackerearth.0x10.info/api/payment_portals?type=json&query=list_gateway";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),"DONE NBBBcf fv", Toast.LENGTH_SHORT).show();
            try {
                JSONObject obj = new JSONObject(s);

                JSONArray arr = obj.getJSONArray("payment_gateways");
                for (int j = 0; j < arr.length(); j++) {

                    JSONObject jobj = arr.getJSONObject(j);
                    int id = jobj.getInt("id");
                    String name = jobj.getString("name");
                    Double rating = jobj.getDouble("rating");
                    int tfree = jobj.getInt("setup_fee");
                    mydb.insertContact(name,id,rating,tfree);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fragment_selector(1);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                URL url = new URL(my_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                //httpURLConnection.setRequestProperty("Content-Length", Integer.toString(data.length));
                // httpURLConnection.setFixedLengthStreamingMode();
                // List<NameValuePair> nameValuePairList = new ArrayList<NameValuePairList>();
                //params.add(new BasicNameValuePair("someParam", "someValue"));

                OutputStream output =httpURLConnection.getOutputStream();

                output.close();

                /*conn.setFixedLengthStreamingMode(postdat.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")*/
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!= null){
                    stringBuilder.append(JSON_STRING);
                }
                mydb.insertStr(JSON_STRING);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
