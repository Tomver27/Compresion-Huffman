/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 * Clase para construir el árbol binario
 *
 * @author Nicolás Moreno
 * @author Julián Nova
 * @author Felipe Triviño
 * @author Tomás Vera
 */
public class ArbolBinario {

    public ArbolBinario(int key, int val) {
        this.put(key, val);
    }

    public ArbolBinario() {
    }

    private Node root;

    //Clase anidada del nodo
    private class Node {

        private int key;
        private int val;
        private Node left, right;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * Insertar una llave y un valor nuevo en el arbol
     *
     * @param key Llave única para los caracteres
     * @param val Valor de recurrencia
     */
    public void put(int key, int val) {
        if (root == null) {
            // Si el árbol está vacío, el primer put establece la raíz
            root = new Node(key, val);
        } else {
            // Si la raíz ya está establecida, se agregan los hijos
            put(root, key, val);
        }
    }

    /**
     * Insertar una llave y un valor nuevo en el arbol de manera recursiva
     *
     * @param x Nodo para direccionar
     * @param key Llave única para los caracteres
     * @param val Valor de recurrencia
     */
    private void put(Node x, int key, int val) {
        if (x.left == null) {
            x.left = new Node(key, val); // Agregar al lado izquierdo
        } else if (x.right == null) {
            x.right = new Node(key, val); // Agregar al lado derecho
        } else {
            System.out.println("Ambos hijos ya están ocupados"); // Si ya hay hijos izquierdo y derecho
        }
    }

    /**
     * Insertar un nodo nuevo en el arbol
     *
     * @param nuevoNodo Nodo a insertar
     */
    public void put(Node nuevoNodo) {
        if (root == null) {
            root = nuevoNodo; // Si no hay raíz, el nodo dado se convierte en raíz
        } else {
            put(root, nuevoNodo);
        }
    }

    /**
     * Insertar un nodo nuevo en el arbol de manera recursiva
     *
     * @param actual Nodo para direccionar
     * @param nuevoNodo Nodo a insertar
     */
    private void put(Node actual, Node nuevoNodo) {
        if (actual.left == null) {
            actual.left = nuevoNodo; // Insertar como hijo izquierdo
        } else if (actual.right == null) {
            actual.right = nuevoNodo; // Insertar como hijo derecho
        } else {
            System.out.println("Ambos hijos ya están ocupados"); // Si ya tiene ambos hijos
        }
    }

    /**
     * Obtener el código a partir del arbol formado
     *
     * @param entrada Código que cambiará recursivamente
     * @return El código en binario
     */
    public String codigoHuf(int entrada) {
        return codigoHuf(entrada, root, "");
    }

    /**
     * Obtener el código a partir del arbol formado de manera recursiva
     *
     * @param entrada Código que cambiará recursivamente
     * @param n Nodo para direccionar
     * @param salida Código que retornará
     * @return El código en binario
     */
    private String codigoHuf(int entrada, Node n, String salida) {
        if (n == null) {
            return "";
        }
        if ((n.left == null && n.right == null) && n.key == entrada) {
            return salida;
        }
        String izq = codigoHuf(entrada, n.left, salida + "0");
        if (!izq.isEmpty()) {
            return izq;
        }

        String der = codigoHuf(entrada, n.right, salida + "1");
        if (!der.isEmpty()) {
            return der;
        }
        return "";
    }

    /**
     * Obtener el arbol como lista
     *
     * @return El arbol como lista
     */
    public ArrayList<Integer> arbolList() {
        ArrayList<Integer> lista = new ArrayList<>();
        arbolList(root, lista);
        return lista;
    }

    /**
     * Obtener el arbol como lista de manera recursiva
     *
     * @param node Nodo para direccionar
     * @param lista Lista con las llaves del arbol
     */
    private void arbolList(Node node, ArrayList<Integer> lista) {
        if (node == null) {
            return;
        }
        lista.add(node.key); // Agrega el valor del nodo actual
        arbolList(node.left, lista); // Recorre el hijo izquierdo
        arbolList(node.right, lista); // Recorre el hijo derecho
    }

    /**
     *
     * @return La llave de la raíz
     */
    public int rootKey() {
        return root != null ? root.key : -1;
    }

    /**
     *
     * @return El valor de la raíz
     */
    public int rootVal() {
        return root != null ? root.val : -1;
    }

    /**
     *
     * @return La raíz como nodo
     */
    public Node root() {
        return root;
    }

    /**
     * Recorrer el arbol en preorder
     */
    public void recorridoPreOrderRecursivo() {
        recorridoPreOrderRecursivo(root);
    }

    /**
     * Recorrer el arbol en preorder de manera recursiva
     *
     * @param node Nodo para direccionar
     */
    private void recorridoPreOrderRecursivo(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.key + " ");
        recorridoPreOrderRecursivo(node.left);
        recorridoPreOrderRecursivo(node.right);
    }

    /**
     * Recorrer el arbol con sus hijos en específico de cada nodo
     */
    public void imprimirArbol() {
        imprimirArbol(root);
    }

    /**
     * Recorrer el arbol de manera recursiva
     * @param node Nodo para direccionar
     */
    private void imprimirArbol(Node node) {
        if (node == null) {
            return;
        }
        System.out.println("Clave: " + node.key + ", Valor: " + node.val);
        if (node.left != null) {
            System.out.println("    Hijo izquierdo: " + node.left.key + "-" + String.valueOf((char) (int) node.left.key));
        } else {
            System.out.println("    Hijo izquierdo: null");
        }
        if (node.right != null) {
            System.out.println("    Hijo derecho: " + node.right.key + "-" + String.valueOf((char) (int) node.right.key));
        } else {
            System.out.println("    Hijo derecho: null");
        }
        imprimirArbol(node.left);
        imprimirArbol(node.right);
    }

}
