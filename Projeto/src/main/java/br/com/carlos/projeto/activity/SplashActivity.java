package br.com.carlos.projeto.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.concurrent.TimeUnit;

import br.com.carlos.projeto.BaseActivity;
import br.com.carlos.projeto.R;
import br.com.carlos.projeto.utils.MyUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {

    private LinearLayout ly_splash;
    private MaterialDialog dialog;
    private Animation.AnimationListener makeTopGone;


    BroadcastReceiver receiverInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                efetuarLogin();
                unregisterReceiver(receiverInternet);
            }
        }
    };


    @Override
    protected int layoutResource() {
        return R.layout.act_splash_mobile;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash_mobile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.half_black));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ly_splash = (LinearLayout) findViewById(R.id.ly_splash);
        initAnimations();
        if (MyUtil.isNetworkAvailable(SplashActivity.this)) {
            if(!checaSeLogado()) {
                efetuarLogin();
            } else {
                telaPrincipal();
            }
        } else {
        //    if (SocialUtils.isLoginStored(ActivitySplashMobile.this)) {
        //        efetuarLogin();
        //    } else {
             dialog = new MaterialDialog.Builder(SplashActivity.this)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            // TODO
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // Nada fazer.
                        }
                    })
                    .title(getString(R.string.atencao_))
                    .content(getString(R.string.verifique_conexao_reinicie_app))
                    .positiveText("OK")
                    .show();
                ly_splash.setVisibility(View.VISIBLE);
                registerReceiver(receiverInternet, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
         //   }
        }
    }

    protected void efetuarLogin() {
        Animation a = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.pull_up_from_bottom);
        a.setAnimationListener(makeTopGone);
        ly_splash.startAnimation(a);
    }

    private void initAnimations() {
        makeTopGone = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ly_splash.setVisibility(View.INVISIBLE);

                if (MyUtil.isNetworkAvailable(SplashActivity.this)) {
                    //if (SocialUtils.isLoginStored(SplashActivity.this)) {
                    //    LoginTransaction.setInstance(SocialUtils.getPreferences(ActivitySplashMobile.this));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(SplashActivity.this, ActLogin.class);
                          startActivity(intent);
                          finish();

                    //} else {
                    //    ly_splash.setVisibility(View.INVISIBLE);
                    //    Intent intent = new Intent(SplashActivity.this, ActMainActivity.class);
                    //    startActivity(intent);
                    //    finish();
                    //}
                //} else {
                //    if (SocialUtils.isLoginStored(ActivitySplashMobile.this)) {
                //        LoginTransaction.setInstance(SocialUtils.getPreferences(ActivitySplashMobile.this));
                //        SocialUtils.setUltimaTela(ActivitySplashMobile.this, MyExtras.ACAO_ESTANTE);
                //        Intent intent = new Intent(ActivitySplashMobile.this, BaseActivity.class);
                //        startActivity(intent);
                //        finish();
                //    } else {
                //        MyDialogs.showMessageDialog(ActivitySplashMobile.this, getString(R.string.verifique_conexao_reinicie_app), getString(R.string.atencao_));
                //        lay.setVisibility(View.VISIBLE);
                //        registerReceiver(receiverInternet, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                //    }
                }
            }
        };

    }

    private void telaPrincipal() {

        Intent intent = new Intent(SplashActivity.this, ActHome.class);
        startActivity(intent);
        finish();

    }

}
