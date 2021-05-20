package com.cesur;
import java.io.File;

import javax.sound.sampled.*;

public class MusicPlayer {
	
	static Clip clip; //Clip de sonido, la canci�n la guardo aqu�
		
	public static boolean Load(String path) //Cargo un archivo de m�sica y devuelvo true si se ha hecho correctamente
	{
		boolean ok = false;
		try //Try catch para la carga de ficheros, puesto que puede o no existir y eso dar�a un error
		{
			File musicFile = new File(path);
			
			if(musicFile.exists())
			{
				AudioInputStream stream = AudioSystem.getAudioInputStream(musicFile); //Carga del archivo de audio
				clip = AudioSystem.getClip(); //Le asigno el clip
				clip.open(stream);//Lo abro
				System.out.println("M�sica cargada correctamente.");
				ok = true;
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Error en la carga del fichero de m�sica.");
		}
		return ok;
	}
	
	public static void Play(boolean loop)
	{
		clip.setMicrosecondPosition(0);
		if(loop)
		{
			clip.loop(Clip.LOOP_CONTINUOUSLY); //Se repite constantemente
		}
		clip.start();
	}
	
	public static void Stop()
	{
		clip.stop();
	}
}
