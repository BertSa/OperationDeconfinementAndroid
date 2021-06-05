package ca.bertsa.cal.operationdeconfinementandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.ZoneId;

import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.R.id;
import static ca.bertsa.cal.operationdeconfinementandroid.R.layout;
import static ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense.NEGATIVETEST;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.getInstance;
import static java.util.Objects.requireNonNull;

public class LoginRegisterActivity extends AppCompatActivity {

    private View login;
    private View register;
    private Button btnRegister;
    private AppCompatEditText registerEmail;
    private AppCompatEditText registerNassm;
    private AppCompatEditText registerPhone;
    private AppCompatEditText registerPassword;
    private AppCompatEditText registerPasswordConfirmation;
    private AppCompatEditText loginEmail;
    private AppCompatEditText loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
//        getInstance().checkConnection(this, result -> {
//
//        });

        loginEmail = this.findViewById(id.loginEmail);
        loginPassword = this.findViewById(id.loginPassword);
        findViewById(id.btn_login).setOnClickListener(v -> {
            try {
                String email = requireNonNull(loginEmail.getText()).toString();
                String password = requireNonNull(loginPassword.getText()).toString();
                if (email.equals("") || password.equals(""))
                    return;
                getInstance().login(this, email, password, citizen -> {
                    if (isTooYoung(citizen)) return;
                    goToActivity();
                });
            } catch (JSONException | JsonProcessingException e) {
                e.printStackTrace();
            }
        });


        this.registerEmail = this.findViewById(id.registerEmail);
        this.registerNassm = this.findViewById(id.registerNassm);
        this.registerPhone = this.findViewById(id.registerPhone);
        this.registerPassword = this.findViewById(id.registerPassword);
        this.registerPasswordConfirmation = this.findViewById(id.registerPasswordConfirmation);
        login = this.findViewById(id.login);
        register = findViewById(id.register);

        findViewById(id.link_signIn).setOnClickListener(v -> {
            register.setVisibility(GONE);
            login.setVisibility(VISIBLE);
        });
        findViewById(id.link_signUp).setOnClickListener(v -> {
            login.setVisibility(GONE);
            register.setVisibility(VISIBLE);
        });
        this.btnRegister = findViewById(id.btn_register);
        this.btnRegister.setOnClickListener(btn -> {
            btn.setClickable(false);
            register();
        });
/*
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
*/


    }

    private void register() {
/*
        String nassm = requireNonNull(this.registerNassm.getText()).toString();
        String email = requireNonNull(this.registerEmail.getText()).toString();
        String phone = requireNonNull(this.registerPhone.getText()).toString();
        String password = requireNonNull(this.registerPassword.getText()).toString();
        String passwordConfirmation = requireNonNull(this.registerPasswordConfirmation.getText()).toString();
        new Citizen(nassm, email, password, phone)
*/
        try {
            getInstance().register(this, NEGATIVETEST, new Citizen("eeee11112222", "aeryxajin@gmail.com", "1111", "5554443333"),
                    citizen -> {
                        if (isTooYoung(citizen)) return;
                        goToActivity();
                    });

        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isTooYoung(Citizen citizen) {
        LocalDate birth = citizen.getBirth().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (birth.isAfter(LocalDate.now().minusYears(16))) {
            getInstance().disconnect(this);
            Toast.makeText(getApplicationContext(), "Children cant connect here", Toast.LENGTH_LONG).show();
            btnRegister.setClickable(true);
            return true;
        }
        return false;
    }

    private void goToActivity() {
        Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
        startActivity(intent);
    }

}
