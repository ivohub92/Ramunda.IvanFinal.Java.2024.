/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.controlador;

import com.juego.metodosAuxiliares.MostrarAlerta;
import com.juego.metodosAuxiliares.PersonajeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.juego.excepciones.HandlerExcepciones;
import com.juego.excepciones.PersonajeDuplicadoException;
import com.juego.excepciones.ValorNegativoException;
import java.io.*;
import javafx.scene.control.*;
import com.juego.personajes.*;
import com.juego.repositorios.*;
import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 *
 * @author ivan_
 */
public class ControladorPersonajes {
    
    //Declaramos textfields y labels. Los relacionamos al scenebuilder
    
    @FXML
    private TextField campoNombre; 
    
    @FXML
    private TextField campoVidas;
    
    @FXML
    private TextField campoNivel;
    
    @FXML
    private TextField campoMana;
    
    @FXML
    private TextField campoArcanos;
    
    @FXML
    private TextField campoFuerza;
    
    @FXML
    private TextField campoEscudo;
    
    @FXML
    private TextField campoFlechas;
    
    @FXML
    private TextField campoVelocidad; 
    
    @FXML
    private Label labelMana;
    
    @FXML
    private Label labelArcanos;
    
    @FXML
    private Label labelFuerza;
    
    @FXML
    private Label labelEscudo;
    
    @FXML
    private Label labelFlechas;
    
    @FXML
    private Label labelVelocidad;
        
    @FXML
    private ListView<Personaje> listaVistaPersonajes;
    
    @FXML
    private ComboBox<String> comboTipo;
    
    
    //Definimos el repositorio y la listaObserbale que mostrara los resultados
    private RepositorioPersonajes repositorioPersonajes;
    private ObservableList<Personaje> listaObservable;
    
    //Definimos metodo para setear la lista ibserbable
     public void setListaPersonajes(ObservableList<Personaje> listaPersonajes) {
        this.listaObservable = listaPersonajes;
    }
    
    //Definimos Gson adaptado a personajes y a su subclases
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Personaje.class, new PersonajeTypeAdapter()).setPrettyPrinting().create();

    

    //Nombre del archivo autoguardado
    private final String nombreArchivoJson = "partida.json";

   
    //Inicio de la aplicacion
    @FXML
    public void initialize() {
        
        //Definimos objeto
        repositorioPersonajes = new RepositorioPersonajes(gson,nombreArchivoJson);
        repositorioPersonajes.leerDesdeJson(Personaje.class);//Leemos el archivo autoguardado y cargamos la lista
        listaObservable = FXCollections.observableArrayList(repositorioPersonajes.getPersonajes());//Obtengo personajes y los llevo a la lista observable                                     
        listaVistaPersonajes.setItems(listaObservable);//Seteo la lista obserbable en la listview
        
         // Configurar el ComboBox
        comboTipo.getItems().addAll("Arquero", "Mago","Guerrero");
        
        
        
       // Agregar listener al ComboBox para cambiar visibilidad de campos
        comboTipo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue == null) {
                // Ocultar todos los campos si no hay selección
                ocultarCamposArquero();
                ocultarCamposMago();
                ocultarCamposGuerrero();
            } else {
                // Mostrar/ocultar campos según la selección
                switch (newValue) {
                    case "Arquero" -> {
                        mostrarCamposArquero();
                        ocultarCamposMago();
                        ocultarCamposGuerrero();
                    }
                    case "Mago" -> {
                        mostrarCamposMago();
                        ocultarCamposArquero();
                        ocultarCamposGuerrero();
                    }
                    case "Guerrero" -> {
                        mostrarCamposGuerrero();
                        ocultarCamposMago();
                        ocultarCamposArquero();
                    }
                    default -> {
                        // Ocultar todos los campos en caso de valor inesperado
                        ocultarCamposArquero();
                        ocultarCamposMago();
                        ocultarCamposGuerrero();
                    }
                }
            }
        });
         // Listener para cambios en la selección del ListView
        listaVistaPersonajes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosPersonaje(newSelection);
            }
        });
    }
    
    //Carga los datos en los textFields segun el personaje seleccionado
    private void cargarDatosPersonaje(Personaje personaje) {
        // Campos comunes
        campoNombre.setText(personaje.getNombre());
        campoNivel.setText(String.valueOf(personaje.getNivel()));
        campoVidas.setText(String.valueOf(personaje.getVidas()));

        // Limpio campos específicos de subclases
        campoMana.clear();
        campoArcanos.clear();
        campoFlechas.clear();
        campoVelocidad.clear();
        campoFuerza.clear();
        campoEscudo.clear();

            // Configuración específica según la subclase
            switch (personaje) {
                case Mago mago -> {
                    comboTipo.setValue("Mago");
                    campoMana.setText(String.valueOf(mago.getMana()));
                    campoArcanos.setText(String.valueOf(mago.getArcanos()));
                }       case Arquero arquero -> {
                    comboTipo.setValue("Arquero");
                    campoFlechas.setText(String.valueOf(arquero.getFlechas()));
                    campoVelocidad.setText(String.valueOf(arquero.getVelocidadDeTiro()));
                }       case Guerrero guerrero -> {
                    comboTipo.setValue("Guerrero");
                    campoFuerza.setText(String.valueOf(guerrero.getFuerza()));
                    campoEscudo.setText(String.valueOf(guerrero.getEscudo()));
                }       default -> {
                }
            }
    }


    //Metodos para mostrar y ocultar los campos
     private void mostrarCamposGuerrero() {
        labelFuerza.setVisible(true);
        campoFuerza.setVisible(true);
        labelEscudo.setVisible(true);
        campoEscudo.setVisible(true);
    }
     
    private void ocultarCamposGuerrero() {
        labelFuerza.setVisible(false);
        campoFuerza.setVisible(false);
        labelEscudo.setVisible(false);
        campoEscudo.setVisible(false);
    }

    private void mostrarCamposMago() {
        labelMana.setVisible(true);
        campoMana.setVisible(true);
        labelArcanos.setVisible(true);
        campoArcanos.setVisible(true);
    }
    
    private void ocultarCamposMago() {
        labelMana.setVisible(false);
        campoMana.setVisible(false);
        labelArcanos.setVisible(false);
        campoArcanos.setVisible(false);
    }

    private void mostrarCamposArquero() {
        labelVelocidad.setVisible(true);
        campoVelocidad.setVisible(true);
        labelFlechas.setVisible(true);
        campoFlechas.setVisible(true);
    }
    
    private void ocultarCamposArquero() {
        labelVelocidad.setVisible(false);
        campoVelocidad.setVisible(false);
        labelFlechas.setVisible(false);
        campoFlechas.setVisible(false);
    }
    
    //Configuro botones
    @FXML
    public void btnAgregarPersonaje(){
         try {
                // Validaciones iniciales
                if (comboTipo.getValue() == null) {
                    MostrarAlerta.mostrarAlerta("Error", "Seleccione un tipo de personaje");
                    return;
                }

                String nombre = campoNombre.getText();
                if (nombre.isBlank()) {
                    throw new IllegalArgumentException("El nombre no puede estar vacío.");//Valida que no este vacio el nombre
                }

                // Validaciones comunes
                int nivel = HandlerExcepciones.validarCampoPositivo(campoNivel, "Nivel");//Valida campo que sea positivo
                int vida = HandlerExcepciones.validarCampoPositivo(campoVidas, "Vida");

                Personaje nuevoPersonaje;
                switch (comboTipo.getValue()) {
                    case "Arquero" -> {
                        int flechas = HandlerExcepciones.validarCampoPositivo(campoFlechas, "Flechas");
                        int velocidad= HandlerExcepciones.validarCampoPositivo(campoVelocidad, "Velocidad");
                        nuevoPersonaje = new Arquero(flechas, nombre, nivel, vida,velocidad);
                    }
                    case "Mago" -> {
                        int mana = HandlerExcepciones.validarCampoPositivo(campoMana, "Mana");
                        int arcanos = HandlerExcepciones.validarCampoPositivo(campoArcanos, "Arcanos");
                        nuevoPersonaje = new Mago(mana, nombre, nivel, vida,arcanos);
                    }
                    case "Guerrero" -> {
                        int fuerza = HandlerExcepciones.validarCampoPositivo(campoFuerza, "Fuerza");
                        int escudo = HandlerExcepciones.validarCampoPositivo(campoEscudo, "Escudo");
                        nuevoPersonaje = new Guerrero(fuerza, nombre, nivel, vida,escudo);
                    }
                    default -> {
                        MostrarAlerta.mostrarAlerta("Error", "Tipo de personaje no reconocido.");
                        return;
                    }
                }
                
                HandlerExcepciones.verificarDuplicadoDePersonaje(repositorioPersonajes.getLista(),nuevoPersonaje);//Verificamos que no este duplicado el objeto, sino dispara excepcion 
                // Guardar y refrescar
                repositorioPersonajes.agregarPersonajes(nuevoPersonaje);  
                listaObservable.add(nuevoPersonaje); 
                listaVistaPersonajes.refresh(); // Refresca el ListView si es necesario
                MostrarAlerta.mostrarAlerta("Éxito", "Personaje agregado correctamente.");
                   
            } catch (IllegalArgumentException | NullPointerException|PersonajeDuplicadoException|ValorNegativoException ex) {//Capturo excepciones.
                MostrarAlerta.mostrarAlerta("Error", ex.getMessage());
            }
    
    }
    
    @FXML
    
    public void btnActualizarPersonaje(){
        {
            Personaje personajeSeleccionado = listaVistaPersonajes.getSelectionModel().getSelectedItem();//Selecciona en pantalla
            if (personajeSeleccionado != null) {
                try {
                    // Verificar campos vacíos antes de asignar valores
                    HandlerExcepciones.verificarValorVacio(campoNombre.getText());
                    HandlerExcepciones.verificarValorVacio(campoNivel.getText());
                    HandlerExcepciones.verificarValorVacio(campoVidas.getText()); 
                    
                    // Actualizar valores comunes
                    personajeSeleccionado.setNombre(campoNombre.getText());
                    personajeSeleccionado.setNivelPersonaje(HandlerExcepciones.validarCampoPositivo(campoNivel, "Nivel"));
                    personajeSeleccionado.setVidasPersonaje(HandlerExcepciones.validarCampoPositivo(campoVidas, "Vida"));

                    // Validar tipo de personaje y actualizar sus atributos específicos
                    String tipoSeleccionado = comboTipo.getValue();
                    if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) {
                        throw new IllegalArgumentException("Debe seleccionar un tipo de personaje.");
                    }

                    switch (tipoSeleccionado) {
                        case "Arquero" -> {
                            if (!(personajeSeleccionado instanceof Arquero)) {
                                throw new IllegalArgumentException("El personaje seleccionado no es un Arquero.");
                            }
                            HandlerExcepciones.verificarValorVacio(campoFlechas.getText());
                            ((Arquero) personajeSeleccionado).setFlechas(HandlerExcepciones.validarCampoPositivo(campoFlechas, "Flechas"));
                            ((Arquero) personajeSeleccionado).setVelocidadDeTiro(HandlerExcepciones.validarCampoPositivo(campoVelocidad, "Velocidad"));
                        }
                        case "Mago" -> {
                            if (!(personajeSeleccionado instanceof Mago)) {
                                throw new IllegalArgumentException("El personaje seleccionado no es un Mago.");
                            }
                            HandlerExcepciones.verificarValorVacio(campoMana.getText());
                            ((Mago) personajeSeleccionado).setMana(HandlerExcepciones.validarCampoPositivo(campoMana, "Mana"));
                            ((Mago) personajeSeleccionado).setArcanos(HandlerExcepciones.validarCampoPositivo(campoArcanos, "Arcanos"));
                        }
                        case "Guerrero" -> {
                            if (!(personajeSeleccionado instanceof Guerrero)) {
                                throw new IllegalArgumentException("El personaje seleccionado no es un Guerrero.");
                            }
                            HandlerExcepciones.verificarValorVacio(campoFuerza.getText());
                            ((Guerrero) personajeSeleccionado).setFuerza(HandlerExcepciones.validarCampoPositivo(campoFuerza, "Fuerza"));
                            ((Guerrero) personajeSeleccionado).setEscudo(HandlerExcepciones.validarCampoPositivo(campoEscudo, "Escudo"));
                        }
                        default -> throw new IllegalArgumentException("Tipo de personaje no válido.");
                    }

                    // Actualizar en el repositorio y refrescar la lista
                    repositorioPersonajes.actualizar(personajeSeleccionado);
                    listaVistaPersonajes.refresh(); // Refresca el ListView si es necesario
                } catch (ValorNegativoException|IllegalArgumentException ex) {
                    MostrarAlerta.mostrarAlerta("Entrada Inválida", ex.getMessage());
                }

            } else {
                MostrarAlerta.mostrarAlerta("Selección Inválida", "Por favor, selecciona un personaje de la lista.");
            }   
        }
    }
    
    
    
    @FXML    
    public void btnEliminarPersonaje(){
         Personaje personaSeleccionada = listaVistaPersonajes.getSelectionModel().getSelectedItem();//Seleccionamos de pantalla
            if (personaSeleccionada != null) {
                System.out.println(personaSeleccionada);
                repositorioPersonajes.eliminar(personaSeleccionada.getId());//Eliminamos por Id coincidiente
               
            }
            actualizarListView();
    }
    
    @FXML    
    public void btnOrdenarPorNombre(){
    
        repositorioPersonajes.ordenarPorNombre(listaObservable);//Llamamos metodo que ordene y refrescamos pantalla
        listaVistaPersonajes.refresh();
    
    }
    
    @FXML    
    public void btnOrdenarPorTipoYNivel(){    
        repositorioPersonajes.ordenarPorTipoYNivel(listaObservable);//Llamamos metodo que ordene y refrescamos pantalla
        listaVistaPersonajes.refresh();   
    }

    
    @FXML
    
    public void btnSubirNivel(){
    
        Personaje personaSeleccionada = listaVistaPersonajes.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null){
            repositorioPersonajes.subirNivelDePersonaje(personaSeleccionada, 1);//Mmetodo para subir nivel, en este caso uno solo
            repositorioPersonajes.guardarEnJson();
            listaVistaPersonajes.refresh();
        }
    
    }
    
    @FXML    
    
    public void btnExportarJson() {
        // datos a exportar
        List<Personaje> listaPersonaje = repositorioPersonajes.getPersonajes();

        // Abrir el diálogo para seleccionar el archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como JSON");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos JSON", "*.json")
        );
        File archivoSeleccionado = fileChooser.showSaveDialog(new Stage());

        if (archivoSeleccionado != null) {
            // Convertir los datos a JSON y escribir en el archivo
            repositorioPersonajes.guardarEnJson(listaPersonaje, archivoSeleccionado);//Guardamos el archivo seleccionado
        }
    }
    
    @FXML
    
    public void btnCargarJson() {
        // Abrir el diálogo para seleccionar un archivo JSON
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo JSON");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos JSON", "*.json")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(new Stage());

        if (archivoSeleccionado != null) {
            // Leer y cargar los datos desde el archivo JSON
            repositorioPersonajes.leerDesdeJson(Personaje.class ,archivoSeleccionado);
            repositorioPersonajes.guardarEnJson();
            actualizarListView();
        }
    }
    
    
    private void actualizarListView() {
        listaVistaPersonajes.getItems().clear();
        repositorioPersonajes.getPersonajes().forEach(personaje -> listaVistaPersonajes.getItems().add(personaje));
    }
    
    //Abre ventana para ingresar valor de filtrado
    @FXML
    public void btnFiltroPorNivel(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/juego/vistas/sceneFiltro.fxml"));
            Parent root = loader.load();

            // Pasar la lista de personajes al controlador de la ventana de filtro
            ControladorVentanaFiltro filterController = loader.getController();
            filterController.setListaPersonajes(listaObservable);

            Stage stage = new Stage();
            stage.setTitle("Filtrar por Nivel");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    
    
    
    
   
   
    @FXML    
    public void btnGuardarCvs() {
        File archivo = elegirArchivo(); // Muestra un diálogo para elegir dónde guardar
        if (archivo != null) {
            repositorioPersonajes.exportarCsv(new ArrayList<>(listaObservable), archivo);//Usamos exportar para escribir el archivo csv
        }
    }   

    
    @FXML
    public void btnCargarCvs() {
        File archivo = cargarArchivo(); // Muestra un diálogo para seleccionar el archivo
        if (archivo != null) {
            System.out.println("exportando");
            List<Personaje> importados = repositorioPersonajes.importarCsv(archivo);            
            listaObservable.setAll(importados); // Actualiza la lista observable
            repositorioPersonajes.setLista(importados);
            repositorioPersonajes.guardarEnJson();
            listaVistaPersonajes.setItems(null); // Desvincula temporalmente
            listaVistaPersonajes.setItems(listaObservable); // Vuelve a vincular          
        }
    }
    
    
    //Metodos para abrir y guardar csv
    private File elegirArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        return fileChooser.showSaveDialog(null);
    }
    private File cargarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar archivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        return fileChooser.showOpenDialog(null); // Muestra el diálogo para abrir un archivo
    }
    
    
    //Guardado y cargado de binario
    
    @FXML    
    public void btnGuardarBin() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar personajes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos binarios", "*.bin"));

        File archivo = fileChooser.showSaveDialog(new Stage());
        if (archivo != null) {
            try {
                repositorioPersonajes.exportarBinario(repositorioPersonajes.getPersonajes(), archivo);
                System.out.println("Personajes exportados correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al exportar personajes.");
            }
        }
    }
    
    @FXML
    public void btnCargarBinario() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo de personajes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos binarios", "*.bin"));

        File archivo = fileChooser.showOpenDialog(new Stage());
        if (archivo != null) {
            try {
                List<Personaje> importados = repositorioPersonajes.importarBinario(archivo);
                listaObservable.setAll(importados); // Actualiza la lista observable
                repositorioPersonajes.setLista(importados);
                repositorioPersonajes.guardarEnJson();
                listaVistaPersonajes.setItems(null); // Desvincula temporalmente
                listaVistaPersonajes.setItems(listaObservable); // Vuelve a vincular 
                System.out.println("Personajes importados correctamente.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error al importar personajes.");
            }
        }
    }

}
