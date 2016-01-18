package data;

import java.util.LinkedList;
import java.util.List;

public class CompactSuffixTree {
	
	private Nodo raiz;
	
	public CompactSuffixTree(String[] textos){
		suffixTree(textos);
	}
	
	private void suffixTree(String[] textos){
		raiz = new Nodo();
		raiz.setCaracter('R');
		raiz.setHijos(new LinkedList<Nodo>());
		
		for(int i = 0; i < textos.length; i++){
			String text = textos[i];
			
			// cadenas
			for(int j = 0; j < text.length(); j++){
				String partial = text.substring(j, text.length());
				insertar(partial);
			}
		}
	}
	
	private void insertar(String text){
		Nodo inicio = raiz;
		
		for(int j = 0; j < text.length(); j++){	
			Nodo x = null;
			if(inicio.getHijos() != null && !inicio.getHijos().isEmpty()){
				for(Nodo n : inicio.getHijos()){
					if(n.getCaracter() == text.charAt(j)){
						x = n;
						break;
					}
				}
			}
			
			if(x != null){
				inicio = x;
			}
			else{
				Nodo n = new Nodo(text.charAt(j), new LinkedList<Nodo>());
				
				List<Nodo> hijos = inicio.getHijos();
				hijos.add(n);
				inicio.setHijos(hijos);
				
				inicio = n;
			}
		}
		
		Nodo n = new Nodo();
		List<Nodo> hijos = new LinkedList<Nodo>();
		hijos.add(n);
		inicio.setHijos(hijos);
	}
	
	@Override
	public String toString(){
		List<String> lista = new LinkedList<String>();
		
		String out = "";
		for(Nodo n : raiz.getHijos()){
			lista = toString(out, n, lista);
		}
		
		out = "";
		for(String s : lista){
			out = out + s + "\n";
		}
		return out;
	}
	
	private List<String> toString(String out, Nodo n, List<String> lista){
		out = out + n.getCaracter();
		if(n.getHijos() == null){
			lista.add(out);
		}
		else if(!n.getHijos().isEmpty()){
			for(Nodo nn : n.getHijos()){
				lista = toString(out, nn, lista);
			}
		}
		return lista;
	}
}
