package app.trial.warehouse.location;

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

import app.trial.warehouse.warehouse.WhAdapter;
import app.trial.warehouse.warehouse.WhListItem;

public class LcDispShow extends AppCompatActivity {
    String loc;
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lc_disp_show);

        loc = getIntent().getStringExtra("wnumber");

        recyclerView = (RecyclerView) findViewById(R.id.lcrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lclistItems = new ArrayList<>();

        loadRecyclerViewData();

    }
    private static final String URL_DATA = "https://newms.innovasivtech.com/location/read.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<LcListItem> lclistItems;


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
                                LcListItem item = new LcListItem(
                                        object.getString("locnum"),
                                        object.getString("locdesc"),
                                        object.getString("whouse")
                                );
                                String locate= object.getString("whouse");
                                if ( (!object.getString("locnum").equals("Select Location")) && (locate.equals(loc)) ) {
                                    lclistItems.add(item);

                                }
                            }
                            adapter = new LcMyAdapter(lclistItems, getApplicationContext());
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