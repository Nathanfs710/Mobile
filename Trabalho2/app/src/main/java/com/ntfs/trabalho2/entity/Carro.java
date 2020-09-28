package com.ntfs.trabalho2.entity;


import java.io.Serializable;

public class Carro implements Serializable {
    static int c = 1;
    private int id;
    private String fabricante;
    private String modelo;
    private int ano;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carro (){

    }

    public Carro (String fabricante, String modelo, int ano){
        this.id = c;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.ano = ano;
        c++;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public static int getC() {
        return c;
    }

    public static void setC(int c) {
        Carro.c = c;
    }

    @Override
    public String toString() {
        return
                "  id : " + id +
                "\n  fabricante : " + fabricante +
                "\n  modelo : " + modelo +
                "\n  ano : " + ano
                ;
    }
}
