package ca.bertsa.cal.operationdeconfinementandroid;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.ZoneId;

import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.R.id;
import static ca.bertsa.cal.operationdeconfinementandroid.R.layout;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.*;
import static ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense.NEGATIVETEST;

public class LoginRegisterActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputNassm;
    private EditText inputPhone;
    private EditText inputPassword;
    private EditText inputPasswordConfirmation;
    private View login;
    private View register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
//
//        this.inputEmail = findViewById(id.registerEmail);
//        this.inputNassm = findViewById(id.registerNassm);
//        this.inputPhone = findViewById(id.registerPhone);
//        this.inputPassword = findViewById(id.registerPassword);
//        this.inputPasswordConfirmation = findViewById(id.registerPasswordConfirmation);
//        login = findViewById(id.login);
//        register = findViewById(id.register);
//        findViewById(id.link_signIn).setOnClickListener(v -> {
//            register.setVisibility(GONE);
//            login.setVisibility(VISIBLE);
//        });
//        findViewById(id.link_signUp).setOnClickListener(v -> {
//            login.setVisibility(GONE);
//            register.setVisibility(VISIBLE);
//        });
//
//        findViewById(id.btn_register).setOnClickListener(v -> {
//            register();
//        });
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        String MyUA = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://10.0.2.2:4200");
        WebSettings webSettings = myWebView.getSettings();
        myWebView.getSettings().setUserAgentString(MyUA);
        webSettings.setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);


    }

    private void register() {
        String nassm = this.inputNassm.getText().toString();
        String email = this.inputEmail.getText().toString();
        String phone = this.inputPhone.getText().toString();
        String password = this.inputPassword.getText().toString();
        String passwordConfirmation = this.inputPasswordConfirmation.getText().toString();

        try {
            Citizen citizen = getInstance().postData(getApplicationContext(), "register/" + NEGATIVETEST, new Citizen(nassm, email, password, phone));
            LocalDate birth = citizen.getBirth().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (birth.isBefore(LocalDate.now().minusYears(16))) {
                getInstance().disconnect();
                Toast.makeText(getApplicationContext(), "Children cant connect here", Toast.LENGTH_LONG);
            }
            if (citizen.isProfileCompleted()) {

            }

        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }


}
