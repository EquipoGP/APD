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

public class SuffixTreePruebas {

	/*
	 * Clase para las pruebas del arbol de sufijos compacto
	 */

	private static final int MAX_PATRON = 100;
	private static final int MAX_CHARS = 10000000;
	private static final int MAX_TEXTOS = 50;

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Cargando archivos...");

		// chequear la validez de la llamada
		if (args.length == 0) {
			System.err.println("Uso incorrecto: SuffixTreePruebas (<directorio|fichero>)*");
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

		int i = 1;
		for (File f : files) {
			System.out.println("Archivo " + i + " de " + files.size() + ": " + f.getPath());
			pruebas(f);
			i++;
		}

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

		while (!textosTotal.isEmpty()) {
			List<String> textos = new LinkedList<String>();

			int num_elems = 0;
			if (getChars(textosTotal) > MAX_CHARS) {
				num_elems = MAX_TEXTOS;
			} else {
				num_elems = textosTotal.size();
			}

			for (int i = 0; i < num_elems; i++) {
				textos.add(textosTotal.remove(0));
			}

			Set<Character> alfabeto = alfabeto(textos);

			String patronValido = "";
			String patronInvalido = "";

			Random r = new Random();

			// patron valido
			String t = textos.get(r.nextInt(textos.size()));
			int inicio = r.nextInt(t.length() / 2);
			int fin = 1 + r.nextInt(Math.min((t.length() / 2), MAX_PATRON)) + inicio;
			patronValido = t.substring(inicio, fin);

			// patron invalido
			for (char c = 65; c < 123; c++) {
				if (c == '$') {
					; // caracter invalido en patron
				}
				if (!alfabeto.contains(c)) {
					patronInvalido = patronInvalido + c;

					// salir con probabilidad creciente cuanto mas larga sea la
					// cadena
					double probabilidad = 1.0 / (double) patronInvalido.length();
					if (r.nextDouble() > probabilidad) {
						break;
					}
				}
			}

			// cambiar tipo de los textos
			String[] txt = new String[textos.size()];
			txt = textos.toArray(txt);

			System.out.printf("\tCreando arbol compacto (%d textos)...", textos.size());
			
			long begin = System.nanoTime();
			CompactSuffixTree T = new CompactSuffixTree(txt);
			long end = System.nanoTime();
			double secondsCreacion = (end - begin) / 1000000000.0;
			
			System.out.printf("Hecho (%.2f segundos)\n", secondsCreacion);
			
			System.out.println("Patron valido: " + patronValido);
			
			// patron valido
			begin = System.nanoTime();
			List<Posicion> validas = Matching.substringMatching(T, patronValido);
			end = System.nanoTime();
			double secondsValido = (end - begin) / 1000000.0;

			System.out.printf("\tEl patron %s se encuentra en %d posiciones (%.2f segundos)\n", 
					patronValido, validas.size(), secondsValido);

			// patron invalido
			begin = System.nanoTime();
			List<Posicion> invalidas = Matching.substringMatching(T, patronInvalido);
			end = System.nanoTime();
			double secondsInvalido = (end - begin) / 1000000.0;

			System.out.printf("\tEl patron %s se encuentra en %d posiciones (%.2f segundos)\n", 
					patronInvalido, invalidas.size(), secondsInvalido);
		}
	}

	/**
	 * 
	 * @param f
	 * @param fasta
	 * @return
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
	 * 
	 * @param textos
	 * @return
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
	 * 
	 * @param txt
	 * @return
	 */
	private static int getChars(List<String> txt){
		int n = 0;
		for(String t : txt){
			n += t.length();
		}
		return n;
	}
}
