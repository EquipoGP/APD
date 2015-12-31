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

		System.out.println("Formula sin reducir: " + f.toString());
		
		/* Mientras la formula es consistente y reducible se reduce */
		while(consistente && formulaReducible(f)){
			/* Reduccion */
			System.out.println("Reduciendo...");
			f = reducirFormula(f);

//			for (Clausula c : f.getClausulas()) {
//				for(Literal l : c.getLiterales()) {
//					System.out.println(l);
//				}
//			}
			
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
				
				if (!formulaConsistente(f)) {
					
					// Si la formula no es consistente se borra
					// (v or v) y se incluye la clausula opuesta (¬v or ¬v)
					f.removeClausula(clausulaPositiva);
					f.addClausula(clausulaNegativa);
				}
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
	
	private static boolean formulaConsistente(Formula f){
		boolean consistente = true;
		Set<Variable> variables = f.getVariables();
		List<Clausula> clausulas = f.getClausulas();
		
		for (Variable v : variables) {
			
			/* Nueva clausula (v or v) */
			Clausula clausulaPositiva = new Clausula();
			clausulaPositiva.addLiteral(new Literal(false, v));
			clausulaPositiva.addLiteral(new Literal(false, v));
			
			/* Nueva clausula (¬v or ¬v) */
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
//						System.out.println("v = " + v.getNombre());
//						System.out.println("notC1 = " + l.getVariable().getNombre());
						notC1 = l;
					}
				}
				for(Literal l : c2ls){
//					System.out.println(l.getVariable().getNombre() + " =? " + v.getNombre());
					if(!l.getVariable().equals(v)
							||
							(l.getVariable().equals(v) && !l.negado())){
						// entra si no es la variable a quitar o,
						// si siendolo, no coincide con el signo que le toca
						// example: buscamos b, tenemos (b+-b) => coge b
//						System.out.println("v = " + v.getNombre());
//						System.out.println("notC2 =  " + l.getVariable().getNombre());
						notC2 = l;
					}
				}
				
				Clausula c = new Clausula();
				c.addLiteral(notC1);
				c.addLiteral(notC2);
				f.addClausula(c);
				
				f.removeClausula(c1);
				f.removeClausula(c2);
				
				System.out.println("Reduccion: clausulas borradas: " + c1.toString() + " AND " + c2.toString());
				System.out.println("Reduccion: clausula añadida: " + notC1.toString() + " + " + notC2.toString());
				System.out.println("Formula reducida: " + f.toString());
			}
		}
		return f;
	}
	
	private static boolean formulaSatisfacible(Formula f) {
		return f.getValor();
	}
}
