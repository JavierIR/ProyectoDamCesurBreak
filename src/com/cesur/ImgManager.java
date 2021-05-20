package com.cesur;

import java.util.ArrayList;
import java.util.List;

public class ImgManager {
	public static List<AsciiImage> images = new ArrayList<AsciiImage>();

	// Añade una imagen al arraylist
	public static void Add(String p) {
		AsciiImage image = new AsciiImage();
		image.Load(p);
		images.add(image);
	}

	// Imprime una imagen línea a línea
	public static void Print(int img) {
		if (img < images.size()) {
			for (int i = 0; i < images.get(img).lines.size(); i++) {
				System.out.println(images.get(img).lines.get(i));
			}
		}
	}

}
