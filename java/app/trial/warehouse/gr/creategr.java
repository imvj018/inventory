package app.trial.warehouse.gr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import app.trial.warehouse.R;

public class creategr extends AppCompatActivity {
    LinearLayout layoutList;
    String URL = "https://newms.innovasivtech.com/whouse/read.php";
    String vURL = "https://newms.innovasivtech.com/vendor/read.php";
    String materialURL = "https://newms.innovasivtech.com/inventory/material_master/read.php";
    String ponumurl = "https://newms.innovasivtech.com/goods/goods1/read.php";
    Spinner vendorip, materialip;
    Button additems, submit;
    String grnum, vennum, warnum, currentDateandTime;
    String matCode, material, quantity, matGrp, uOm, locNum, ibNum, obNum;
    String matCodex, materialx, quantityx, matGrpx, uOmx, locNumx, ibNumx, obNumx;
    String matcode, materialgrp, Uom, partnumber, avlcheck, avldate, matId;
    String lastid, nextid, totalzeros, ponumber, selmaterial;
    EditText Material, testxyz, matgroup, Quantity, uom, locnum, ibroom, obroom;
    float stock_check, base_price, total_cost;
    ArrayList<String> whouselist, vendorlist, materiallist;
    ArrayList<additems> itemsList = new ArrayList<>();
    private ArrayList<matItem> mtestList;
    TextInputLayout grn, pnum;
    private RequestQueue mRequestQueue;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategr);

        vendorip = findViewById(R.id.venip);
        grn = findViewById(R.id.grnip);
        pnum = findViewById(R.id.grniptest);
        mRequestQueue = Volley.newRequestQueue(this);
        mtestList = new ArrayList<>();
        layoutList = findViewById(R.id.layout_list);
        additems = findViewById(R.id.button_add);
        submit = findViewById(R.id.button_submit_list);
        whouselist = new ArrayList<>();
        materiallist = new ArrayList<>();
        vendorlist = new ArrayList<>();

        loadWhouseData(ponumurl);
        loadVendorData(vURL);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
        currentDateandTime = sdf.format(new Date());

        vendorip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vennum = vendorip.getItemAtPosition(vendorip.getSelectedItemPosition()).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grnum = Objects.requireNonNull(grn.getEditText()).getText().toString();

//                if (!grnum.equals("") && !vennum.equals("") && !vennum.equals("Select Vendor") && !warnum.equals("") && !warnum.equals("Select Warehouse")) {
                if ( !vennum.equals("") && !vennum.equals("Select Vendor")) {
                    if ((selmaterial == null && stock_check == 0.0) || (selmaterial != null && stock_check != 0.0)) {
                        addView();
                    }

                    if ((selmaterial != null && !selmaterial.equals("Selected Material") && stock_check == 0.0)) {
                        Toast.makeText(creategr.this, "Enter the Quantity", Toast.LENGTH_SHORT).show();
                    }
                    stock_check = 0;
                    base_price = 0;

                } else {
                    Toast.makeText(creategr.this, "Select Vendor!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfValidAndRead();
            }
        });
    }

    private void loadVendorData(String vURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, vURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String wnumber = jsonObject1.getString("vdesc");
                        vendorlist.add(wnumber);
                    }
                    vendorip.setAdapter(new ArrayAdapter<String>(creategr.this, android.R.layout.simple_spinner_dropdown_item, vendorlist));
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

    private void loadWhouseData(String ponumurl) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ponumurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < 1; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        lastid = jsonObject1.getString("id");
                    }
                    nextid = String.valueOf(Integer.parseInt(lastid) + 1);

                    int zerolength = 10 - Integer.parseInt(String.valueOf(nextid.length()));
                    System.out.println(zerolength);
                    totalzeros = "";
                    for (int i = 0; i < zerolength; i++) {
                        totalzeros = "0" + totalzeros;

                    }
                    ponumber = "PO-" + totalzeros + nextid;
                    Objects.requireNonNull(pnum.getEditText()).setText(ponumber);
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
        requestQueue.add(stringRequest);
    }


    private void addView() {
        @SuppressLint("InflateParams") final View goodsView = getLayoutInflater().inflate(R.layout.additems, null, false);
        materialip = (Spinner) goodsView.findViewById(R.id.selmat);

        testxyz = (EditText) goodsView.findViewById(R.id.textxyz);
        Material = (EditText) goodsView.findViewById(R.id.material);
        matgroup = (EditText) goodsView.findViewById(R.id.matgrp);
        Quantity = (EditText) goodsView.findViewById(R.id.quantity);
        uom = (EditText) goodsView.findViewById(R.id.uom);
        locnum = (EditText) goodsView.findViewById(R.id.locnum);
        ibroom = (EditText) goodsView.findViewById(R.id.ibroom);
        obroom = (EditText) goodsView.findViewById(R.id.obroom);

        loadmaterialdata(materialURL);
        materialip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selmaterial = materialip.getItemAtPosition(materialip.getSelectedItemPosition()).toString();
                if (!selmaterial.equals("Select Material")) {
                    parsejson();
                    testxyz.setText(selmaterial);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        materiallist.clear();
        Button imageClose = goodsView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(goodsView);
            }
        });
        layoutList.addView(goodsView);


        Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!selmaterial.equals("Select Material")) {
                    String teststring = Quantity.getText().toString();
                    if (teststring.equals("")) {
                        stock_check = 0;
                        total_cost = stock_check * base_price;
                        ibroom.setText(String.valueOf(total_cost));
                    } else {
                        stock_check = Float.parseFloat(teststring);
                        total_cost = stock_check * base_price;
                        ibroom.setText(String.valueOf(total_cost));


                    }
                } else {
                    Toast.makeText(creategr.this, "Select the Material!", Toast.LENGTH_SHORT).show();
                    Quantity.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        locnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!selmaterial.equals("Select Material")) {
                    String coststring = locnum.getText().toString();
                    if (coststring.equals("")) {
                        base_price = 0;
                        total_cost = stock_check * base_price;
                        ibroom.setText(String.valueOf(total_cost));
                    } else {
                        base_price = Float.parseFloat(coststring);
                        total_cost = stock_check * base_price;
                        ibroom.setText(String.valueOf(total_cost));
                    }
                } else {
                    Toast.makeText(creategr.this, "Select the Material!", Toast.LENGTH_SHORT).show();
                    locnum.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void parsejson() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, materialURL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        matId = hit.getString("id");
                        matcode = hit.getString("matcode");
                        material = hit.getString("matdesc");
                        partnumber = hit.getString("partnumber");
                        materialgrp = hit.getString("matgrp");
                        Uom = hit.getString("buom");
                        avlcheck = hit.getString("avlcheck");
                        avldate = hit.getString("avldate");

                        if (matcode.equals(selmaterial)) {
                            mtestList.add(new matItem(matcode, material, materialgrp, Uom, partnumber, avlcheck, avldate, matId));
                            Material.setText(material);
                            matgroup.setText(materialgrp);
                            uom.setText(Uom);

                        }
                    }

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

    private void loadmaterialdata(String materialURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, materialURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String country = jsonObject1.getString("matcode");
                        materiallist.add(country);
                    }
                    materialip.setAdapter(new ArrayAdapter<String>(creategr.this, android.R.layout.simple_spinner_dropdown_item, materiallist));
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

    private boolean checkIfValidAndRead() {
        grnum = Objects.requireNonNull(grn.getEditText()).getText().toString();

        boolean result = true;
        new PostGR().execute("https://newms.innovasivtech.com/inventory/purchase_order/po1/create.php");
        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View goodsView = layoutList.getChildAt(i);
            materialip = goodsView.findViewById(R.id.selmat);
            Material = goodsView.findViewById(R.id.material);
            testxyz = goodsView.findViewById(R.id.textxyz);
            matgroup = goodsView.findViewById(R.id.matgrp);
            Quantity = goodsView.findViewById(R.id.quantity);
            uom = goodsView.findViewById(R.id.uom);
            locnum = goodsView.findViewById(R.id.locnum);
            ibroom = goodsView.findViewById(R.id.ibroom);
            obroom = goodsView.findViewById(R.id.obroom);
            additems goods = new additems();
            if (!Quantity.getText().toString().equals("")) {
                matCode = testxyz.getText().toString();
                material = Material.getText().toString();
                matGrp = matgroup.getText().toString();
                quantity = Quantity.getText().toString();
                uOm = uom.getText().toString();
                locNum = locnum.getText().toString();
                ibNum = ibroom.getText().toString();
                obNum = obroom.getText().toString();
                goods.setselmat(matCode);
                goods.setMaterial(material);
                goods.setQuantity(quantity);


            } else {
                result = false;
                break;
            }
            String k = String.valueOf(i);
            new PostMaterial().execute("https://newms.innovasivtech.com/goods/goods2/create.php", k);
            new matStock().execute("https://newms.innovasivtech.com/stockcheck/create.php", k);
        }
        if (stock_check != 0.0) {
            if (mtestList.size() == 0) {
                result = false;
                Toast.makeText(this, "Add at least 1 Item!", Toast.LENGTH_SHORT).show();
            }
            if (mtestList.size() > 0) {
                result = true;
                Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(creategr.this, "Enter the Quantity!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public class PostGR extends AsyncTask<String, Void, String> {
        String server_response;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            java.net.URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                JSONObject jsonObjectFinal = new JSONObject();
                jsonObjectFinal.put("remarks", grnum);
                jsonObjectFinal.put("vendor", vennum);
                jsonObjectFinal.put("ponumber", ponumber);
                jsonObjectFinal.put("crdate", currentDateandTime);
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream2(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + server_response);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class PostMaterial extends AsyncTask<String, Void, String> {
        String server_response;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            java.net.URL url;
            HttpURLConnection urlConnection = null;
            try {


                String val = strings[1];
                View goodsView = layoutList.getChildAt(Integer.parseInt(val));
                materialip = goodsView.findViewById(R.id.selmat);
                Material = goodsView.findViewById(R.id.material);
                testxyz = goodsView.findViewById(R.id.textxyz);
                matgroup = goodsView.findViewById(R.id.matgrp);
                Quantity = goodsView.findViewById(R.id.quantity);
                uom = goodsView.findViewById(R.id.uom);
                locnum = goodsView.findViewById(R.id.locnum);
                ibroom = goodsView.findViewById(R.id.ibroom);
                obroom = goodsView.findViewById(R.id.obroom);

                if (!Quantity.getText().toString().equals("")) {
                    matCodex = testxyz.getText().toString();
                    materialx = Material.getText().toString();
                    matGrpx = matgroup.getText().toString();
                    quantityx = Quantity.getText().toString();
                    uOmx = uom.getText().toString();
                    locNumx = locnum.getText().toString();
                    ibNumx = ibroom.getText().toString();
                    obNumx = obroom.getText().toString();
                } else {
                    Toast.makeText(creategr.this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
                }
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                JSONObject jsonObjectFinal = new JSONObject();
                jsonObjectFinal.put("grncopy", grnum);
                jsonObjectFinal.put("material", matCodex);
                jsonObjectFinal.put("matdesc", materialx);
                jsonObjectFinal.put("quantity", quantityx);
                jsonObjectFinal.put("uom", uOmx);
                jsonObjectFinal.put("locnum", locNumx);
                jsonObjectFinal.put("ibroomtype", ibNumx);
                jsonObjectFinal.put("obroomtype", obNumx);


                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());

                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                assert urlConnection != null;
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream2(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + server_response);
        }
    }

    public class matStock extends AsyncTask<String, Void, String> {
        String server_response;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            java.net.URL url;
            HttpURLConnection urlConnection = null;
            try {


                String val = strings[1];
                View goodsView = layoutList.getChildAt(Integer.parseInt(val));
                materialip = goodsView.findViewById(R.id.selmat);
                Material = goodsView.findViewById(R.id.material);
                testxyz = goodsView.findViewById(R.id.textxyz);
                matgroup = goodsView.findViewById(R.id.matgrp);
                Quantity = goodsView.findViewById(R.id.quantity);
                uom = goodsView.findViewById(R.id.uom);
                locnum = goodsView.findViewById(R.id.locnum);
                ibroom = goodsView.findViewById(R.id.ibroom);
                obroom = goodsView.findViewById(R.id.obroom);

                if (!Quantity.getText().toString().equals("")) {
                    matCodex = testxyz.getText().toString();
                    materialx = Material.getText().toString();
                    matGrpx = matgroup.getText().toString();
                    quantityx = Quantity.getText().toString();
                    uOmx = uom.getText().toString();
                    locNumx = locnum.getText().toString();
                    ibNumx = ibroom.getText().toString();
                    obNumx = obroom.getText().toString();
                } else {
                    Toast.makeText(creategr.this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
                }
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                JSONObject jsonObjectFinal = new JSONObject();
                jsonObjectFinal.put("gr_del", grnum);
                jsonObjectFinal.put("matcode", matCodex);
                jsonObjectFinal.put("matdesc", materialx);
                jsonObjectFinal.put("quantity", quantityx);
                jsonObjectFinal.put("uom", uOmx);
                jsonObjectFinal.put("location", locNumx);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());

                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                assert urlConnection != null;
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream2(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + server_response);
        }
    }

    private String readStream2(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private void removeView(View view) {
        selmaterial = null;
        layoutList.removeView(view);
    }
}