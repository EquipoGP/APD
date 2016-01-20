/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

import java.util.List;

public class Nodo {
	/*
	 * Clase para la representacion de un nodo
	 */

	/* atributos privados */
	private Identificador id;
	
	private Nodo padre;
	private List<Nodo> hijos;
	
	private List<Posicion> texto_posicion;
	
	/**
	 * @param padre padre del nodo creado
	 */
	public Nodo(Nodo padre) {
		this.padre = padre;
		this.hijos = null;
		
		this.id = new Identificador();
		this.id.setLabel("$");
		
		this.texto_posicion = null;
	}
	
	/**
	 * @param etiq etiqueta
	 * @param hijos hijos del nodo
	 */
	public Nodo(String etiq, List<Nodo> hijos) {
		this.id = new Identificador();
		this.id.setLabel(etiq);
		
		this.hijos = hijos;
		this.texto_posicion = null;
	}

	/**
	 * @return etiqueta
	 */
	public String getLabel() {
		return this.id.getLabel();
	}

	/**
	 * @param etiq etiqueta
	 */
	public void setLabel(String etiq) {
		this.id.setLabel(etiq);
	}
	
	/**
	 * @return padre
	 */
	public Nodo getPadre() {
		return padre;
	}

	/**
	 * @param padre padre
	 */
	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	/**
	 * @return hijos
	 */
	public List<Nodo> getHijos() {
		return hijos;
	}

	/**
	 * @param hijos hijos
	 */
	public void setHijos(List<Nodo> hijos) {
		this.hijos = hijos;
	}
	
	/**
	 * @return textos
	 */
	public List<Posicion> getTextos() {
		return texto_posicion;
	}

	/**
	 * @param textos textos
	 */
	public void setTextos(List<Posicion> textos) {
		this.texto_posicion = textos;
	}
	
	/**
	 * @return identificador
	 */
	public Identificador getId() {
		return id;
	}

	/**
	 * @param id identificador
	 */
	public void setId(Identificador id) {
		this.id = id;
	}

	/**
	 * @return grado del nodo
	 */
	public int getGrado(){
		int grado = 0;
		if(this.padre != null){
			grado++;
		}
		if(hijos != null && !hijos.isEmpty()){
			grado = grado + hijos.size();
		}
		
		return grado;
	}

}
