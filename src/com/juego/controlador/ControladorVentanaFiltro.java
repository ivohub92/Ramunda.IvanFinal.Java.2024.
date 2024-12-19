/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.controlador;

import com.juego.metodosAuxiliares.MostrarAlerta;
import com.juego.metodosAuxiliares.InformeTxt;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import com.juego.personajes.*;
import com.juego.repositorios.*;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;


/**
 *
 * @author ivan_
 */
public class ControladorVentanaFiltro {
    //Definimos campos y botones, relacionamos al archivo FXML
    @FXML
    private TextField valorIngresado;

    @FXML
    private Button btnFiltrarPorNivel;

    private ObservableList<Personaje> listaPersonajes;

    public void setListaPersonajes(ObservableList<Personaje> listaPersonajes) 
    {
        this.listaPersonajes = listaPersonajes;
    }
    
    //Inicializamos el boton y le asignamos un evento.
    @FXML
    public void initialize() {
        btnFiltrarPorNivel.setOnAction(event -> filtrarPersonajes());
    }

    
    //Evento que filtra personajes
    @FXML    
    private void filtrarPersonajes() {
        try {
            System.out.println("boton filtro");
            // Obtener el nivel ingresado
            int nivel = Integer.parseInt(valorIngresado.getText());
            System.out.println(nivel);
            // Filtrar personajes
            List<Personaje> personajesFiltrados = RepositorioPersonajes.filtrarPersonajesPorNivel(listaPersonajes, nivel);//Usamos el metodo estatico que filtra personajes por nivel          
                       
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar informe");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo de texto", "*.txt"));

            File archivo = fileChooser.showSaveDialog(new Stage());
            if (archivo != null) {
                try {
                    InformeTxt.generarInforme(personajesFiltrados, archivo,nivel);
                    MostrarAlerta.mostrarAlerta("Aviso","Informe generado correctamente.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al generar el informe.");
                }
            }  

            // Cerrar la ventana auxiliar
            ((Stage) valorIngresado.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            System.out.println("Nivel inválido, ingrese un número.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
