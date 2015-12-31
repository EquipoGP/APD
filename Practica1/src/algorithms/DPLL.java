package algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class DPLL {
	
	private static Random r = new Random();
	private static List<Variable> vars = new LinkedList<Variable>();
	
	public static boolean dpll(Formula f){
		vars.addAll(f.getVariables());
		return DPLLAlgorithm(f);
	}
	
	private static boolean DPLLAlgorithm(Formula f){
		// si la formula es consistente: true
		if(formulaConsistente(f)){
			return true;
		}
		
		// si hay una clausula vacia: false
		for(Clausula c : f.getClausulas()){
			if(c.getLiterales().isEmpty()){
				return false;
			}
		}
		
		boolean seguir = true;
		// para cada unit clause: propagar
		// para cada literal puro: asignar
		while(seguir){
			Literal l = null;
			Clausula cl = null;
			if(hayClausulasUnitarias(f)){
				cl = encontrarUnitario(f);
				l = cl.getLiterales().get(0);
			}
			else if(hayLiteralesPuros(f)){
				l = encontrarPuro(f);
			}
			else{
				seguir = false;
				break;
			}
			
			if(l.negado()){
				/* caso: ¬x */
				Variable v = l.getVariable();
				if(!v.isSet()){
					v.setValor(false);
				}
				else{
					/* contradiccion? */
					return false;
				}
			}
			else{
				/* caso: x */
				Variable v = l.getVariable();
				if(!v.isSet()){
					v.setValor(true);
				}
				else{
					/* contradiccion? */
					return false;
				}
			}
			
			if(cl != null){
				f.removeClausula(cl);
			}
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(l.getVariable(), l.negado())){
					// quitar clausulas que tengan x = true
					f.removeClausula(c);
				}
				if(c.contains(l.getVariable(), !l.negado())){
					// quitar literales que tengan x = false
					c.removeLiteral(new Literal(!l.negado(), l.getVariable()));
					if(c.getLiterales().isEmpty()){
						/* 
						 * nos hemos cargado todos los literales de la
						 * clausula sin conseguir que ninguno fuera cierto
						 */
						return false;
					}
				}
			}
		}
		
		// random variable
		boolean continuar = false;
		Variable v = null;
		while(!continuar){
			v = vars.get(r.nextInt(f.getVariables().size()));
			if (!v.isSet()){
				continuar = true;
			}
		}
		
		// probar con la afirmacion
		v.setValor(true);
		boolean afirmado = DPLLAlgorithm(f);
		
		// probar con la negacion
		v.setValor(false);
		boolean negado = DPLLAlgorithm(f);
		
		return afirmado || negado;
	}
	
	private static Literal encontrarPuro(Formula f) {
		Set<Variable> vars = f.getVariables();
		
		for(Variable v : vars){
			int afirmados = 0;
			int negados = 0;
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(v, false)){ // x
					afirmados++;
				}
				if(c.contains(v, true)){ // -x
					negados++;
				}
			}
			
			if(afirmados + negados > 0){
				// comprobamos que la variable aparezca
				if(afirmados == 0){
					// todas las apariciones de X son negadas
					return new Literal(true, v);
				}
				if(negados == 0){
					// todas las apariciones de X son afirmadas
					return new Literal(false, v);
				}
			}
		}
		return null;
	}

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

	/*
	 * Pure literal: solo aparece de la forma x, nunca
	 * de la forma -x. Se aplica al caso contrario, que
	 * siempre aparezca como -x y nunca como x
	 */
	private static boolean hayLiteralesPuros(Formula f) {
		Set<Variable> vars = f.getVariables();
		
		for(Variable v : vars){
			int afirmados = 0;
			int negados = 0;
			
			for(Clausula c : f.getClausulas()){
				if(c.contains(v, false)){ // x
					afirmados++;
				}
				if(c.contains(v, true)){ // -x
					negados++;
				}
			}
			
			if(afirmados + negados > 0){
				// comprobamos que la variable aparezca
				if(afirmados == 0){
					// todas las apariciones de X son negadas
					return true;
				}
				if(negados == 0){
					// todas las apariciones de X son afirmadas
					return true;
				}
			}
		}
		return false;
	}

	private static Clausula encontrarUnitario(Formula f) {
		List<Clausula> cs = f.getClausulas();
		for(Clausula c : cs){
			if(c.getLiterales().size() == 1){
				return c;
			}
		}
		return null;
	}

	private static boolean hayClausulasUnitarias(Formula f) {
		List<Clausula> cs = f.getClausulas();
		for(Clausula c : cs){
			if(c.getLiterales().size() == 1){
				return true;
			}
		}
		return false;
	}
}
