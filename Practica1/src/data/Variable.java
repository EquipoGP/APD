package data;

public class Variable {

	private String nombre;
	private boolean valor;
	private boolean set;
	
	public Variable(String nombre){
		this.nombre = nombre;
		this.valor = false;
		this.set = false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getValor() {
		return valor;
	}

	public void setValor(boolean valor) {
		set = true;
		this.valor = valor;
	}
	
	public boolean isSet(){
		return set;
	}
}
