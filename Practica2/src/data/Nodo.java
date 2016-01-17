package data;

import java.util.List;

public class Nodo {

	private char caracter;
	private List<Nodo> hijos;
	
	public Nodo() {
		this.caracter = '$';
		this.hijos = null;
	}
	
	public Nodo(char caracter, List<Nodo> hijos) {
		this.caracter = caracter;
		this.hijos = hijos;
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

}
