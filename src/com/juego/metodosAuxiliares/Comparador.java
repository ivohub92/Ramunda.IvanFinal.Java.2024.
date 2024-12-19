/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.metodosAuxiliares;

import com.juego.personajes.Personaje;
import java.util.Comparator;

/**
 *
 * @author ivan_
 */
public class Comparador {
    // Comparator para ordenar por nivel primero, luego por nombre   
    //Declaramos como statico
    public static final Comparator<Personaje> POR_TIPO_Y_NIVEL = 
       Comparator.comparing(Personaje::getTipo)
                 .thenComparingInt(Personaje::getNivel);

}
