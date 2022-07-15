package com.igh.crud.service;

import java.util.Map;

import com.igh.crud.model.Producto;

public interface ProductoService {

	public int insertar(Producto producto);
	
	public int actualizar(Producto producto,Integer id);
	
	public int deleteById(Integer id);
	
	public Map<String,Object> findAll(int page,int size);
	
	public Producto findById(Integer id);
	
	public Producto findByNombreProducto(String nombreProducto);
	
	public int actualizarImagen(String nombreArchivo,Integer id);
	
	
}
