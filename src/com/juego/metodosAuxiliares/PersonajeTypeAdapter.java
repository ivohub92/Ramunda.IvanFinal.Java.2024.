/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juego.metodosAuxiliares;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.juego.personajes.Arquero;
import com.juego.personajes.Guerrero;
import com.juego.personajes.Mago;
import com.juego.personajes.Personaje;
import java.io.IOException;

public class PersonajeTypeAdapter extends TypeAdapter<Personaje> {

    //escribir en json
    @Override
    public void write(JsonWriter out, Personaje value) throws IOException {
        // Lógica de serialización 
        out.beginObject();
        out.name("nombre").value(value.getNombre());
        out.name("nivel").value(value.getNivel());
        out.name("vidas").value(value.getVidas());
        if (value instanceof Arquero) {
            Arquero arquero = (Arquero) value;
            out.name("tipo").value("ARQUERO");
            out.name("flechas").value(arquero.getFlechas());
            out.name("velocidadDeTiro").value(arquero.getVelocidadDeTiro());
        } else if (value instanceof Mago) {
            Mago mago = (Mago) value;
            out.name("tipo").value("MAGO");
            out.name("mana").value(mago.getMana());
            out.name("arcanos").value(mago.getArcanos());
        }
        else if (value instanceof Guerrero) {
            Guerrero guerrero = (Guerrero) value;
            out.name("tipo").value("GUERRERO");
            out.name("fuerza").value(guerrero.getFuerza());
            out.name("escudo").value(guerrero.getEscudo());
        }
        out.endObject();
    }

    
    
    //Leer json
    @Override
    public Personaje read(JsonReader in) throws IOException {
       JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();

       // Extraer campos comunes
       String nombre = jsonObject.has("nombre") ? jsonObject.get("nombre").getAsString() : "";
       int nivel = jsonObject.has("nivel") ? jsonObject.get("nivel").getAsInt() : 0;
       int vidas = jsonObject.has("vidas") ? jsonObject.get("vidas").getAsInt() : 0;

       // Verificar que el tipo esté presente en el JSON
       if (jsonObject.has("tipo")) {
           String tipo = jsonObject.get("tipo").getAsString();

           switch (tipo.toUpperCase()) {
               case "ARQUERO" -> {
                   int flechas = jsonObject.has("flechas") ? jsonObject.get("flechas").getAsInt() : 0;
                   int velocidad = jsonObject.has("velocidadDeTiro") ? jsonObject.get("velocidadDeTiro").getAsInt() : 0;
                   return new Arquero(flechas, nombre, nivel, vidas,velocidad);
               }
               case "MAGO" -> {
                   int mana = jsonObject.has("mana") ? jsonObject.get("mana").getAsInt() : 0;
                   int arcanos = jsonObject.has("arcanos") ? jsonObject.get("arcanos").getAsInt() : 0;
                   return new Mago(mana, nombre, nivel, vidas,arcanos);
               }
               case "GUERRERO" -> {
                   int fuerza = jsonObject.has("fuerza") ? jsonObject.get("fuerza").getAsInt() : 0;
                   int escudo = jsonObject.has("escudo") ? jsonObject.get("escudo").getAsInt() : 0;
                   return new Guerrero(fuerza, nombre, nivel, vidas,escudo);
               }
               default -> throw new IOException("Tipo de personaje no reconocido: " + tipo);
           }
       }

       throw new IOException("Campo 'tipo' no presente en el JSON");
   }
}