package com.igh.crud.config;

import java.io.File;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Loader {

	
	private String carpeta = "c:/producto/imagenes/";
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		File dir = new File(carpeta);
		if(!dir.exists()) {
			dir.mkdirs();
			System.out.println("Se creo existosamente");
		}else {
			System.out.println("Ya existe carpeta");
		}

	
	}
}
