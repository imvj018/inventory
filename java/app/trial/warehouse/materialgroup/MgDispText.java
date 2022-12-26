package app.trial.warehouse.materialgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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


public class MgDispText extends AppCompatActivity {
    String grp;
    final LoadingDialog loadingDialog = new LoadingDialog(this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mg_disp_text);

        grp = getIntent().getStringExtra("grp");

        recyclerView = (RecyclerView) findViewById(R.id.mgrecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mgItemLists = new ArrayList<>();

        loadRecyclerViewData();

    }
    private static final String URL_DATA1 = "https://newms.innovasivtech.com/materialMaster/read.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MgItemList> mgItemLists;

    private void loadRecyclerViewData() {
        loadingDialog.startLoadingDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loadingDialog.dismissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("body");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                MgItemList item1 = new MgItemList(
                                        object.getString("matcode"),
                                        object.getString("matdesc"),
                                        object.getString("matgrp"),
                                        object.getString("buom"),
                                        object.getString("locnumber"),
                                        object.getString("ibroomtype"),
                                        object.getString("obroomtype"),
                                        object.getString("wnumber")
                                );

                                String matgrp= object.getString("matgrp");
                                if(matgrp.equals(grp)){
                                    mgItemLists.add(item1);
                                }

                            }
                            adapter = new MgTxAdapter(mgItemLists,getApplicationContext());
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