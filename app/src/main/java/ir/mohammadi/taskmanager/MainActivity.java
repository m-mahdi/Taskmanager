package ir.mohammadi.taskmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        final Button register = (Button) findViewById(R.id.register);
        final EditText u_name =(EditText) findViewById(R.id.username);
        final EditText pass = (EditText) findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiUrl = "https://api.backtory.com/auth/login";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
//                        Intent intent=new Intent(MainActivity.this,TestActivity.class);
//                        startActivity(intent);
                        Log.i("Result", s);
                        Gson g = new Gson();
                        HashMap<String,String> map  = new HashMap<>();
                         HashMap map1= g.fromJson(s, HashMap.class);
                         G.token =(String) map1.get("access_token");
                        Toast.makeText(MainActivity.this, (String) map1.get("access_token"), Toast.LENGTH_SHORT).show();

                           }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_SHORT).show();
//                        Log.i("Error", volleyError.getMessage());
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
//                        params.put("username", "mahdi74");
//                        params.put("password", "mahdi74");
                        params.put("username", u_name.getText().toString());
                        params.put("password", pass.getText().toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-Backtory-Authentication-Id", "5a9314fbe4b04e579ee1edbe");
                        headers.put("X-Backtory-Authentication-Key", "5a9314fbe4b05bb64131ee38");
                        return headers;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(stringRequest);



            }
        });

    }
}
