/*
 * Patricia Lazaro Tello (554209)
 * Alejandro Royo Amondarain (560285)
 */

package data;

public class Variable {
	/*
	 * Clase que representa una variable en una formula CNF
	 */

	private String nombre;
	private boolean valor;
	private boolean set;
	
	/**
	 * Constructor
	 * @param nombre nombre de la variable
	 */
	public Variable(String nombre){
		this.nombre = nombre;
		this.valor = false;
		this.set = false;
	}

	/**
	 * @return nombre de la variable
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre nombre de la variable
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el valor (true o false) de la variable,
	 * o false si no esta seteado
	 */
	public boolean getValor() {
		return valor;
	}

	/**
	 * @param valor setea el valor de la variable
	 */
	public void setValor(boolean valor) {
		set = true;
		this.valor = valor;
	}
	
	/**
	 * @return si la variable esta seteada o no
	 */
	public boolean isSet(){
		return set;
	}
}
