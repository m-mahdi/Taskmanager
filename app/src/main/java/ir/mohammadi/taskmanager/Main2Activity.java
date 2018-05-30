package ir.mohammadi.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.mohammadi.taskmanager.adapter.TaskAdapter;
import ir.mohammadi.taskmanager.model.Task;
import ir.mohammadi.taskmanager.model.Tasks;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    TaskAdapter adapter;

//    String[] test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        final Button button = (Button) findViewById(R.id.test);
//        final EditText firstName =(EditText) findViewById(R.id.firstName);
//        final EditText lastName =(EditText) findViewById(R.id.lastName);
//        final EditText username =(EditText) findViewById(R.id.rej_username);
//        final EditText password =(EditText) findViewById(R.id.rej_password);
//        final EditText email =(EditText) findViewById(R.id.rej_email);
//        final EditText phoneNumber =(EditText) findViewById(R.id.phoneNumber);

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
//                                    Toast.makeText(Main2Activity.this, response.toString(), Toast.LENGTH_SHORT).show();

//                                    Log.i("Result", response.toString());
//                                    Gson g = new Gson();
//                                    HashMap<String, ArrayList<Task>> map = new HashMap<>();
//                                    HashMap map1 = g.fromJson(response.toString(), HashMap.class);
//                                    Tasks tasks =  new Tasks((ArrayList<Task>) map1.get("results"));
//                                    Task task = new Task();
                            JSONObject jsnobject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsnobject.getJSONArray("results");
//                                    List<String> list = new ArrayList<>();
//                                    String[] list =new String[]{};
                            List<Task> tasks = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                Gson g = new Gson();
                                HashMap<String,String> map  = new HashMap<>();
                                HashMap map1= g.fromJson(explrObject.toString(), HashMap.class);
//                                        G.token =(String) map1.get("access_token");
                                Task task =new Task();
                                task.setDescription((String) map1.get("description"));
                                task.setName((String) map1.get("name"));
                                tasks.add(task);

                            }

//                                    adapter.notifyDataSetChanged();
//                            Toast.makeText(Main2Activity.this, tasks.size()+"", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Main2Activity.this, getTaskId(), Toast.LENGTH_SHORT).show();
                            adapter=new TaskAdapter(tasks,Main2Activity.this);
                            ListView listView = (ListView) findViewById(R.id.listView);
                            listView.setAdapter(adapter);


//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        JSONObject explrObject = jsonArray.getJSONObject(i);
//                                        Gson g = new Gson();
//                                        HashMap<String,String> map  = new HashMap<>();
//                                        HashMap map1= g.fromJson(explrObject.toString(), HashMap.class);
////                                        G.token =(String) map1.get("access_token");
//
//                                        list.add((String) map1.get("name"));
//                                    }

//                                    Toast.makeText(Main2Activity.this, tasks.get(3).getName(), Toast.LENGTH_SHORT).show();



//                                    if (task.getDescription() != null)

//                                    Toast.makeText(Main2Activity.this, "کاربر با موفقیت ثبت شد.", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(Main2Activity.this,TaskActivity.class);
//                                    startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main2Activity.this, "خطا رخ داده است لطفا مجدد تلاش نمایید.", Toast.LENGTH_SHORT).show();
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

}

