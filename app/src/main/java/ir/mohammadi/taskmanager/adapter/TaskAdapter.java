package ir.mohammadi.taskmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import ir.mohammadi.taskmanager.G;
import ir.mohammadi.taskmanager.MainActivity;
import ir.mohammadi.taskmanager.R;
import ir.mohammadi.taskmanager.RegisterActivity;
import ir.mohammadi.taskmanager.TaskActivity;
import ir.mohammadi.taskmanager.model.Task;

import static com.android.volley.VolleyLog.TAG;

public class TaskAdapter extends BaseAdapter {
//
    private LayoutInflater inflater;
    private List<Task> tasks;
    private Activity activity;
    private Context mContext;

    public TaskAdapter(List<Task> tasks, Activity activity) {
        this.tasks = tasks;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();


    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.task_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Task task = tasks.get(i);
//        final Task task= (Task) getItem(i);
        viewHolder.name.setText(task.getName());
        viewHolder.description.setText(task.getDescription());


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(activity, task.getId() + "", Toast.LENGTH_SHORT).show();
                final String url = " https://api.backtory.com/object-storage/classes/tasks/"+task.getId();
                final RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("userID",G.username);

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE,url, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    VolleyLog.v("Response:%n %s", response.toString(4));
                                    Toast.makeText(activity, "کار با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(activity, TaskActivity.class);
                                    activity.startActivity(intent);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity, "خطا رخ داده است لطفا بررسی نمایید.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(activity, "کار با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
                        VolleyLog.e("Error: ", error.getMessage());
                        Intent intent = new Intent(activity, TaskActivity.class);
                        activity.startActivity(intent);

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

            }});


        return view;

                                             }


    static class ViewHolder{
        TextView name;
        TextView description;
        Button delete;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.item_name);
            description = (TextView) v.findViewById(R.id.item_description);
            delete = (Button) v.findViewById(R.id.delete);
        }
    }

}