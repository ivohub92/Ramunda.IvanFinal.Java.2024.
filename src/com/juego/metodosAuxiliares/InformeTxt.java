package com.juego.metodosAuxiliares;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.juego.personajes.*;

public class InformeTxt {
    public static void generarInforme(List<Personaje> personajes, File archivo, int nivel) throws IOException {
        if (personajes.isEmpty()) {
            throw new IllegalArgumentException("No hay personajes para generar el informe.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // Escribir los detalles de cada personaje
            writer.write("INFORME DE PERSONAJES FILTRADOS POR NIVEL MAXIMO: " + nivel);
            writer.newLine();
            writer.write("=====================");
            writer.newLine();

            for (Personaje personaje : personajes) {
                writer.write(personaje.imprimirTxt());
                writer.newLine();
            }

            writer.newLine();
            writer.write("RESUMEN ESTADÍSTICO");
            writer.newLine();
            writer.write("=====================");
            writer.newLine();

            // Calcular estadísticas
            Map<String, List<Personaje>> personajesPorRaza = personajes.stream()
                    .collect(Collectors.groupingBy(p -> p.getClass().getSimpleName()));

            int totalPersonajes = personajes.size();
            int nivelTotal = personajes.stream().mapToInt(Personaje::getNivel).sum();
            double nivelPromedioGlobal = (double) nivelTotal / totalPersonajes;

            for (Map.Entry<String, List<Personaje>> entrada : personajesPorRaza.entrySet()) {
                String tipo = entrada.getKey();
                List<Personaje> personajesDeRaza = entrada.getValue();
                int cantidad = personajesDeRaza.size();
                double porcentaje = (cantidad * 100.0) / totalPersonajes;

                int nivelTotalRaza = personajesDeRaza.stream().mapToInt(Personaje::getNivel).sum();
                double nivelPromedioRaza = (double) nivelTotalRaza / cantidad;

                writer.write(String.format("Raza: %s, Cantidad: %d, Porcentaje: %.2f%%, Nivel Promedio: %.2f",
                        tipo, cantidad, porcentaje, nivelPromedioRaza));
                writer.newLine();
            }

            writer.newLine();
            writer.write(String.format("Nivel Promedio Global de personajes filtrados los Personajes: %.2f", nivelPromedioGlobal));
            writer.newLine();
        }
    }
}