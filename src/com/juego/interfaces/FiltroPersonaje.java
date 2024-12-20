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

@FunctionalInterface
public interface FiltroPersonaje {

    /**
     * Método funcional que filtra un personaje según una condición específica.
     * @param personaje el personaje a evaluar
     * @return true si cumple la condición, false en caso contrario
     */
    boolean filtrar(Personaje personaje);

    /**
     * Método estático que retorna un filtro por tipo de personaje.
     * @param <T> el tipo específico de personaje
     * @param tipoPersonaje la clase del tipo de personaje
     * @return un filtro funcional para personajes de ese tipo
     */
    static <T extends Personaje> FiltroPersonaje porTipo(Class<T> tipoPersonaje) {
        return personaje -> tipoPersonaje.isInstance(personaje);
    }

    /**
     * Método estático que retorna un filtro por nivel.
     * @param nivel el nivel máximo permitido
     * @return un filtro funcional para personajes con nivel menor o igual al especificado
     */
    static FiltroPersonaje porNivel(int nivel) {
        return personaje -> personaje.getNivel() <= nivel;
    }
}