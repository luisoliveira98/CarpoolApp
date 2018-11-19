package com.example.android.carpoolapp;

import java.io.Serializable;

public class Carro implements Serializable {
    private String marca,
                modelo,
                cor,
                matricula,
                ano;

    public Carro(){

    }

    public Carro(String marca, String modelo, String cor, String matricula, String ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.matricula = matricula;
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
