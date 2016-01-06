/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Formula {
	/*
	 * Clase que representa una formula en CNF
	 */

	private List<Clausula> clausulas;
	private Set<Variable> vars;
	
	/**
	 * Constructor
	 * @param vars variables que utiliza la formula CNF
	 */
	public Formula(Set<Variable> vars){
		clausulas = new LinkedList<Clausula>();
		this.vars = vars;
	}
	
	/**
	 * @param c clausula a agregar a la formula
	 */
	public void addClausula(Clausula c){
		clausulas.add(c);
	}
	
	/**
	 * @param c clausula que eliminar de la formula
	 */
	public void removeClausula(Clausula c){
		clausulas.remove(c);
	}
	
	/**
	 * @return el valor de la formula CNF, que se calcula como
	 * el operador AND sobre todas las clausulas de la formula
	 */
	public boolean getValor(){
		boolean resultado = false;
		if(clausulas.isEmpty()){
			resultado = true;
		}
		else{
			resultado = clausulas.get(0).getValor();
			for(Clausula c : clausulas){
				resultado = resultado && c.getValor();
				if(!resultado) break;
			}
		}
		return resultado;
	}
	
	/**
	 * @return el listado de clausulas de la formula
	 */
	public List<Clausula> getClausulas(){
		return clausulas;
	}
	
	/**
	 * @return el conjunto de variables de la formula
	 */
	public Set<Variable> getVariables(){
		return vars;
	}
	
	/**
	 * @param c clausula
	 * @return true si la clausula esta contenida en la formula,
	 * false en caso contrario
	 */
	public boolean contains(Clausula c){
		if(c == null){
			return false;
		}
		else{
			for(Clausula fc : clausulas){
				if(c.equals(fc)){
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public String toString() {
		String resp = "";
		for(Clausula c : clausulas){
			resp = resp + c.toString() + " * ";
		}
		resp = resp.substring(0, resp.length() - 2);
		return resp.trim();
	}
}
