package algorithms;

import java.util.LinkedList;
import java.util.List;

import data.CompactSuffixTree;
import data.Nodo;

public class StringMatching {

	public static List<Integer> stringMatching(String[] textos, String patron) {
		List<Integer> posiciones = new LinkedList<Integer>();
		CompactSuffixTree T = new CompactSuffixTree(textos);

		/* Inicializacion */
		Nodo x = T.getRaiz();
		int i = 1;
		boolean found = false;
		boolean possible = true;
		
		while (!found && possible) {
			
			/* Busca una arista desde x con label empezando en pi */
			List<Nodo> hijos = x.getHijos();
			boolean fitting = false;
			String myLabel = null;
			Nodo v = null;
			
			while (!fitting && !hijos.isEmpty()) {
				v = hijos.get(0);
				
				if (v.getLabel().equals(patron.charAt(i))) {
					fitting = true;
					myLabel = v.getLabel();
				}
				else if (!v.getId().esLabel() &&
						textos[0].charAt(v.getId().getPosInicio()) ==
						patron.charAt(v.getId().getPosInicio())) {
					
					/*
					 * Se trata de una etiqueta comprimida y tk = pi
					 */
					int k = v.getId().getPosInicio();
					int l = v.getId().getPosFinal();
					int m = patron.length();
					
					fitting = true;
					int l2 = Math.min(l, k + m - i);
					myLabel = textos[0].substring(k, l2);
					// posiblemente haya que crear identificador
					// para guardar las posiciones inicial y final
				}
				
				hijos.remove(v);
			}
			
			/* Compara mylabel con la parte de p por encontrar */

			// restoPatron = (pi ... pm)
			String restoPatron = patron.substring(i);
			
			if (!esPrefijo(restoPatron, myLabel) &&
					!esPrefijo(myLabel, restoPatron)) {
				
				// p no aparece en t
				possible = false;
			}
			else if (esPrefijo(myLabel, restoPatron)) {
				x = v;
				i = i + myLabel.length();
			}
			else {
				
				/* restoPatron es prefijo de myLabel */
				x = v;
				found = true;
			}
			
			if (found) {
				
				/* TODO: Calcular el conjunto de l etiquetas de hojas en
				 * el subarbol de raiz x (busqueda en profundidad) */
				
			}
		}
		return posiciones;
	}
	
	/**
	 * @param a
	 * @param b
	 * @return <true> si a es prefijo de b, 
	 * 			<false> en caso contrario
	 */
	private static boolean esPrefijo(String a, String b) {
		boolean prefijo = true;
		
		for (int i = 0; prefijo && i < a.length(); i++) {
			prefijo = (a.charAt(i) == b.charAt(i));
		}
		
		return prefijo;
	}
}
