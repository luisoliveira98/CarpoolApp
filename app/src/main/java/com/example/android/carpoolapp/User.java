package com.example.android.carpoolapp;

import java.io.Serializable;

public class User implements Serializable {
    private String nome;
    private String email;
    private String telemovel;
    private String cidade;
    private double classificacao;
    private Carro carro;

    public User() {

    }

    public User(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.telemovel = "";
        this.cidade = "";
        this.classificacao = 0;
        this.carro = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Boolean complete() {
        if (telemovel.equals("") || cidade.equals("") || carro==null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return email;
    }
}
