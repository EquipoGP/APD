package data;

import java.util.List;

public class Nodo {

	private Identificador id;
	
	private Nodo padre;
	private List<Nodo> hijos;
	
	private List<Posicion> texto_posicion;
	
	public Nodo(Nodo padre) {
		this.padre = padre;
		this.hijos = null;
		
		this.id = new Identificador();
		this.id.setLabel("$");
		
		this.texto_posicion = null;
	}
	
	public Nodo(String etiq, List<Nodo> hijos) {
		this.id = new Identificador();
		this.id.setLabel(etiq);
		
		this.hijos = hijos;
		this.texto_posicion = null;
	}

	public String getLabel() {
		return this.id.getLabel();
	}

	public void setLabel(String etiq) {
		this.id.setLabel(etiq);
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
	
	public List<Posicion> getTextos() {
		return texto_posicion;
	}

	public void setTextos(List<Posicion> textos) {
		this.texto_posicion = textos;
	}
	
	public Identificador getId() {
		return id;
	}

	public void setId(Identificador id) {
		this.id = id;
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
