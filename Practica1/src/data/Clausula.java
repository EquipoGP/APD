/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

import java.util.LinkedList;
import java.util.List;

public class Clausula {
	/*
	 * Clase que representa una clausula en una formula CNF
	 */
	
	private List<Literal> literales;
	
	/**
	 * Constructor
	 */
	public Clausula(){
		literales = new LinkedList<Literal>();
	}
	
	/**
	 * @param lit literal a agregar
	 */
	public void addLiteral(Literal lit){
		literales.add(lit);
	}
	
	/**
	 * @return valor de la clausula, que se define como la
	 * operacion OR de los literales
	 */
	public boolean getValor(){
		boolean resultado = false;
		if(literales.isEmpty()){
			resultado = true;
		}
		else{
			resultado = literales.get(0).getValor();
			for(Literal l : literales){
				resultado = resultado || l.getValor();
				if(resultado) break;
			}
		}
		return resultado;
	}
	
	/**
	 * @return lista de los literales de la clausula
	 */
	public List<Literal> getLiterales(){
		return literales;
	}
	
	@Override
	public String toString() {
		String resp = "(";
		for (int i = 0; i < literales.size(); i++) {
			if (i < literales.size() - 1) {
				Literal l = literales.get(i);
				resp = resp + l.toString() + " + ";
			}
			else if (i == literales.size() - 1) {
				Literal l = literales.get(i);
				resp = resp + l.toString();
			}
		}
		resp = resp + ")";
		return resp;
	}
	
	/**
	 * @param c clausula que comparar
	 * @return true si las clausulas son iguales,
	 * false si no lo son
	 */
	public boolean equals(Clausula c){
		boolean res = true;
		List<Literal> cs = c.getLiterales();
		for(Literal l : cs){
			boolean esta = false;
			for(Literal li : literales){
				if(li.equals(l)){
					esta = true;
					break;
				}
			}
			if(!esta){
				res = false;
				break;
			}
		}
		return res;
	}
	
	/**
	 * @param v variable
	 * @param negado true si esta negado, false en caso contrario
	 * @return true si hay un literal en la lista que referencia
	 * a la variable @param v cuyo signo es @param negado, y 
	 * false en caso contrario 
	 */
	public boolean contains(Variable v, boolean negado){
		for(Literal l : literales){
			if(l == null){
				return false;
			}
			if(l.getVariable().getNombre().equals(v.getNombre())
					&& l.negado() == negado){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param l literal a ser eliminado de la clausula
	 */
	public void removeLiteral(Literal l){
		for(int i = 0; i < literales.size(); i++){
			Literal lit = literales.get(i);
			if(lit.equals(l)){
				literales.remove(i);
			}
		}
	}
}
