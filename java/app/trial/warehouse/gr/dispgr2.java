package app.trial.warehouse.gr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

import static app.trial.warehouse.gr.dispgr.EXTRA_GRN;
import static app.trial.warehouse.gr.dispgr.EXTRA_VENDOR;
import static app.trial.warehouse.gr.dispgr.EXTRA_WAREHOUSE;

import app.trial.warehouse.LoadingDialog;
import app.trial.warehouse.R;


public class dispgr2 extends AppCompatActivity {

    TextView grn, ven, whouse;
    String grnumber, vendor, warehouse;
    private RecyclerView mRecyclerView;
    private dgr2Adapter mdgr2Adapter;
    private ArrayList<dgr2Item> mdgr2List;
    private RequestQueue mRequestQueue;
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispgr2);

        Intent intent = getIntent();
        grnumber = intent.getStringExtra(EXTRA_GRN);
        vendor = intent.getStringExtra(EXTRA_VENDOR);
        warehouse = intent.getStringExtra(EXTRA_WAREHOUSE);


        grn = findViewById(R.id.grn);
        ven = findViewById(R.id.ven);
        whouse = findViewById(R.id.whouse);

        grn.setText("GR Number :  " + grnumber);
        ven.setText("Vendor : " + vendor);
        whouse.setText("Warehouse :  " + warehouse);
        mRecyclerView = findViewById(R.id.recycler_view1);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mdgr2List = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();


    }

    private void parseJSON() {
        loadingDialog.startLoadingDialog();
        String url = "https://newms.innovasivtech.com/goods/goods2/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingDialog.dismissDialog();
                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String grncopy = hit.getString("grncopy");
                        String material = hit.getString("material");
                        String matdesc = hit.getString("matdesc");
                        String quantity = hit.getString("quantity");
                        String uom = hit.getString("uom");

                        if (grncopy.equals(grnumber)) {
                            mdgr2List.add(new dgr2Item(id, grncopy, material, matdesc, quantity, uom));
                        }

                    }
                    mdgr2Adapter = new dgr2Adapter(dispgr2.this, mdgr2List);
                    mRecyclerView.setAdapter(mdgr2Adapter);

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


}