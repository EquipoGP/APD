package data;

public class Variable {

	private String nombre;
	private boolean valor;
	
	public Variable(String nombre){
		this.nombre = nombre;
		this.valor = false;
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
		this.valor = valor;
	}
}
