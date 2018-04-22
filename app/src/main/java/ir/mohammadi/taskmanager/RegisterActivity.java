package ir.mohammadi.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        final Button button =(Button) findViewById(R.id.register_now);
        final EditText firstName =(EditText) findViewById(R.id.firstName);
        final EditText lastName =(EditText) findViewById(R.id.lastName);
        final EditText username =(EditText) findViewById(R.id.rej_username);
        final EditText password =(EditText) findViewById(R.id.rej_password);
        final EditText email =(EditText) findViewById(R.id.rej_email);
        final EditText phoneNumber =(EditText) findViewById(R.id.phoneNumber);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String url = "https://api.backtory.com/auth/users";
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Map<String, String>  params = new HashMap<String, String>();
                params.put("firstName", firstName.getText().toString());
                params.put("lastName",lastName.getText().toString());
                params.put("username", username.getText().toString());
                params.put("password",password.getText().toString());
                params.put("email", email.getText().toString());
                params.put("phoneNumber", phoneNumber.getText().toString());
                params.put("avatar", "mydomain.com/avatar.png");
//

                JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                    Toast.makeText(RegisterActivity.this, "کاربر با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "خطا رخ داده است لطفا مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
//
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-Backtory-Authentication-Id", "5a9314fbe4b04e579ee1edbe");
                        headers.put("X-Backtory-Authentication-Key", "5a9314fbe4b05bb64131ee38");
                        return headers;
                    }

                };
                requestQueue.add(req);
            }

        });

    }

}

