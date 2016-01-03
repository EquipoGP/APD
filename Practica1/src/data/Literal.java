/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

public class Literal {
	/*
	 * Clase que representa un liertal en una formula CNF
	 */

	private boolean negado = false;
	private Variable var = null;
	
	/**
	 * Constructor
	 * @param negado true si el literal esta negado, false en caso contrario
	 * @param v variable a la que hace referencia el literal
	 */
	public Literal(boolean negado, Variable v){
		this.negado = negado;
		this.var = v;
	}
	
	/**
	 * @return true o false, dependiendo del valor de la variable a la que
	 * referencia y de si esta negado o no
	 */
	public boolean getValor(){
		if(negado){
			return !var.getValor();
		}
		else{
			return var.getValor();
		}
	}
	
	/**
	 * @return true si esta negado, false si no lo esta
	 */
	public boolean negado(){
		return negado;
	}
	
	/**
	 * @return variable a la que referencia
	 */
	public Variable getVariable(){
		return var;
	}
	
	@Override
	public String toString() {
		String resp = "";
		if(negado){
			resp = "-";
		}
		resp = resp + var.getNombre();
		return resp;
	}
	
	/**
	 * @param l literal con el que comparar
	 * @return true si son iguales, false si no lo son. Se consideran dos 
	 * literales iguales si tienen el mismo signo y hacen referencia a 
	 * la misma variable
	 */
	public boolean equals(Literal l){
		return this.toString().equals(l.toString());
	}
}
