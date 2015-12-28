package data;

import java.util.LinkedList;
import java.util.List;

public class Clausula {
	private List<Literal> literales;
	
	public Clausula(){
		literales = new LinkedList<Literal>();
	}
	
	public void addLiteral(Literal lit){
		literales.add(lit);
	}
	
	public boolean getValor(){
		boolean resultado = false;
		if(literales.isEmpty()){
			resultado = true;
		}
		else{
			resultado = literales.get(0).getValor();
			for(Literal l : literales){
				resultado = resultado || l.getValor();
				if(resultado) break;
			}
		}
		return resultado;
	}
	
	public List<Literal> getLiterales(){
		return literales;
	}
	
	@Override
	public String toString() {
		String resp = "(";
		for(Literal l : literales){
			resp = resp + l.toString() + " + ";
		}
		resp = resp.substring(0, resp.length()-3);
		resp = resp + ")";
		return resp;
	}
}
