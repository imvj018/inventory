package app.trial.warehouse.unitofmeasure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import app.trial.warehouse.gr.creategr;


public class UomDispShow extends AppCompatActivity {

    private static final String URL_DATA = "https://newms.innovasivtech.com/uom/read.php";
    EditText search;
    String searched_data;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UmListItem> umlistItems;
    final LoadingDialog loadingDialog = new LoadingDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uom_disp_show);

        search = findViewById(R.id.search_box);

        recyclerView = (RecyclerView) findViewById(R.id.umrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        umlistItems = new ArrayList<>();
        umlistItems.clear();
        loadRecyclerViewData();



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searched_data = search.getText().toString();
                if (searched_data.equals("")) {
                    umlistItems.clear();
                    loadRecyclerViewData();
                }
                if (!searched_data.equals("")) {
                    umlistItems.clear();
                    searchRecyclerViewData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                                UmListItem item = new UmListItem(
                                        object.getString("uom"),
                                        object.getString("uomdesc")
                                );
                                if (!object.getString("uomdesc").equals("Inch")) {
                                    umlistItems.add(item);
                                }
                            }
                            adapter = new UmMyAdapter(umlistItems, getApplicationContext());
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

    private void searchRecyclerViewData() {
//        loadingDialog.startLoadingDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
//                        loadingDialog.dismissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("body");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                UmListItem item = new UmListItem(
                                        object.getString("uom"),
                                        object.getString("uomdesc")

                                );
                                if ((!object.getString("uomdesc").equals("Inch")) && (object.getString("uomdesc").equals(searched_data))) {
                                    umlistItems.add(item);
                                }
                            }
                            adapter = new UmMyAdapter(umlistItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        loadingDialog.dismissDialog();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}