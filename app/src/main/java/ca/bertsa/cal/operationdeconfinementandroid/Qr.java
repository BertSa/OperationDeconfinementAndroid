package ca.bertsa.cal.operationdeconfinementandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.security.CryptoPrimitive;

import ca.bertsa.cal.operationdeconfinementandroid.models.Address;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;
import ca.bertsa.cal.operationdeconfinementandroid.services.SystemService;

import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.*;

public class Qr extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Citizen user = getInstance().getUser();
        if (getInstance().isCompleted()) {
            findViewById(R.id.confirmation).setVisibility(VISIBLE);
        }
        findViewById(R.id.btn_complete).setOnClickListener(v -> {
            String street = ((EditText) findViewById(R.id.street)).getText().toString();
            String zipCode = ((EditText) findViewById(R.id.zipCode)).getText().toString();
            String apt = ((EditText) findViewById(R.id.apt)).getText().toString();
            String city = ((EditText) findViewById(R.id.city)).getText().toString();

//            Address address = new Address(zipCode, street, city, "qc", apt);
            Address address = new Address("h3h3h3", "rue e", "Montreal", "qc", apt);

            Log.i("dadadadadadada","dadadadadada");
        try {
            getInstance().complete(this, address, result -> {
            });
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getInstance().disconnect(this);
    }
}