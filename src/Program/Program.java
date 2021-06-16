package Program;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import Clases.Persona;
import Servicios.Consultas;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Program {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//Crear objeto de tipo Persona
		Persona p = new Persona();
		/*
		//Test 1a obtenerAtributos
		ArrayList<Field> fields = UBean.obtenerAtributos(p);
		
		for(Field f: fields) {
			System.out.println(f.getName());
		}
		
		//Test 1b ejecutarSet
		UBean.ejecutarSet(p, "nombre", "Carlos");
		UBean.ejecutarSet(p, "apellido", "Gomez");
		
		//Test 1c ejecutarGet
		System.out.println(UBean.ejecutarGet(p, "nombre"));
		System.out.println(UBean.ejecutarGet(p, "apellido"));
		
		//Test 4 UConexion
		UConexion cn = UConexion.generarObjeto();
		
		//Test 3a guardar
		System.out.println(Consultas.guardar(p));
		
		//Test 3b modificar
		UBean.ejecutarSet(p, "id", 2);
		UBean.ejecutarSet(p, "nombre", "Maria");
		UBean.ejecutarSet(p, "apellido", "Lopez");
		System.out.println(Consultas.modificar(p));
		*/
		//Test 3c eliminar
		UBean.ejecutarSet(p, "id", 2);
		System.out.println(Consultas.eliminar(p));
		
		//Test 7 obtenerTodos
		ArrayList<Persona> personas = (ArrayList<Persona>) Consultas.obtenerTodos(Persona.class);
		for(Persona per: personas) {
			System.out.println(per);
		}
		
	}
}
