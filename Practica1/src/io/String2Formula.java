package io;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class String2Formula {
	
	private static Set<Variable> vars;
	
	public static Formula parsearFormula(String formula){
		vars = getVariables(formula);
		
		Formula f = new Formula(vars);
		formula = formula.trim();
		String[] clausulas = formula.split("\\*");
		for(String s : clausulas){
			Clausula c = parsearClausula(s);
			f.addClausula(c);
		}
		return f;
	}
	
	private static Clausula parsearClausula(String clausula){
		Clausula c = new Clausula();
		clausula = clausula.trim();
		clausula = clausula.replaceAll("\\(", "");
		clausula = clausula.replaceAll("\\)", "");
		String[] literales = clausula.split("\\+");
		for(String s : literales){
			Literal l = parsearLiteral(s);
			c.addLiteral(l);
		}
		return c;
	}
	
	private static Literal parsearLiteral(String literal){
		boolean negado = literal.contains("-");
		literal = literal.trim();
		literal = literal.replace("-", "");
		Variable var = null;
		for(Variable v : vars){
			if(v.getNombre().equals(literal)){
				var = v;
				break;
			}
		}
		if(var == null) System.out.println("WOW");
		Literal lit = new Literal(negado, var);
		return lit;
	}
	
	private static Set<Variable> getVariables(String formula){
		Set<Variable> vars = new HashSet<Variable>();
		
		formula.trim();
		formula = formula.replaceAll("\\(", "");
		formula = formula.replaceAll("\\)", "");
		formula = formula.replaceAll("\\*", " ");
		formula = formula.replaceAll("\\+", " ");
		formula = formula.replaceAll("-", "");
		
		Scanner s = new Scanner(formula);
		while(s.hasNext()){
			boolean found = false;
			String nombre = s.next();
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
