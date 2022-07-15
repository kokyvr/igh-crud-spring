package com.igh.crud.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.igh.crud.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{

	Optional<Producto> findByNombreProducto(String nombreProducto);
	
	Page<Producto> findByEstadoProducto(String estadoProducto,Pageable page);
}
