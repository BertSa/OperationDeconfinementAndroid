package ca.bertsa.cal.operationdeconfinementandroid.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;
import lombok.Getter;

public class SystemService {
    private final ObjectMapper objectMapper;
    @Getter
    private Citizen user;

    private static SystemService instance = null;

    private SystemService() {
        user = null;
        objectMapper = new ObjectMapper();
    }

    public static SystemService getInstance() {
        if (instance == null)
            instance = new SystemService();

        return instance;
    }

    public Citizen postData(Context context, String endPoint, Citizen citizen1) throws JsonProcessingException, JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:9333/api/user/" + endPoint;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(objectMapper.writeValueAsString(citizen1)),
                response -> {
                    try {
                        user = objectMapper.readValue(response.toString(), Citizen.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("error : ", error.getLocalizedMessage()));
        requestQueue.add(jsonObjectRequest);
        return user;
    }

    public void disconnect() {
        this.user = null;
    }
}