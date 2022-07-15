package com.igh.crud.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.igh.crud.model.Producto;
import com.igh.crud.service.ProductoService;
import com.igh.crud.util.Generador;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	private String nombreArchivo;
	
	@Value("${carpeta}")
	private String carpeta;
	
	
	@PostMapping("/imagen")
	public ResponseEntity<?> subirImagen(@RequestParam("file") MultipartFile mp3) {
		File file;

		if(mp3.isEmpty()) {
			return new ResponseEntity<>(nombreArchivo, HttpStatus.BAD_REQUEST);
			
		}
		
			nombreArchivo = Generador.randomAlphaNumeric(5) + "-" + (mp3.getOriginalFilename()).toUpperCase();
			nombreArchivo= nombreArchivo.replaceAll(" ","");
			file = new File(carpeta + nombreArchivo);
			try {
				OutputStream os = new FileOutputStream(file);
				os.write(mp3.getBytes());
				os.close();
			} catch (Exception e) {
			
				this.nombreArchivo = null;
				
				return new ResponseEntity<>(nombreArchivo, HttpStatus.BAD_REQUEST);
			}
		

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping("/actualizarImagen")
	public ResponseEntity<Integer> actualizarImagen(@RequestParam("file") MultipartFile mp3,@RequestParam(required = true)Integer id) {
		File file;
		int rpta = 0;
		if(mp3.isEmpty()) {
			return new ResponseEntity<>(rpta, HttpStatus.BAD_REQUEST);
			
		}
		
			nombreArchivo = Generador.randomAlphaNumeric(5) + "-" + (mp3.getOriginalFilename()).toUpperCase();
			nombreArchivo= nombreArchivo.replaceAll(" ","");
			file = new File(carpeta + nombreArchivo);
			try {
				OutputStream os = new FileOutputStream(file);
				os.write(mp3.getBytes());
				os.close();
				
				rpta = productoService.actualizarImagen(nombreArchivo, id);
				if(rpta ==0) {
					Generador.deleteFile(carpeta + nombreArchivo);
				}
				
			} catch (Exception e) {
				Generador.deleteFile(carpeta + nombreArchivo);
				return new ResponseEntity<>(rpta, HttpStatus.BAD_REQUEST);
			}finally {
				this.nombreArchivo=null;
			}
		

		return new ResponseEntity<>(rpta,HttpStatus.OK);
	}
	
	
	
	
	
	
	@PostMapping
	public ResponseEntity<Integer> insertar(@RequestBody Producto producto){
		int rpta = 0;
		try {
			if(this.nombreArchivo ==null) {
				return new ResponseEntity<Integer>(rpta,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			producto.setNombreArchivo(nombreArchivo);
			rpta = productoService.insertar(producto);
		} catch (Exception e) {
			e.printStackTrace();
			Generador.deleteFile(carpeta + nombreArchivo);
			return new ResponseEntity<Integer>(rpta,HttpStatus.INTERNAL_SERVER_ERROR);
		}finally {
			this.nombreArchivo = null;
		}
		
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	
	
	@PutMapping(path = "{id}")
	public ResponseEntity<Integer> actualizar(@RequestBody Producto producto,@PathVariable Integer id){
		int rpta = 0;
		try {
			
			rpta = productoService.actualizar(producto,id);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta,HttpStatus.INTERNAL_SERVER_ERROR);
		}finally {
			this.nombreArchivo = null;
		}
		
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Integer> deleteById(@PathVariable Integer id){
		int rpta = 0;
		try {
			
			rpta = productoService.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<Map<String, Object>> findAll(@RequestParam(defaultValue = "0",required = false)int page ,@RequestParam(defaultValue = "5",required = false)int size){
		Map<String, Object> map= null;
		try {
			map = productoService.findAll(page, size);
		} catch (Exception e) {

			return new ResponseEntity<Map<String, Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
		
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Producto> findById(@PathVariable Integer id ){
		Producto producto = null;
		try {
			producto = productoService.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<Producto>(producto,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Producto>(producto,HttpStatus.OK);
	}

	@GetMapping(value = "/getNombre/{nombre}")
	public ResponseEntity<Producto> findByNombreProducto(@PathVariable String nombre ){
		Producto producto = null;
		try {
			producto = productoService.findByNombreProducto(nombre);
		} catch (Exception e) {
			return new ResponseEntity<Producto>(producto,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Producto>(producto,HttpStatus.OK);
	}
	
}
