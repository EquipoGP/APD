/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */
package io;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import algorithms.Matching;
import data.CompactSuffixTree;
import data.Posicion;

public class SuffixTreePruebas {
	
	/*
	 * Clase para las pruebas del arbol de sufijos compacto
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			System.err.println("Uso incorrecto: SuffixTreePruebas <texto>+ <patron>");
			System.exit(1);
		}
		
		// obtener textos
		String[] textos = new String[args.length - 1];
		for (int i = 0; i < textos.length; i++) {
			textos[i] = args[i];
		}
		
		// obtener patron
		String patron = args[args.length - 1];
		
		CompactSuffixTree T = new CompactSuffixTree(textos);
		Set<Posicion> posiciones = Matching.substringMatching(T, patron);
		
		if(textos.length == 1){	// string matching
			List<Posicion> pos = new LinkedList<Posicion>(posiciones);
			
			/* Muestra la respuesta para String Matching */
			if (pos.isEmpty()) {
				System.out.println("El patron '" + patron + "' no aparece en el texto.");
			}
			else {
				System.out.print("El patron '" + patron + "' aparece en el"
						+ " texto en las posiciones: ");
				System.out.print(pos.get(0).getPosicion());
				for (int i = 1; i < pos.size(); i++) {
					System.out.print(", " + pos.get(i).getPosicion());
				}
			}
		}
		else{
			/* Muestra la respuesta para substring */
			if (posiciones.isEmpty()) {
				System.out.println("El patron '" + patron + "' no aparece en ningun texto.");
			}
			else {
				System.out.print("El patron '" + patron + "' aparece en los"
						+ " textos: ");
				
				List<Integer> textosPatron = new LinkedList<Integer>();
				for (Posicion p : posiciones) {
					int nuevoTexto = p.getTexto();
					if (!textosPatron.contains(nuevoTexto)) {
						textosPatron.add(nuevoTexto);
					}
				}
				
				System.out.print(textosPatron.get(0));
				for (int i = 1; i < textosPatron.size(); i++) {
					System.out.print(", " + textosPatron.get(i));
				}
			}
		}
	}

}
