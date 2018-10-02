package br.com.carlos.projeto.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

import br.com.carlos.projeto.R;
import static br.com.carlos.projeto.BaseActivity.VersionName;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String documento = "Solitar texto para o About.";

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_directions_car)
                .setDescription(documento)
                .addItem(new Element().setTitle("Versão "+ VersionName))
                //.addItem(adsElement)
                .addGroup("Contatos")
                .addEmail("contato@carlos.com.br")
                .addWebsite(" https://www.carlos.com.br")
                //.addFacebook("capsistemas")
                //.addTwitter("caneto")
                //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("br.com.carlos.projeto")
                //.addInstagram("medyo80")
                //.addGitHub("caneto")
                //.addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
        //setContentView(R.layout.activity_sobre);
    }
}
