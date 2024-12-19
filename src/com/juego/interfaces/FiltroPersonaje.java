/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.juego.interfaces;
import com.juego.personajes.*;

/**
 *
 * @author ivan_
 */
import java.util.function.Predicate;

public class FiltroPersonaje {

    /**
     * 
     * @param <T>
     * @param tipoPersonaje
     * @return expresion lambda que retorna true cuando coincide el personaje con la clase
     * usamos para recorrer una lista
     */
    public static <T extends Personaje> Predicate<Personaje> porTipo(Class<T> tipoPersonaje) {
        return personaje -> tipoPersonaje.isInstance(personaje);
    }

    /**
     * 
     * @param valor
     * @return expresion lambda que retorna true cuando el nivel es menor o igual al ingresado
     * usamos para recorrer una lista
     */
    
    public static Predicate<Personaje> porNivel(int valor) {
        return personaje -> personaje.getNivel() <= valor;
    }
}