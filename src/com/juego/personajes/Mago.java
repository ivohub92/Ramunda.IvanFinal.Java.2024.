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
public class Mago extends Personaje implements HabilidadEspecial{
    protected int mana;
    private int arcanos;


    public Mago(int mana, String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
        this.setTipo(TipoPersonaje.MAGO);
        this.mana = mana;
    }

    public Mago(String nombre, int nivel, int vidas) {
        super(nombre, nivel, vidas);
    }
    
    public Mago(int mana, String nombre, int nivel, int vidas, int arcanos) {
        super(nombre, nivel, vidas);
        this.setTipo(TipoPersonaje.MAGO);
        this.mana = mana;
        this.arcanos=arcanos;
    }
    

    public int getMana() {
        return mana;
    }
    
    public int getArcanos(){
        return arcanos;
    }
    
    public void setArcanos(int arcanos){
        this.arcanos=arcanos;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    
    
    @Override
    public String toString(){
    
        return super.toString() + this.getTipo()+" | Mana: "+mana + " | Arcanos: " +arcanos + " | " + activarHabilidad();
    }
    
    @Override
    public String imprimirTxt(){
       
        return super.imprimirTxt() +  this.getTipo()+ "\nMana: " + this.getMana()+ "\nArcanos: " + this.getArcanos()+"\n"+activarHabilidad()+"\n" +"******************************";
    }
    
    
    @Override
    public String getTipo() {
        return super.getTipo(); // Llama al método de la superclase
    }
    
    @Override
    public String atributosEspecificosCsv() {
        return mana + "," + arcanos;
    }
    
    @Override
    public String activarHabilidad() {
        StringBuilder sb = new StringBuilder();

        if (this.getNivel() > 30) {
            sb.append(this.getNombre()).append(" adquiere hechizo: Encanto de hada");
            sb.append("\n- Adquiere segunda habilidad: Tormenta de hielo");
        } else if (this.getNivel() > 15) {
            sb.append(this.getNombre()).append(" adquiere hechizo: Encanto de hada");
        } else {
            sb.append(this.getNombre()).append(" - Sin habilidades especiales");
        }

        return sb.toString();
    }
}
