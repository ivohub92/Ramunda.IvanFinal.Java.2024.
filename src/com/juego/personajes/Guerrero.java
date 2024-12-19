/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.personajes;
import com.juego.interfaces.Defensivo;

/**
 *
 * @author ivan_
 */
public class Guerrero extends Personaje implements Defensivo{
    
    //Atriburos particulares de la clase
    protected int fuerza;
    private int escudo;

    
    //Constructor
    public Guerrero(int fuerza, String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
        this.setTipo(TipoPersonaje.GUERRERO);
        this.fuerza = fuerza;
    }
    
     public Guerrero(int fuerza, String nombre, int nivel, int vidas, int escudo) {
        super(nombre, nivel, vidas);
        this.setTipo(TipoPersonaje.GUERRERO);
        this.fuerza = fuerza;
        this.escudo=escudo;
    }

    public Guerrero(String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
    }
    
    
    ///Getter
    public int getFuerza() {
        return fuerza;
    }

    public int getEscudo() {
        return escudo;
    }
    
    
    
    //Setter
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }
    
    
    
    //Sobreescribe el toString para imprimir
     @Override
    
    public String toString(){
    
        return super.toString() + this.getTipo()+ " | Fuerza: " + this.getFuerza()+ " | Escudo: " + this.getEscudo() + " | " +defensa();
    
    }
    
    
    //Imprime en el texto los atributos particulares
    @Override
    public String imprimirTxt(){
       
        return super.imprimirTxt() +  this.getTipo()+ "\nFuerza: " + this.getFuerza()+ "\nEscudo: " + this.getEscudo() +"\n"+ defensa()+"\n"+"******************************";
    }
    
    
    //Capturar  tipo de personaje
      @Override
    public String getTipo() {
        return super.getTipo(); // Llama al método de la superclase
    }
    
    
    //Imprimimos en Csv atributos especificos
    @Override
    public String atributosEspecificosCsv() {
        return fuerza + "," + escudo;
    }
    
    
    //Habilidades particulares de este gpersonaje
    @Override
    public String defensa() {
        StringBuilder sb = new StringBuilder();

        if (this.getNivel() > 30) {
            sb.append(this.getNombre()).append("  Defensa de oso evoluciona a Muro impenetrable");            
        } else if (this.getNivel() > 15) {
            sb.append(this.getNombre()).append(" Habilidad pasiva activada: Defensa de oso");
        }

        return sb.toString();
    }
    
}
