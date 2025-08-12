/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.proyecto2tresenraya.modelo;

/**
 *
 * @author Victor
 */
public class Tablero {
    private char[][] celdas;

    public Tablero() {
        celdas = new char[3][3];
        limpiar();
    }

    // Reinicia el tablero a vacío
    public void limpiar() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                celdas[i][j] = ' ';
    }

    // Coloca una ficha si la celda está vacía
    public boolean colocarFicha(int fila, int col, char ficha) {
        if (fila < 0 || fila >= 3 || col < 0 || col >= 3) return false;
        if (celdas[fila][col] != ' ') return false;
        celdas[fila][col] = ficha;
        return true;
    }

    // Verifica si el tablero está lleno
    public boolean estaCompleto() {
        for (char[] fila : celdas)
            for (char celda : fila)
                if (celda == ' ') return false;
        return true;
    }

    // Devuelve el ganador si lo hay ('X', 'O'), o ' ' si no hay
    public char verificarGanador() {
        // Filas y columnas
        for (int i = 0; i < 3; i++) {
            if (celdas[i][0] != ' ' && celdas[i][0] == celdas[i][1] && celdas[i][1] == celdas[i][2])
                return celdas[i][0];
            if (celdas[0][i] != ' ' && celdas[0][i] == celdas[1][i] && celdas[1][i] == celdas[2][i])
                return celdas[0][i];
        }

        // Diagonales
        if (celdas[0][0] != ' ' && celdas[0][0] == celdas[1][1] && celdas[1][1] == celdas[2][2])
            return celdas[0][0];
        if (celdas[0][2] != ' ' && celdas[0][2] == celdas[1][1] && celdas[1][1] == celdas[2][0])
            return celdas[0][2];

        return ' '; // No hay ganador
    }

    // NUEVO: Obtener el símbolo en una celda específica
    public char getSimbolo(int fila, int columna) {
        if (fila < 0 || fila >= 3 || columna < 0 || columna >= 3) return ' ';
        return celdas[fila][columna];
    }

    // NUEVO: Devuelve una copia del tablero (para probar jugadas en MinMax)
    public Tablero clonar() {
        Tablero copia = new Tablero();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                copia.celdas[i][j] = this.celdas[i][j];
        return copia;
    }

    // (Opcional) Mostrar tablero en consola
    public void mostrar() {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(celdas[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("  -+-+-");
        }
    }

    public int[][] obtenerLineaGanadora() {
        // Revisar filas
        for (int i = 0; i < 3; i++) {
            if (celdas[i][0] != ' ' && celdas[i][0] == celdas[i][1] && celdas[i][1] == celdas[i][2]) {
                return new int[][] {{i,0}, {i,1}, {i,2}};
            }
        }
        // Revisar columnas
        for (int j = 0; j < 3; j++) {
            if (celdas[0][j] != ' ' && celdas[0][j] == celdas[1][j] && celdas[1][j] == celdas[2][j]) {
                return new int[][] {{0,j}, {1,j}, {2,j}};
            }
        }
        // Revisar diagonales
        if (celdas[0][0] != ' ' && celdas[0][0] == celdas[1][1] && celdas[1][1] == celdas[2][2]) {
            return new int[][] {{0,0}, {1,1}, {2,2}};
        }
        if (celdas[0][2] != ' ' && celdas[0][2] == celdas[1][1] && celdas[1][1] == celdas[2][0]) {
            return new int[][] {{0,2}, {1,1}, {2,0}};
        }
        // No hay línea ganadora
        return null;
    }

    public char[][] getCeldas() {
        return celdas;
    }

    public void setCeldas(char[][] celdas) {
        this.celdas = celdas;
    }
    
    
}