/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.EDD;

/**
 *
 * @author Home
 */
public class NodoLista<T> {
    T dato;                // Dato que almacena el nodo
    NodoLista<T> siguiente; // Referencia al siguiente nodo

    public NodoLista(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
