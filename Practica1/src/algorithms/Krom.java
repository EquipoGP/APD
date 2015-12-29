package algorithms;

import java.util.List;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class Krom {

	public static boolean krom(Formula f){
		boolean consistente = true;
		boolean satisfacible = false;
		Set<Variable> variables = f.getVariables();
		List<Clausula> clausulas = f.getClausulas();
		
		/* Mientras la formula es consistente y reducible se reduce */
		while(consistente && formulaReducible(f)){
			
			/* Reduccion */
			f = reducirFormula(f);
			consistente = formulaConsistente(f);
		}
		
		if (consistente) {
			
			// Construye la formula final, con clausulas del tipo:
			// (v or v) o (�v or �v) para cada variable v
			for(Variable v : variables){
				
				/* Nueva clausula (v or v) */
				Clausula clausulaPositiva = new Clausula();
				clausulaPositiva.addLiteral(new Literal(false, v));
				clausulaPositiva.addLiteral(new Literal(false, v));
				
				/* Nueva clausula (�v or �v) */
				Clausula clausulaNegativa = new Clausula();
				clausulaNegativa.addLiteral(new Literal(true, v));
				clausulaNegativa.addLiteral(new Literal(true, v));
				
				// Si no estan incluidas ya las clausulas (v or v) y
				// (�v or �v), es decir, es consistente, entonces
				// se incluye (v or v)
				f.addClausula(clausulaPositiva);
				
				if (!formulaConsistente(f)) {
					
					// Si la formula no es consistente se borra
					// (v or v) y se incluye la clausula opuesta (�v or �v)
					f.removeClausula(clausulaPositiva);
					f.addClausula(clausulaNegativa);
				}
			}
			
			/* Da valor a las variables segun las clausulas incluidas */
			for (Variable v : variables) {
					
				/* Nueva clausula (v or v) */
				Clausula clausulaPositiva = new Clausula();
				clausulaPositiva.addLiteral(new Literal(false, v));
				clausulaPositiva.addLiteral(new Literal(false, v));
				
				/* Nueva clausula (�v or �v) */
				Clausula clausulaNegativa = new Clausula();
				clausulaNegativa.addLiteral(new Literal(true, v));
				clausulaNegativa.addLiteral(new Literal(true, v));

				// Si aparece (v or v) se asigna true a la variable v
				if (clausulas.contains(clausulaPositiva)){
					v.setValor(true);
				}
				// Si aparece (�v or �v) se asigna false a la variable v
				else if(clausulas.contains(clausulaNegativa)){
					v.setValor(false);
				}
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
	
	private static boolean formulaConsistente(Formula f){
		boolean consistente = true;
		Set<Variable> variables = f.getVariables();
		List<Clausula> clausulas = f.getClausulas();
		
		for (Variable v : variables) {
			
			/* Nueva clausula (v or v) */
			Clausula clausulaPositiva = new Clausula();
			clausulaPositiva.addLiteral(new Literal(false, v));
			clausulaPositiva.addLiteral(new Literal(false, v));
			
			/* Nueva clausula (�v or �v) */
			Clausula clausulaNegativa = new Clausula();
			clausulaNegativa.addLiteral(new Literal(true, v));
			clausulaNegativa.addLiteral(new Literal(true, v));
			
			if (clausulas.contains(clausulaPositiva) ||
					clausulas.contains(clausulaNegativa)) {
				consistente = false;
			}
			if (!consistente) break;
		}
		
		return consistente;
	}
	
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
			
			if(c1 != null && c2 != null){
				reducible = true;
				break;
			}
		}
		return reducible;
	}
	
	private static Formula reducirFormula(Formula f){
		for(Variable v : f.getVariables()){
			Clausula c1 = null;
			Clausula c2 = null;
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(v, false)){
					// sin negar
					c1 = c;
				}
				if(c.contains(v, true)){
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
					if(!l.getVariable().equals(v)){
						notC1 = l;
					}
				}
				for(Literal l : c2ls){
					if(!l.getVariable().equals(v)){
						notC2 = l;
					}
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
	
	private static boolean formulaSatisfacible(Formula f) {
		return f.getValor();
	}
}