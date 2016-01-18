package data;

import java.util.List;
import java.util.Map;

public class Nodo {

	private String label;
	
	private Nodo padre;
	private List<Nodo> hijos;
	
	private Map<Integer, Integer> texto_posicion;
	
	public Nodo(Nodo padre) {
		this.padre = padre;
		this.label = "$";
		this.hijos = null;
		this.texto_posicion = null;
	}
	
	public Nodo(String etiq, List<Nodo> hijos) {
		this.label = etiq;
		this.hijos = hijos;
		this.texto_posicion = null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String etiq) {
		this.label = etiq;
	}
	
	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
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
