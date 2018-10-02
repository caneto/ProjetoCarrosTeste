package br.com.carlos.projeto.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;

import br.com.carlos.projeto.MyApplication;
import br.com.carlos.projeto.R;
import br.com.carlos.projeto.utils.SystemUtils;

public class ActLogin extends EasyLocationAppCompatActivity {

    private static final String TAG = ActLogin.class.getSimpleName();

    private AppCompatActivity act;
    private TextInputLayout tiLogin, tiSenha;
    private AppCompatButton btn_entrar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        act = ActLogin.this;

        SystemUtils.hideKeyboardSingle(act);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.azul_login));
        }

        setaref();

        setalistiner();

        obtemLocalizacao();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void finalize() throws Throwable {

        super.finalize();
    }

    public void setaref() {

        tiLogin = findViewById(R.id.ti_login);
        tiSenha = findViewById(R.id.ti_senha);

        btn_entrar = findViewById(R.id.btn_entrar);


    }

    private void setalistiner() {

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked();

            }
        });

       }

    private void obtemLocalizacao() {
        LocationRequest locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);

        EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                .setLocationRequest(locationRequest)
                .setFallBackToLastLocationTime(3000)
                .build();
        requestSingleLocationFix(easyLocationRequest);
    }

   private void loginClicked() {
        if(valida_campos()) {
            String login = tiLogin.getEditText().getText().toString();
            String senha = tiSenha.getEditText().getText().toString();

            if("123456".equals(senha)) {
                MaterialDialog dialog = new MaterialDialog.Builder(ActLogin.this)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {

                                Intent intent = new Intent(ActLogin.this, ActHome.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // Nada fazer.
                            }
                        })
                        .title("Login")
                        .content("Login efetuado com sucesso!")
                        .positiveText("Ok")
                        .show();

            } else {
                MaterialDialog dialog = new MaterialDialog.Builder(ActLogin.this)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {

                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // Nada fazer.
                            }
                        })
                        .title("Login")
                        .content("Senha informada não confere, favor verificar os dados digitados!")
                        .positiveText("Ok")
                        .show();
            }

        }
    }

    private Boolean valida_campos() {

        String email = tiLogin.getEditText().getText().toString();

        if (email.isEmpty()) {
            tiLogin.setErrorEnabled(true);
            tiLogin.setError( getString(R.string.empty_email));
            return false;
        } else {
            tiLogin.setErrorEnabled(false);

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tiLogin.setErrorEnabled(true);
                tiLogin.setError(getString(R.string.incorrect_email));
                return false;
            } else {
                tiLogin.setErrorEnabled(false);
            }
        }


        if (tiSenha.getEditText().getText().toString().isEmpty()) {
            tiSenha.setErrorEnabled(true);
            tiSenha.setError(getString(R.string.empty_senha));
            return false;
        } else {
            tiSenha.setErrorEnabled(false);
        }

        return true;

    }


    @Override
    public void onLocationPermissionGranted() {
        Log.d(TAG,"Location permission granted");
    }

    @Override
    public void onLocationPermissionDenied() {
        Log.d(TAG, "Location permission denied");
    }

    @Override
    public void onLocationReceived(Location location) {
        //tv_gps.setText(location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude());

        MyApplication.Lat = location.getLatitude();
        MyApplication.Long = location.getLongitude();
        MyApplication.provider = location.getProvider();

    }


    @Override
    public void onLocationProviderEnabled() {
        Log.d(TAG,"Location services are now ON");
    }

    @Override
    public void onLocationProviderDisabled() {
        Log.d(TAG,"Location services are still Off");
    }
}
