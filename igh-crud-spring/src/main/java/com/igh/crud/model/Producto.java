package com.igh.crud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
@SequenceGenerator(name = "sq_producto",sequenceName = "sq_producto",allocationSize = 1)
@Table(name = "PRODUCTO")
@Entity
public class Producto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "sq_producto")
	private Integer id;
	
	@Column(name = "nombre_producto")
	private String nombreProducto;
	
	@NotNull
	private Integer stock;
	
	@NotNull
	private Double precio;
	
	@NotEmpty
	private String marca;
	
	@Column(name = "estado_producto")
	private String estadoProducto;
	
	@Column(name = "nombre_archivo")
	private String nombreArchivo;
	
}
