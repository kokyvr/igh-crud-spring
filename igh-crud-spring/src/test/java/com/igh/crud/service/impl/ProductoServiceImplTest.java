package com.igh.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.igh.crud.model.Producto;
import com.igh.crud.repository.ProductoRepository;


@MockBean
class ProductoServiceImplTest {

	@Mock
	private ProductoRepository productoDao;
	
	@InjectMocks
	private ProductoServiceImpl productoService;
	
	private Producto producto;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		producto = new Producto();
		producto.setId(1);
	}
	
	@Test
	void productoEqualsNull() {
		int id = 1;
		
		when(productoDao.findById(id))
		.thenReturn(Optional.empty());
	}
	
	@Test
	void TestFindByIdNotNull() {
	int id = 1;
	when(productoDao.findById(id))
	.thenReturn(Optional.of(producto));
	Producto producto = productoService.findById(id);
	assertNotNull(producto);
	
	}
	
	
	@Test
	void TestFindByIdIsnull() {
	int id = 1;
	when(productoDao.findById(id))
	.thenReturn(Optional.empty());
	Producto producto = productoService.findById(id);
	assertNull(producto);
	
	}
	
	
	

}
