/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista;

import modelo.ArbolBinario;
import java.io.*;
import java.util.*;

/**
 * Compresión mediante el algoritmo de Huffman
 *
 * @author Nicolás Moreno
 * @author Julián Nova
 * @author Felipe Triviño
 * @author Tomás Vera
 */
public class InterfazApp {

    public static void main(String[] args) {
//        String entrada = "data/anitaPatina";
        String entrada = "C:\\\\Users\\\\tomas\\\\Downloads\\\\entrada.mp4";
        readFile(entrada);
    }

    /**
     * Lee el archivo y lo convierte en .pkz
     *
     * @param File Ruta de lectura
     */
    public static void readFile(String File) {
        try {

            //Input de lectura del txt
            InputStream input = new BufferedInputStream(new FileInputStream(File));
            //Output de escritura del .pkz
            OutputStream output = new BufferedOutputStream(new FileOutputStream(File.replaceAll("entrada","salida") + ".pkz"));
            //Inicialización de Variables
            System.out.println("Comprimiendo el archivo...");
            String entrada = "", binario = "";
            int BS, NF;
            HashMap<Integer, Integer> mapa = new HashMap<>();
            ArrayList<Integer> arrayArbol;
            ArbolBinario arbol;
            int ascii = input.read();

            /**
             * Ciclo de lectura hasta que no hayan más caracteres
             */
            while (ascii != -1) {
                //Se llena la tabla hash con el número de veces que se repite el byte
                if (mapa.containsKey(ascii)) {
                    mapa.put(ascii, mapa.get(ascii) + 1);
                } else {
                    mapa.putIfAbsent(ascii, 1);
                }
                //Se llena la entrada para pasarla a binario
                entrada += String.valueOf((char) (int) ascii);
                ascii = input.read();
            }
            //Ordenamiento de la tabla hash por frecuencia y ascii
            List<Map.Entry<Integer, Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());
            listaOrdenada.sort(Map.Entry.comparingByKey());
            listaOrdenada.sort(Map.Entry.comparingByValue());
            //Se crea el arbol de frecuencia
            arbol = crearArbol(listaOrdenada).get(0);
            //Calcular Nodos Frecuencia
            NF = arbol.NF();
            System.out.println("NF: " + NF);
            System.out.println("-------------------------------------");
            output.write((byte) NF);
            arrayArbol = arbol.arbolList();

            for (int i = 0; i < arrayArbol.size(); i++) {

                if (arrayArbol.get(i).equals(-1)) {
                    System.out.println(0);
                    System.out.println(0);
                    output.write((byte) 0);
                    output.write((byte) 0);
                } else {
                    System.out.println(1);
                    System.out.println(arrayArbol.get(i));
                    output.write((byte) 1);
                    output.write((byte) (int) arrayArbol.get(i));
                }
            }

            //Pasa a binario la entrada en orden
            for (int i = 0; i < entrada.length(); i++) {
                binario += arbol.codigoHuf((int) entrada.charAt(i));
            }
            BS = binario.length() % 8;
            System.out.println("-------------------------------------");
            System.out.println("BS: " + BS);
            output.write((byte) BS);
            for (int i = 0; i < 8 - BS; i++) {
                binario += "0";
            }
            System.out.println(binario);
            for (int i = 0; i < binario.length() - 7; i += 8) {
                    System.out.println(binario.substring(i, i + 8));
                    System.out.println((int) Integer.parseInt(binario.substring(i, i + 8), 2));
                    output.write((byte) Integer.parseInt(binario.substring(i, i + 8), 2));
            }
            output.flush();
        } catch (IOException e) {
        }

    }

    /**
     * Crea el arbol de Huffman a partir de una lista con la tabla de
     * frecuencias
     *
     * @param listaOrdenada lista con los valores de llave (ascii) y valor
     * (frecuencia)
     * @return El arbol mediante una lista
     */
    private static ArrayList<ArbolBinario> crearArbol(List<Map.Entry<Integer, Integer>> listaOrdenada) {
        //Lista de tipo arbol donde se guardan los arboles
        ArrayList<ArbolBinario> listArbol = new ArrayList();
        //Llenar el arbol  base de enteros iniciales
        for (Map.Entry<Integer, Integer> entrada : listaOrdenada) {
            Integer llave = entrada.getKey();
            Integer valor = entrada.getValue();
            listArbol.add(new ArbolBinario((int) llave, (int) valor));
        }
        //Recorrer lista ordenada de la tabla de frecuencias
        while (listArbol.size() != 1) {
            //Arbol Usado para ingresarlo a la lista
            ArbolBinario suma = new ArbolBinario();
            //Obtener dos entradas de la lista para recibir de esa forma el nodo completo
            ArbolBinario entradaizq = listArbol.get(0);
            ArbolBinario entradader = listArbol.get(1);
            //Ingresar los nodos del arreglo como la root, es un valor y no un 
            // byte dentro de la lectura
            suma.put(-1, (int) (entradaizq.rootVal() + entradader.rootVal()));
            //Dependiendo de si son valores o ya resultados que dan un nodo sin hijos
            //Se ingresa en orden
            if (entradaizq.rootKey() == -1 && entradader.rootKey() == -1) {
                suma.put(entradaizq.root());
                suma.put(entradader.root());
            } else if (entradaizq.rootKey() == -1 && entradader.rootKey() != -1) {
                suma.put(entradader.root());
                suma.put(entradaizq.root());
            } else {
                suma.put(entradaizq.root());
                suma.put(entradader.root());
            }
            //Eliminar los dos nodos que se acaban de agrupar
            listArbol.remove(listArbol.get(0));
            listArbol.remove(listArbol.get(0));
            //Se inserta el nodo dentro de la lista del arbol
            listArbol = recorrerListAr(listArbol, suma);
        }
        return listArbol;
    }

    /**
     * Agrega a la lista del arbool binario un arbol en específico
     *
     * @param listArbol Lista de tipo arbol
     * @param suma Arbol binario que será insertado
     * @return nueva lista de tipo arbol con el arbol insertado
     */
    private static ArrayList recorrerListAr(ArrayList<ArbolBinario> listArbol, ArbolBinario suma) {
        for (ArbolBinario arbol : listArbol) {
            if (suma.rootVal() < arbol.rootVal()) {
                listArbol.add(listArbol.indexOf(arbol), suma);
                return listArbol;
            }
        }
        listArbol.add(listArbol.size(), suma);
        return listArbol;
    }

}
