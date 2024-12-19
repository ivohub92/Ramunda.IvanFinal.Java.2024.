package com.juego.repositorios;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.function.Predicate;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RepositorioGenerico<T> {
    
    /**
     * Declaramos las variables
     * Lista generica a trabajar
     * Gson, que nos permite trabajar con formatos json
     * ArchvoJson es el nombre del archivo json que va a autoguardarse con cada modificacion
     */
    
    
    protected List<T> lista;
    protected Gson gson;
    protected String archivoJson;


    /**
     * 
     * @param gson
     * @param archivoJson 
     */
    public RepositorioGenerico(Gson gson, String archivoJson) {
        this.gson = gson;
        this.archivoJson = archivoJson;
        this.lista = new ArrayList<>();
    } 

    /**
     * 
     * @param objeto Objeto T que quiero agregar al archivo Json
     */
    public void guardar(T objeto) {
        lista.add(objeto);
        guardarEnJson();
    }
    
    /**
     * 
     * @return una copia de la lista<T>, es para evitar modificaciones de la lista original 
     */
    public List<T> getLista(){
        return new ArrayList<>(lista);    
    }
    
    /**
    * @param listaCargada: Si deseamos sobreescribir/cargar la lista
    */
    
    public void setLista(List<T> listaCargada){
        this.lista=listaCargada;    
        guardarEnJson();//metodo que guarda en el Json de autoguardado el estado actual de la lista
    }    
    
    /**
     * @param lista guardar la lista actual en codigo binario
     * @param nombreArchivoBinario nombre del archivo a exportar
    */
    public void guardarListaBinario(List<?> lista, String nombreArchivoBinario) {
    
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivoBinario))) {
            salida.writeObject(lista);        
        } catch (IOException e) {
            System.err.println("Error al guardar la lista binaria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga una lista de objetos desde un archivo binario.
     *
     * @param nombreArchivoBinario El nombre del archivo binario.
     * @return Una lista de objetos leída desde el archivo binario.
     */
    public List<T> cargarListaBinaria(String nombreArchivoBinario) 
    {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivoBinario))) 
        {
            // Verificamos que el archivo contiene una lista
            Object obj = entrada.readObject();
            if (obj instanceof List<?>) {
                return (List<T>) obj; // Realizamos el casting de forma segura
            } else {
                System.err.println("El archivo no contiene una lista.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassCastException e) {
            System.err.println("El contenido del archivo no es una lista del tipo esperado.");
            e.printStackTrace();
            return null;
        }
    }
      
    
    /**
     * Para objetos que fueron actualizados y debemos guardarlos
     *     
     * @param elementoActualizado valor a reemplazar en lista
     * @param criterio expresion lambda que permite ser aplicada en el metodo replace all
     * Se guarda automaticamente en el Json. El criterio a usarse esta relacionado con los listeners de javaFX     */    
    
    public  void actualizarElementoGenerico(T elementoActualizado, Predicate<T> criterio) {
        lista.replaceAll(e -> criterio.test(e) ? elementoActualizado : e);
        guardarEnJson();
    }
        
    /**
     * @param criterio expresion lambda
     * elimina elemento generico de la lista
     * 
     */
    public void eliminarElementoGenerico(Predicate<T> criterio) {
        lista.removeIf(criterio);
        guardarEnJson();
    }
    
    /**
     * Exportamos documento en txt
     * @param lista archivo para guardar en Txt
     * @param archivo 
     */
    
    public void guardarEnArchivoTxt(List<T> lista, String archivo) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("La lista está vacía o es nula.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) { // Sin el segundo argumento
            for (T item : lista) {
                if (item != null) {  // Verificar si el objeto es null
                    System.out.println("Escribiendo: " + item.toString());  // Verifica lo que se va a escribir
                    writer.write(item.toString());
                    writer.newLine();
                } else {
                    System.out.println("Elemento nulo encontrado, se omite.");
                }
            }
            writer.flush();  // Asegúrate de que todo se escriba
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }



    
    /**
     * Guarda en Json el estado de la lista en ese momento.
     */
    public void guardarEnJson() {
        try (Writer escritor = new FileWriter(archivoJson)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Usamos para guardar datos cuando exportamos el archivo Json
     * @param listaGenerica lista a guardarse en json
     * @param archivo json a guardar
    */
    
    public void guardarEnJson(List<T> listaGenerica, File archivo) {
        
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(listaGenerica, writer);
            System.out.println("Datos exportados correctamente a " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos en JSON: " + e.getMessage());
        }
    }
    
    /**
     *      * 
     * @param clase declaramos la clase que vamos a leer. Es para que Json pueda manejar de manera optima ya que tiene conflicto
     * con los genericos
     * @param archivo. Usamos para leer el archivo que vamos a importar
     * lee el archivo json que vamos a importar a traves de una ventana de sitema
     * 
     */
    public void leerDesdeJson(Class<T> clase, File archivo) {
        try (Reader lector = new FileReader(archivo)) {
            // Usar TypeToken para obtener el tipo de la lista de la clase genérica
            Type tipoLista = TypeToken.getParameterized(ArrayList.class, clase).getType();
            // Deserializar la lista usando Gson
            lista = gson.fromJson(lector, tipoLista);
            if (lista == null) {
                lista = new ArrayList<>();
            }
        } catch (IOException e) {
            // Si ocurre un error, inicializar la lista como vacía
            lista = new ArrayList<>();
        }
    }
    
    
    /**
     * 
     * @param clase que clase se va a cargar desde nuestro archivo preguardado Json
     * metodo sobrecargado, preparado para leer el archivo de autoguardado
     */
    
    public void leerDesdeJson(Class<T> clase) {
        try (Reader lector = new FileReader(archivoJson)) {
            // Usar TypeToken para obtener el tipo de la lista de la clase genérica
            Type tipoLista = TypeToken.getParameterized(ArrayList.class, clase).getType();
            // Deserializar la lista usando Gson
            lista = gson.fromJson(lector, tipoLista);
            if (lista == null) {
                lista = new ArrayList<>();
            }
        } catch (IOException e) {
            // Si ocurre un error, inicializar la lista como vacía
            lista = new ArrayList<>();
        }
    }  
   
    
    /**
     * 
     * @param lista Lista a exportar en binario
     * @param archivo en que archivo vamos a exportar
     * @throws java.io.IOException
     * exporta generico a binario
     */

     
    public void exportarGenerico(List<T> lista, File archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
        }
    }

    /**
     * 
     * @param archivo
     * @return devuelve una lista para ser usada/leida en un entorno, ya sea consola, interfaz grafica
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public List<T> importarBinario(File archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        }
    }
}
