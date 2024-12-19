/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.personajes;

import com.juego.interfaces.HabilidadEspecial;

/**
 *
 * @author ivan_
 */
public class Arquero extends Personaje implements HabilidadEspecial{
    
    //Atributos particulares
    private int flechas;
    private int velocidadDeTiro;
    
    //Constructor
    public Arquero(int flechas, String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
        this.flechas = flechas;
        this.setTipo(TipoPersonaje.ARQUERO);        
    }

    public Arquero(String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
    }
    public Arquero(int flechas, String nombre, int nivel, int vidas, int velocidadDeTiro) {
        super(nombre, nivel, vidas);
        this.flechas = flechas;
        this.velocidadDeTiro=velocidadDeTiro;
        this.setTipo(TipoPersonaje.ARQUERO);        
    }

    
    //Getter
    public int getFlechas() {
        return flechas;
    }

    public int getVelocidadDeTiro() {
        return velocidadDeTiro;
    }
    
    
    //Setter
    public void setFlechas(int flechas) {
        this.flechas = flechas;
    }
    
    public void setVelocidadDeTiro(int velocidadDeTiro){
    
        this.velocidadDeTiro=velocidadDeTiro;
        
    }

    
    //Sobreescrituras
    @Override
    
    public String toString(){
    
        return super.toString() + this.getTipo()+" | Flechas: " + this.getFlechas()+ " | Velocidad de tiro: " + this.velocidadDeTiro + " | " + activarHabilidad();    
    }
    
    //Formato de salida de impresion de texto
    @Override
    public String imprimirTxt(){
       
        return super.imprimirTxt() +  this.getTipo()+"\nFlechas: " + this.getFlechas()+ "\nVelocidad de tiro: " + this.velocidadDeTiro+"\n"+activarHabilidad()+"\n"+"******************************" ;   
    }
    
    
    //Sobreescrivo metodos para escribir en csv los atributos especificos 
    @Override
    public String atributosEspecificosCsv() {
        return flechas + "," + velocidadDeTiro;
    }
    
    
    //Habilidad particular de personaje
    @Override
    public String activarHabilidad() {
        StringBuilder sb = new StringBuilder();

        if (this.getNivel() > 30) {
            sb.append(this.getNombre()).append(" adquiere habilidad: flechas en llamas");
            sb.append("\n- Adquiere segunda habilidad: tiro certero");
        } else if (this.getNivel() > 15) {
            sb.append(this.getNombre()).append(" adquiere habilidad: flechas en llamas");
        } else {
            sb.append(this.getNombre()).append(" - Sin habilidades especiales");
        }

        return sb.toString();
    }


    
}
