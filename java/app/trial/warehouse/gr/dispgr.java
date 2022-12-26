package app.trial.warehouse.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.trial.warehouse.LoadingDialog;
import app.trial.warehouse.R;


public class dispgr extends AppCompatActivity implements dgrAdapter.OnItemClickListener {
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_GRN = "grn";
    public static final String EXTRA_VENDOR = "vendor";
    public static final String EXTRA_WAREHOUSE = "warehouse";
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    SharedPreferences sharedpreferences;

    private RecyclerView mRecyclerView;
    private dgrAdapter mdgrAdapter;
    private ArrayList<dgrItem> mdgrList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispgr);
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mdgrList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }


    private void parseJSON() {
        loadingDialog.startLoadingDialog();
        String url = "https://newms.innovasivtech.com/goods/goods1/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingDialog.dismissDialog();
                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String grnum = hit.getString("grn");
                        String vendor = hit.getString("vendor");
                        String warehouse = hit.getString("warehouse");

                        mdgrList.add(new dgrItem(id, grnum, vendor, warehouse));

                    }
                    mdgrAdapter = new dgrAdapter(dispgr.this, mdgrList);
                    mRecyclerView.setAdapter(mdgrAdapter);
                    mdgrAdapter.setOnItemClickListener(dispgr.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismissDialog();
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, dispgr2.class);
        dgrItem clickedItem = mdgrList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_GRN, clickedItem.getGrn());
        detailIntent.putExtra(EXTRA_VENDOR, clickedItem.getVendor());
        detailIntent.putExtra(EXTRA_WAREHOUSE, clickedItem.getWarehouse());
        startActivity(detailIntent);

    }
}