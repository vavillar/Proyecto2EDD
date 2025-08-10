/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.ia;

import ec.edu.espol.proyecto2tresenraya.modelo.NodoArbol;
import ec.edu.espol.proyecto2tresenraya.modelo.Tablero;

public class MinMax {

    // Método principal para obtener la mejor jugada de la IA
    public static int[] obtenerMejorMovimiento(Tablero tablero, char fichaIA, char fichaHumano) {
        // Creamos el nodo raíz con el tablero actual
        NodoArbol raiz = new NodoArbol(tablero, fichaIA);

        // Inicializamos valores para el algoritmo
        int mejorValor = Integer.MIN_VALUE; // Queremos maximizar
        int[] mejorMovimiento = {-1, -1};

        // Generamos los hijos del nodo raíz (todas las jugadas posibles)
        raiz.generarHijos(fichaIA, fichaHumano);

        // Recorremos cada posible jugada
        for (int k = 0; k < raiz.getHijos().tamaño(); k++) {
            NodoArbol hijo = raiz.getHijos().obtener(k);

            // Llamamos al minimax para evaluar esta jugada
            int valor = minimax(hijo, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, false, fichaIA, fichaHumano);

            // Guardamos la mejor jugada
            if (valor > mejorValor) {
                mejorValor = valor;

                // Buscamos dónde cambió el tablero respecto al original
                mejorMovimiento = buscarDiferencia(tablero, hijo.getEstado());
            }
        }

        return mejorMovimiento;
    }

    // Algoritmo minimax con poda alfa-beta
    private static int minimax(NodoArbol nodo, int profundidad, int alfa, int beta, boolean maximizando, char fichaIA, char fichaHumano) {
        char ganador = nodo.getEstado().verificarGanador();

        // Caso base: si hay ganador o el tablero está lleno o se llegó a la profundidad límite
        if (ganador == fichaIA) return 10;
        if (ganador == fichaHumano) return -10;
        if (nodo.getEstado().estaCompleto() || profundidad == 0) {
            return evaluarUtilidad(nodo.getEstado(), fichaIA, fichaHumano);
        }

        // Turno de la IA (maximizando)
        if (maximizando) {
            int maxEval = Integer.MIN_VALUE;
            nodo.generarHijos(fichaIA, fichaHumano);

            for (int i = 0; i < nodo.getHijos().tamaño(); i++) {
                NodoArbol hijo = nodo.getHijos().obtener(i);
                int eval = minimax(hijo, profundidad - 1, alfa, beta, false, fichaIA, fichaHumano);
                maxEval = Math.max(maxEval, eval);
                alfa = Math.max(alfa, eval);

                // Poda alfa-beta
                if (beta <= alfa) break;
            }
            nodo.setValor(maxEval);
            return maxEval;

        // Turno del humano (minimizando)
        } else {
            int minEval = Integer.MAX_VALUE;
            nodo.generarHijos(fichaIA, fichaHumano);

            for (int i = 0; i < nodo.getHijos().tamaño(); i++) {
                NodoArbol hijo = nodo.getHijos().obtener(i);
                int eval = minimax(hijo, profundidad - 1, alfa, beta, true, fichaIA, fichaHumano);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);

                // Poda alfa-beta
                if (beta <= alfa) break;
            }
            nodo.setValor(minEval);
            return minEval;
        }
    }

    // Evaluar qué tan bueno es el tablero para la IA
    private static int evaluarUtilidad(Tablero tablero, char fichaIA, char fichaHumano) {
        int lineasIA = contarLineasDisponibles(tablero, fichaIA, fichaHumano);
        int lineasHumano = contarLineasDisponibles(tablero, fichaHumano, fichaIA);
        return lineasIA - lineasHumano;
    }

    // Contar filas, columnas y diagonales donde un jugador aún puede ganar
    private static int contarLineasDisponibles(Tablero tablero, char jugador, char oponente) {
        int total = 0;

        // Filas
        for (int i = 0; i < 3; i++) {
            boolean disponible = true;
            for (int j = 0; j < 3; j++) {
                if (tablero.getSimbolo(i, j) == oponente) disponible = false;
            }
            if (disponible) total++;
        }

        // Columnas
        for (int j = 0; j < 3; j++) {
            boolean disponible = true;
            for (int i = 0; i < 3; i++) {
                if (tablero.getSimbolo(i, j) == oponente) disponible = false;
            }
            if (disponible) total++;
        }

        // Diagonal principal
        boolean diag1 = true;
        for (int i = 0; i < 3; i++) {
            if (tablero.getSimbolo(i, i) == oponente) diag1 = false;
        }
        if (diag1) total++;

        // Diagonal secundaria
        boolean diag2 = true;
        for (int i = 0; i < 3; i++) {
            if (tablero.getSimbolo(i, 2 - i) == oponente) diag2 = false;
        }
        if (diag2) total++;

        return total;
    }

    // Buscar la celda que cambió entre dos tableros
    private static int[] buscarDiferencia(Tablero original, Tablero modificado) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (original.getSimbolo(i, j) != modificado.getSimbolo(i, j)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // No se encontró diferencia
    }
}
