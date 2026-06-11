package unlar.edu.ar.Tp4_Sbancario.inicial;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /** Tamaños de entrada a evaluar según la consigna. */
    private static final int[] TAMANOS = {100, 1_000, 10_000, 100_000};

    public static void main(String[] args) {
        TransactionProcessor processor = new TransactionProcessor();

        long[] tiemposLineal  = new long[TAMANOS.length];
        long[] tiemposBinario = new long[TAMANOS.length];
        long[] tiemposBubble  = new long[TAMANOS.length];
        long[] tiemposBuiltIn = new long[TAMANOS.length];

        for (int i = 0; i < TAMANOS.length; i++) {
            int n = TAMANOS[i];
            List<Transaction> datos = processor.generarDatos(n);

            // --- Búsqueda lineal ---
            processor.ordenarBuiltIn(new ArrayList<>(datos)); // warm-up JVM
            long inicio = System.nanoTime();
            processor.buscarPorId(new ArrayList<>(datos), (long) (n / 2));
            tiemposLineal[i] = System.nanoTime() - inicio;

            // --- Búsqueda binaria (requiere lista ordenada) ---
            List<Transaction> datosOrdenados = new ArrayList<>(datos);
            processor.ordenarBuiltIn(datosOrdenados);
            processor.buscarPorIdBinario(datosOrdenados, (long) (n / 2)); // warm-up
            inicio = System.nanoTime();
            processor.buscarPorIdBinario(new ArrayList<>(datosOrdenados), (long) (n / 2));
            tiemposBinario[i] = System.nanoTime() - inicio;

            // --- Bubble Sort ---
            processor.ordenarManual(new ArrayList<>(datos)); // warm-up
            inicio = System.nanoTime();
            processor.ordenarManual(new ArrayList<>(datos));
            tiemposBubble[i] = System.nanoTime() - inicio;

            // --- Built-in Sort ---
            processor.ordenarBuiltIn(new ArrayList<>(datos)); // warm-up
            inicio = System.nanoTime();
            processor.ordenarBuiltIn(new ArrayList<>(datos));
            tiemposBuiltIn[i] = System.nanoTime() - inicio;
        }

        processor.imprimirTabla(TAMANOS, tiemposLineal, tiemposBinario,
                                tiemposBubble, tiemposBuiltIn);
    }
}