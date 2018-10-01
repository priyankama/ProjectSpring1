package com.example.bandhan.projectspring;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG="UserActivity";
    private static final String Login_TAG = "LoginActivity";
    private ListView lv;
    ArrayList<HashMap<String, String>> userList;


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle bundle =  getIntent().getExtras();
        assert bundle != null;
        String user = bundle.getString("username");
        TextView greetingTextView = (TextView) findViewById(R.id.greeting_text_view);
        Button btnLogOut = (Button) findViewById(R.id.logout_button);
        greetingTextView.setText("hello : "+user);
        userList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list_booking);

        new GetContacts().execute();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("StaticFieldLeak")
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://www.mocky.io/v2/5bb049303100008000fb60f7";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.i("email",jsonObj.getString("email"));
                    Log.i("password",jsonObj.getString("password"));
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("");
//
//                    // looping through All Contacts
//                   for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);
//                        String email = c.getString("email");
//                        String password = c.getString("password");
//                       /* String email = c.getString("email");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");
//
//                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");
//*/
//                        // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        contact.put("email", email);
//                        contact.put("password", password);
                        //contact.put("email", email);
                        //contact.put("mobile", mobile);

                        // adding contact to contact list
//                        userList.add(contact);
//                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(LoginActivity.this, userList,
                    R.layout.ground_list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.tvGroundName, R.id.tvgroundsport});
            lv.setAdapter(adapter);
        }
    }
}



