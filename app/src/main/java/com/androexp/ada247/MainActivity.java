package com.androexp.ada247;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<UserModel> userModelList;
    private UserAdapter userAdapter;
    private LottieAnimationView loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {

        recyclerView = findViewById(R.id.user_recycler_view);
        loadingBar = findViewById(R.id.loading_bar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkInternetConnection();
    }

    private void checkInternetConnection() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            fetchUserList();
        } else {
            Snackbar.make(recyclerView, "No Internet Connection !", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void fetchUserList() {
        userModelList = new ArrayList<>();

        if (loadingBar.getVisibility() == View.GONE) {
            loadingBar.setVisibility(View.VISIBLE);
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://gorest.co.in/public-api/users";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject user = jsonArray.getJSONObject(i);

                        String name = user.getString("name");
                        String gender = user.getString("gender");
                        String status = user.getString("status");
                        String email = user.getString("email");

                        userModelList.add(new UserModel(name, gender, email, status));
                        userAdapter = new UserAdapter(MainActivity.this, userModelList);
                        recyclerView.setAdapter(userAdapter);
                        if (loadingBar.getVisibility() == View.VISIBLE) {
                            loadingBar.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "e: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    if (loadingBar.getVisibility() == View.VISIBLE) {
                        loadingBar.setVisibility(View.GONE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                if (loadingBar.getVisibility() == View.VISIBLE) {
                    loadingBar.setVisibility(View.GONE);
                }
            }
        });

        queue.add(request);
    }
}