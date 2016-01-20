/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CompactSuffixTree {
	
	/*
	 * Clase para representar el arbol de sufijos compacto
	 */
	
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
	
	/* atributos privados */
	private Nodo raiz;
	private int alfabeto;
	private String[] textos;
	
	/**
	 * Crea un arbol de sufijos compacto a partir de los textos de entrada
	 * @param textos textos de entrada
	 */
	public CompactSuffixTree(String[] textos){
		alfabeto = alfabeto(textos);
		this.textos = textos;
		
		suffixTree(textos);
		compactar(textos);
	}
	
	/**
	 * @return nodo raiz del arbol
	 */
	public Nodo getRaiz() {
		return raiz;
	}
	
	/**
	 * @param textos textos sobre los que crear el arbol de sufijos compacto
	 * @return numero de caracteres total de los textos
	 */
	private int totalCaracteres(String[] textos) {
		int numCaracteres = 0;
		for (String s : textos) {
			numCaracteres += s.length();
		}
		return numCaracteres;
	}
	
	/**
	 * @param textos textos sobre los que crear el arbol de sufijos compacto
	 * @return numero de caracteres distintos en el texto
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
	 * Crea un arbol de sufijos (sin compactar)
	 * @param textos textos sobre los que crear el arbol de sufijos
	 */
	private void suffixTree(String[] textos){
		/* creacion del nodo raiz */
		raiz = new Nodo(null);
		raiz.setLabel("R");
		raiz.setHijos(new LinkedList<Nodo>());
		
		for(int i = 0; i < textos.length; i++){
			String text = textos[i];
			
			// obtener sufijos de los textos
			for(int j = 0; j < text.length(); j++){
				String partial = text.substring(j, text.length());
				insertar(partial, i+1, j+1);
			}
		}
	}
	
	/**
	 * Inserta un texto @param text en el arbol de sufijos sin compactar
	 * @param text texto a insertar
	 * @param texto texto en que se encuentra el sufijo
	 * @param posicion posicion en que se encuentra el sufijo dentro de
	 * @param texto.
	 */
	private void insertar(String text, int texto, int posicion){
		Nodo inicio = raiz;
		
		for(int j = 0; j < text.length(); j++){
			Nodo x = null;
			// buscar texto en el arbol
			if(inicio.getHijos() != null && !inicio.getHijos().isEmpty()){
				for(Nodo n : inicio.getHijos()){
					if(n.getLabel().equals("" + text.charAt(j))){
						x = n;
						break;
					}
				}
			}
			
			if(x != null){
				// la letra ya se encuentra en el arbol
				inicio = x;
			}
			else{
				// la letra no se encuentra en el arbol
				Nodo n = new Nodo("" + text.charAt(j), new LinkedList<Nodo>());
				n.setPadre(inicio);
				
				List<Nodo> hijos = inicio.getHijos();
				hijos.add(n);
				inicio.setHijos(hijos);
				
				inicio = n;
			}
		}
		
		// crear un nuevo nodo
		Nodo n = new Nodo(inicio);
		List<Posicion> map = n.getTextos();
		if(map == null){
			map = new LinkedList<Posicion>();
		}
		map.add(new Posicion(texto, posicion));
		n.setTextos(map);
		
		List<Nodo> hijos = inicio.getHijos();
		if(hijos == null){
			hijos = new LinkedList<Nodo>();
		}
		hijos.add(n);
		inicio.setHijos(hijos);
	}
	
	/**
	 * Realiza la compactacion del arbol
	 * @param textos textos sobre los que crear el arbol de sufijos compacto
	 */
	private void compactar(String[] textos){
		// obtiene los nodos de grado 2
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
		if(log < 1){
			log = 2.0;
		}
		for(Nodo n : nodos){
			String label = n.getLabel();
			
			/* Elimina el caracter $ de una etiqueta */
			if (label.charAt(label.length()-1) == '$') {
				label = label.substring(0,label.length()-1);
				n.setLabel(label);
			}
			
			if (label.length() >= log) {
				/* Reemplaza la etiqueta por los numeros de posicion */
				int pos = pos(n);
				int depth = depth(n);
				int c1 = pos + depth;
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
	 * @return lita de todos los nodos (excepto raiz)
	 */
	private List<Nodo> nodos(){
		List<Nodo> nodos = new LinkedList<Nodo>();
		
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(raiz);
		
		// recorre el arbol
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
	 * @return nodos de grado 2
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
	 * @param nodo nodo sobre el que calcular pos
	 * @return minima etiqueta de las hojas del subarbol de raiz @param nodo
	 */
	private int pos(Nodo nodo) {
		int minEtiqueta = Integer.MAX_VALUE;
		int minTexto = Integer.MAX_VALUE;
		List<Nodo> actuales = new LinkedList<Nodo>();
		
		if (nodo.getHijos() == null) {
			actuales.add(nodo);
		}
		else {
			actuales.addAll(nodo.getHijos());
		}
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			
			/* Comprueba minEtiqueta */
			if(n.getTextos() != null){
				for (Posicion p : n.getTextos()){
					int texto = p.getTexto();
					int etiqueta = p.getPosicion();
					
					if (texto < minTexto || (texto == minTexto && etiqueta < minEtiqueta)) {
						minTexto = texto;
						minEtiqueta = etiqueta;
					}
				}
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		
		return minEtiqueta;
	}
	
	/**
	 * @param nodo nodo sobre el que calcular minTexto
	 * @return primer texto en el que aparece el patron que 
	 * comienza por @param nodo
	 */
	private int minTexto(Nodo nodo) {
		int minTexto = Integer.MAX_VALUE;
		List<Nodo> actuales = new LinkedList<Nodo>();
		actuales.add(nodo);
		
		while(!actuales.isEmpty()){
			Nodo n = actuales.remove(0);
			
			/* Comprueba minEtiqueta */
			if(n.getTextos() != null){
				for (Posicion p : n.getTextos()){
					int texto = p.getTexto();
					
					if (texto < minTexto) {
						minTexto = texto;
					}
				}
			}
			
			if(n.getHijos() != null){
				actuales.addAll(n.getHijos());
			}
		}
		
		return minTexto;
	}
	
	/**
	 * @param nodo nodo sobre el que calcular depth
	 * @return longitud del pathlabel (concatenacion 
	 * de etiquetas de los nodos que hay entre 
	 * @param nodo y la raiz) de un nodo
	 */
	private int depth(Nodo nodo) {
		Nodo actual = nodo.getPadre();
		if(actual == null || actual.equals(raiz)){
			return 0;
		}
		
		String pathlabel = "";
		
		if(actual.getId().esLabel()){
			pathlabel = actual.getLabel();
		}
		else{
			pathlabel = textos[actual.getId().getNumTexto() - 1]
					.substring(actual.getId().getPosInicio() - 1, 
							actual.getId().getPosFinal());
		}
		
		while (actual.getPadre() != null &&
				!actual.getPadre().equals(raiz)) {
			actual = actual.getPadre();
			if(actual.getId().esLabel()){
				pathlabel += actual.getLabel();
			}
			else{
				pathlabel += textos[actual.getId().getNumTexto() - 1]
						.substring(actual.getId().getPosInicio() - 1, 
								actual.getId().getPosFinal());
			}
		}
		return pathlabel.length();
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
	
	/**
	 * @param out almacena resultados intermedios
	 * @param n nodo que visitar
	 * @param lista lista donde almacenar el resultado final
	 * @return lista de los sufijos del arbol
	 */
	private List<String> toString(String out, Nodo n, List<String> lista){
		if(n.getId().esLabel()){
			out = out + n.getLabel();
		}
		else{
			int t = n.getId().getNumTexto();
			int init = n.getId().getPosInicio();
			int end = n.getId().getPosFinal();
			
			out = out + textos[t-1].substring(init-1, end);
		}
		
		if(n.getHijos() == null){
			// estamos en nodo hoja
			for (Posicion p : n.getTextos()){
				int texto = p.getTexto();
				int etiqueta = p.getPosicion();
				out = out + " ("+ texto + ", " + etiqueta + ")";
				
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
