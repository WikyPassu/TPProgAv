package Clases;

import Anotaciones.*;

@Tabla(nombre = "personas")
public class Persona {
	
	@Id
	private Integer id;
	@Columna(nombre = "per_nombre")
	private String nombre;
	@Columna(nombre = "per_apellido")
	private String apellido;
	
	public Persona() {}

	public Persona(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
