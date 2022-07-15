package com.igh.crud.util;

import java.io.File;

public class Generador {

	private Generador() {
		
	}
	
	public static String randomAlphaNumeric(int count) {
		String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * CARACTERES.length());
			builder.append(CARACTERES.charAt(character));
		}
		return builder.toString();
	}
	
	public static void deleteFile(String ruta) {
		File file = new File(ruta);
		file.delete();
	}

}
