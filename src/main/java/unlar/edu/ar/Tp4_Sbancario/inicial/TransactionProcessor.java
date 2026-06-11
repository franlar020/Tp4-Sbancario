package unlar.edu.ar.Tp4_Sbancario.inicial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionProcessor {

    public Transaction buscarPorId(List<Transaction> transacciones, Long id) {
        // A IMPLEMENTAR: búsqueda lineal O(n)
        return null;
    }

    public Transaction buscarPorIdBinario(List<Transaction> transacciones, Long id) {
        // A IMPLEMENTAR: búsqueda binaria O(log n)
        return null;
    }

    public List<Transaction> buscarPorMonto(List<Transaction> transacciones,
                                            double montoMin, double montoMax) {
        // A IMPLEMENTAR: filtro lineal por rango de monto
        return new ArrayList<>();
    }

    public void ordenarManual(List<Transaction> transacciones) {
        // A IMPLEMENTAR: Bubble Sort con conteo de comparaciones e intercambios
    }


    public void ordenarBuiltIn(List<Transaction> transacciones) {
        // A IMPLEMENTAR: List.sort() con medición de tiempo
    }

    public List<Transaction> generarDatos(int cantidad) {
        List<Transaction> lista = new ArrayList<>();
        Random random = new Random(42);
        String[] tipos = {"DEPOSITO", "RETIRO", "TRANSFERENCIA", "PAGO"};

        for (int i = 0; i < cantidad; i++) {
            lista.add(new Transaction(
                (long) i,
                tipos[random.nextInt(tipos.length)],
                Math.round(random.nextDouble() * 100_000.0 * 100.0) / 100.0,
                "2024-0" + (random.nextInt(9) + 1) + "-" + (random.nextInt(28) + 1),
                "Transacción #" + i
            ));
        }
        return lista;
    }

    public void imprimirTabla(int[] tamanos, long[] tiemposLineal, long[] tiemposBinario,
                                long[] tiemposBubble, long[] tiemposBuiltIn) {
        String linea = "+------------------+---------------------+---------------------+" +
                        "------------------+------------------+";
        System.out.println(linea);
        System.out.printf("| %-16s | %-19s | %-19s | %-16s | %-16s |%n",
                "Tamaño entrada", "Búsqueda Lineal(ns)", "Búsqueda Binaria(ns)",
                "Bubble Sort(ns)", "Built-in Sort(ns)");
        System.out.println(linea);
        for (int i = 0; i < tamanos.length; i++) {
            System.out.printf("| %-16d | %19d | %19d | %16d | %16d |%n",
                    tamanos[i], tiemposLineal[i], tiemposBinario[i],
                    tiemposBubble[i], tiemposBuiltIn[i]);
        }
        System.out.println(linea);
    }
}