package Program;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import Clases.Persona;
import Servicios.Consultas;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Program {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//Crear objeto de tipo Persona
		Persona p = new Persona();
		
		//Test 1a obtenerAtributos
		ArrayList<Field> fields = UBean.obtenerAtributos(p);
		
		for(Field f: fields) {
			System.out.println(f.getName());
		}
		
		//Test 1b ejecutarSet
		UBean.ejecutarSet(p, "nombre", "Juan");
		UBean.ejecutarSet(p, "apellido", "Perez");
		
		//Test 1c ejecutarGet
		System.out.println(UBean.ejecutarGet(p, "nombre"));
		System.out.println(UBean.ejecutarGet(p, "apellido"));
		
		//Test 3a guardar (por ahora solo trae la query armada)
		System.out.println(Consultas.guardar(p));
		
		//Test 3b modificar (por ahora solo trae la query armada)
		UBean.ejecutarSet(p, "id", 1);
		UBean.ejecutarSet(p, "nombre", "Maria");
		UBean.ejecutarSet(p, "apellido", "Lopez");
		System.out.println(Consultas.modificar(p));
		
		//Test 4 UConexion
		UConexion cn = UConexion.generarObjeto();
		UConexion.abrirConexion();
		UConexion.cerrarConexion();
	}

}
