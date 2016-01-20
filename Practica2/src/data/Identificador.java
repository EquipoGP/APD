/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

public class Identificador {
	/*
	 * Clase para la representacion de un identificador
	 */

	/* atributos privados */
	private int numTexto;
	private int posInicio;
	private int posFinal;
	
	private String label;
	
	/**
	 * Creacion de un identificador vacio
	 */
	public Identificador(){
		this.numTexto = -1;
		this.posInicio = -1;
		this.posFinal = -1;
		
		this.label = null;
	}

	/**
	 * @return numero de texto
	 */
	public int getNumTexto() {
		return numTexto;
	}

	/**
	 * @return posicion de inicio
	 */
	public int getPosInicio() {
		return posInicio;
	}

	/**
	 * @return posicion final
	 */
	public int getPosFinal() {
		return posFinal;
	}

	/**
	 * @return etiqueta
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param numTexto numero de texto
	 */
	public void setNumTexto(int numTexto) {
		this.numTexto = numTexto;
	}

	/**
	 * @param posInicio posicion de inicio
	 */
	public void setPosInicio(int posInicio) {
		this.posInicio = posInicio;
	}

	/**
	 * @param posFinal posicion final
	 */
	public void setPosFinal(int posFinal) {
		this.posFinal = posFinal;
	}

	/**
	 * @param label etiqueta
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @return true si tiene etiqueta, false si hace referencia a un texto
	 */
	public boolean esLabel(){
		return this.label != null;
	}
	
	@Override
	public String toString(){
		if(esLabel()){
			return label;
		}
		else{
			return "[" + numTexto + ": " + posInicio + ", " + posFinal + "]";
		}
	}
}
