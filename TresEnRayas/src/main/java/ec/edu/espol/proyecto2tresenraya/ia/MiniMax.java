package ec.edu.espol.proyecto2tresenraya.ia;

import ec.edu.espol.proyecto2tresenraya.modelo.NodoArbol;
import ec.edu.espol.proyecto2tresenraya.modelo.Tablero;

/**
 * Minimax con poda alfa-beta, con comprobaciones explícitas de
 * victoria inmediata y bloqueo inmediato.
 */
public class MiniMax {

    // Profundidad estándar (puedes ajustarla)
    private static final int PROFUNDIDAD = 5;

    // Método principal para obtener la mejor jugada de la IA
    public static int[] obtenerMejorMovimiento(Tablero tablero, char fichaIA, char fichaHumano) {
        // 0) Caso especial: tablero vacío -> colocar en el centro (1,1)
        if (tableroVacio(tablero)) {
            return new int[]{1, 1};
        }

        NodoArbol raiz = new NodoArbol(tablero, fichaIA);
        // Generamos hijos del root (cada hijo = una posible jugada de la IA)
        raiz.generarHijos(fichaIA, fichaHumano);

        int[] mejorMovimiento = {-1, -1};

        // 1) Buscar victoria inmediata de la IA: elegirla si existe
        for (int k = 0; k < raiz.getHijos().tamaño(); k++) {
            NodoArbol hijo = raiz.getHijos().obtener(k);
            if (hijo.getEstado().verificarGanador() == fichaIA) {
                // si alguna jugada inmediata gana, la devolvemos
                return buscarDiferencia(tablero, hijo.getEstado());
            }
        }

        // 2) Buscar si el humano tiene una victoria inmediata en el tablero actual.
        // Si existe alguna casilla donde el humano ganaría en su próximo turno,
        // la IA debe ocupar una de esas casillas (bloquear).
        boolean[][] casillasVictoriaHumano = new boolean[3][3];
        boolean existeVictoriaHumano = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero.getSimbolo(i, j) == ' ') {
                    Tablero copia = tablero.clonar();
                    copia.colocarFicha(i, j, fichaHumano);
                    if (copia.verificarGanador() == fichaHumano) {
                        casillasVictoriaHumano[i][j] = true;
                        existeVictoriaHumano = true;
                    }
                }
            }
        }

        if (existeVictoriaHumano) {
            // Intentar bloquear: elegir una jugada de la IA que ocupe alguna casilla de victoria humana
            for (int k = 0; k < raiz.getHijos().tamaño(); k++) {
                NodoArbol hijo = raiz.getHijos().obtener(k);
                int[] diff = buscarDiferencia(tablero, hijo.getEstado());
                if (diff[0] >= 0 && casillasVictoriaHumano[diff[0]][diff[1]]) {
                    return diff; // bloqueamos la victoria humana
                }
            }
            // Si no hay ningún hijo que bloquee (doble amenaza o situación imposible de bloquear),
            // seguimos al minimax normal (la heurística decidirá lo mejor dentro de lo posible).
        }

        // 3) Si no hay victoria inmediata ni bloqueo inmediato, usamos minimax con poda alfa-beta.
        int mejorValor = Integer.MIN_VALUE;
        for (int k = 0; k < raiz.getHijos().tamaño(); k++) {
            NodoArbol hijo = raiz.getHijos().obtener(k);

            // Llamamos a minimax: después de la jugada de la IA, será turno del humano -> maximizando = false
            int valor = minimax(hijo, PROFUNDIDAD, Integer.MIN_VALUE, Integer.MAX_VALUE, false, fichaIA, fichaHumano);

            int[] diff = buscarDiferencia(tablero, hijo.getEstado());

            // Si valor mejor, o en empate preferimos el centro (1,1) como desempate práctico
            if (valor > mejorValor ||
               (valor == mejorValor && esCentro(diff) && (mejorMovimiento[0] != 1 || mejorMovimiento[1] != 1))) {
                mejorValor = valor;
                mejorMovimiento = diff;
            }
        }

        return mejorMovimiento;
    }

    // minimax recursivo con poda alfa-beta
    private static int minimax(NodoArbol nodo, int profundidad, int alfa, int beta, boolean maximizando, char fichaIA, char fichaHumano) {
        char ganador = nodo.getEstado().verificarGanador();

        // Caso terminal
        if (ganador == fichaIA) return 1000;      // victoria de IA -> puntuación alta
        if (ganador == fichaHumano) return -1000; // victoria humano -> puntuación baja
        if (nodo.getEstado().estaCompleto() || profundidad == 0) {
            return evaluarUtilidad(nodo.getEstado(), fichaIA, fichaHumano);
        }

        // Generar hijos solo si no están ya generados (evita duplicación)
        if (nodo.getHijos().estaVacia()) {
            nodo.generarHijos(fichaIA, fichaHumano);
        }

        if (maximizando) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < nodo.getHijos().tamaño(); i++) {
                NodoArbol hijo = nodo.getHijos().obtener(i);
                int eval = minimax(hijo, profundidad - 1, alfa, beta, false, fichaIA, fichaHumano);
                maxEval = Math.max(maxEval, eval);
                alfa = Math.max(alfa, eval);
                if (beta <= alfa) break; // poda
            }
            nodo.setValor(maxEval);
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < nodo.getHijos().tamaño(); i++) {
                NodoArbol hijo = nodo.getHijos().obtener(i);
                int eval = minimax(hijo, profundidad - 1, alfa, beta, true, fichaIA, fichaHumano);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alfa) break; // poda
            }
            nodo.setValor(minEval);
            return minEval;
        }
    }

    // Heurística (la tuya: líneas disponibles)
    private static int evaluarUtilidad(Tablero tablero, char fichaIA, char fichaHumano) {
        int lineasIA = contarLineasDisponibles(tablero, fichaIA, fichaHumano);
        int lineasHumano = contarLineasDisponibles(tablero, fichaHumano, fichaIA);
        return lineasIA - lineasHumano;
    }

    // Cuenta líneas (tu función actual)
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
        // Diagonales
        boolean diag1 = true;
        for (int i = 0; i < 3; i++) {
            if (tablero.getSimbolo(i, i) == oponente) diag1 = false;
        }
        if (diag1) total++;
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
        return new int[]{-1, -1};
    }

    // Comprueba si el tablero está vacío
    private static boolean tableroVacio(Tablero t) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (t.getSimbolo(i, j) != ' ') return false;
        return true;
    }

    // Preferencia: centro
    private static boolean esCentro(int[] diff) {
        return diff != null && diff.length == 2 && diff[0] == 1 && diff[1] == 1;
    }
}