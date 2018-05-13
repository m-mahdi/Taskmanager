package ir.mohammadi.taskmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import ir.mohammadi.taskmanager.R;
import ir.mohammadi.taskmanager.model.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends BaseAdapter {
//
//    private Context context;
//    private ArrayList<Task> data;
//    private LayoutInflater inflater;
//
//    public TaskAdapter(Context context, ArrayList<Task> data) {
//        this.context = context;
//        this.data = data;
//        inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    public static class viewHolder{
//        public TextView txtname;
//        public TextView txtdescription;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {

//        View vi =convertView;
//        viewHolder holder=new viewHolder();
//        if (vi==null){
//            vi=inflater.inflate(R.layout.task_item,null);
//            holder.txtname=(TextView) vi.findViewById(R.id.item_name);
//            holder.txtdescription=(TextView) vi.findViewById(R.id.item_description);
//            vi.setTag(holder);
//        }
//        else
//            holder=(viewHolder) vi.getTag();
//        if(data.size()>0){
//
//            Task task=data.get(position);
//            holder.txtname.setText(task.getName());
//            holder.txtdescription.setText(task.getDescription());
//        }
//        return vi;
//        if (convertView == null)
//            convertView = activity.getLayoutInflater().inflate(R.layout.task_item, null);
//
//
//
//        TextView text =(TextView)convertView.findViewById(R.id.txtAlertText);
//
//        JSONObject json_data = getItem(position);
//        if(null!=json_data ){
//            String jj=json_data.getString("f_name");
//            text.setText(jj);
//        }
//
//        return convertView;
//
//    }
//
//    @Nullable
//    @Override
//    public CharSequence[] getAutofillOptions() {
//        return new CharSequence[0];
//    }
//}
    private LayoutInflater inflater;
    private List<Task> tasks;
    private Activity activity;

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
    public View getView(final int i, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.task_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Task task = tasks.get(i);
//        final Task task= (Task) getItem(i);
        viewHolder.name.setText(task.getName());
        viewHolder.description.setText(task.getDescription());


        return view;

    }
//    private BookApiService bookApiService;

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