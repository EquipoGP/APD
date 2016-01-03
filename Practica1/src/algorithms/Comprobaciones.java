/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package algorithms;

import java.util.List;

import data.Clausula;
import data.Formula;
import data.Literal;

public class Comprobaciones {
	/*
	 * Clase para comprobar el tipo de la formula CNF
	 * introducida previamente
	 */
	
	/**
	 * @param f formula CNF
	 * @return true si es una formula 2-SAT o false si no lo es
	 * Es 2-SAT si cada clausula contiene, como maximo, 2 literales
	 */
	public static boolean es2SAT(Formula f){
		List<Clausula> cs = f.getClausulas();
		
		boolean _2sat = true;
		for(Clausula c : cs){
			List<Literal> ls = c.getLiterales();
			if(ls != null && ls.size() > 2){
				_2sat = false;
				break;
			}
		}
		
		return _2sat;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si es una formula Horn, o false si no lo es
	 * Es Horn si cada clausula tiene, como maximo, un literal afirmado
	 */
	public static boolean esHorn(Formula f){
		List<Clausula> cs = f.getClausulas();
		
		boolean horn = true;
		for(Clausula c : cs){
			List<Literal> ls = c.getLiterales();
			if(ls != null && !ls.isEmpty()){
				int afirmados = 0;
				for(Literal l : ls){
					if(!l.negado()){
						afirmados++;
					}
				}
				if(afirmados > 1){
					horn = false;
					break;
				}
			}
		}
		
		return horn;
	}

}
