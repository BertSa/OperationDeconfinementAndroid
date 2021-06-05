package ca.bertsa.cal.operationdeconfinementandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

import ca.bertsa.cal.operationdeconfinementandroid.designP.ServerCallback;
import ca.bertsa.cal.operationdeconfinementandroid.models.Address;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;
import ca.bertsa.cal.operationdeconfinementandroid.tasks.DownloadImageTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.getInstance;

public class LoggedInActivity extends AppCompatActivity {


    private static final String TAG = "LoggedInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Citizen user = getInstance().getUser();
        if (getInstance().isCompleted()) {
            showQr(user);
        } else {
            setViewsVisibilities(VISIBLE, GONE);
        }
        findViewById(R.id.btn_complete).setOnClickListener(btn -> {
            btn.setEnabled(false);
            complete(btn);
        });
        findViewById(R.id.refresh).setOnClickListener(v -> {
            v.setEnabled(false);
            refresh(user);
            cooldown(v);
        });
    }

    private void cooldown(View v) {
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> v.setEnabled(true));
            }
        }, 5000);
    }

    private void setViewsVisibilities(int visible, int gone) {
        findViewById(R.id.confirmation).setVisibility(visible);
        findViewById(R.id.qrView).setVisibility(gone);
    }

    private void showQr(Citizen user) {
        setViewsVisibilities(GONE, VISIBLE);
        refresh(user);
    }

    private void refresh(Citizen user) {
        new DownloadImageTask(findViewById(R.id.qrCode))
                .execute("http://10.0.2.2:9333/api/user/" + user.getEmail() + "/qr.png");
    }

    private void complete(View btn) {
        String street = ((EditText) this.findViewById(R.id.street)).getText().toString();
        String zipCode = ((EditText) this.findViewById(R.id.zipCode)).getText().toString();
        String apt = ((EditText) this.findViewById(R.id.apt)).getText().toString();
        String city = ((EditText) this.findViewById(R.id.city)).getText().toString();

        Address address = new Address(zipCode, street, city, "qc", apt);

        try {
            getInstance().complete(this, address, new ServerCallback() {
                @Override
                public void onSuccess(Citizen citizen) {
                    btn.setEnabled(true);
                    showQr(citizen);
                }

                @Override
                public void onFailed() {
                    btn.setEnabled(true);
                }
            });
        } catch (JSONException | JsonProcessingException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getInstance().disconnect();
    }
}