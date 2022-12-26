package app.trial.warehouse.del;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.trial.warehouse.LoadingDialog;
import app.trial.warehouse.R;

public class DLDisplayShow extends AppCompatActivity {
    private TextView head, desc, desc1;
    String delnumber;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dldisplay_show);
        head = findViewById(R.id.dltextview1);
        desc = findViewById(R.id.dltextview2);
        desc1 = findViewById(R.id.dltextview3);

        head.setText("Delivery Number : " + getIntent().getStringExtra("delnum"));
        desc.setText("Ship To Party : " + getIntent().getStringExtra("shiparty"));
        desc1.setText("Created Time : " + getIntent().getStringExtra("ctime"));

        delnumber = getIntent().getStringExtra("delnum");

        recyclerView = (RecyclerView) findViewById(R.id.dlrecyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dlitemLists = new ArrayList<>();

        loadRecyclerViewData();

    }

    private static final String URL_DATA1 = "https://newms.innovasivtech.com/delivery/del2/read.php";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<DLItemList> dlitemLists;
    final LoadingDialog loadingDialog = new LoadingDialog(this);

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
                                DLItemList item1 = new DLItemList(
                                        object.getString("id"),
                                        object.getString("delnum"),
                                        object.getString("matnum"),
                                        object.getString("matdesc"),
                                        object.getString("quantity"),
                                        object.getString("uom"),
                                        object.getString("locnum"));

                                String deliverynum = object.getString("delnum");
                                if(deliverynum.equals(delnumber)){
                                    dlitemLists.add(item1);
                                }

                            }
                            adapter = new DLMyAdapter(dlitemLists,getApplicationContext());
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