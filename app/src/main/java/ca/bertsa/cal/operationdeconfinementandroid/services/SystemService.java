package ca.bertsa.cal.operationdeconfinementandroid.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import ca.bertsa.cal.operationdeconfinementandroid.ErrorResponse;
import ca.bertsa.cal.operationdeconfinementandroid.LoginData;
import ca.bertsa.cal.operationdeconfinementandroid.ServerCallback;
import ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense;
import ca.bertsa.cal.operationdeconfinementandroid.models.Address;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;
import lombok.Getter;

import static ca.bertsa.cal.operationdeconfinementandroid.R.string;

public class SystemService {
    private static SystemService instance = null;
    private final ObjectMapper objectMapper;

    @Getter
    private Citizen user;


    private SystemService() {
        objectMapper = new ObjectMapper();
        user = null;
    }

    public void login(Activity activity, String email, String password, final ServerCallback serverCallback) throws JSONException, JsonProcessingException {
        LoginData loginData = new LoginData(email, password);
        postt(activity, "/login", loginData, serverCallback);
    }

    public static SystemService getInstance() {
        if (instance == null)
            instance = new SystemService();
        return instance;
    }

    public void register(Activity activity, TypeLicense type, Citizen citizen, final ServerCallback serverCallback) throws JsonProcessingException, JSONException {
        postt(activity, "/register/" + type, citizen, serverCallback);
    }

    private void postt(Activity activity, String endPoint, Object obj, final ServerCallback serverCallback) throws JSONException, JsonProcessingException {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url = "http://10.0.2.2:9333/api/user" + endPoint;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(objectMapper.writeValueAsString(obj)),
                response -> {
                    try {
                        user = objectMapper.readValue(response.toString(), Citizen.class);
                    } catch (JsonProcessingException e) {
                        Toast.makeText(activity, "Err", Toast.LENGTH_LONG).show();

                        Log.e("error : ", e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                    Log.i("tag", user.getEmail());
                    saveLogin(activity);
                    serverCallback.onSuccess(user);
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error : ", error.getMessage() + "");
                        Toast.makeText(activity, error.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }


    private void saveLogin(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(string.userEmail), user.getEmail());
        editor.putString(activity.getString(string.userPassword), user.getPassword());
        editor.apply();
    }

    public void disconnect(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(activity.getString(string.userEmail));
        editor.remove(activity.getString(string.userPassword));
        editor.apply();
        this.user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isCompleted() {
        return user.isProfileCompleted();
    }

    public void checkConnection(Activity activity, ServerCallback callback) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString(activity.getString(string.userEmail), "");
        String password = sharedPref.getString(activity.getString(string.userPassword), "");
        if (email.equals("") || password.equals(""))
            return;
        try {
            login(activity, email, password, callback);
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void complete(Activity activity, Address address, final ServerCallback callback) throws JSONException, JsonProcessingException {
        user.setAddress(address);
        postt(activity, "/complete", user, callback);
    }
}