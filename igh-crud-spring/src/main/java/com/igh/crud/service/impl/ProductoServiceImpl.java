package com.igh.crud.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.igh.crud.model.Estado;
import com.igh.crud.model.Producto;
import com.igh.crud.repository.ProductoRepository;
import com.igh.crud.service.MapperPageable;
import com.igh.crud.service.ProductoService;
import com.igh.crud.util.Generador;

@Service
public class ProductoServiceImpl implements ProductoService, MapperPageable<Producto> {

	@Value("${carpeta}")
	private String carpeta;
	
	@Autowired
	private ProductoRepository productoDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int insertar(Producto producto) {
		int rpta = 0;
		try {
			producto.setEstadoProducto(Estado.ACTIVO.toString());
			productoDao.save(producto);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int actualizar(Producto producto, Integer id) {
		int rpta = 0;
		Producto productoBD = productoDao.findById(id).orElse(null);
		if(productoBD == null) {
			return rpta;
		}
		
		try {
			producto.setId(id);
			producto.setNombreArchivo(productoBD.getNombreArchivo());
			producto.setEstadoProducto(Estado.ACTIVO.toString());
			productoDao.save(producto);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int deleteById(Integer id) {
		int rpta = 0;
		boolean existe = productoDao.existsById(id);
		if(!existe) {
			return rpta;
		}
		try {
			productoDao.deleteById(id);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return 0;
	}

	@Override
	public Map<String, Object> findAll(int page, int size) {
		// TODO Auto-generated method stub
		Map<String, Object> map = null;

		PageRequest pageRequest = PageRequest.of(page, size);
		try {
			Page<Producto> productoPageable = productoDao.findByEstadoProducto(Estado.ACTIVO.toString(), pageRequest);
			map = mapperPageable(productoPageable);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return map;
	}

	@Override
	public Producto findById(Integer id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public Map<String, Object> mapperPageable(Page<Producto> mapper) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<>();

		mapReturn.put("productos", mapper.getContent());
		mapReturn.put("totalPages", mapper.getTotalPages());
		mapReturn.put("totalElements", mapper.getTotalElements());
		mapReturn.put("currentPage", mapper.getNumber());

		return mapReturn;
	}

	@Override
	public Producto findByNombreProducto(String nombreProducto) {
		return productoDao.findByNombreProducto(nombreProducto).orElse(null);
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public int actualizarImagen(String nombreArchivo, Integer id) {
		System.out.println(id);
		int rpta = 0;
		Producto productoBD = productoDao.findById(id).orElse(null);
		if(productoBD == null) {
			return rpta;
		}	
		Generador.deleteFile(carpeta + productoBD.getNombreArchivo());
		try {
			productoBD.setNombreArchivo(nombreArchivo);
			productoDao.save(productoBD);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		
		return rpta;
	}

}
