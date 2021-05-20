package com.cesur;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class CESURBreak {
	// Enum para no tener que acordarme de qué índice en el array tienen los objetos
	public static enum items {
		llaves, dremel, tarjeta, escobilla, clip, cable
	};

	// Enum para no tener que acordarme de qué índice en el array tienen las
	// localizaciones
	public static enum locations {
		wc, recepción, almacén, dirección, profesores, jardinería, odontología, pasillo, informática
	}
	
	//Enum para las imágenes
	public static enum pics {
		logo, mapa, almacén, cadenas, chema, clip, recepción, usb, vending, card
	}

	// MAIN
	public static void main(String[] args) {
		// Creo un array de items boleanos del tamaño del enum. False, no lo tengo,
		// true, lo tengo
		boolean[] itemList = new boolean[items.values().length];

		// Variables de flujo de juego (estas son para no repetir frases en sitios ya
		// visitados)
		boolean gameOver = false;
		boolean storeOpened = false;
		boolean principalOpened = false;
		boolean hallOpened = false;
		// Localización
		int location = locations.wc.ordinal();

		// Orden que da el usuario
		String orden;

		// Creo objetos de mis clases
		ImgManager imgManager = new ImgManager();
		TextManager textManager = new TextManager();
		MusicPlayer mPlayer = new MusicPlayer();
		
		//Cargo el archivo de música y lo reproduzco
		if(mPlayer.Load("music.wav"))
			mPlayer.Play(true);

		// Creo el Scanner
		Scanner sc = new Scanner(System.in);

		// Cargo los ficheros de texto e imágenes
		textManager.Load("textos.txt");
		imgManager.Add("Logo.txt");
		imgManager.Add("mapa.txt");
		imgManager.Add("almacén.txt");
		imgManager.Add("chained.txt");
		imgManager.Add("Chema.txt");
		imgManager.Add("clip.txt");
		imgManager.Add("recepción.txt");
		imgManager.Add("usb.txt");
		imgManager.Add("vending.txt");
		imgManager.Add("card.txt");

		// Imprimo el Logo
		imgManager.Print(pics.logo.ordinal());

		// Imprimo el contexto inicial
		textManager.Print(0);
		textManager.Print(1);
		textManager.Print(2);

		// Bucle principal del juego, se repite hasta el game over
		do {
			System.out.print("¿Qué quieres hacer?: ");
			orden = sc.nextLine();
			orden = orden.toLowerCase(); // convierto la orden a minúsculas para facilitar su comprobación
			String[] ordenSeparada = orden.split("\\s+"); // separo la orden en palabras por espacio

			// Este switch sirve para ver qué orden da el usuario teniendo en cuenta la
			// primera palabra. Siempre habrá una.
			switch (ordenSeparada[0]) {
			//Case para la música, reproducir y parar
			case "reproducir":
				if (ordenSeparada[1].equals("música")) {
					mPlayer.Play(true);
					}
				else
					textManager.Print(2);
				break;
			case "parar":
				if (ordenSeparada[1].equals("música")) {
					mPlayer.Stop();
					}
				else
					textManager.Print(2);
				break;
			//------------------------------------------
			case "ir":
				// En caso de ser Ir, debe haber un a
				if (ordenSeparada[1].equals("a")) {
					// Pero si el usuario no escribe destino, esto provocaría una lectura fuera del
					// array, con lo que lo prevengo con un texto
					if (ordenSeparada.length != 3)
						textManager.Print(38);
					// En caso de que haya destino, veo cual es, y para cada destino pongo su texto
					// y cambio la localización
					else {
						// Orden IR A, se basa en si tienes un objeto para poder cambiar la localización
						switch (ordenSeparada[2]) {
						case "wc":
							System.out.println("Estás en el wc.");
							location = locations.wc.ordinal();
							break;

						case "recepción":
							imgManager.Print(6);
							System.out.println("Estás en recepción.");
							location = locations.recepción.ordinal();
							break;
						case "almacén":
							if (itemList[items.escobilla.ordinal()]) {
								if (!storeOpened) {
									imgManager.Print(pics.almacén.ordinal());
									textManager.Print(7);
									storeOpened = true;
								} else {
									imgManager.Print(pics.almacén.ordinal());
									System.out.println("Estás en el almacén.");
								}
								location = locations.almacén.ordinal();
							} else {
								textManager.Print(6);
							}
							break;
						case "dirección":
							if (itemList[items.llaves.ordinal()] && itemList[items.dremel.ordinal()]) {
								if (!principalOpened) {
									textManager.Print(25);
									principalOpened = true;
								} else {
									if (itemList[items.cable.ordinal()]) {
										textManager.Print(30);
										gameOver = true;
									}
								}
								location = locations.dirección.ordinal();
							} else if (itemList[items.llaves.ordinal()]) {
								location = locations.pasillo.ordinal();
								imgManager.Print(pics.cadenas.ordinal());
								textManager.Print(15);
							} else {
								textManager.Print(32);
							}
							break;

						case "pasillo":
							if (itemList[items.llaves.ordinal()]) {
								if (!hallOpened) {
									textManager.Print(13);
									hallOpened = true;
								} else {
									System.out.println("Estás en el pasillo");
								}
								location = locations.pasillo.ordinal();
							} else {
								textManager.Print(12);
							}
							break;
						case "profesores":
							if (itemList[items.llaves.ordinal()]) {
								textManager.Print(14);
								location = locations.profesores.ordinal();
							} else {
								textManager.Print(12);
							}
							break;

						case "odontología":
							if (hallOpened) {
								imgManager.Print(pics.vending.ordinal());
								textManager.Print(22);
								location = locations.odontología.ordinal();
							} else {
								textManager.Print(32);
							}
							break;

						case "jardinería":
							if (hallOpened) {
								imgManager.Print(pics.chema.ordinal());
								textManager.Print(17);
								location = locations.jardinería.ordinal();
							} else {
								textManager.Print(32);
							}
							break;

						case "informática":
							if (hallOpened) {
								imgManager.Print(pics.usb.ordinal());
								textManager.Print(27);
								location = locations.informática.ordinal();
							} else {
								textManager.Print(32);
							}
							break;
						default:
							textManager.Print(33);
						}
					}
				}
				// Pequeño chascarrillo de error para que el usuario escriba IR A y no sólo IR
				else {
					System.out.println("Se dice ir a. Con la a, vamos...");
				}
				break;

			// Orden COGER, se basa en la localización y cambia a true el valor del objeto
			// si puedes cogerlo
			case "coger":
				switch (ordenSeparada[1]) {
				case "cable":
					if (location == locations.informática.ordinal()) {
						if (principalOpened) {
							textManager.Print(29);
							itemList[items.cable.ordinal()] = true;
						} else {
							textManager.Print(28);
						}
					} else {
						textManager.Print(34);
					}
					break;
				case "escobilla":
					if (location == locations.wc.ordinal()) {
						if (!itemList[items.escobilla.ordinal()]) {
							textManager.Print(4);
							itemList[items.escobilla.ordinal()] = true;
						} else {
							textManager.Print(35);
						}
					} else {
						textManager.Print(34);
					}
					break;
				case "clip":
					if (location == locations.almacén.ordinal()) {
						if (!itemList[items.clip.ordinal()]) {
							imgManager.Print(pics.clip.ordinal());
							textManager.Print(10);
							itemList[items.clip.ordinal()] = true;
						} else {
							textManager.Print(35);
						}
					} else {
						textManager.Print(34);
					}
					break;
				case "señor":
					if (location == locations.almacén.ordinal()) {
						textManager.Print(9);
					} else {
						textManager.Print(34);
					}
					break;
				case "cizalla":
					if (location == locations.jardinería.ordinal()) {
						textManager.Print(18);
					} else {
						textManager.Print(34);
					}
					break;
				case "Chema":
					if (location == locations.jardinería.ordinal()) {
						imgManager.Print(pics.chema.ordinal());
						textManager.Print(20);
					} else {
						textManager.Print(34);
					}
					break;
				case "abono":
					if (location == locations.jardinería.ordinal()) {
						imgManager.Print(pics.card.ordinal());
						textManager.Print(21);
						itemList[items.tarjeta.ordinal()] = true;
					} else {
						textManager.Print(34);
					}
					break;
				default:
					textManager.Print(34);

				}
				break;
			// Orden Mirar, repite textos que ayudan al usuario cuando no sabe qué hacer
			case "mirar":
				if (location == locations.almacén.ordinal()) {
					textManager.Print(8);
				} else if (location == locations.wc.ordinal()) {
					textManager.Print(3);
				} else if (location == locations.recepción.ordinal()) {
					textManager.Print(5);
				} else if (location == locations.dirección.ordinal()) {
					textManager.Print(26);
				} else {
					textManager.Print(36);
				}
				break;
			// Orden usar, es como la de Ir a, requiere un predicado
			case "usar":
				if (ordenSeparada.length != 2)
					textManager.Print(40);
				else {
					switch (ordenSeparada[1]) {
					case "clip":
						if (location == locations.recepción.ordinal() && itemList[items.clip.ordinal()]) {
							itemList[items.llaves.ordinal()] = true;
							textManager.Print(11);
						} else
							textManager.Print(41);
						break;
					case "máquina":
						if (location == locations.odontología.ordinal() && itemList[items.tarjeta.ordinal()]) {
							itemList[items.dremel.ordinal()] = true;
							textManager.Print(24);
						} else
							textManager.Print(41);
						break;
					default:
						textManager.Print(31);
						break;
					}
				}
				break;
			// Orden Ayuda y Mapa
			case "ayuda":
				textManager.Print(2);
				break;
			case "mapa":
				imgManager.Print(1); // Imprimo el mapa
				break;
			// Orden secreta, para aquellos que saben qué es arriba abajo arriba abajo
			// izquierda derecha izquierda derecha a, b
			case "solución":
				textManager.Print(39);
				break;
			default:
				textManager.Print(37);
				break;
			}

		} while (!gameOver);

	}

}
