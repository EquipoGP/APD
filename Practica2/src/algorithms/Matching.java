/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package algorithms;

import java.util.LinkedList;
import java.util.List;

import data.CompactSuffixTree;
import data.Nodo;
import data.Posicion;

public class Matching {
	/*
	 * Clase que contiene los algoritmos de StringMatching y SubString
	 */

	/**
	 * @param patron patron que encontrar en los textos
	 * @return una lista con las posiciones en las que se encuentra el patron
	 * en los textos
	 */
	public static List<Posicion> substringMatching(CompactSuffixTree T, String patron) {
		/* inicializacion */
		List<Posicion> posiciones = new LinkedList<Posicion>();
		String[] textos = T.getTextos();

		String p = patron;
		Nodo inicio = T.getRaiz();
		boolean seguir = true;

		while (seguir) {
			List<Nodo> hijos = inicio.getHijos();
			if (hijos == null) {
				hijos = new LinkedList<Nodo>();
			}

			if (p.isEmpty()) {
				/* patron vacio: se ha encontrado la totalidad del patron */
				seguir = false;
				
				List<Nodo> actual = new LinkedList<Nodo>();
				actual.add(inicio);
				
				while(!actual.isEmpty()) {
					Nodo n = actual.remove(0);
					if (n.getTextos() != null) {
						posiciones.addAll(n.getTextos());
					}
					
					/* agregar hijos */
					if(n.getHijos() != null){
						actual.addAll(n.getHijos());
					}
				}
			} else {
				/* todavia no se ha encontrado la totalidad del patron */
				seguir = false;
				for (Nodo n : hijos) {
					// obtener etiqueta
					String etiq = "";
					if (n.getId().esLabel()) {
						etiq = n.getLabel();
					} else {
						int t = n.getId().getNumTexto();
						int init = n.getId().getPosInicio();
						int end = n.getId().getPosFinal();

						etiq = textos[t - 1].substring(init - 1, end);
					}

					int np = p.length();
					int ne = etiq.length();
					int min = Math.min(np, ne);

					String pp = p.substring(0, min);
					String ee = etiq.substring(0, min);

					// matchear etiqueta y patron
					if(etiq.length() == 0){
						;	// final de la cadena, pero todavia queda parte del patron por leer
					}
					else if (pp.equals(ee)) {
						// el patron coincide con el texto
						inicio = n;
						p = p.substring(min);
						seguir = true;
						break;	// salir por el hijo que coincide
					}
					else {
						// el patron no coincide
						seguir = false;
					}
				}
			}
		}
		return posiciones;
	}

}
