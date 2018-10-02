package br.com.carlos.projeto.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import br.com.carlos.projeto.db.models.Carro;
import br.com.carlos.projeto.db.models.CarroDao;
import br.com.carlos.projeto.BaseActivity;
import br.com.carlos.projeto.R;
import br.com.carlos.projeto.utils.SystemUtils;

public class ActCadastro extends BaseActivity {

    private AppCompatActivity act;
    private TextInputLayout tiModelo,tiMarca,tiAno,tiPlaca;
    private AppCompatSpinner sp_cor;
    private AppCompatButton tv_salvar;
    private TimePickerDialog dpd;

    private Carro carro;
    private CarroDao carroDao;

    private  String txtCor, idCor;

    @Override
    protected int layoutResource() {
        return R.layout.act_cadastro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = ActCadastro.this;

        SystemUtils.hideKeyboardSingle(act);

        setaref();
        setalistiner();

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

    public void setaref() {

        tv_salvar = findViewById(R.id.tv_salvar);
        sp_cor = findViewById(R.id.sp_cor);
        tiMarca = findViewById(R.id.ti_marca);
        tiModelo = findViewById(R.id.ti_modelo);
        tiPlaca = findViewById(R.id.ti_placa);
        tiAno = findViewById(R.id.ti_ano);

    }

   private void setalistiner() {

       sp_cor.setOnItemSelectedListener(
           new AdapterView.OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   txtCor = String.valueOf(sp_cor.getSelectedItem());
                   idCor = String.valueOf(sp_cor.getSelectedItemId());

               }

               public void onNothingSelected(AdapterView<?> parent) {
                   //showToast("Spinner2: unselected");
               }
           });

       tv_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carroCadastra();
            }
        });
    }


    private void carroCadastra() {

        if(valida_campos()) {
            String ti_marca = tiMarca.getEditText().getText().toString();
            String ti_modelo = tiModelo.getEditText().getText().toString();
            String ti_placa = tiPlaca.getEditText().getText().toString();
            String ti_ano = tiAno.getEditText().getText().toString();

            carro = new Carro();

            carro.setModelo(ti_modelo);
            carro.setMarca(ti_marca);
            carro.setPlaca(ti_placa);
            carro.setAno(ti_ano);
            carro.setCor(idCor);

            gravaCarroDB(carro);

            MaterialDialog dialog = new MaterialDialog.Builder(ActCadastro.this)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            finish();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // Nada fazer.
                        }
                    })
                    .title("Cadastro")
                    .content("Cadastro efetuado com sucesso!")
                    .positiveText("Ok")
                    .show();

        }
    }

    private Boolean valida_campos() {

        String ti_marca = tiMarca.getEditText().getText().toString();
        String ti_modelo = tiModelo.getEditText().getText().toString();
        String ti_placa = tiPlaca.getEditText().getText().toString();
        String ti_ano = tiAno.getEditText().getText().toString();

        if (ti_marca.isEmpty()) {
            tiMarca.setErrorEnabled(true);
            tiMarca.setError("Favor preencher o campo marca!");
            return false;
        } else {
            tiMarca.setErrorEnabled(false);
        }

        if (ti_modelo.isEmpty()) {
            tiModelo.setErrorEnabled(true);
            tiModelo.setError("Favor preencher o campo modelo!");
            return false;
        } else {
            tiModelo.setErrorEnabled(false);
        }

        if (ti_placa.isEmpty()) {
            tiPlaca.setErrorEnabled(true);
            tiPlaca.setError("Favor preencher o campo placa!");
            return false;
        } else {
            tiPlaca.setErrorEnabled(false);

        }

        if (ti_ano.isEmpty()) {
            tiAno.setErrorEnabled(true);
            tiAno.setError("Favor preencher as instruções, ano!");
            return false;
        } else {
            tiAno.setErrorEnabled(false);

        }

        return true;

    }


   @Override
   public void onBackPressed() {
       finish();
   }

    private void gravaCarroDB (Carro carro) {

        CarroDao carroDao = new CarroDao();

        // insert carro into database
        carroDao.adicionaCarro(carro);
    }

}
