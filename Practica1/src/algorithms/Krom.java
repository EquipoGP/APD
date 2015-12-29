package algorithms;

import java.util.List;
import java.util.Set;

import data.Clausula;
import data.Formula;
import data.Literal;
import data.Variable;

public class Krom {

	public static boolean krom(Formula f){
		boolean consistente = true;
		boolean satisfacible = false;
		Set<Variable> variables = f.getVariables();
		List<Clausula> clausulas = f.getClausulas();
		
		/* Mientras la formula es consistente y reducible se reduce */
		while(consistente && formulaReducible(f)){
			
			/* Reduccion */
			f = reducirFormula(f);
			consistente = formulaConsistente(f);
		}
		
		if (consistente) {
			
			// Construye la formula final, con clausulas del tipo:
			// (v or v) o (¬v or ¬v) para cada variable v
			for(Variable v : variables){
				
				// TODO: Solo hay que incluir una nueva clausula si no estan
				// ya ni (v or v) ni (¬v or ¬v)
				
				/* Nueva clausula (v or v) */
				Clausula clausula = new Clausula();
				clausula.addLiteral(new Literal(false, v));
				clausula.addLiteral(new Literal(false, v));
				f.addClausula(clausula);
				
				if (!formulaConsistente(f)) {
					
					// Si la formula no es consistente se incluye la
					// clausula opuesta (¬v or ¬v) y se borra (v or v)
					f.removeClausula(clausula);
					clausula = new Clausula();
					clausula.addLiteral(new Literal(true, v));
					clausula.addLiteral(new Literal(true, v));
					f.addClausula(clausula);
				}
			}
			
			/* Da valor a las variables segun las clausulas incluidas */
			for (Variable v : variables) {
				for(Clausula c : clausulas){
					
					List<Literal> literales = c.getLiterales();
					Literal l1 = literales.get(0);
					Literal l2 = literales.get(1);

					// Si aparece (v or v) se asigna true a la variable v
					if (l1.equals(l2) &&
						  l1.toString().equals(v.getNombre())){
						v.setValor(true);
						//TODO: actualizar variable en la formula
					}
					// Si aparece (¬v or ¬v) se asigna false a la variable v
					else if(l1.equals(l2) &&
						  l1.toString().equals("-" + v.getNombre())){
						v.setValor(false);
						//TODO: actualizar variable en la formula
					}
				}
			}
			
			/* Comprueba si la formula es satisfacible */
			if (!formulaSatisfacible(f)){
				
				//TODO: aplicar reduccion si es necesario y volver
				// a comprobar la consistencia de la formula
				
				consistente = formulaConsistente(f);
			}
		}
		else{
			
			// Si la formula no es consistente no es satisfacible
			satisfacible = false;
		}
		
		return satisfacible;
	}
	
	private static boolean formulaConsistente(Formula f){
		boolean consistente = true;
		Set<Variable> variables = f.getVariables();
		List<Clausula> clausulas = f.getClausulas();
		
		for (Variable v : variables) {
			int numC = 0;
			
			/* Comprueba consistencia para una variable */
			for(Clausula c : clausulas){
				
				List<Literal> literales = c.getLiterales();
				Literal l1 = literales.get(0);
				Literal l2 = literales.get(1);

				// Comprueba si aparecen a la vez las clausulas
				// (v or v) and (¬v or ¬v)
				if ( (l1.equals(l2) &&
					  l1.toString().equals(v.getNombre()))
						||
					  (l1.equals(l2) &&
					  l1.toString().equals("-" + v.getNombre()))
					) {
					
					numC++;
				}
				
				// Si numC == 2 entonces aparecen al mismo tiempo
				// (v or v) and (¬v or ¬v), es decir, f no es consistente
				consistente = (numC < 2);
				if (!consistente) break;
			}
			if (!consistente) break;
		}
		
		return consistente;
	}
	
	private static boolean formulaReducible(Formula f){
		//TODO: comprobar si formula f es reducible
	}
	
	private static Formula reducirFormula(Formula f){
		//TODO: devolver reduccion de formula f
	}
}
