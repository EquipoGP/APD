/*
 * Patricia Lazaro Tello (554309)
 * Aejandro Royo Amondarain (560285)
 */

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
	/*
	 * Clase que contiene el algoritmo DPLL para la resolucion
	 * de SAT en casos generales
	 */
	
	private static Random r = new Random();
	private static List<Variable> vars;
	
	/**
	 * @param form formula CNF
	 * @return true si la formula @param f es satisfacible, o false si no lo es
	 */
	public static boolean dpll(Formula form){
		
		/* No modifica la variable formula */
		Formula f = new Formula(form.getVariables());
		for (Clausula c : form.getClausulas()) {
			f.addClausula(c);
		}
		
		vars = new LinkedList<Variable>();
		vars.addAll(f.getVariables());
		return DPLLAlgorithm(f);
	}
	
	/**
	 * @param f formula CNF
	 * @return true si la formula @param f es satisfacible, y false si no lo es
	 */
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
			
			for (int i = 0; i < f.getClausulas().size(); i++) {
				Clausula c = f.getClausulas().get(i);
				if(c.contains(l.getVariable(), l.negado())){
					// quitar clausulas que tengan x = true
					f.removeClausula(c);
				}
				if(c.contains(l.getVariable(), !l.negado())){
					// quitar literales que tengan x = false
					c.removeLiteral(new Literal(!l.negado(), l.getVariable()));
					if(c.getLiterales().isEmpty()){
						/* 
						 * hemos eliminado todos los literales de la
						 * clausula sin conseguir que ninguno fuera cierto
						 */
						return false;
					}
				}
			}
		}
		
		if(todas_seteadas(f)){
			return f.getValor();
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
	
	/**
	 * @param f formula CNF
	 * @return true si la formula es consistente y false si no lo es
	 * Una formula es no consistente si se encuentran los literales
	 * x y ¬x en la formula
	 */
	private static boolean formulaConsistente(Formula f){
		boolean consistente = true;
		Set<Variable> variables = f.getVariables();
		List<Literal> literales = new LinkedList<Literal>();
		
		for(Clausula c : f.getClausulas()){
			literales.addAll(c.getLiterales());
		}
		
		for (Variable v : variables) {
			int afirmados = 0;
			int negados = 0;
			for(Literal l : literales){
				if(l.getVariable().getNombre().equals(v.getNombre())){
					if(l.negado()){
						negados++;
					}
					else{
						afirmados++;
					}
				}
			}
			if(afirmados != 0 && negados != 0){
				consistente = false;
				break;
			}
		}
		
		return consistente;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si todas las variables de la formula tienen
	 * un valor asignado
	 */
	private static boolean todas_seteadas(Formula f) {
		for(Variable v : f.getVariables()){
			if(!v.isSet()){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param f formula CNF
	 * @return true si hay literales puros en la formula, y false
	 * si no los hay.
	 * Un literal puro es aquel que se encuentra solo afirmado o
	 * solo negado en la formula.
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
	
	/**
	 * @param f formula CNF
	 * @return literal puro de la formula @param f
	 * Un literal puro es aquel que solo aparece afirmado o
	 * negado en la formula.
	 */
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

	/**
	 * @param f formula CNF
	 * @return true si hay clausulas unitarias, false en caso contrario
	 * Una clausula unitaria es aquella que se compone de un solo literal.
	 */
	private static boolean hayClausulasUnitarias(Formula f) {
		List<Clausula> cs = f.getClausulas();
		for(Clausula c : cs){
			if(c.getLiterales().size() == 1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param f formula CNF
	 * @return una clausula unitaria de @param f
	 * Una clausula unitaria es aquella que se compone de un solo literal.
	 */
	private static Clausula encontrarUnitario(Formula f) {
		List<Clausula> cs = f.getClausulas();
		for(Clausula c : cs){
			if(c.getLiterales().size() == 1){
				return c;
			}
		}
		return null;
	}
}
