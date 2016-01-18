package data;

public class Identificador {

	private int numTexto;
	private int posInicio;
	private int posFinal;
	
	private String label;
	
	public Identificador(){
		this.numTexto = -1;
		this.posInicio = -1;
		this.posFinal = -1;
		
		this.label = null;
	}

	public int getNumTexto() {
		return numTexto;
	}

	public int getPosInicio() {
		return posInicio;
	}

	public int getPosFinal() {
		return posFinal;
	}

	public String getLabel() {
		return label;
	}

	public void setNumTexto(int numTexto) {
		this.numTexto = numTexto;
	}

	public void setPosInicio(int posInicio) {
		this.posInicio = posInicio;
	}

	public void setPosFinal(int posFinal) {
		this.posFinal = posFinal;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
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
