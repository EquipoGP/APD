package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import algorithms.Comprobaciones;
import algorithms.DPLL;
import algorithms.LimitedBacktracking;
import algorithms.UnitPropagation;
import data.Formula;

public class SATSolverPruebas {
	
	private static String nombreFichero = "formulas.txt";

	public static void main(String[] args) throws FileNotFoundException {
		
		/* Obtiene las formulas de un fichero */
		Scanner leer = new Scanner(new File(nombreFichero));
		
		while (leer.hasNextLine()) {
			
			String linea = leer.nextLine();
			Formula formula = String2Formula.parsearFormula(linea);
			String tipo = "";
			String algoritmo = "";
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
				algoritmo = "Algoritmo general";
				satisfacible = DPLL.dpll(formula);
			}
			
			/* Muestra por pantalla el resultado de cada prueba */
			System.out.print("Formula: " + formula);
			System.out.print(", " + satisfacible + ", ");
			System.out.print("(" + tipo + ", " + algoritmo + ")");
			System.out.println();
		}
		leer.close();
	}
	
}
