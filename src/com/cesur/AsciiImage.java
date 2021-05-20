package com.cesur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsciiImage {
	// Creo una lista que va a contener la imagen
	public List lines = new ArrayList();

	// Función de carga
	public void Load(String p) {
		BufferedReader reader;
		try {
			// Reservo memoria y voy leyendo línea por línea
			reader = new BufferedReader(new FileReader(p));
			String line = reader.readLine();
			while (line != null) {
				// Si no es null, la añado al array
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

}
