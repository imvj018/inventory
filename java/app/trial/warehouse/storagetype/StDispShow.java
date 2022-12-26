package app.trial.warehouse.storagetype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.trial.warehouse.LoadingDialog;
import app.trial.warehouse.R;

public class StDispShow extends AppCompatActivity {

    String sto;
    String wh;

    private static final String URL_DATA = "https://newms.innovasivtech.com/storage_type/read.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<StListItem> stListItems;
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_disp_show);

        sto = getIntent().getStringExtra("locnum");
        wh = getIntent().getStringExtra("whouse");

        recyclerView = (RecyclerView) findViewById(R.id.strecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stListItems = new ArrayList<>();
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

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                StListItem item = new StListItem(
                                        object.getString("strtype"),
                                        object.getString("locnum"),
                                        object.getString("whouse")
                                );
                                String storage= object.getString("locnum");
                                String warehouse= object.getString("whouse");
                                if (!object.getString("strtype").equals("Select Storage Type") && (storage.equals(sto)) && (warehouse.equals(wh))) {
                                    stListItems.add(item);
                                }
                            }
                            adapter = new StMyAdapter(stListItems, getApplicationContext());
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
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}