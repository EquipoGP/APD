package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompactSuffixTree {
	
	/**
	 * Para representar las etiquetas comprimidas se optado por:
	 * 
	 * 		(numTexto, posInicialOcurrencia, posFinalOcurrencia)
	 * 
	 * de esta forma el usuario puede observar facilmente donde 
	 * se encuentra el match del patron en alguno de los textos
	 * solicitados
	 * 
	 */
	
	private Nodo raiz;
	private int alfabeto;
	private String[] textos;
	
	/**
	 * 
	 * @param textos
	 */
	public CompactSuffixTree(String[] textos){
		alfabeto = alfabeto(textos);
		this.textos = textos;
		
		suffixTree(textos);
		compactar(textos);
	}
	
	public Nodo getRaiz() {
		return raiz;
	}
	
	/**
	 * 
	 * @param textos
	 * @return
	 */
	private int totalCaracteres(String[] textos) {
		int numCaracteres = 0;
		for (String s : textos) {
			numCaracteres += s.length();
		}
		return numCaracteres;
	}
	
	/**
	 * 
	 * @param textos
	 * @return
	 */
	private int alfabeto(String[] textos){
		String t = "";
		for(String txt : textos){
			t = t + txt;
		}
		Set<Character> chars = new HashSet<Character>();
		for(int i = 0; i < t.length(); i++){
			char c = t.charAt(i);
			chars.add(c);
		}
		
		return chars.size();
	}
	
	/**
	 * 
	 * @param textos
	 */
	private void suffixTree(String[] textos){
		raiz = new Nodo(null);
		raiz.setLabel("R");
		raiz.setHijos(new LinkedList<Nodo>());
		
		for(int i = 0; i < textos.length; i++){
			String text = textos[i];
			
			// cadenas
			for(int j = 0; j < text.length(); j++){
				String partial = text.substring(j, text.length());
				insertar(partial, i+1, j+1);
			}
		}
	}
	
	/**
	 * 
	 * @param text
	 * @param texto
	 * @param posicion
	 */
	private void insertar(String text, int texto, int posicion){
		Nodo inicio = raiz;
		
		for(int j = 0; j < text.length(); j++){	
			Nodo x = null;
			if(inicio.getHijos() != null && !inicio.getHijos().isEmpty()){
				for(Nodo n : inicio.getHijos()){
					if(n.getLabel().equals("" + text.charAt(j))){
						x = n;
						break;
					}
				}
			}
			
			if(x != null){
				inicio = x;
			}
			else{
				Nodo n = new Nodo("" + text.charAt(j), new LinkedList<Nodo>());
				n.setPadre(inicio);
				
				List<Nodo> hijos = inicio.getHijos();
				hijos.add(n);
				inicio.setHijos(hijos);
				
				inicio = n;
			}
		}
		
		Nodo n = new Nodo(inicio);
		Map<Integer, Integer> map = n.getTextos();
		if(map == null){
			map = new HashMap<Integer, Integer>();
		}
		map.put(texto, posicion);
		n.setTextos(map);
		
		List<Nodo> hijos = inicio.getHijos();
		if(hijos == null){
			hijos = new LinkedList<Nodo>();
		}
		hijos.add(n);
		inicio.setHijos(hijos);
	}
	
	/**
	 * 
	 * @param textos
	 */
	private void compactar(String[] textos){
		List<Nodo> grado2 = elemsGrado2();
		
		/* Elimina los nodos de grado 2 */
		for(Nodo n : grado2){
			Nodo hijo = n.getHijos().get(0);
			Nodo padre = n.getPadre();
			
			hijo.setLabel(n.getLabel() + hijo.getLabel());
			hijo.setPadre(padre);
			
			List<Nodo> hijos = padre.getHijos();
			hijos.remove(n);
			hijos.add(hijo);
			padre.setHijos(hijos);			
		}
		
		/* Comprime las etiquetas largas */
		List<Nodo> nodos = nodos();
		double log = Math.log(totalCaracteres(textos) - alfabeto);
		for(Nodo n : nodos){
			String label = n.getLabel();
			if (label.length() >= log) {
				
				/* Reemplaza la etiqueta por los numeros de posicion */
				int c1 = pos(n) + depth(n);
				int c2 = c1 + label.length() - 1;
				int t = minTexto(n);
				
				Identificador id = n.getId();
				id.setLabel(null);
				
				id.setNumTexto(t);
				id.setPosInicio(c1);
				id.setPosFinal(c2);
			}
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Nodo> nodos(){
		List<Nodo> nodos = new LinkedList<Nodo>();
		
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(raiz);
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			if(!nodos.contains(n)){
				nodos.add(n);
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		return nodos;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Nodo> elemsGrado2(){
		List<Nodo> nodos = new LinkedList<Nodo>();
		
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(raiz);
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			if(n.getGrado() == 2){
				nodos.add(n);
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		return nodos;
	}
	
	/**
	 * 
	 * @param nodo
	 * @return
	 */
	private int pos(Nodo nodo) {
		int minEtiqueta = Integer.MAX_VALUE;
		int minTexto = Integer.MAX_VALUE;
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(raiz);
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			
			/* Comprueba minEtiqueta */
			for (Map.Entry<Integer, Integer> entry : n.getTextos().entrySet()){
				int texto = entry.getKey();
				int etiqueta = entry.getValue();
				
				if (texto < minTexto || (texto == minTexto && etiqueta < minEtiqueta)) {
					minTexto = texto;
					minEtiqueta = etiqueta;
				}
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		
		return minEtiqueta;
	}
	
	/**
	 * 
	 * @param nodo
	 * @return
	 */
	private int minTexto(Nodo nodo) {
		int minTexto = Integer.MAX_VALUE;
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(raiz);
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			
			/* Comprueba minEtiqueta */
			for (Map.Entry<Integer, Integer> entry : n.getTextos().entrySet()){
				int texto = entry.getKey();
				
				if (texto < minTexto) {
					minTexto = texto;
				}
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		
		return minTexto;
	}
	
	/**
	 * 
	 * @param nodo
	 * @return
	 */
	private int depth(Nodo nodo) {
		Nodo actual = nodo.getPadre();
		String pathlabel = actual.getLabel();
		while (actual.getPadre() != null) {
			actual = actual.getPadre();
			pathlabel += actual.getLabel();
		}
		return pathlabel.length();
	}
	
	/**
	 * 
	 */
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
	
	/**
	 * 
	 * @param out
	 * @param n
	 * @param lista
	 * @return
	 */
	private List<String> toString(String out, Nodo n, List<String> lista){
		if(n.getId().esLabel()){
			out = out + n.getLabel();
		}
		else{
			int t = n.getId().getNumTexto();
			int init = n.getId().getPosInicio();
			int end = n.getId().getPosFinal();
			
			out = out + textos[t-1].substring(init-1, end-1);
		}
		
		if(n.getHijos() == null){
			Map<Integer, Integer> map = n.getTextos();
			for (Map.Entry<Integer, Integer> entry : map.entrySet()){
				out = out + " ("+ entry.getKey() + ", " + entry.getValue() + ")";
			}
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
