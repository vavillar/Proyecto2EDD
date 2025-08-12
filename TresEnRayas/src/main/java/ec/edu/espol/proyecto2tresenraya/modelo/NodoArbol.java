package ec.edu.espol.proyecto2tresenraya.modelo;

/**
 *
 * @author Victor
 */


import ec.edu.espol.proyecto2tresenraya.EDD.ListaEnlazada;

public class NodoArbol {

    private Tablero estado; // Representa el tablero en este nodo
    private char jugador;   // 'X' o 'O'
    private int valor;      // Valor de evaluación
    private ListaEnlazada<NodoArbol> hijos; // Lista de hijos (nuestra propia estructura)

    // Constructor
    public NodoArbol(Tablero estado, char jugador) {
        this.estado = estado;
        this.jugador = jugador;
        this.hijos = new ListaEnlazada<>();
    }

    // Agregar hijo
    public void agregarHijo(NodoArbol hijo) {
        hijos.agregar(hijo);
    }

    // Getters y setters
    public Tablero getEstado() {
        return estado;
    }

    public char getJugador() {
        return jugador;
    }

    public void setJugador(char jugador) {
        this.jugador = jugador;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public ListaEnlazada<NodoArbol> getHijos() {
        return hijos;
    }

    public boolean esHoja() {
        return hijos.estaVacia();
    }

    // Generar hijos
    public void generarHijos(char fichaIA, char fichaHumano) {
        // Evitar regenerar hijos si ya existen (previene duplicaciones)
        if (!hijos.estaVacia()) return;

        if (estado.verificarGanador() != ' ' || estado.estaCompleto()) return;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (estado.getSimbolo(i, j) == ' ') {
                    Tablero copia = estado.clonar();
                    copia.colocarFicha(i, j, jugador);

                    char siguienteJugador = (jugador == fichaIA) ? fichaHumano : fichaIA;
                    NodoArbol hijo = new NodoArbol(copia, siguienteJugador);

                    hijos.agregar(hijo);
                }
            }
        }
    }

}