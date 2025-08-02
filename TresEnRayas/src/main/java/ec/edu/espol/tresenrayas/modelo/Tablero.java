/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.tresenrayas.modelo;

/**
 *
 * @author Victor
 */
public class Tablero {
    private char[][] celdas; //Declarar una matriz 3x3 para representar las casillas  del tablero

    public Tablero() {
        celdas = new char[3][3];
        limpiar();
    }

    public void limpiar() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                celdas[i][j] = ' ';
    }
    //Método que valida 
    public boolean colocarFicha(int fila, int col, char ficha) { 
        if (fila < 0 || fila >= 3 || col < 0 || col >= 3) return false;
        if (celdas[fila][col] != ' ') return false;
        celdas[fila][col] = ficha;
        return true;
    }

    public boolean estaCompleto() {
        for (char[] fila : celdas)
            for (char celda : fila)
                if (celda == ' ') return false;
        return true;
    }

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

        return ' '; // Sin ganador
    }

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
}