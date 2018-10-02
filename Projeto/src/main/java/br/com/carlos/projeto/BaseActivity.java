package br.com.carlos.projeto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.carlos.projeto.MyApplication;
import br.com.carlos.projeto.R;
import br.com.carlos.projeto.activity.SobreActivity;
import br.com.carlos.projeto.classes.NetworkUtil;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getName();
    public static String AppName, PackageName, VersionCode, VersionName, AnoBase = "2017";
    public static final Locale brasil = new Locale("pt", "BR");
    public static NumberFormat nf = NumberFormat.getCurrencyInstance(brasil);

    public static AppCompatTextView tv_nome_usuario, tv_email_usuario;
    public static AppCompatImageView iv_usuario;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    public static View header;

    private FragmentManager fragmentManager;

    protected abstract
    @LayoutRes
    int layoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!NetworkUtil.isConnected(this)) {
            //Implementar a msg para não conectado para primeiro acesso.
        }

        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(1) // default 10
                .setRemindInterval(3) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(TAG, Integer.toString(which));
                    }
                })
                .monitor();

        this.packinfo();
        this.setContentView(layoutResource());
        this.setupToolbar();
        this.setaBaseRef();
        this.setaDraw();

        fragmentManager = getSupportFragmentManager();

        AppRate.showRateDialogIfMeetsConditions(this);

    }

    public void setaBaseRef() {

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    public void seterefView(View header) {

       tv_email_usuario = (AppCompatTextView) header.findViewById(R.id.tv_email_usuario);
       tv_nome_usuario = (AppCompatTextView) header.findViewById(R.id.tv_nome_usuario);
       //iv_usuario = (AppCompatImageView) header.findViewById(R.id.iv_usuario);

    }

    private void packinfo() {

        PackageInfo pinfo = null;

        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AppName = pinfo.applicationInfo.loadLabel(getPackageManager()).toString();
        PackageName = pinfo.packageName;
        VersionCode = String.valueOf(pinfo.versionCode);
        VersionName = pinfo.versionName;

    }

    private void setupToolbar() {

       toolbar = findViewById(R.id.toolbar);

       if (toolbar == null) {
            return;
       }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setaDraw() {

        if (drawer == null) {
            return;
        }

        // Show a dialog if meets conditions
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        this.seterefView(header);

    }

    static public void toastRapido(String msg, Activity act) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }

    static public void toastDemoradoo(String msg, Activity act) {
        Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Will replace de current Fragment on the layout
     *
     * @Param fragment The fragment to show/replace.
     */
    public final void replaceFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            //String tag = fragment.getTag() != null && !fragment.getTag().isEmpty() ? fragment.getTag() : fragment.getClass().getSimpleName();
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.content_frame, fragment, tag)
                    .commitAllowingStateLoss();
            //.commit();

        }
    }

    public final void showFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .show(fragment)
                    .commit();

        }
    }

    public static void startActivity(Context context, Class<? extends Activity> activityClass) {

        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
       // }
        Intent it = new Intent();

        if (id == R.id.action_exit) {
            finish();
            return true;
        } else if (id == R.id.nav_sobre) {


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent it = new Intent();

        if (id == R.id.action_exit) {
            // Handle the camera action
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        } else if (id == R.id.nav_sobre) {
                it = new Intent(getApplicationContext(), SobreActivity.class);
                startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean checaSeCadastrado() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(MyApplication.CONTEXT);
        boolean cadastro = p.getBoolean("CADASTRADO", false);
        return cadastro;
    }

    public void setaCadastrado() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(MyApplication.CONTEXT);
        p.edit().putBoolean("CADASTRADO", true).commit();
    }

    public static boolean checaSeLogado() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(MyApplication.CONTEXT);
        boolean logado = p.getBoolean("LOGADO", false);
        return logado;
    }

    public void setaLogado() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(MyApplication.CONTEXT);
        p.edit().putBoolean("LOGADO", true).commit();
    }


}
