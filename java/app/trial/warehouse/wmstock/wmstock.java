package app.trial.warehouse.wmstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.trial.warehouse.R;

public class wmstock extends AppCompatActivity {
    Spinner warehouse_spinner, material_spinner, location_spinner, storage_type_spinner;
    Button check_stock;
    TextView total_stock;
    private RecyclerView recyclerView;
    private wmstockAdapter wmAdapter;
    private ArrayList<wmstockItem> wmstockItemlist;
    private RequestQueue mRequestQueue;
    ArrayList<String> whouselist, matlist, loclist, strtypelist;
    String warnum, matnum, locnum, strnum, unitofmeasure, final_quantity;
    int add_quantity, total_quantity;
    String wURL = "https://newms.innovasivtech.com/whouse/read.php";
    String mURL = "https://newms.innovasivtech.com/materialMaster/read.php";
    String lURL = "https://newms.innovasivtech.com/location/read.php";
    String stURL = "https://newms.innovasivtech.com/storage_type/read.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmstock);

        warehouse_spinner = findViewById(R.id.whouseip);
        material_spinner = findViewById(R.id.matip);
        location_spinner = findViewById(R.id.locip);
        storage_type_spinner = findViewById(R.id.strtypeip);
        check_stock = findViewById(R.id.button_add);
        recyclerView = findViewById(R.id.stock_list);
        total_stock = findViewById(R.id.totalstock);

        whouselist = new ArrayList<>();
        matlist = new ArrayList<>();
        loclist = new ArrayList<>();
        strtypelist = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wmstockItemlist = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        loadWarehouseSpinner(wURL);
//        loadmaterialSpinner(mURL);
//        loadLocationSpinner(lURL);
//        loadStorageTypeSpinner(stURL);

        material_spinner.setEnabled(false);
        location_spinner.setEnabled(false);
        storage_type_spinner.setEnabled(false);
        warehouse_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> AdapterView, View view, int i, long l) {
                warnum = warehouse_spinner.getItemAtPosition(warehouse_spinner.getSelectedItemPosition()).toString();
                matlist.clear();
                loadmaterialSpinner(mURL);
                if (!warnum.equals("Select Warehouse")) {
                    material_spinner.setEnabled(true);
                    location_spinner.setEnabled(true);
                } else {
                    material_spinner.setEnabled(false);
                    location_spinner.setEnabled(false);
                    storage_type_spinner.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> AdapterView) {
                // DO Nothing here
            }
        });
        material_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> AdapterView, View view, int i, long l) {
                matnum = material_spinner.getItemAtPosition(material_spinner.getSelectedItemPosition()).toString();
                loclist.clear();
                loadLocationSpinner(lURL);

            }

            @Override
            public void onNothingSelected(AdapterView<?> AdapterView) {
                // DO Nothing here
            }
        });
        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> AdapterView, View view, int i, long l) {
                locnum = location_spinner.getItemAtPosition(location_spinner.getSelectedItemPosition()).toString();
                strtypelist.clear();
                loadStorageTypeSpinner(stURL);
                if (!locnum.equals("Select Location")) {
                    storage_type_spinner.setEnabled(true);
                } else {
                    storage_type_spinner.setEnabled(false);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> AdapterView) {
                // DO Nothing here
            }
        });
        storage_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> AdapterView, View view, int i, long l) {
                strnum = storage_type_spinner.getItemAtPosition(storage_type_spinner.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> AdapterView) {
                // DO Nothing here
            }
        });

        check_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(warnum + "-" + matnum + "-" + locnum + "-" + strnum);
                wmstockItemlist.clear();
                total_quantity = 0;
                if (warnum.equals("Select Warehouse") || matnum.equals("Select Material")) {
                    Toast.makeText(wmstock.this, "Select Warehouse and Material to Check Stock", Toast.LENGTH_SHORT).show();
                }
                if (!warnum.equals("Select Warehouse") && !matnum.equals("Select Material") && locnum.equals("Select Location")) {
                    checkStock2();
                }
                if (!warnum.equals("Select Warehouse") && !matnum.equals("Select Material") && !locnum.equals("Select Location") &&
                        strnum.equals("Select Storage Type")) {
                    checkStock1();


                }
                if (!warnum.equals("Select Warehouse") && !matnum.equals("Select Material") && !locnum.equals("Select Location") &&
                        !strnum.equals("Select Storage Type")) {
                    checkStock();

                }
            }
        });
    }

    private void checkStock() {
        String url = "https://newms.innovasivtech.com/stockcheck/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String grncopy = hit.getString("gr_del");
                        String material = hit.getString("matcode");
                        String matdesc = hit.getString("matdesc");
                        String quantity = hit.getString("quantity");
                        String uom = hit.getString("uom");
                        String location = hit.getString("location");
                        String storagetype = hit.getString("location");

                        if (material.equals(matnum) && location.equals(locnum) && storagetype.equals(strnum)) {
                            wmstockItemlist.add(new wmstockItem(id, grncopy, material, matdesc, quantity, uom, location, storagetype));
                            add_quantity = Integer.parseInt(quantity);
                            total_quantity = total_quantity + Integer.parseInt(quantity);
                            if (wmstockItemlist == null || wmstockItemlist.size() == 0) {
                                System.out.println("Empty");
                            }
                        }

                    }
                    if (total_quantity == 0) {
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText(matnum + " is out of Stock!");
                    } else {
                        final_quantity = String.valueOf(total_quantity);
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText("Total " + matnum + " available : " + final_quantity + " " + unitofmeasure);
                    }

                    wmAdapter = new wmstockAdapter(wmstock.this, wmstockItemlist);
                    recyclerView.setAdapter(wmAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private void checkStock1() {
        String url = "https://newms.innovasivtech.com/stockcheck/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String grncopy = hit.getString("gr_del");
                        String material = hit.getString("matcode");
                        String matdesc = hit.getString("matdesc");
                        String quantity = hit.getString("quantity");
                        String uom = hit.getString("uom");
                        String location = hit.getString("location");
                        String storagetype = hit.getString("location");

                        if (material.equals(matnum) && location.equals(locnum)) {
                            wmstockItemlist.add(new wmstockItem(id, grncopy, material, matdesc, quantity, uom, location, storagetype));
                            add_quantity = Integer.parseInt(quantity);
                            total_quantity = total_quantity + Integer.parseInt(quantity);
                            if (wmstockItemlist == null || wmstockItemlist.size() == 0) {
                                System.out.println("Empty");
                            }
                        }

                    }
                    if (total_quantity == 0) {
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText(matnum + " is out of Stock!");
                    } else {
                        final_quantity = String.valueOf(total_quantity);
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText("Total " + matnum + " available : " + final_quantity + " " + unitofmeasure);
                    }
                    wmAdapter = new wmstockAdapter(wmstock.this, wmstockItemlist);
                    recyclerView.setAdapter(wmAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private void checkStock2() {
        String url = "https://newms.innovasivtech.com/stockcheck/read.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {


                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String id = hit.getString("id");
                        String grncopy = hit.getString("gr_del");
                        String material = hit.getString("matcode");
                        String matdesc = hit.getString("matdesc");
                        String quantity = hit.getString("quantity");
                        String uom = hit.getString("uom");
                        String location = hit.getString("location");
                        String storagetype = hit.getString("location");

                        if (material.equals(matnum)) {
                            wmstockItemlist.add(new wmstockItem(id, grncopy, material, matdesc, quantity, uom, location, storagetype));
                            add_quantity = Integer.parseInt(quantity);
                            total_quantity = total_quantity + Integer.parseInt(quantity);


                        }

                    }
                    if (total_quantity == 0) {
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText(matnum + " is out of Stock!");
                    } else {
                        final_quantity = String.valueOf(total_quantity);
                        total_stock.setVisibility(View.VISIBLE);
                        total_stock.setText("Total " + matnum + " available : " + final_quantity + " " + unitofmeasure);
                    }
                    wmAdapter = new wmstockAdapter(wmstock.this, wmstockItemlist);
                    recyclerView.setAdapter(wmAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private void loadWarehouseSpinner(String wURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, wURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String wnumber = jsonObject1.getString("wnumber");
                        whouselist.add(wnumber);
                    }
                    warehouse_spinner.setAdapter(new ArrayAdapter<String>(wmstock.this, android.R.layout.simple_spinner_dropdown_item, whouselist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void loadmaterialSpinner(String mURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String material = jsonObject1.getString("matcode");
                        String wnumber = jsonObject1.getString("wnumber");
                        unitofmeasure = jsonObject1.getString("buom");
                        if (warnum.equals(wnumber) || material.equals("Select Material")) {
                            matlist.add(material);
                        }

                    }
                    material_spinner.setAdapter(new ArrayAdapter<String>(wmstock.this, android.R.layout.simple_spinner_dropdown_item, matlist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void loadLocationSpinner(String lURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, lURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String location = jsonObject1.getString("locnum");
                        String whouse = jsonObject1.getString("whouse");
                        if (warnum.equals(whouse) || location.equals("Select Location")) {
                            loclist.add(location);
                        }

                    }
                    location_spinner.setAdapter(new ArrayAdapter<String>(wmstock.this, android.R.layout.simple_spinner_dropdown_item, loclist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void loadStorageTypeSpinner(String stURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, stURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String whouse = jsonObject1.getString("whouse");
                        String locnumber = jsonObject1.getString("locnum");
                        String storagenumber = jsonObject1.getString("strtype");
                        if ((warnum.equals(whouse) && locnum.equals(locnumber)) || storagenumber.equals("Select Storage Type")) {
                            strtypelist.add(storagenumber);
                        }

                    }
                    storage_type_spinner.setAdapter(new ArrayAdapter<String>(wmstock.this, android.R.layout.simple_spinner_dropdown_item, strtypelist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}