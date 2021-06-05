package ca.bertsa.cal.operationdeconfinementandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import ca.bertsa.cal.operationdeconfinementandroid.models.Address;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.*;

public class LoggedInActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Citizen user = getInstance().getUser();
        if (getInstance().isCompleted()) {
            findViewById(R.id.confirmation).setVisibility(GONE);
            findViewById(R.id.qrView).setVisibility(VISIBLE);
            new DownloadImageTask(findViewById(R.id.qrCode))
                    .execute("http://10.0.2.2:9333/api/user/" + user.getEmail() + "/qr.png");
        } else {
            findViewById(R.id.confirmation).setVisibility(VISIBLE);
            findViewById(R.id.qrView).setVisibility(GONE);
        }
        findViewById(R.id.btn_complete).setOnClickListener(v -> {
            String street = ((EditText) findViewById(R.id.street)).getText().toString();
            String zipCode = ((EditText) findViewById(R.id.zipCode)).getText().toString();
            String apt = ((EditText) findViewById(R.id.apt)).getText().toString();
            String city = ((EditText) findViewById(R.id.city)).getText().toString();

//            Address address = new Address(zipCode, street, city, "qc", apt);
            Address address = new Address("h3h3h3", "rue e", "Montreal", "qc", apt);

            try {
                getInstance().complete(this, address, result -> {
                    findViewById(R.id.confirmation).setVisibility(GONE);
                    findViewById(R.id.qrView).setVisibility(VISIBLE);
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

@SuppressWarnings("deprecation")
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}