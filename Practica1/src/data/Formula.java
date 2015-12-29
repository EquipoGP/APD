package data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Formula {

	private List<Clausula> clausulas;
	private Set<Variable> vars;
	
	public Formula(Set<Variable> vars){
		clausulas = new LinkedList<Clausula>();
		this.vars = vars;
	}
	
	public void addClausula(Clausula c){
		clausulas.add(c);
	}
	
	public void removeClausula(Clausula c){
		clausulas.remove(c);
	}
	
	public boolean getValor(){
		boolean resultado = false;
		if(clausulas.isEmpty()){
			resultado = true;
		}
		else{
			resultado = clausulas.get(0).getValor();
			for(Clausula c : clausulas){
				resultado = resultado && c.getValor();
				if(!resultado) break;
			}
		}
		return resultado;
	}
	
	public List<Clausula> getClausulas(){
		return clausulas;
	}
	
	public Set<Variable> getVariables(){
		return vars;
	}

	@Override
	public String toString() {
		String resp = "";
		for(Clausula c : clausulas){
			resp = resp + c.toString() + " * ";
		}
		return resp.substring(0, resp.length() - 2);
	}
}
