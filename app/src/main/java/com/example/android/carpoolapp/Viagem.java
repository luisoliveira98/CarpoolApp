package com.example.android.carpoolapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Viagem implements Serializable {

    public enum State {
        CREATED, STARTED, FULL, FINISHED
    }

    private String emailUser;
    private String pontoPartida;
    private String pontoDestino;
    private String data;
    private String hora;
    private int lugaresDisponiveis;
    private double precoPassageiro;
    private String comentarios;
    private State estado;
    private Map<String, Integer> reservas;

    public Viagem() {
        emailUser="";
        estado = State.CREATED;
        reservas = new HashMap<>();
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

    public State getEstado() {
        return estado;
    }

    public void setEstado(State estado) {
        this.estado = estado;
    }

    public Map<String, Integer> getReservas() {
        return reservas;
    }

    public void setReservas(Map<String, Integer> reservas) {
        this.reservas = reservas;
    }

    @Override
    public String toString() {
        String retVal = "De: " + this.pontoPartida + "\nPara: " + this.pontoDestino + "\nData e hora: " + this.data + " - " + this.hora + "\nEstado: " + this.estado;
        return retVal;
    }
}
