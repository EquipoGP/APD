/*
 * Patricia Lazaro Tello (554309)
 * Alejandro Royo Amondarain (560285)
 */

package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import algorithms.Matching;
import data.CompactSuffixTree;
import data.Posicion;

public class SuffixTreePruebasEficiencia {

	/*
	 * Clase para las pruebas de eficiencia del arbol de sufijos compacto
	 */

	private static final int MAX_PATRON = 10;
	private static final int MAX_CHARS = 500;
	
	private static int numValidos, numInvalidos, numTrees;
	private static long miliValidos, miliInvalidos, miliTrees;

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Cargando archivos...");

		// chequear la validez de la llamada
		if (args.length == 0) {
			System.err.println("Uso incorrecto: SuffixTreePruebasEficiencia (<directorio|fichero>)+");
			System.exit(1);
		}

		List<File> files = new LinkedList<File>();
		for (String s : args) {
			File f = new File(s);

			// chequear que existe el fichero
			if (!f.exists()) {
				System.err.println("Fichero no existente: " + s);
				System.exit(1);
			}

			List<File> filesS = getFilesFromFile(f);
			files.addAll(filesS);
		}

		System.out.println("Archivos cargados: " + files.size());
		System.out.println("Comenzando pruebas...");
		
		/* inicializar variables */
		numTrees = 0;
		numValidos = 0;
		numInvalidos = 0;
		
		miliTrees = 0;
		miliValidos = 0;
		miliInvalidos = 0;
		/* fin inicializar variables */

		int i = 1;
		for (File f : files) {
			System.out.println("Archivo " + i + " de " + files.size() + ": " + f.getPath());
			pruebas(f);
			i++;
		}
		
		double mediaTree = (double) miliTrees / (double) numTrees;
		mediaTree = mediaTree / 1000000000.0;
		System.out.printf("Tiempo medio para la creacion del arbol: %.2f segundos\n", mediaTree);
		
		double mediaValido = (double) miliValidos / (double) numValidos;
		mediaValido = mediaValido / 1000000000.0;
		System.out.printf("Tiempo medio para la busqueda de un patron valido: %.2f segundos\n", mediaValido);
		
		double mediaInvalido = (double) miliInvalidos / (double) numInvalidos;
		mediaInvalido = mediaInvalido / 1000000.0;
		System.out.printf("Tiempo medio para la busqueda un patron invalido: %.2f milisegundos\n", mediaInvalido);

		System.out.println("Pruebas finalizadas.");
	}

	/**
	 * @param f
	 *            fichero del que obtener todos los ficheros
	 * @return una lista con los ficheros normales contenidos en @param f
	 */
	private static List<File> getFilesFromFile(File f) {
		List<File> files = new LinkedList<File>();

		List<File> actual = new LinkedList<File>();
		actual.add(f);
		while (!actual.isEmpty()) {
			File ff = actual.remove(0);
			if (ff.isFile()) {
				// introducir fichero
				files.add(ff);
			} else if (ff.isDirectory()) {
				// introducir ficheros del directorio
				File[] ffs = f.listFiles();
				for (int i = 0; i < ffs.length; i++) {
					actual.add(ffs[i]);
				}
			}
		}
		return files;
	}

	/**
	 * @param f
	 *            fichero
	 * @throws FileNotFoundException
	 */
	private static void pruebas(File f) throws FileNotFoundException {
		boolean fasta = f.getName().endsWith(".fasta");
		
		System.out.printf("\tEscaneando...");
		List<String> textosTotal = escanear(f, fasta);
		System.out.println(" Hecho");

		System.out.print("\tTrabajando...");
		while (!textosTotal.isEmpty()) {
			List<String> textos = new LinkedList<String>();

			int num_elems = getTextos(textosTotal);

			for (int i = 0; i < num_elems; i++) {
				textos.add(textosTotal.remove(0));
			}
			pruebas(textos);
		}
		System.out.println(" Hecho");
	}
	
	public static String pad(int i){
	    String s = ""+i;
	    if(s.length()==1){
	      s += "  ";
	    }
	    else if(s.length()==2){
	      s += " ";
	    }
	    return s;
	  }
	
	/**
	 * @param textos lista de textos sobre los que realizar las pruebas
	 */
	private static void pruebas(List<String> textos){
		CompactSuffixTree T = null;
		Set<Character> alfabeto = alfabeto(textos);

		String patronValido = "";
		String patronInvalido = "";

		Random r = new Random();

		// patron valido
		patronValido = patronValido(textos, r);

		// patron invalido
		patronInvalido = patronInvalido(alfabeto, r);

		// cambiar tipo de los textos
		String[] txt = new String[textos.size()];
		txt = textos.toArray(txt);

		long begin = System.nanoTime();
		T = new CompactSuffixTree(txt);
		long end = System.nanoTime();
		
		numTrees++;
		miliTrees += (end - begin);
		
		// patron valido
		begin = System.nanoTime();
		Set<Posicion> validas = Matching.substringMatching(T, patronValido);
		end = System.nanoTime();
		
		if(validas.size() == 0){
			System.err.println("Se ha producido un fallo. Saliendo del programa...");
			System.exit(1);
		}
		
		numValidos++;
		miliValidos += (end - begin);

		// patron invalido
		begin = System.nanoTime();
		Set<Posicion> invalidas = Matching.substringMatching(T, patronInvalido);
		end = System.nanoTime();
		
		if(invalidas.size() > 0){
			System.err.println("Se ha producido un fallo. Saliendo del programa...");
			System.exit(1);
		}
		
		numInvalidos++;
		miliInvalidos += (end - begin);
	}

	/**
	 * @param f fichero
	 * @param fasta true si es fasta, false en caso contrario (formato del fichero)
	 * @return lista de cadenas que representan los distintos textos del fichero
	 * @throws FileNotFoundException
	 */
	private static List<String> escanear(File f, boolean fasta) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		List<String> textos = new LinkedList<String>();
		String acc = "";

		while (s.hasNextLine()) {
			String str = s.nextLine();

			if (fasta && str.startsWith(">")) {
				// no introducir las lineas de descripcion
				if (acc.length() == 0) {
					; // inicio
				} else {
					textos.add(acc);
					acc = "";
				}
			} else {
				if (!fasta) {
					textos.add(str);
				} else {
					acc = acc + str;
				}
			}
		}

		if (acc.length() > 0) {
			textos.add(acc);
		}

		s.close();
		return textos;
	}

	/**
	 * @param textos lista de textos en los que encontrar el alfabeto
	 * @return conjunto de caracteres que conforman el alfabeto de los textos
	 */
	private static Set<Character> alfabeto(List<String> textos) {
		Set<Character> alfabeto = new HashSet<Character>();

		// introducir los diferentes caracteres para cada texto
		for (String txt : textos) {
			for (int i = 0; i < txt.length(); i++) {
				alfabeto.add(txt.charAt(i));
			}
		}

		return alfabeto;
	}
	
	/**
	 * @param textos textos sobre los que crear el arbol de sufijos compacto
	 * @return numero de textos que utilizar para crear el ASC
	 */
	private static int getTextos(List<String> textos){
		int chars = 0;
		int numTextos = 0;
		
		while(chars < MAX_CHARS && numTextos < textos.size()){
			String s = textos.get(numTextos);
			chars = chars + s.length();
			numTextos++;
		}
		return numTextos;
	}
	
	/**
	 * @param textos textos sobre los que crear el patron valido
	 * @param r randomizador
	 * @return un patron valido acorde a los textos
	 */
	private static String patronValido(List<String> textos, Random r){
		String t = textos.get(r.nextInt(textos.size()));
		int inicio = r.nextInt(t.length() / 2);
		int fin = 1 + r.nextInt(Math.min((t.length() / 2), MAX_PATRON)) + inicio;
		return t.substring(inicio, fin);
	}
	
	/**
	 * @param alfabeto alfabeto utilizado en los textos
	 * @param r randomizador
	 * @return un patron invalido de acuerdo al alfabeto
	 */
	private static String patronInvalido(Set<Character> alfabeto, Random r){
		String patronInvalido = "";
		for (char c = 65; c < 123; c++) {
			if (c == '$') {
				; // caracter invalido en patron
			}
			else if (!alfabeto.contains(c)) {
				patronInvalido = patronInvalido + c;

				// salir con probabilidad creciente cuanto mas larga sea la
				// cadena
				double probabilidad = 1.0 / (double) patronInvalido.length();
				if (r.nextDouble() > probabilidad) {
					break;
				}
			}
		}
		return patronInvalido;
	}
}
