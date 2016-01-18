package data;

import java.util.List;
import java.util.Map;

public class Nodo {

	private char caracter;
	private List<Nodo> hijos;
	private Map<Integer, Integer> texto_posicion;
	
	public Nodo() {
		this.caracter = '$';
		this.hijos = null;
		this.texto_posicion = null;
	}
	
	public Nodo(char caracter, List<Nodo> hijos) {
		this.caracter = caracter;
		this.hijos = hijos;
		this.texto_posicion = null;
	}

	public char getCaracter() {
		return caracter;
	}

	public void setCaracter(char caracter) {
		this.caracter = caracter;
	}

	public List<Nodo> getHijos() {
		return hijos;
	}

	public void setHijos(List<Nodo> hijos) {
		this.hijos = hijos;
	}
	
	public Map<Integer, Integer> getTextos() {
		return texto_posicion;
	}

	public void setTextos(Map<Integer, Integer> textos) {
		this.texto_posicion = textos;
	}

}
