/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.personajes;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import com.juego.interfaces.HabilidadEspecial;

/**
 *
 * @author ivan_
 */

//Implementamos comparable para aplicar compareTo y serializable nos permite serializar a binario las clases hijas.

public abstract class Personaje implements Serializable, Comparable<Personaje>  {
    
    private static final long serialVersionUID = 1L;//identificador de la serializacion
    //
    private String nombre;
    private int nivel;
    protected int vidas;
    protected TipoPersonaje tipo;
    protected String id;

    
    
    public Personaje() {
        this.id = UUID.randomUUID().toString(); // Generar ID unico
        this.setNivel(0); // Nivel por default
        this.setVidas(0); // Vida por defaul
    }

    public Personaje(String nombre) {
        this(); 
        this.nombre = nombre;
    }

    public Personaje(String nombre, int nivel) {
        this(nombre); 
        this.setNivel(nivel); 
    }

    public Personaje(String nombre, int nivel, int vidas) {
        this(nombre, nivel); 
        this.setVidas(vidas);
    }

    //Getters

    public String getNombre() {
        return nombre;
    }
    

    public int getNivel() {
        return nivel;
    }

    public int getVidas() {
        return vidas;
    }
    
    public String getTipo() {
        return tipo.toString();
    }
    
    public String getId(){
        return id;
    }
    

    //Setters
    public void setTipo(TipoPersonaje tipo) {
        this.tipo = tipo;
    }
   
    
    public void setVidasPersonaje(int vidas) {
        setVidas(vidas);
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setNivelPersonaje(int nivel){
        setNivel(nivel);
    }       
    
    
    //Metodo privado para asegurarnos que nivel y vida no esten por debajo de cero
    private void setNivel(int nivel) {
        if (nivel < 0) {
            throw new IllegalArgumentException("El nivel no puede ser negativo.");
        }
        this.nivel = nivel;
    }
     private void setVidas(int vidas) {
        if (nivel < 0) {
            throw new IllegalArgumentException("La vida no puede ser negativa.");
        }
        this.vidas = vidas;
    }
     
     //Metodo para escribir en archivo Csv
     public String toCsv() {
         return String.format("%s,%d,%d,%s", nombre, nivel, vidas, getTipo());
    }
     
     // Método abstracto para exportar atributos específicos
    public abstract String atributosEspecificosCsv();
    
    public String imprimirTxt(){
        StringBuilder sb= new StringBuilder();
        sb.append("Nombre del personaje: ").append(getNombre()).append("\nNivel: ").append(getNivel()).append("\nPuntos de vida: ").append(getVidas() ).append("\nRol: ");
         
        String resultado= sb.toString();
        return resultado;   
    }

    
    
     
     //Metodo para devolver caracteres personalizados
     
     
     @Override
     public String toString(){
         StringBuilder sb= new StringBuilder();
         sb.append("Nombre del personaje: ").append(getNombre()).append(" | Nivel: ").append(getNivel()).append(" | Puntos de vida: ").append(getVidas() ).append(" |Rol: ");
         
         String resultado= sb.toString();
         return resultado;
     
     }   
    
    //Sobreescribimos equals para comparar
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Si los dos objetos son el mismo, retorna true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Si el objeto es nulo o de otra clase, retorna false
        }
        Personaje personaje = (Personaje) obj;
        return Objects.equals(this.getNombre(), personaje.getNombre()) && Objects.equals(this.getTipo(),personaje.getTipo()); // Son el mismo personaje si poseen el mismo nombre y nivel
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo); // Sobrescribe también hashCode para mantener la consistencia. Esto tiene que ver con
                                            //el contrato de comportambiento que poseen hashCode y equals
    }
    
    
    
     //Se usara en repositorioPersonajes para ordenar lista por Nombre 
    //Interfaz usada: comparable
    @Override
    public int compareTo(Personaje personaje) {
       // se compara por Nombre
            return this.getNombre().compareTo(personaje.getNombre());
       
    }
    
    
 
    
    
}
