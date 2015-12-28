package algorithms;

import java.util.List;

import data.Clausula;
import data.Formula;
import data.Literal;

public class Comprobaciones {
	
	public static boolean es2SAT(Formula f){
		List<Clausula> cs = f.getClausulas();
		
		boolean _2sat = true;
		for(Clausula c : cs){
			List<Literal> ls = c.getLiterales();
			if(ls != null && ls.size() > 2){
				_2sat = false;
				break;
			}
		}
		
		return _2sat;
	}
	
	public static boolean esHorn(Formula f){
		List<Clausula> cs = f.getClausulas();
		
		boolean horn = true;
		for(Clausula c : cs){
			List<Literal> ls = c.getLiterales();
			if(ls != null && !ls.isEmpty()){
				int afirmados = 0;
				for(Literal l : ls){
					if(!l.negado()){
						afirmados++;
					}
				}
				if(afirmados > 1){
					horn = false;
					break;
				}
			}
		}
		
		return horn;
	}

}
