package io;

import java.util.Scanner;

import data.CompactSuffixTree;

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
			
			String patron;
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
				break;
				
			default:
				textos = new String[0];
			}
			
			CompactSuffixTree cst = new CompactSuffixTree(textos);
			System.out.println(cst.toString());
		}
		while(opcion != 3);
		in.close();
	}
}
