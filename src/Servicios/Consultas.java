package Servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Utilidades.UBean;

public class Consultas {
	
	public static String guardar(Object o) {
		String query = "insert into ".concat(o.getClass().getName()).concat(" (");
		ArrayList<Field> fields = UBean.obtenerAtributos(o);
		
		for(Field f: fields) {
			query = query.concat(f.getName()).concat(", ");
		}
		query = query.substring(0, query.length() - 2).concat(") values(");
		for(Field f: fields) {
			query = query.concat(UBean.ejecutarGet(o, f.getName()).toString()).concat(", ");
		}
		query = query.substring(0, query.length() - 2).concat(")");
		
		return query;
	}
}
