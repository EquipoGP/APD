/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */
package io;

import java.util.Collections;
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
			List<Integer> numPos = new LinkedList<Integer>();
			
			/* Respuesta para String Matching */
			System.out.println();
			System.out.println("String Matching");
			System.out.println("===============");
			System.out.println("Patron: " + patron);
			System.out.println("Texto: " + textos[0]);
			System.out.println();
			
			if (pos.isEmpty()) {
				System.out.println("El patron '" + patron + "' no aparece en el texto.");
			}
			else {
				
				for (int i = 0; i < pos.size(); i++) {
					numPos.add(pos.get(i).getPosicion());
				}
				Collections.sort(numPos);
				
				System.out.println("El patron '" + patron + "' aparece en el"
						+ " texto en: ");
				System.out.print("	Posiciones: " + numPos.get(0));
				for (int i = 1; i < numPos.size(); i++) {
					System.out.print(", " + numPos.get(i));
				}
			}
			System.out.println();
		}
		else{
			
			/* Respuesta para substring */
			System.out.println("Problema del substring");
			System.out.println("======================");
			System.out.println("Textos: ");
			
			for (int i = 0; i < textos.length; i++) {
				
				/* Muestra todos los textos */
				System.out.println("	" + (i+1) + ": " + textos[i]);
				
			}
			System.out.println();
			
			if (posiciones.isEmpty()) {
				System.out.println("El patron '" + patron + "' no aparece en ningun texto.");
			}
			else {
				System.out.print("El patron '" + patron + "' aparece en los"
						+ " textos: ");
				
				System.out.println();
				
				List<Integer> textosPatron = new LinkedList<Integer>();
				for (Posicion p : posiciones) {
					int nuevoTexto = p.getTexto();
					if (!textosPatron.contains(nuevoTexto)) {
						textosPatron.add(nuevoTexto);
					}
				}
				Collections.sort(textosPatron);
				int maxCaracteres = 30;
				for (int i = 0; i < textosPatron.size(); i++) {
					
					int j = textosPatron.get(i) - 1;
					
					/* Muestra los textos en los que se ha encontrado el patron */
					if (textos[j].length() > maxCaracteres) {
						System.out.println("	" + (j+1) + ": " + textos[j].substring(0, maxCaracteres) + "...");
					}
					else {
						System.out.println("	" + (j+1) + ": " + textos[j]);
					}
					
				}
			}
			System.out.println();
		}
	}

}
