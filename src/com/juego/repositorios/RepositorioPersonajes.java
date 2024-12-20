/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.repositorios;


import java.util.Comparator;
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
        agregarYGuardarObjeto(personaje);
    
    }
    
   
    
    
    
    public static void filtrarArqueros(List<? super Arquero> personajes, List<? extends Personaje> todosPersonajes) {//Filtramos lista cuya salida sera Arquero o supertipos
        for (Personaje personaje : todosPersonajes) {//Una lista asi acepta una lista de genericos
            if (personaje instanceof Arquero) {
                personajes.add((Arquero) personaje);//Casteamos a Arquero para poder aplicar sus metodos especificos de la clase a la que pertenece
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
        FiltroPersonaje filtro = FiltroPersonaje.porNivel(nivel); // Creamos el filtro una vez
        for (Personaje personaje : personajes) {
            if (filtro.filtrar(personaje)) { // Usamos el método funcional `filtrar`
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
         FiltroPersonaje filtro = FiltroPersonaje.porTipo(tipo);
        for (Personaje personaje : personajes) {
            if (filtro.filtrar(personaje)) {
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
     
     public void ordenarPorTipoYNivel(List<Personaje> personajes) {
        // Usamos thenComparing para encadenar las comparaciones
        Collections.sort(personajes, Comparator.comparing(Personaje::getTipo)
                .thenComparingInt(Personaje::getNivel));
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
    
    
    

