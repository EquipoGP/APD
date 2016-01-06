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

public class SATSolverPruebas {
	/*
	 * Clase de pruebas automaticas
	 */
	
	private static String nombreFichero = "formulas.txt";

	/**
	 * @param args <nombreFichero> (opcional) para obtener las formulas
	 * de otro fichero que no sea el de por defecto. Este fichero debera
	 * tener las formulas cada una en una linea.
	 * @throws FileNotFoundException si no encuentra los ficheros a 
	 * utilizar.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// si hay argumentos, tomar el primero como nombre del fichero
		if(args.length > 0){
			nombreFichero = args[0];
		}
		
		/* Obtiene las formulas de un fichero */
		Scanner leer = new Scanner(new File(nombreFichero));
		
		while (leer.hasNextLine()) {
			String linea = leer.nextLine();
			Formula formula = String2Formula.parsearFormula(linea);
			
			String tipo = "";
			String algoritmo = "";
			String sat = "";
			boolean satisfacible = false;
			
			/* Ejecuta el algoritmo correspondiente */
			if (Comprobaciones.es2SAT(formula)) {
				tipo = "2-SAT";
				algoritmo = "Limited Backtracking";
				satisfacible = LimitedBacktracking.limitedBT(formula);
			}
			else if (Comprobaciones.esHorn(formula)) {
				tipo = "Horn-SAT";
				algoritmo = "Unit Propagation";
				satisfacible = UnitPropagation.unitPropagation(formula);
			}
			else {
				tipo = "Caso general";
				algoritmo = "DPLL";
				satisfacible = DPLL.dpll(formula);
			}
			
			if(satisfacible){
				sat = "satisfacible";
			}
			else{
				sat = "no satisfacible";
			}
			
			/* Muestra por pantalla el resultado de cada prueba */
			System.out.println("[" + formula + "] => " + sat + 
					" (" + tipo + " , " + algoritmo + ")");
		}
		leer.close();
	}
	
}
