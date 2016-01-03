/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package algorithms;

import java.util.List;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class UnitPropagation {
	/*
	 * Clase que contiene el algoritmo de Unit Propagation
	 * para la resolucion de formulas del tipo Horn
	 */

	/**
	 * @param f formula CNF
	 * @return true si es satsfacible, false en caso contrario
	 */
	public static boolean unitPropagation(Formula f){
		while(hayClausulasUnitarias(f)){
			Clausula cl = encontrarUnitario(f);
			Literal l = cl.getLiterales().get(0);
			
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
			f.removeClausula(cl);
			
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
		/* 
		 * si no hay clausulas unitarias es satisfacible poniendo todas las
		 * variables a false
		 */
		return true;
	}

	/**
	 * @param f formula CNF
	 * @return true si hay una clausula unitaria, false si no la hay
	 * Una clausula es unitaria si solo contiene un literal
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
	 * @return una clausula unitaria
	 * Una clausula es unitaria si solo contiene un literal
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
