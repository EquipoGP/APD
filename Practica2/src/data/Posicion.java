package data;

public class Posicion {
	
	private int texto;
	private int posicion;
	
	/**
	 * @param texto
	 * @param posicion
	 */
	public Posicion(int texto, int posicion) {
		this.texto = texto;
		this.posicion = posicion;
	}
	public int getTexto() {
		return texto;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setTexto(int texto) {
		this.texto = texto;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
