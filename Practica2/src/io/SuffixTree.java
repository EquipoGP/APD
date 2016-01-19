package io;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import algorithms.Matching;
import data.Posicion;

public class SuffixTree {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int opcion = 0;
		
		do{
			/* printear menu */
			System.out.println("MENU");
			System.out.println("======================================");
			System.out.println("1) String matching");
			System.out.println("2) Substring");
			System.out.println("3) Salir");
			System.out.printf("$> ");
			
			opcion = in.nextInt();
			in.nextLine();
			
			String patron = null;
			String textos[];
			switch(opcion){
			case 1:
				/* leer patron y texto */
				System.out.printf("Introduce el patron: ");
				patron = in.nextLine();
				
				System.out.printf("Introduce el texto: ");
				String texto = in.nextLine();
				textos = new String[1];
				textos[0] = texto;
				
				/* Ejecuta el algoritmo para String Matching */
				if(patron != null){
					List<Posicion> pos = Matching.substringMatching(textos, patron);
					
					/* Muestra la respuesta para String Matching */
					System.out.print("El patron '" + patron + "' aparece en el"
							+ " texto en las posiciones: ");
					System.out.print(pos.get(0).getPosicion());
					for (int i = 1; i < pos.size(); i++) {
						System.out.print(", " + pos.get(i).getPosicion());
					}
					System.out.println();
				}
				
				break;
				
			case 2:
				/* leer patron y textos */
				System.out.printf("Introduce el patron: ");
				patron = in.nextLine();
				
				System.out.printf("Introduce el numero de textos: ");
				int n = in.nextInt();
				in.nextLine();
				textos = new String[n];
				
				for(int i = 0; i < n; i++){
					System.out.printf("Introduce el texto: ");
					textos[i] = in.nextLine();
				}
				
				/* Ejecuta el algoritmo para substring */
				if(patron != null){
					List<Posicion> pos = Matching.substringMatching(textos, patron);
					
					/* Muestra la respuesta para substring */
					System.out.print("El patron '" + patron + "' aparece en los"
							+ " textos: ");
					
					List<Integer> textosPatron = new LinkedList<Integer>();
					for (int i = 0; i < pos.size(); i++) {
						int nuevoTexto = pos.get(i).getTexto();
						if (!textosPatron.contains(nuevoTexto)) {
							textosPatron.add(nuevoTexto);
						}
					}
					
					System.out.print(textosPatron.get(0));
					for (int i = 1; i < textosPatron.size(); i++) {
						System.out.print(", " + textosPatron.get(i));
					}
					System.out.println();
				}
				
				break;
				
			default:
				textos = new String[0];
			}
			
		}
		while(opcion != 3);
		in.close();
	}
}
