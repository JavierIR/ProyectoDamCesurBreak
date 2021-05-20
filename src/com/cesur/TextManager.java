package com.cesur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextManager {
	public static List<String> textos = new ArrayList<String>();

	// Carga la lista de textos desde un archivo
	public static void Load(String p) {
		BufferedReader reader;
		try {
			// Reservo memoria y voy leyendo línea por línea
			reader = new BufferedReader(new FileReader(p));
			String line = reader.readLine();
			while (line != null) {
				// Si no es null, la añado al arraylist
				textos.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	// Imprime un texto
	void Print(int text) {
		if (text < textos.size()) {
			System.out.println(textos.get(text));
		}
	}

}
