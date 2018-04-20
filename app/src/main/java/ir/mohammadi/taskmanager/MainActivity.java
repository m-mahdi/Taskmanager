package ir.mohammadi.taskmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiUrl = "https://api.backtory.com/auth/login";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
//                        Intent intent=new Intent(MainActivity.this,TestActivity.class);
//                        startActivity(intent);
                        Log.i("Result", s);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                        Log.i("Error", volleyError.getMessage());
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", "mahdi74");
                        params.put("password", "mahdi74");

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
