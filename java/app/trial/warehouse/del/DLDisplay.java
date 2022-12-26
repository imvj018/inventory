package app.trial.warehouse.del;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.trial.warehouse.LoadingDialog;
import app.trial.warehouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DLDisplay extends AppCompatActivity {

    private static final String URL_DATA = "https://newms.innovasivtech.com/delivery/del1/read.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<app.trial.warehouse.del.DLListItem> dllistItems;
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dldisplay);

        recyclerView =(RecyclerView) findViewById(R.id.dlrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dllistItems = new ArrayList<>();

        loadRecyclerViewData();

    }



    private void loadRecyclerViewData() {
        loadingDialog.startLoadingDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loadingDialog.dismissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("body");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                app.trial.warehouse.del.DLListItem item = new app.trial.warehouse.del.DLListItem(
                                        object.getString("id"),
                                        object.getString("ctime"),
                                        object.getString("dtime"),
                                        object.getString("shiparty"),
                                        object.getString("delnum")
                                );
                                dllistItems.add(item);
                            }
                            adapter = new app.trial.warehouse.del.MyAdapter(dllistItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loadingDialog.dismissDialog();
                        Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}