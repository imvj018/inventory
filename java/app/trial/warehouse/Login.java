package app.trial.warehouse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageButton;

public class Login extends AppCompatActivity {
    TextView head1, head2;
    Button login, forgpword;
    TextInputLayout username, password;
    String userName, passWord;
    String id, fullname, empid, phnum, loginpassword, mailid, access;

    private ArrayList<profileItem> mprofileList;
    private RequestQueue mRequestQueue;
    private static final HttpURLConnection connection = null;
    private static final HttpURLConnection writeconn = null;
    private static final List<String> session = null;
    final LoadingDialog loadingDialog = new LoadingDialog(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mprofileList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        head1 = findViewById(R.id.head);
        head2 = findViewById(R.id.textView3);
        login = findViewById(R.id.login_button);
        forgpword = findViewById(R.id.fpw_button);
        username = findViewById(R.id.login_Username);
        password = findViewById(R.id.login_Password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = Objects.requireNonNull(username.getEditText()).getText().toString();
                passWord = Objects.requireNonNull(password.getEditText()).getText().toString();
                loadingDialog.startLoadingDialog();
                if (!userName.equals("") && !passWord.equals("")) {
                    checkprofile();
                } else {
                    Toast.makeText(Login.this, "Enter Username and Password", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismissDialog();
                }
            }
        });

    }

    private void checkprofile() {
        userName = Objects.requireNonNull(username.getEditText()).getText().toString();
        passWord = Objects.requireNonNull(password.getEditText()).getText().toString();
        String URL = "https://newms.innovasivtech.com/invlogin/single.php?empid=" + userName;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("body");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        id = hit.getString("id");
                        fullname = hit.getString("fullname");
                        empid = hit.getString("empid");
                        phnum = hit.getString("phnum");
                        loginpassword = hit.getString("password");
                        mailid = hit.getString("mailid");
                        access = hit.getString("access");


                        {
                            mprofileList.add(new profileItem(id, fullname, empid, phnum, loginpassword, mailid, access));
                            if (userName.equals(empid) && passWord.equals(loginpassword) && access.equals("transaction")) {
                                suplogin();
                                loadingDialog.dismissDialog();
                            }
                            if (userName.equals(empid) && passWord.equals(loginpassword) && access.equals("pick")) {
                                picklogin();
                                loadingDialog.dismissDialog();
                            }
                            if (!userName.equals(empid) || !passWord.equals(loginpassword)) {
                                loadingDialog.dismissDialog();
                                Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                            }
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

    private void suplogin() {
        Intent intent = new Intent(Login.this, Tdashboard.class);
        startActivity(intent);
    }

    private void picklogin() {
        Intent intent = new Intent(Login.this, Pdashboard.class);
        startActivity(intent);
    }
}