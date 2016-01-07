/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */
package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import algorithms.Comprobaciones;
import algorithms.DPLL;
import algorithms.LimitedBacktracking;
import algorithms.UnitPropagation;
import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class SATSolverPruebas {
	/*
	 * Clase de pruebas automaticas
	 */
	
	/**
	 * @param args <nombreFichero> (opcional) para obtener las formulas
	 * de otro fichero que no sea el de por defecto. Este fichero debera
	 * tener las formulas cada una en una linea.
	 * @throws FileNotFoundException si no encuentra los ficheros a 
	 * utilizar.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		if(args.length == 1){
			System.out.println("CASOS DE PRUEBA");
			System.out.println("===============");
			
			casosDePrueba(args[0]);
			
			System.out.println();
		}
		else if (args.length == 3){
			System.out.println("PRUEBAS EFICIENCIA");
			System.out.println("==================");
			
			int numFormulas = Integer.parseInt(args[0]);
			int numVariables = Integer.parseInt(args[1]);
			int numClausulas = Integer.parseInt(args[2]);	
			
			pruebasDeEficiencia(numFormulas, numVariables, numClausulas);
		}
		else {
			System.err.println("ERROR: <ejecutar.sh [nombreFicheroEntrada"
							+ "|(numFormulas numVariables numClausulas)]>");
		}
	}
	
	private static void casosDePrueba(String nombreFichero) 
			throws FileNotFoundException {
		
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
	
	private static void pruebasDeEficiencia(int numFormulas, 
			int numVariables, int numClausulas) {
		
		/* Genera formulas aleatorias para cada tipo de algoritmo */
		HashSet<Variable> variables = generarVariablesAleatorias(numVariables);
		
		List<Variable> lista_variables = new LinkedList<Variable>();
		for (Variable v : variables) {
			lista_variables.add(v);
		}
		
		System.out.println("Limited Backtracking");
		System.out.println("====================");
		
		/* Algoritmo Limited Backtracking (LB) */
		List<Formula> formulasLB = new LinkedList<Formula>();
		for (int i = 0; i < numFormulas; i++) {
			Formula f = generarFormulaAleatoria(variables, 
					numClausulas,lista_variables,"LB");
			formulasLB.add(f);
			System.out.println(f.toString());
		}
		
		/* Tiempos */
		double tiempoTotalLB = 0.0;
		for (int i = 0; i < numFormulas; i++) {
			long startTime = System.nanoTime();
			LimitedBacktracking.limitedBT(formulasLB.get(i));
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / (1000.0);
			tiempoTotalLB += duration;
		}
		
		System.out.println();
		System.out.println("Unit Propagation");
		System.out.println("================");
		
		/* Algoritmo Unit Propagation (UP) */
		List<Formula> formulasUP = new LinkedList<Formula>();
		for (int i = 0; i < numFormulas; i++) {
			Formula f = generarFormulaAleatoria(variables, 
					numClausulas,lista_variables,"UP");
			formulasUP.add(f);
			System.out.println(f.toString());
		}
		
		/* Tiempos */
		double tiempoTotalUP = 0.0;
		for (int i = 0; i < numFormulas; i++) {
			long startTime = System.nanoTime();
			UnitPropagation.unitPropagation(formulasLB.get(i));
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / (1000.0);
			tiempoTotalUP += duration;
		}
		
		System.out.println();
		System.out.println("DPLL");
		System.out.println("====");
		
		/* Algoritmo DPLL */
		List<Formula> formulasDPLL = new LinkedList<Formula>();
		for (int i = 0; i < numFormulas; i++) {
			Formula f = generarFormulaAleatoria(variables, 
					numClausulas,lista_variables,"DPLL");
			formulasDPLL.add(f);
			System.out.println(f.toString());
		}
		
		/* Tiempos */
		double tiempoTotalDPLL = 0.0;
		for (int i = 0; i < numFormulas; i++) {
			long startTime = System.nanoTime();
			DPLL.dpll(formulasLB.get(i));
			long endTime = System.nanoTime();
			double duration = (endTime - startTime) / (1000.0);
			tiempoTotalDPLL += duration;
		}
		
		/* Muestra los resultados finales */
		System.out.println();
		System.out.println("Parametros: " + numFormulas + " formulas"
								    + ", " + numVariables + " variables"
								  	+ ", " + numClausulas + " clausulas.");
		System.out.println("===================================================");
		System.out.println("Tiempo total LB: " + tiempoTotalLB + " ms");
		System.out.println("Tiempo total UP: " + tiempoTotalUP + " ms");
		System.out.println("Tiempo total DPLL: " + tiempoTotalDPLL + " ms");
	}
	
	private static HashSet<Variable> generarVariablesAleatorias(int numVariables) {
		HashSet<Variable> variables = new HashSet<Variable>();
		int vars = 0;
		while (vars < numVariables) {
			String nombre = generarNombreAleatorio(numVariables);
			Variable v = new Variable(nombre);
			if (!esta(variables, v)) {
				variables.add(v);
				vars++;
			}
		}
		return variables;
	}
	
	private static Formula generarFormulaAleatoria(HashSet<Variable> variables,
			int numClausulas, List<Variable> lista_variables, String tipo) {
		Formula formula = new Formula(variables);
		for (int i = 0; i < numClausulas; i++) {
			Clausula clausula = generarClausulaAleatoria(lista_variables, tipo);
			formula.addClausula(clausula);
		}
		return formula;
	}
	
	private static Clausula generarClausulaAleatoria(List<Variable> variables, String tipo) {
		Clausula clausula = new Clausula();
		Random gen = new Random();
		int numLiterales = gen.nextInt(variables.size() - 2) + 2;
		int numAfirmados = 0;
		
		if (tipo.equals("LB")) {
			numLiterales = gen.nextInt(2) + 1;
		}
		
		for (int i = 0; i < numLiterales; i++) {
			
			/* Literal aleatorio */
			Variable v = variables.get(gen.nextInt(variables.size()));
			boolean negada = gen.nextInt(2) == 1;
			
			if (tipo.equals("UP") && !negada) {
				numAfirmados++;
				if (numAfirmados > 1) {
					negada = true;
				}
			}
			
			Literal literal = new Literal(negada, v);
			clausula.addLiteral(literal);
		}
		return clausula;
	}
	
	private static String generarNombreAleatorio(int numVariables) {
		String nombre = "v";
		Random gen = new Random();
		nombre = nombre + gen.nextInt(numVariables + 1);
		return nombre;
	}
	
	private static boolean esta(HashSet<Variable> variables, Variable var) {
		boolean esta = false;
		Iterator<Variable> iter = variables.iterator();
		while (!esta && iter.hasNext()) {
			Variable v = iter.next();
			if (v.getNombre().equals(var.getNombre())) {
				esta = true;
			}
		}
		return esta;
	}
	
}
