package ca.bertsa.cal.operationdeconfinementandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.ZoneId;

import ca.bertsa.cal.operationdeconfinementandroid.designP.ServerCallback;
import ca.bertsa.cal.operationdeconfinementandroid.enums.TypeLicense;
import ca.bertsa.cal.operationdeconfinementandroid.models.Citizen;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ca.bertsa.cal.operationdeconfinementandroid.R.id;
import static ca.bertsa.cal.operationdeconfinementandroid.R.layout;
import static ca.bertsa.cal.operationdeconfinementandroid.services.SystemService.getInstance;
import static java.util.Objects.requireNonNull;

public class LoginRegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginRegisterActivity";
    private View login;
    private View register;
    private AppCompatEditText registerEmail;
    private AppCompatEditText registerNassm;
    private AppCompatEditText registerPhone;
    private AppCompatEditText registerPassword;
    private AppCompatEditText registerPasswordConfirmation;
    private AppCompatEditText loginEmail;
    private AppCompatEditText loginPassword;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login_register);
//        getInstance().checkConnection(this, result -> {
//
//        });
        registerEmail = this.findViewById(id.registerEmail);
        registerNassm = this.findViewById(id.registerNassm);
        registerPhone = this.findViewById(id.registerPhone);
        registerPassword = this.findViewById(id.registerPassword);
        registerPasswordConfirmation = this.findViewById(id.registerPasswordConfirmation);
        login = this.findViewById(id.login);
        register = findViewById(id.register);
        loginEmail = this.findViewById(id.loginEmail);
        loginPassword = this.findViewById(id.loginPassword);

        spinner = findViewById(id.registerTypeLicense);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeLicense, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        findViewById(id.link_signIn).setOnClickListener(v -> {
            register.setVisibility(GONE);
            login.setVisibility(VISIBLE);
        });
        findViewById(id.link_signUp).setOnClickListener(v -> {
            login.setVisibility(GONE);
            register.setVisibility(VISIBLE);
        });

        findViewById(id.btn_login).setOnClickListener(btn -> {
            btn.setEnabled(false);
            login(btn);
        });
        findViewById(id.btn_register).setOnClickListener(btn -> {
            btn.setEnabled(false);
            register(btn);
        });
    }

    private void login(View btn) {
        try {
            String email = requireNonNull(loginEmail.getText()).toString();
            String password = requireNonNull(loginPassword.getText()).toString();
            if (email.equals("") || password.equals(""))
                return;
            getInstance().login(this, email, password, new ServerCallback() {
                @Override
                public void onSuccess(Citizen citizen) {
                    btn.setEnabled(true);
                    if (isTooYoung(citizen)) return;
                    loginEmail.setText("");
                    loginPassword.setText("");
                    goToActivity();
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

    private void register(View btn) {
        String nassm = requireNonNull(this.registerNassm.getText()).toString();
        String email = requireNonNull(this.registerEmail.getText()).toString();
        String phone = requireNonNull(this.registerPhone.getText()).toString();
        String password = requireNonNull(this.registerPassword.getText()).toString();
        String passwordConfirmation = requireNonNull(this.registerPasswordConfirmation.getText()).toString();
        String selected = spinner.getSelectedItem().toString().toUpperCase();

        if (!password.equals(passwordConfirmation) || password.equals("")) {
            Toast.makeText(this, getResources().getText(R.string.passwordsMustMatch), Toast.LENGTH_LONG).show();
            btn.setEnabled(true);
            return;
        }
        Citizen user = new Citizen(nassm, email, password, phone);
        try {
            getInstance().register(this, TypeLicense.valueOf(selected), user, new ServerCallback() {
                @Override
                public void onSuccess(Citizen citizen) {
                    btn.setEnabled(true);
                    if (isTooYoung(citizen)) return;

                    goToActivity();
                }

                @Override
                public void onFailed() {
                    btn.setEnabled(true);
                }
            });

        } catch (JsonProcessingException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private boolean isTooYoung(Citizen citizen) {
        LocalDate birth = citizen.getBirth().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (birth.isAfter(LocalDate.now().minusYears(16))) {
            getInstance().disconnect();
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.ageRestriction), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void goToActivity() {
        Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNassm.setText("");
        registerEmail.setText("");
        registerPhone.setText("");
        registerPassword.setText("");
        registerPasswordConfirmation.setText("");
        loginEmail.setText("");
        loginPassword.setText("");
    }
}
