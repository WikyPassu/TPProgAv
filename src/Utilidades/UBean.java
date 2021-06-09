package Utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UBean {
	
	public static ArrayList<Field> obtenerAtributos(Object o) {
		Class c = o.getClass();
		ArrayList<Field> fields = new ArrayList<Field>();
		
		for(Field f: c.getDeclaredFields()) {
			fields.add(f);
		}
		
		return fields;
	}
	
	public static void ejecutarSet(Object o, String att, Object valor) {
		Class c = o.getClass();
		Object[] parametros = new Object[1];
		parametros[0] = valor;
		String nombreSetter = "set".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
		
		for(Method m: c.getDeclaredMethods()) {
			if(nombreSetter.equals(m.getName())) {
				try {
					m.invoke(o, parametros);
					break;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Object ejecutarGet(Object o, String att) {
		Class c = o.getClass();
		Object retorno = null;
		
		for(Field f: c.getDeclaredFields()) {
			String nombreGetter = "get".concat(att.substring(0, 1).toUpperCase()).concat(att.substring(1));
			
			for(Method m: c.getDeclaredMethods()) {
				if(nombreGetter.equals(m.getName())) {
					try {
						retorno = m.invoke(o, null);
						break;
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return retorno;
	}
}
