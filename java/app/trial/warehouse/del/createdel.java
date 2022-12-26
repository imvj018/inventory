package app.trial.warehouse.del;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import app.trial.warehouse.MainActivity;
import app.trial.warehouse.R;
import app.trial.warehouse.Tdashboard;
import app.trial.warehouse.gr.creategr;
import app.trial.warehouse.gr.matItem;
import app.trial.warehouse.wmstock.wmstockItem;

public class createdel extends AppCompatActivity {
    LinearLayout layoutList;
    Button additems, submit;
    TextInputLayout documentdate, delNumber;

    EditText showdate, Material, testxyz, Quantity, uom, locnum, avlstock;
    Spinner shipartyip, materialip;
    ArrayList<String> customerlist, materiallist;
    String custURL = "https://newms.innovasivtech.com/customer/read.php";
    String materialURL = "https://newms.innovasivtech.com/materialMaster/read.php";
    String shipartynum, date, currentDateandTime, docdate;
    String material_code, material_description, material_uom, location_number;
    String delivery_number, document_date, selected_material, final_quantity;
    String material_code_inlist, material_description_inlist, quantity_inlist, material_uom_inlist, location_number_inlist;
    String material_code_inlistx, material_description_inlistx, quantity_inlistx, material_uom_inlistx, location_number_inlistx;
    Calendar calendar;
    String testq, testm, deliveryNumber;
    int year, month, day;
    int add_quantity, total_quantity;
    float stock_check;
    private ArrayList<matItem> mtestList;
    private ArrayList<wmstockItem> wmstockItemlist;
    private RequestQueue mRequestQueue;
    private static final HttpURLConnection connection = null;
    private static final HttpURLConnection writeconn = null;
    private static final List<String> session = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createdel);
        layoutList = findViewById(R.id.layout_list);

        delNumber = findViewById(R.id.grnip);
        documentdate = findViewById(R.id.dateip);

        showdate = findViewById(R.id.datee);
        shipartyip = findViewById(R.id.shippip);
        additems = findViewById(R.id.button_add);
        submit = findViewById(R.id.button_submit_list);
        customerlist = new ArrayList<>();
        materiallist = new ArrayList<>();


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
        SimpleDateFormat crdate = new SimpleDateFormat("dd-MM-yyyy");
        currentDateandTime = sdf.format(new Date());
        docdate = crdate.format(new Date());

        loadCustData(custURL);
        shipartyip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shipartynum = shipartyip.getItemAtPosition(shipartyip.getSelectedItemPosition()).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        documentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepick = new DatePickerDialog(createdel.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String dayip, monthip, yearip;
                        dayip = String.valueOf(day);
                        monthip = String.valueOf(month);
                        yearip = String.valueOf(year);
                        if (day < 10) {
                            dayip = "0" + dayip;
                        }
                        if (month < 10) {
                            monthip = "0" + monthip;
                        }

                        date = dayip + "-" + monthip + "-" + yearip;
                        showdate.setText(date);

                    }
                }, year, month, day);
                datepick.show();


            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                showdate.setText(docdate);
                documentdate.getEditText().setText(docdate);
            }
        }, 350);
        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                document_date = showdate.getText().toString();
                deliveryNumber = Objects.requireNonNull(delNumber.getEditText()).getText().toString();
                if (!deliveryNumber.equals("") && !document_date.equals("") && !shipartynum.equals("Select Vendor")) {

                    if ((selected_material != null && stock_check != 0.0) || (selected_material == null && stock_check == 0.0)) {
                        addmaterials();
                    }

                    if ((selected_material != null && !selected_material.equals("Selected Material") && stock_check == 0.0)) {
                        Toast.makeText(createdel.this, "Enter the Quantity", Toast.LENGTH_SHORT).show();
                    }
                    stock_check = 0;
                } else {
                    Toast.makeText(createdel.this, "Enter All Data to Add Items!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedelivery();
            }
        });
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
                    materialip.setAdapter(new ArrayAdapter<String>(createdel.this, android.R.layout.simple_spinner_dropdown_item, materiallist));
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

    private void loadCustData(String custURL) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, custURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String wnumber = jsonObject1.getString("vdesc");
                        customerlist.add(wnumber);
                    }
                    shipartyip.setAdapter(new ArrayAdapter<String>(createdel.this, android.R.layout.simple_spinner_dropdown_item, customerlist));
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

    private void addmaterials() {
        @SuppressLint("InflateParams") final View matView = getLayoutInflater().inflate(R.layout.addelitems, null, false);
        materialip = (Spinner) matView.findViewById(R.id.selmat);
        Material = (EditText) matView.findViewById(R.id.material);
        testxyz = (EditText) matView.findViewById(R.id.textxyz);
        Quantity = (EditText) matView.findViewById(R.id.quantity);
        uom = (EditText) matView.findViewById(R.id.uom);
        locnum = (EditText) matView.findViewById(R.id.locnum);
        avlstock = (EditText) matView.findViewById(R.id.avstock);
        mRequestQueue = Volley.newRequestQueue(this);


        mtestList = new ArrayList<>();
        wmstockItemlist = new ArrayList<>();
        loadmaterialdata(materialURL);
        materialip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_material = materialip.getItemAtPosition(materialip.getSelectedItemPosition()).toString();
                if (!selected_material.equals("Select Material")) {
                    testxyz.setText(selected_material);
                    wmstockItemlist.clear();
                    total_quantity = 0;
                    parsejson();
                    checkstock();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        materiallist.clear();
        Button imageClose = matView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(matView);
            }
        });
        layoutList.addView(matView);
        Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!selected_material.equals("Select Material")) {
                    String teststring = Quantity.getText().toString();
                    if (teststring.equals("")) {
                        stock_check = 0;
                    } else {
                        stock_check = Float.parseFloat(teststring);
                        if (stock_check > total_quantity) {
                            Toast.makeText(createdel.this, "Quantity exceeds Stock!", Toast.LENGTH_SHORT).show();
                            Quantity.getText().clear();
                            Quantity.setText(String.valueOf(total_quantity));
                        }
                    }
                } else {
                    Toast.makeText(createdel.this, "Select the Material!", Toast.LENGTH_SHORT).show();
                    Quantity.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void removeView(View view) {
        selected_material = null;
        layoutList.removeView(view);
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
                        material_code = hit.getString("matcode");
                        material_description = hit.getString("matdesc");
                        String materialgrp = hit.getString("matgrp");
                        material_uom = hit.getString("buom");
                        location_number = hit.getString("locnumber");
                        String Ibroom = hit.getString("ibroomtype");
                        String Obroom = hit.getString("obroomtype");
                        String Wnumber = hit.getString("wnumber");
                        if (material_code.equals(selected_material)) {
                            mtestList.add(new matItem(material_code, material_description, materialgrp, material_uom, location_number, Ibroom, Obroom, Wnumber));

                            Material.setText(material_description);
                            uom.setText(material_uom);
                            locnum.setText(location_number);

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

    private void checkstock() {
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

                        if (material.equals(selected_material)) {
                            wmstockItemlist.add(new wmstockItem(id, grncopy, material, matdesc, quantity, uom, location, storagetype));
                            add_quantity = Integer.parseInt(quantity);
                            total_quantity = total_quantity + Integer.parseInt(quantity);


                        }

                    }

                    final_quantity = String.valueOf(total_quantity);
                    avlstock.setText(final_quantity + " " + material_uom);

//                        total_stock.setText("Total " + matnum + " available : " + final_quantity + " " + unitofmeasure);


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

    private boolean validatedelivery() {

        deliveryNumber = Objects.requireNonNull(delNumber.getEditText()).getText().toString();

        System.out.println("GR  Number : " + deliveryNumber);
        new PostDelivery().execute("https://newms.innovasivtech.com/delivery/del1/create.php");
        boolean result = true;
        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View matView = layoutList.getChildAt(i);
            materialip = matView.findViewById(R.id.selmat);
            Material = matView.findViewById(R.id.material);
            testxyz = matView.findViewById(R.id.textxyz);

            Quantity = matView.findViewById(R.id.quantity);
            uom = matView.findViewById(R.id.uom);
            locnum = matView.findViewById(R.id.locnum);

            if (!Quantity.getText().toString().equals("")) {
                material_code_inlist = testxyz.getText().toString();
                material_description_inlist = Material.getText().toString();
                quantity_inlist = Quantity.getText().toString();
                material_uom_inlist = uom.getText().toString();
                location_number_inlist = locnum.getText().toString();


            } else {
                result = false;
                break;
            }
            String k = String.valueOf(i);
            new PostMaterial().execute("https://newms.innovasivtech.com/delivery/del2/create.php", k);
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
            Toast.makeText(this, "Enter the Quantity!", Toast.LENGTH_SHORT).show();
        }
        return result;

    }

    public class PostDelivery extends AsyncTask<String, Void, String> {
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
                jsonObjectFinal.put("ctime", currentDateandTime);
                jsonObjectFinal.put("dtime", document_date);
                jsonObjectFinal.put("delnum", deliveryNumber);
                jsonObjectFinal.put("shiparty", shipartynum);
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
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
                View matView = layoutList.getChildAt(Integer.parseInt(val));
                materialip = matView.findViewById(R.id.selmat);
                Material = matView.findViewById(R.id.material);
                testxyz = matView.findViewById(R.id.textxyz);
                Quantity = matView.findViewById(R.id.quantity);
                uom = matView.findViewById(R.id.uom);
                locnum = matView.findViewById(R.id.locnum);

                if (!Quantity.getText().toString().equals("")) {
                    material_code_inlistx = testxyz.getText().toString();
                    material_description_inlistx = Material.getText().toString();
                    quantity_inlistx = Quantity.getText().toString();
                    material_uom_inlistx = uom.getText().toString();
                    location_number_inlistx = locnum.getText().toString();
                } else {
                    Toast.makeText(createdel.this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
                }
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                JSONObject jsonObjectFinal = new JSONObject();
                jsonObjectFinal.put("delnum", deliveryNumber);
                jsonObjectFinal.put("matnum", material_code_inlistx);
                jsonObjectFinal.put("matdesc", material_description_inlistx);
                jsonObjectFinal.put("quantity", quantity_inlistx);
                jsonObjectFinal.put("uom", material_uom_inlistx);
                jsonObjectFinal.put("locnum", location_number_inlistx);


                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());

                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
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
                View matView = layoutList.getChildAt(Integer.parseInt(val));
                materialip = matView.findViewById(R.id.selmat);
                Material = matView.findViewById(R.id.material);
                testxyz = matView.findViewById(R.id.textxyz);
                Quantity = matView.findViewById(R.id.quantity);
                uom = matView.findViewById(R.id.uom);
                locnum = matView.findViewById(R.id.locnum);

                if (!Quantity.getText().toString().equals("")) {
                    material_code_inlistx = testxyz.getText().toString();
                    material_description_inlistx = Material.getText().toString();
                    quantity_inlistx = Quantity.getText().toString();
                    material_uom_inlistx = uom.getText().toString();
                    location_number_inlistx = locnum.getText().toString();
                } else {
                    Toast.makeText(createdel.this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
                }
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();


                JSONObject jsonObjectFinal = new JSONObject();
                jsonObjectFinal.put("gr_del", deliveryNumber);
                jsonObjectFinal.put("matcode", material_code_inlistx);
                jsonObjectFinal.put("matdesc", material_description_inlistx);
                jsonObjectFinal.put("quantity", "-" + quantity_inlistx);
                jsonObjectFinal.put("uom", material_uom_inlistx);
                jsonObjectFinal.put("location", location_number_inlistx);


                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(jsonObjectFinal.toString());

                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
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

    private String readStream(InputStream in) {
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
}