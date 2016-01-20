/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

public class Posicion {
	/*
	 * Clase para la representacion de la posicion
	 */
	
	/* atributos privados */
	private int texto;
	private int posicion;
	
	/**
	 * @param texto numero del texto
	 * @param posicion posicion inicial
	 */
	public Posicion(int texto, int posicion) {
		this.texto = texto;
		this.posicion = posicion;
	}
	
	/**
	 * @return numero de texto
	 */
	public int getTexto() {
		return texto;
	}
	
	/**
	 * @return posicion inicial
	 */
	public int getPosicion() {
		return posicion;
	}
	
	/**
	 * @param texto numero de texto
	 */
	public void setTexto(int texto) {
		this.texto = texto;
	}
	
	/**
	 * @param posicion posicion inicial
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
