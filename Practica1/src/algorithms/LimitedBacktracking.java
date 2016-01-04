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
	 * ESTRUCTURA
	 * ===============================
	 * si(clausula == false){
	 * 	volver atras
	 * }
	 * si(clausula tiene una variable seteada){
	 * 	setear la otra para que sea true la clausula
	 * 	volver al punto 1
	 * }
	 * si(todo bien hasta este punto){
	 * 	coger variable
	 * 	afirmarla
	 * 	probar
	 * 	si no funciona
	 * 		negar variable
	 * 		probar
	 * 		si no funciona
	 * 			return false
	 */
	
	private static Random r = new Random();
	private static List<Variable> vars = new LinkedList<Variable>();
	
	public static boolean limitedBT(Formula f){
		vars.addAll(f.getVariables());
		return limitedBacktracking(f);
	}
	
	private static boolean limitedBacktracking(Formula f){
		if(clausula_falsa(f)){
			System.out.println("Clausula falsa");
			return false;
		}
		if(clausulas_triviales(f)){
			Clausula c = clausula_trivial(f);
			for(Literal l : c.getLiterales()){
				if(!l.getVariable().isSet()){
					l.getVariable().setValor(!l.negado());
				}
			}
			System.out.println("Clausula trivial");
			return limitedBacktracking(f);
		}
		if(todas_seteadas(f)){
			System.out.println("Fin del algoritmo");
			return true;
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
		System.out.println("Probando con " + v.getNombre() + " = true");
		if(!limitedBacktracking(f)){
			// probar con la negacion
			v.setValor(false);
			System.out.println("Probando con " + v.getNombre() + " = false");
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
