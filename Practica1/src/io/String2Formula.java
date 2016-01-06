/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package io;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class String2Formula {
	/*
	 * Clase para convertir un String a un objeto de tipo Formula (CNF)
	 */
	
	private static Set<Variable> vars;
	
	/**
	 * Metodo principal para parsear String
	 * @param formula String que contiene la formula a parsear
	 * @return un objeto de tipo Formula
	 */
	public static Formula parsearFormula(String formula){
		/* obtiene las variables que usara la formula */
		try {
			vars = getVariables(formula);
			Formula f = new Formula(vars);
			formula = formula.trim();
			
			/* parsea cada clausula */
			String[] clausulas = formula.split("\\*");
			for(String s : clausulas){
				Clausula c = parsearClausula(s);
				f.addClausula(c);
			}
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Metodo para parsear clausulas desde String
	 * @param clausula String correspondiente a una clausula
	 * @return un objeto de tipo clausula
	 */
	private static Clausula parsearClausula(String clausula){
		Clausula c = new Clausula();
		
		clausula = clausula.trim();
		clausula = clausula.replaceAll("\\(", "");
		clausula = clausula.replaceAll("\\)", "");
		
		/* parsea cada literal */
		String[] literales = clausula.split("\\+");
		for(String s : literales){
			Literal l = parsearLiteral(s);
			c.addLiteral(l);
		}
		return c;
	}
	
	/**
	 * Metodo para parsear literales desde String
	 * @param literal String correspondiente a un literal
	 * @return un objeto de tipo literal
	 */
	private static Literal parsearLiteral(String literal){
		boolean negado = literal.contains("-");
		
		literal = literal.trim();
		literal = literal.replace("-", "");
		
		/* obtiene la variable a la que hace referencia */
		Variable var = null;
		for(Variable v : vars){
			if(v.getNombre().equals(literal)){
				var = v;
				break;
			}
		}
		Literal lit = new Literal(negado, var);
		return lit;
	}
	
	/**
	 * Metodo para obtener las variables que se usan en la 
	 * formula CNF
	 * @param formula String que representa la formula
	 * @return un conjunto de variables
	 * @throws Exception 
	 */
	private static Set<Variable> getVariables(String formula) throws Exception{
		Set<Variable> vars = new HashSet<Variable>();
		
		/* formatear la entrada */
		formula = formula.trim();
		formula = formula.replaceAll("\\(", "");
		formula = formula.replaceAll("\\)", "");
		formula = formula.replaceAll("\\*", " ");
		formula = formula.replaceAll("\\+", " ");
		formula = formula.replaceAll("-", "");
		
		Scanner s = new Scanner(formula);
		while(s.hasNext()){
			boolean found = false;
			String nombre = s.next();
			if(!nombre.matches("[a-zA-Z][a-zA-Z0-9_]*")){
				s.close();
				throw new Exception("Wrong variables");
			}
			for(Variable v : vars){
				if(nombre.equals(v.getNombre())){
					found = true;
					break;
				}
			}
			if(!found){
				Variable var = new Variable(nombre);
				vars.add(var);
			}
		}
		s.close();
		return vars;
	}
}
