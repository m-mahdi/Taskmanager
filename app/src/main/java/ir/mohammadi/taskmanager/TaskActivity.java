package ir.mohammadi.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      get user from login
//        String user_intent = getIntent().getExtras().getString("user");
//        SharedPreferences preferences = getSharedPreferences("username_sp", MODE_PRIVATE);
//        String usename = preferences.getString("user", null);
//        Toast.makeText(TaskActivity.this, user_intent, Toast.LENGTH_LONG).show();
//      end
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        set user to nav bar
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.user_nav)).setText(G.username);



        final Button button =(Button) findViewById(R.id.send_task);
        final EditText task_name =(EditText) findViewById(R.id.task_name);
        final EditText desc =(EditText) findViewById(R.id.desc);
//        final String user_intent = getIntent().getExtras().getString("user");
//        SharedPreferences preferences = getSharedPreferences("username_sp", MODE_PRIVATE);
//        String usename = preferences.getString("user", null);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String url = "https://api.backtory.com/object-storage/classes/tasks";
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", task_name.getText().toString());
                params.put("description",desc.getText().toString());
                params.put("userID",G.username);
//                Toast.makeText(TaskActivity.this,G.username,Toast.LENGTH_SHORT).show();
//                params.put("password",password.getText().toString());
//                params.put("email", email.getText().toString());
//                params.put("phoneNumber", phoneNumber.getText().toString());
//                params.put("avatar", "mydomain.com/avatar.png");

                JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                    Toast.makeText(TaskActivity.this, "کار با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TaskActivity.this,TaskActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TaskActivity.this, "خطا رخ داده است لطفا مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
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
                        headers.put("Authorization", "Bearer "+G.token);
                        headers.put("X-Backtory-Object-Storage-Id", "5a9314fce4b092a32b632af9");
                        return headers;
                    }

                };
                requestQueue.add(req);
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.task, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {



        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
