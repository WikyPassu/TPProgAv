package Program;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import Clases.Persona;
import Servicios.Consultas;
import Utilidades.UBean;

public class Program {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//Crear objeto de tipo Persona
		Class c = Clases.Persona.class;
		Constructor[] constructores = c.getConstructors();
		Object p = null;
		for(Constructor con: constructores) {
			if(con.getParameterCount() == 0) {
				p = con.newInstance();
				break;
			}
		}
		
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
	}

}
