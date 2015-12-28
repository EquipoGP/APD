package data;

public class Literal {

	private boolean negado = false;
	private Variable var = null;
	
	public Literal(boolean negado, Variable v){
		this.negado = negado;
		this.var = v;
	}
	
	public boolean getValor(){
		if(negado){
			return !var.getValor();
		}
		else{
			return var.getValor();
		}
	}
	
	public boolean negado(){
		return negado;
	}
	
	@Override
	public String toString() {
		String resp = "";
		if(negado){
			resp = "-";
		}
		resp = resp + var.getNombre();
		return resp;
	}
}
