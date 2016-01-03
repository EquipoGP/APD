/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package algorithms;

import java.util.List;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class Krom {
	/*
	 * Clase que contiene el algoritmo de Krom para la
	 * resolucion de problemas 2-SAT
	 */

	/**
	 * @param f formula CNF
	 * @return true si la formula @param f es satisfacible, false
	 * si no lo es
	 */
	public static boolean krom(Formula f){
		boolean consistente = formulaConsistente(f);
		boolean satisfacible = false;
		Set<Variable> variables = f.getVariables();

		System.out.println("Formula sin reducir: " + f.toString());
		
		/* Mientras la formula es consistente y reducible se reduce */
		while(consistente && formulaReducible(f)){
			
			/* Reduccion */
			System.out.println("Reduciendo...");
			f = reducirFormula(f);
			consistente = formulaConsistente(f);
		}
		System.out.println("Formula reducida: " + f.toString());
		System.out.println("Consistente: " + consistente);
		
		if (consistente) {
			
			// Construye la formula final, con clausulas del tipo:
			// (v or v) o (¬v or ¬v) para cada variable v
			for(Variable v : variables){
				
				/* Nueva clausula (v or v) */
				Clausula clausulaPositiva = new Clausula();
				clausulaPositiva.addLiteral(new Literal(false, v));
				clausulaPositiva.addLiteral(new Literal(false, v));
				
				/* Nueva clausula (¬v or ¬v) */
				Clausula clausulaNegativa = new Clausula();
				clausulaNegativa.addLiteral(new Literal(true, v));
				clausulaNegativa.addLiteral(new Literal(true, v));
				
				// Si no estan incluidas ya las clausulas (v or v) y
				// (¬v or ¬v), es decir, es consistente, entonces
				// se incluye (v or v)
				if (!f.contains(clausulaPositiva)){
					f.addClausula(clausulaPositiva);
				}
				
				if (formulaReducible(f)) {
					
					// Si la formula no es consistente se borra
					// (v or v) y se incluye la clausula opuesta (¬v or ¬v)
					f.removeClausula(clausulaPositiva);
					
					if (!f.contains(clausulaNegativa)) {
						f.addClausula(clausulaNegativa);
					}
				}
				
				System.out.println("Formula para variable " + v.getNombre() + ": " + f.toString());
			}
			
			System.out.println("Formula final: " + f.toString());
			
			/* Da valor a las variables segun las clausulas incluidas */
			for (Variable v : variables) {
					
				/* Nueva clausula (v or v) */
				Clausula clausulaPositiva = new Clausula();
				clausulaPositiva.addLiteral(new Literal(false, v));
				clausulaPositiva.addLiteral(new Literal(false, v));
				
				/* Nueva clausula (¬v or ¬v) */
				Clausula clausulaNegativa = new Clausula();
				clausulaNegativa.addLiteral(new Literal(true, v));
				clausulaNegativa.addLiteral(new Literal(true, v));

				// Si aparece (v or v) se asigna true a la variable v
				if (f.contains(clausulaPositiva)){
					v.setValor(true);
				}
				// Si aparece (¬v or ¬v) se asigna false a la variable v
				else if(f.contains(clausulaNegativa)){
					v.setValor(false);
				}
			}
			
			System.out.println("Variables:");
			for(Variable v : variables){
				System.out.println(v.getNombre() + " -> " + v.getValor());
			}
			
			/* Comprueba si la formula es satisfacible */
			satisfacible = formulaSatisfacible(f);
		}
		else {
			
			// Si la formula no es consistente no es satisfacible
			satisfacible = false;
		}
		
		return satisfacible;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si la formula es consistente, false en caso contrario
	 * Una formula es no consistente si tiene las clausulas
	 * (x v x) y (¬x v ¬x)
	 */
	private static boolean formulaConsistente(Formula f){
		boolean consistente = true;
		Set<Variable> variables = f.getVariables();
		
		for (Variable v : variables) {
			
			/* Nueva clausula (v or v) */
			Clausula clausulaPositiva = new Clausula();
			clausulaPositiva.addLiteral(new Literal(false, v));
			clausulaPositiva.addLiteral(new Literal(false, v));
			
			/* Nueva clausula (¬v or ¬v) */
			Clausula clausulaNegativa = new Clausula();
			clausulaNegativa.addLiteral(new Literal(true, v));
			clausulaNegativa.addLiteral(new Literal(true, v));
			
			if (f.contains(clausulaPositiva) &&
					f.contains(clausulaNegativa)) {
				consistente = false;
			}
			if (!consistente) break;
		}
		
		return consistente;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si la formula es reducible, false en caso contrario
	 * Una formula es reducible si existe una variable afirmada en
	 * una clausula, y la misma variable negada en otra clausula
	 * distinta. Ejemplo:
	 * (a + b) * (-a + c) => (b + c)
	 */
	private static boolean formulaReducible(Formula f){
		boolean reducible = false;
		for(Variable v : f.getVariables()){
			Clausula c1 = null;
			Clausula c2 = null;
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(v, false)){
					c1 = c;
				}
				if(c.contains(v, true)){
					c2 = c;
				}
			}
			
			if(c1 != null && c2 != null &&
					c1 != c2){
				reducible = true;
				break;
			}
		}
		return reducible;
	}
	
	/**
	 * @param f formula CNF
	 * @return formula reducida
	 * Una formula es reducible si existe una variable afirmada en
	 * una clausula, y la misma variable negada en otra clausula
	 * distinta. Ejemplo:
	 * (a + b) * (-a + c) => (b + c)
	 */
	private static Formula reducirFormula(Formula f){
		for(Variable v : f.getVariables()){
			Clausula c1 = null;
			Clausula c2 = null;
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(v, false) && c1 == null){
					// sin negar
					c1 = c;
				}
				if(c.contains(v, true) && c2 == null){
					// negado
					c2 = c;
				}
			}
			
			if(c1 != null && c2 != null){
				List<Literal> c1ls = c1.getLiterales();
				List<Literal> c2ls = c2.getLiterales();
				
				Literal notC1 = null;
				Literal notC2 = null;
				
				for(Literal l : c1ls){
					if(!l.getVariable().equals(v)
							||
							(l.getVariable().equals(v) && l.negado())){
						// entra si no es la variable a quitar o,
						// si siendolo, no coincide con el signo que le toca
						// example: buscamos b, tenemos (b+-b) => coge -b
						notC1 = l;
					}
				}
				for(Literal l : c2ls){
					if(!l.getVariable().equals(v)
							||
							(l.getVariable().equals(v) && !l.negado())){
						// entra si no es la variable a quitar o,
						// si siendolo, no coincide con el signo que le toca
						// example: buscamos b, tenemos (b+-b) => coge b
						notC2 = l;
					}
				}
				
				if (notC1 == null){
					notC1 = c1ls.get(0);
				}
				
				if (notC2 == null){
					notC2 = c2ls.get(0);
				}
				
				Clausula c = new Clausula();
				c.addLiteral(notC1);
				c.addLiteral(notC2);
				f.addClausula(c);
				
				f.removeClausula(c1);
				f.removeClausula(c2);
			}
		}
		return f;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si es satisfacible, y false si no lo es
	 */
	private static boolean formulaSatisfacible(Formula f) {
		return f.getValor();
	}
}
