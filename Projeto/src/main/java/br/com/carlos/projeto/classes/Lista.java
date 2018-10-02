package br.com.carlos.projeto.classes;

/**
 * Created by carlos on 22/03/2018.
 */

public class Lista {

    private String nome_exame;
    private String preco_exame;

    public Lista(String nomeexame, String precoexame) {
        this.nome_exame = nomeexame;
        this.preco_exame = precoexame;
    }

    public String getNome_exame() {
        return nome_exame;
    }

    public void setNome_exame(String nome_exame) {
        this.nome_exame = nome_exame;
    }

    public String getPreco_exame() {
        return preco_exame;
    }

    public void setPreco_exame(String preco_exame) {
        this.preco_exame = preco_exame;
    }



}
