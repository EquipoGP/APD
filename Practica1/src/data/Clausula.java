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
	
	public boolean equals(Clausula c){
		boolean res = true;
		List<Literal> cs = c.getLiterales();
		for(Literal l : cs){
			boolean esta = false;
			for(Literal li : literales){
				if(li.equals(l)){
					esta = true;
					break;
				}
			}
			if(!esta){
				res = false;
				break;
			}
		}
		return res;
	}
	
	public boolean contains(Variable v, boolean negado){
		for(Literal l : literales){
			if(l == null){
				System.out.println("WOW");
				return false;
			}
			if(l.getVariable().getNombre().equals(v.getNombre())
					&& l.negado() == negado){
				return true;
			}
		}
		return false;
	}
}
