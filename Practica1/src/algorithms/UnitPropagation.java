package algorithms;

import java.util.List;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class UnitPropagation {

	public static boolean unitPropagation(Formula f){
		/*
		 * ESTRUCTURA
		 * ===========================================
		 * (Si hay clausulas unitarias)
		 * 	Bucle:
		 * 		1. Encontrar clausula unitaria
		 * 		2. (Si x)
		 * 			x = true, eliminar todas las clausulas
		 * 				que contengan x y eliminar -x de todas
		 * 				las clausulas
		 * 		3. (Si -x)
		 * 			x = false, borrar x de todas las clausulas
		 * 				y borrar todas las clausulas que contengan
		 * 				-x
		 */
		
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
