/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import algorithms.Comprobaciones;
import algorithms.DPLL;
import algorithms.LimitedBacktracking;
import algorithms.UnitPropagation;
import data.Formula;

public class SATSolver {
	/*
	 * Clase para la interaccion con el usuario
	 */
	
	/**
	 * Metodo principal para la entrada de datos por parte del usuario
	 * @param args no utilizado
	 * @throws FileNotFoundException si no se encuentra el fichero que
	 * se pasa por entrada para las formulas CNF
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		int opcion = 0;
		
		String form = null;
		Formula formula = null;
		
		boolean _2sat = false;
		boolean horn = false;

		do{
			/* printear menu */
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
				_2sat = Comprobaciones.es2SAT(formula);
				horn = Comprobaciones.esHorn(formula);
				
				if(_2sat){
					System.out.println("Algoritmo LB");
					/* resolver con el algoritmo de Limited Backtracking */
					if(LimitedBacktracking.limitedBT(formula)){
						System.out.println("La formula es satisfacible");
					}
					else {
						System.out.println("La formula NO es satisfacible");
					}
				}
				else if(horn){
					System.out.println("Algoritmo Unit Propagation");
					/* resolver con el algoritmo de Unit Propagation */
					if (UnitPropagation.unitPropagation(formula)) {
						System.out.println("La formula es satisfacible");
					}
					else {
						System.out.println("La formula NO es satisfacible");
					}
				}
				else{
					System.out.println("Algoritmo general");
					/* resolver con el algoritmo general */
					if(DPLL.dpll(formula)){
						System.out.println("La formula es satisfacible");
					}
					else{
						System.out.println("La formula NO es satisfacible");
					}
				}
				break;
				
			case 4:
				/* tipo de la formula */
				if(formula == null) break;
				_2sat = Comprobaciones.es2SAT(formula);
				horn = Comprobaciones.esHorn(formula);
				if(_2sat){
					System.out.println("La formula es 2-SAT");
				}
				if(horn){
					System.out.println("La formula es Horn");
				}
				if(!horn && !_2sat){
					System.out.println("La formula no es 2-SAT ni Horn");
				}
				break;
			}
			
			if(opcion == 1 || opcion == 2){
				// crear formula a partir del string
				formula = String2Formula.parsearFormula(form);
				System.out.println(formula.toString());
			}
		}
		while(opcion != 5);
		in.close();
	}

}