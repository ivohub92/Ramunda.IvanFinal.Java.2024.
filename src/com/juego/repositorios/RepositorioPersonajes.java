/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.repositorios;


import com.juego.metodosAuxiliares.Comparador;
import com.juego.interfaces.FiltroPersonaje;
import com.google.gson.Gson;
import java.io.File;
import com.juego.personajes.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.*;
import java.io.*;

/**
 *
 * @author ivan_
 */


public class RepositorioPersonajes extends RepositorioGenerico implements Iterable<Personaje> {
        
    
    public RepositorioPersonajes(Gson gson, String archivoJson) {
        super(gson, archivoJson);        
    }

    
    //Mandamos una copia de la lista para evitar modificaciones
    public List<Personaje> getPersonajes() {
        return new ArrayList<>(getLista());  
    
    }  
    
    /**
     * Agrego personaje a lista y guardo en json
     * @param personaje 
     */
    
    public void agregarPersonajes(Personaje personaje) {
        guardar(personaje);
    
    }
    
    
    //Metodo exclusivo para cargar arqueros y mostrar la aplicacion de wildcard con limite inferiror
    /**
     * 
     * 
     * @param lista una lista que, para agregar en este caso, solo agregara arqueros o supertipos
     * @param arquero un objeto arquero
     */
    public void agregarArqueros(List<? super Arquero> lista, Arquero arquero) {
        lista.add(arquero); // Agregar un arquero está permitido
        System.out.println("Se ha agregado un arquero: " + arquero.getNombre());
    }

    // Método para procesar los elementos de la lista
    /**
     * 
     * @param lista Una lista que leera arquero y sus supertipos
     */
    public void procesarListaArqueros(List<? super Arquero> lista) {
        for (Object obj : lista) { // wildcard inferior permite tratar la lista como una colección de `Object`
            if (obj instanceof Arquero) { // Verificar si el objeto es un `Arquero`
                Arquero arquero = (Arquero) obj; // Realizar casting seguro. Es necesario para que arquero pueda usar sus metodos propios de su clase
            }
        }
    }
    
      
    

    // Método para filtrar personajes usando un wildcard con límite superior
    //Usaremos este wildcard porque queremos procesar cualquier subtipo de Personaje en la lista de entrada.
    /**
     * 
     * @param personajes  Lista de personajes
     * @param nivel nivel que quiero filtrar
     * @return lista filtrada por nivel maximo
     */
    public static List<Personaje> filtrarPersonajesPorNivel(List<? extends Personaje> personajes, int nivel) {
        List<Personaje> resultado = new ArrayList<>();
        for (Personaje personaje : personajes) {
            if (FiltroPersonaje.porNivel(nivel).test(personaje)) {//Llamamos al metodo generico de la interfaz que permite filtrar por nivel
                resultado.add(personaje);
            }
        }
        return resultado;
    }
    
    // Método para filtrar personajes usando un wildcard con límite superior
    //Usaremos este wildcard porque queremos procesar cualquier subtipo de Personaje en la lista de entrada.
    /**
     * 
     * @param personajes
     * @param tipo
     * @return lista filtrada por tipo
     */
    public static List<Personaje> filtrarPersonajesPorTipo(List<? extends Personaje> personajes, Class<? extends Personaje> tipo) {
        List<Personaje> resultado = new ArrayList<>();
        for (Personaje personaje : personajes) {
            if (FiltroPersonaje.porTipo(tipo).test(personaje)) {
                resultado.add(personaje);
            }
        }
        return resultado;
    }
    
    
    /**
     * 
     * @param personajes lista ordenada
     */

    public void ordenarPorNombre(List<Personaje> personajes) {
        Collections.sort(personajes);  //personajes usa Collections porque esta configurado el compareTo. El objeto personaje implementa comparable 
    }
    
    
    //Permite usar for each en nuestra lista
    @Override
    public Iterator<Personaje> iterator() {
        return getPersonajes().iterator();
    }
    
    
    //funciones para importar y exportar CSV
    public void exportarCsv(List<Personaje> personajes, File archivo) {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
           // Escribir encabezados
           writer.write("Nombre,Nivel,Salud,Tipo,AtributosEspecificos\n");

           for (Personaje personaje : personajes) {
               // Escribir los atributos comunes y específicos
               writer.write(personaje.toCsv() + "," + personaje.atributosEspecificosCsv() + "\n");
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    
    //Importa Csv
    public List<Personaje> importarCsv(File archivo) {
        List<Personaje> personajes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltar encabezado

            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                String nombre = campos[0];
                int nivel = Integer.parseInt(campos[1]);
                int vidas = Integer.parseInt(campos[2]);
                String tipo = campos[3];

                switch (tipo) {
                    case "MAGO" -> {
                        System.out.println("ingresa case");
                        int mana = Integer.parseInt(campos[4]);
                        int arcanos = Integer.parseInt(campos[5]);
                        personajes.add(new Mago(mana,nombre, nivel, vidas ,arcanos));
                    }
                    case "ARQUERO" -> {
                        int flechas = Integer.parseInt(campos[4]);
                        int velocidad = Integer.parseInt(campos[5]);
                        personajes.add(new Arquero(flechas,nombre, nivel, vidas, velocidad));
                    }
                    case "GUERRERO" -> {
                        int fuerza = Integer.parseInt(campos[4]);
                        int escudo = Integer.parseInt(campos[5]);
                        personajes.add(new Guerrero(fuerza, nombre, nivel, vidas, escudo));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return personajes;
    }
   
    /**
     * 
     * @param elementoActualizado elemento a actualizar en la lista (metodo exclusivo a aplicar en interfaz visual)
     */
    
    public void actualizar(Personaje elementoActualizado) {
        actualizarElementoGenerico( elementoActualizado,  e -> ((Personaje)e).getId().equals(elementoActualizado.getId()));//Capturanis y actualizamos por Id         
        guardarEnJson();
    }
    
    /**
     * 
     * @param id id que capturará un listener,aplicar en java fx
     */
    public void eliminar(String id) {
        eliminarElementoGenerico((e -> ((Personaje)e).getId().equals(id)));//Elimina elemento de lalista capturando por id unico de cada elemento    
        guardarEnJson();
    }
    
    
    /**
     * 
     * @param personajes lista a ordenar 
     */ 
     
     public void ordenarPorTipoYNivel(List<Personaje> personajes){
     
         Collections.sort(personajes, Comparador.POR_TIPO_Y_NIVEL);//Llamamos al metodo Comparador que aplica comparator por nivel y nombre
     }
    
     
     /**
      * 
      * @param personaje Personaje a modificar
      * @param nivel niveles a subir
      */
     //Esta funcion sube el nivel del personaje ingresado
    public void subirNivelDePersonaje(Personaje personaje, int nivel){
    
         Function<Integer, Integer> incrementarNivel = niveles -> niveles + nivel;
         personaje.setNivelPersonaje(incrementarNivel.apply(personaje.getNivel()));
    
    }
   
    
}
    
    
    

