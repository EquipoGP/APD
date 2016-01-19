package algorithms;

import java.util.LinkedList;
import java.util.List;

import data.CompactSuffixTree;
import data.Nodo;
import data.Posicion;

public class Wow {
	
	public static List<Integer> stringMatching(String[] textos, String patron) {
		List<Integer> posiciones = new LinkedList<Integer>();
		CompactSuffixTree T = new CompactSuffixTree(textos);
		
		String p = patron;
		Nodo inicio = T.getRaiz();
		boolean seguir = true;
		
		while(seguir){
			List<Nodo> hijos = inicio.getHijos();
			
			if(p.isEmpty()){
				seguir = false;
				if(inicio.getTextos() != null){
					for (Posicion pos : inicio.getTextos()){
						int texto = pos.getTexto();
						int etiqueta = pos.getPosicion();
						posiciones.add(etiqueta);
					}
				}
				else{
					for(Nodo n : hijos){
						if(n.getId().esLabel() && n.getLabel().equals("")){
							if(n.getTextos() != null){
								for (Posicion pos : n.getTextos()){
									int texto = pos.getTexto();
									int etiqueta = pos.getPosicion();
									posiciones.add(etiqueta);
								}
							}
						}
					}
				}
			}
			else{
				for(Nodo n : hijos){
					String etiq = "";
					if(n.getId().esLabel()){
						etiq = n.getLabel();
					}
					else{
						int t = n.getId().getNumTexto();
						int init = n.getId().getPosInicio();
						int end = n.getId().getPosFinal();
						
						etiq = textos[t-1].substring(init-1, end);
					}
					
					int np = p.length();
					int ne = etiq.length();
					int min = Math.min(np, ne);
					
					String pp = p.substring(0, min);
					String ee = etiq.substring(0, min);
					
					if(pp.equals(ee)){
						inicio = n;
						p = p.substring(min);
						break;
					}
				}
			}
		}
		
		return posiciones;
	}

}
