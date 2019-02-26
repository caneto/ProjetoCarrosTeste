package br.com.carlos.projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.carlos.projeto.BaseActivity;
import br.com.carlos.projeto.R;
import br.com.carlos.projeto.adapter.CarroAdapter;
import br.com.carlos.projeto.db.models.Carro;
import br.com.carlos.projeto.db.models.CarroDao;
import io.realm.RealmResults;

public class ActHome extends BaseActivity {

    private final String TAG = this.getClass().getName();

    private FloatingActionButton fabButton;

    private CarroAdapter carroAdaptador;
    private RealmResults<Carro> realmCarro;
    private ListView carroLista;

    private CarroDao carroDao;

    @Override
    protected int layoutResource() {
        return R.layout.activity_home;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setaref();
        setalistiner();

        carroDao = new CarroDao();
        realmCarro = carroDao.getAll();

        carroAdaptador = new CarroAdapter(realmCarro);
        carroLista.setAdapter(carroAdaptador);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        realmCarro = carroDao.getAll();

        carroAdaptador = new CarroAdapter(realmCarro);
        carroLista.setAdapter(carroAdaptador);

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
        fabButton = (FloatingActionButton) findViewById(R.id.fab);

        carroLista = (ListView) findViewById(R.id.lv_carros);

    }

    private void setalistiner() {

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActHome.this, ActCadastro.class);
                startActivityForResult(intent, 0);

            }
        });

        carroLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView modelo = view.findViewById(R.id.tv_modelo);
                String txt_modelo = modelo.getText().toString();

                TextView placamodelo = view.findViewById(R.id.tv_placa);
                String placa_modelo = placamodelo.getText().toString();

                Intent intcap = new Intent(ActHome.this,ActCadastro.class);
                intcap.putExtra("placa", placa_modelo);
                startActivity(intcap);


                //Toast.makeText(getApplicationContext(), "Clickei na linha : "+txt_nome,Toast.LENGTH_SHORT).show();
            }
        });
    }


   @Override
   public void onBackPressed() {

       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       if (drawer.isDrawerOpen(GravityCompat.START)) {
           drawer.closeDrawer(GravityCompat.START);
       } else {
           finish();

       }
   }

}
