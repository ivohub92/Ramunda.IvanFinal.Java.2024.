package com.juego.ejecutar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class InterfazGrafica extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML usando el FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/juego/vistas/final.fxml"));        
        
        // Cargar el panel raíz desde FXML
        Parent root = loader.load(); // Aquí usamos el loader inicializado arriba
        
        // Establecer la escena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        // Mostrar la ventana
        primaryStage.setTitle("Gestor de personajes");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Iniciar la aplicación
    }
}
