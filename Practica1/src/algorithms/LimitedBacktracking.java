/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class LimitedBacktracking {
	/*
	 * Clase que contiene el algoritmo de LimitedBacktracking
	 * para la comprobacion de satisfacibilidad en problemas
	 * de formulas CNF 2-SAT
	 */
	
	private static Random r = new Random();
	private static List<Variable> vars;
	
	/**
	 * @param f formula CNF
	 * @return true si la formula es satisfacible, false en 
	 * caso contrario
	 */
	public static boolean limitedBT(Formula f){
		vars = new LinkedList<Variable>();
		vars.addAll(f.getVariables());
		return limitedBacktracking(f);
	}
	
	/**
	 * @param f formula CNF
	 * @return true si la formula es satisfacible, false en
	 * caso contrario
	 */
	private static boolean limitedBacktracking(Formula f){
		if(clausula_falsa(f)){
			// si hay una clausula a false, no es satisfacible
			return false;
		}
		if(clausulas_triviales(f)){
			/*
			 * Si hay clausulas triviales, obtenemos una de esas
			 * clausulas y hacemos que sea true
			 */
			Clausula c = clausula_trivial(f);
			for(Literal l : c.getLiterales()){
				if(!l.getVariable().isSet()){
					l.getVariable().setValor(!l.negado());
				}
			}
			return limitedBacktracking(f);
		}
		if(todas_seteadas(f)){
			/*
			 * el algoritmo termina si se han conseguido setear
			 * todas las variables 
			 */
			return true;
		}
		
		/*
		 * Se obtiene una variable no seteada de forma aleatoria y
		 * se setea su valor a true y false para comprobar la
		 * satisfacibilidad en los dos casos.
		 */
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
		if(!limitedBacktracking(f)){
			// probar con la negacion
			v.setValor(false);
			if(!limitedBacktracking(f)){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	}
	
	/**
	 * @param f formula CNF
	 * @return true si todas las variables de la formula
	 * tienen valor asignado, false en caso contrario
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
	 * @return true si hay alguna clausula cuyas variables estan
	 * seteadas y ademas es falsa, false en cas contrario
	 */
	private static boolean clausula_falsa(Formula f){
		for(Clausula c : f.getClausulas()){
			boolean valor = c.getValor();
			if(!valor){
				boolean seteadas = true;
				for(Literal l : c.getLiterales()){
					if(!l.getVariable().isSet()){
						seteadas = false;
					}
				}
				if(seteadas){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param f formula CNF
	 * @return true si existe una clausula que tenga una variable seteada
	 * pero que aun pueda ser true o false, false en caso contrario.
	 * Es decir, la variable seteada no decide el valor de la clausula.
	 */
	private static boolean clausulas_triviales(Formula f){
		for(Clausula c : f.getClausulas()){
			int veces = 0;
			for(Literal l : c.getLiterales()){
				if(!l.getVariable().isSet()){
					veces++;
				}
			}
			if(veces == 1){
				for(Literal l : c.getLiterales()){
					if(l.getVariable().isSet() &&
							!l.getValor()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param f formula CNF
	 * @return una clausula que contiene una variable seteada que
	 * no decide el valor de la clausula
	 */
	private static Clausula clausula_trivial(Formula f){
		for(Clausula c : f.getClausulas()){
			int veces = 0;
			for(Literal l : c.getLiterales()){
				if(!l.getVariable().isSet()){
					veces++;
				}
			}
			if(veces == 1){
				for(Literal l : c.getLiterales()){
					if(l.getVariable().isSet() &&
							!l.getValor()){
						return c;
					}
				}
			}
		}
		return null;
	}
}
