package ca.bertsa.cal.operationdeconfinementandroid.designP;

import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

public interface ServerCallback {
    void onSuccess(Citizen citizen);

    void onFailed();
}
