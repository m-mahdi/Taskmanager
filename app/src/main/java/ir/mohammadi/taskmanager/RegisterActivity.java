package ir.mohammadi.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button =(Button) findViewById(R.id.register_now);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//
//                String url = "https://api.backtory.com/auth/users";
//
////                RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("firstName", "mahdi");
//                params.put("lastName", "mohamamdi");
//                params.put("username", "mohamamdi");
//                params.put("password", "123456");
//                params.put("email", "m11@yaho.com");
//                params.put("phoneNumber", "444444487");
//                params.put("avatar", "mydomain.com/avatar.png");
//
//
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.d("TAG", response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("TAG", error.getMessage(), error);
//                    }
//                    })
//                {
//                    //no semicolon or coma
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("Content-Type", "application/json");
//                        params.put("X-Backtory-Authentication-Id", "5a9314fbe4b04e579ee1edbe");
//                        return params;
//                    }
//                };

                try {
//                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    String URL = "https://api.backtory.com/auth/users";;
                    JSONObject jsonBody = new JSONObject();
//             

                    jsonBody.put("firstName", "mahdi");
                    jsonBody.put("lastName", "mohamamdi");
                    jsonBody.put("username", "mohamamdi");
                    jsonBody.put("password", "123456");
                    jsonBody.put("email", "m11@yaho.com");
                    jsonBody.put("phoneNumber", "444444487");
                    jsonBody.put("avatar", "mydomain.com/avatar.png");
//
                    final String mRequestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("LOG_VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOG_VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                                return null;
                            }
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            params.put("X-Backtory-Authentication-Id", "5a9314fbe4b04e579ee1edbe");
                            return params;
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {

                                responseString = String.valueOf(response.statusCode);

                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

//                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }
}

