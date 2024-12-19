/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.excepciones;

import com.juego.personajes.Personaje;
import java.util.List;
import javafx.scene.control.TextField;

/**
 *
 * @author ivan_
 */

//Maneja las excepciones
public class HandlerExcepciones {
    public static int validarCampoPositivo(TextField campo, String nombreCampo) throws ValorNegativoException{
               
            int valor = Integer.parseInt(campo.getText());
            if (valor <= 0) {
                throw new ValorNegativoException(nombreCampo + " debe ser un número positivo.");//Arroja excepcion personalizada si el valor es menor a cero
            }
            return valor;
       
    }
    
    
    
    public static boolean verificarDuplicadoDePersonaje(List<Personaje> listaPersonajes, Personaje personajeBuscado)throws PersonajeDuplicadoException
    {
        for (Personaje personaje : listaPersonajes) {
            if (personaje.equals(personajeBuscado)) //Usando equals, se definio que por nombre y tipo de personajes dos elementos son iguales
                //Si son iguales, arroja la excepcion personalizada PersonajeDuplicadoException
            {  
                
                throw new PersonajeDuplicadoException("PERSONAJE DUPLICADO! REVISAR NOMBRE Y/O TIPO D EPERSONAJE");
                
                  
            }
        }
        return false;    }

    //Maneja valores vacios
    public static void verificarValorVacio(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El valor no puede estar vacío.");
        }
    }
}
