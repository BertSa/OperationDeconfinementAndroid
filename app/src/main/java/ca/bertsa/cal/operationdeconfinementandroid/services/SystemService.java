package ca.bertsa.cal.operationdeconfinementandroid.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import ca.bertsa.cal.operationdeconfinementandroid.R;
import ca.bertsa.cal.operationdeconfinementandroid.designP.ServerCallback;
import ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense;
import ca.bertsa.cal.operationdeconfinementandroid.models.Address;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;
import ca.bertsa.cal.operationdeconfinementandroid.models.ErrorResponse;
import ca.bertsa.cal.operationdeconfinementandroid.models.LoginData;

import static ca.bertsa.cal.operationdeconfinementandroid.R.string;

public class SystemService {
    private static final String TAG = "SystemService";
    private static SystemService instance = null;
    private final ObjectMapper objectMapper;


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

    private synchronized void postt(Activity activity, String endPoint, Object obj, final ServerCallback serverCallback) throws JSONException, JsonProcessingException {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url = "http://10.0.2.2:9333/api/user" + endPoint;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(objectMapper.writeValueAsString(obj)),
                response -> {
                    try {
                        user = objectMapper.readValue(response.toString(), Citizen.class);
                    } catch (JsonProcessingException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                    saveLogin(activity);
                    serverCallback.onSuccess(user);
                },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(activity,
                                activity.getString(R.string.error_network_timeout),
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        if (error.networkResponse.data != null) {
                            String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            ErrorResponse errorResponse = null;
                            try {
                                errorResponse = objectMapper.readValue(body, ErrorResponse.class);
                            } catch (JsonProcessingException e) {
                                Log.e(TAG, e.getMessage());
                            }
                            if (errorResponse != null)
                                Toast.makeText(activity, errorResponse.getDetails().get(0) + "", Toast.LENGTH_LONG).show();
                        }
                    }
                    serverCallback.onFailed();
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

    public boolean isCompleted() {
        return user.isProfileCompleted();
    }

    public void checkConnection(Activity activity, final ServerCallback callback) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString(activity.getString(string.userEmail), "");
        String password = sharedPref.getString(activity.getString(string.userPassword), "");
        if (email.equals("") || password.equals(""))
            return;
        try {
            login(activity, email, password, callback);
        } catch (JSONException | JsonProcessingException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void complete(Activity activity, Address address, final ServerCallback callback) throws JSONException, JsonProcessingException {
        user.setAddress(address);
        postt(activity, "/complete", user, callback);
    }

    public Citizen getUser() {
        return user;
    }
}