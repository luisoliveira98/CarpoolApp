package com.example.android.carpoolapp;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Viagem implements Serializable {

    private String emailUser;
    private String pontoPartida;
    private String pontoDestino;
    private String data;
    private String hora;
    private int lugaresDisponiveis;
    private double precoPassageiro;
    private String comentarios;

    public Viagem() {

    }

    /*
   Getters and Setters
     */
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPontoPartida() {
        return pontoPartida;
    }

    public void setPontoPartida(String pontoPartida) {
        this.pontoPartida = pontoPartida;
    }

    public String getPontoDestino() {
        return pontoDestino;
    }

    public void setPontoDestino(String pontoDestino) {
        this.pontoDestino = pontoDestino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getLugaresDisponiveis() {
        return lugaresDisponiveis;
    }

    public void setLugaresDisponiveis(int lugaresDisponiveis) {
        this.lugaresDisponiveis = lugaresDisponiveis;
    }

    public double getPrecoPassageiro() {
        return precoPassageiro;
    }

    public void setPrecoPassageiro(double precoPassageiro) {
        this.precoPassageiro = precoPassageiro;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        String retVal = "De: " + this.pontoPartida + "\nPara: " + this.pontoDestino + "\nData e hora: " + this.data + " - " + this.hora;
        return retVal;
    }
}
