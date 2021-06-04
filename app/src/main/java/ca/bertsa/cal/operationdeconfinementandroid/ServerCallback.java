package ca.bertsa.cal.operationdeconfinementandroid;

import org.json.JSONObject;

import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

public interface ServerCallback{
    void onSuccess(Citizen result);
}
