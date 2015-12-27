package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data.Formula;

public class SATSolver {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		int opcion = 0;
		String form = null;
		Formula formula = null;

		do{
			System.out.println("MENU");
			System.out.println("======================================");
			System.out.println("1) Formula desde fichero");
			System.out.println("2) Formula desde linea de comandos");
			System.out.println("3) Satisfacible?");
			System.out.println("4) 2-SAT? Horn?");
			System.out.println("5) Salir");
			
			opcion = in.nextInt();
			in.nextLine();
			
			switch(opcion){
			case 1:
				/* leer la formula de fichero */
				System.out.println("Introduzca la ruta del fichero: ");
				String path = in.nextLine();
				File f = new File(path);
				Scanner sf = new Scanner(f);
				form = "";
				while(sf.hasNextLine()){
					form = form + sf.nextLine();
				}
				sf.close();
				break;
			case 2:
				/* leer la formula de entrada estandar */
				System.out.println("Introduzca la formula: ");
				form = in.nextLine();
				break;
			case 3: 
				/* es satisfacible */
				if(formula == null) break;
				break;
			case 4:
				/* tipo de la formula */
				if(formula == null) break;
				break;
			}
			
			if(opcion == 1 || opcion == 2){
				// crear formula a partir del string
				formula = String2Formula.parsearFormula(form);
			}
		}
		while(opcion != 5);
		in.close();
	}

}