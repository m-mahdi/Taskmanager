package ir.mohammadi.taskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ir.mohammadi.taskmanager.adapter.TaskAdapter;
import ir.mohammadi.taskmanager.model.Task;

public class TaskActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    TaskAdapter adapter;
    ArrayList<Task> tasks=new ArrayList<Task>();
    Context context=this;

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
                customdialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        listView=(ListView)findViewById(R.id.listView);
//        adapter=new TaskAdapter(this,tasks);
//        listView.setAdapter(adapter);
//        adapter=new TaskAdapter(TaskActivity.this,)

//        set user to nav bar
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.user_nav)).setText(G.username);



//        final Button button =(Button) findViewById(R.id.send_task);
        final EditText task_name =(EditText) findViewById(R.id.task_name);
        final EditText desc =(EditText) findViewById(R.id.desc);
//        final String user_intent = getIntent().getExtras().getString("user");
//        SharedPreferences preferences = getSharedPreferences("username_sp", MODE_PRIVATE);
//        String usename = preferences.getString("user", null);


//        --------------------------show task
        final String url = "https://api.backtory.com/object-storage/classes/query/tasks";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("userID", G.username);
        Log.i("test1393", "onClick: ");
//

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Log.i("test1393", "onResponse: " + response.toString(4));

                            JSONObject jsnobject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsnobject.getJSONArray("results");
                            List<Task> tasks = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                Gson g = new Gson();
                                HashMap<String,String> map  = new HashMap<>();
                                Task map1= g.fromJson(explrObject.toString(), Task.class);
                                tasks.add(map1);
                            }
                            adapter=new TaskAdapter(tasks,TaskActivity.this){

                            };
                            ListView listView = (ListView) findViewById(R.id.listView);
                            listView.setAdapter(adapter);

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
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + G.token);
                headers.put("X-Backtory-Object-Storage-Id", "5a9314fce4b092a32b632af9");
                return headers;
            }

        };
        requestQueue.add(req);

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
            Intent intent=new Intent(TaskActivity.this,Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {



        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }
    private void  customdialog(){

        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.dialog_add,null);


        AlertDialog.Builder aBuilder=new AlertDialog.Builder(context);
        aBuilder.setView(view);
        aBuilder.setCancelable(false);
        aBuilder.setPositiveButton("ثبت",null);
        aBuilder.setNegativeButton("بیخیال",null);
        final AlertDialog alertDialog =aBuilder.create();
        final EditText task_name=(EditText)view.findViewById(R.id.task_name);
        final EditText description=(EditText)view.findViewById(R.id.desc);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnposetive  =alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnposetive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                final String url = "https://api.backtory.com/object-storage/classes/tasks";
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", task_name.getText().toString());
                params.put("description",description.getText().toString());
                params.put("userID",G.username);

                    JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        VolleyLog.v("Response:%n %s", response.toString(4));
                                        Toast.makeText(TaskActivity.this, "کار با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(TaskActivity.this, TaskActivity.class);
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
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
//
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Authorization", "Bearer " + G.token);
                            headers.put("X-Backtory-Object-Storage-Id", "5a9314fce4b092a32b632af9");
                            return headers;
                        }

                    };
                    requestQueue.add(req);


                    alertDialog.dismiss();
                }
            });
            Button btnnegtive =alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            btnnegtive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        Toast.makeText(TaskActivity.this, "خطا رخ داده است لطفا مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable( R.drawable.rounded_linear));
        alertDialog.show();
    }

    public interface IMethodCaller{
        void yourDesiredMethod();

    }
}
