package unlar.edu.ar.Tp4_Sbancario.solucion.report;

import org.springframework.stereotype.Service;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.SearchResult;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.Transaction;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.TransactionType;
import unlar.edu.ar.Tp4_Sbancario.solucion.service.FilterService;
import unlar.edu.ar.Tp4_Sbancario.solucion.service.SearchService;
import unlar.edu.ar.Tp4_Sbancario.solucion.service.SortService;
import unlar.edu.ar.Tp4_Sbancario.solucion.service.SortService.SortResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Servicio encargado de generar el reporte comparativo de performance.
Recibe las tres dependencias por inyección de constructor (DIP).
*/
@Service
public class PerformanceReport {

    private static final int[] TAMANOS = {100, 1_000, 10_000, 100_000};

    private final SearchService searchService;
    private final SortService sortService;
    private final FilterService filterService;

    public PerformanceReport(SearchService searchService,
                             SortService sortService,
                             FilterService filterService) {
        this.searchService = searchService;
        this.sortService = sortService;
        this.filterService = filterService;
    }

    
    //Genera y ejecuta el reporte completo de performance para todos los tamaños definidos.
     
    public void generarReporteCompleto() {
        long[] tiemposLineal  = new long[TAMANOS.length];
        long[] tiemposBinario = new long[TAMANOS.length];
        long[] tiemposBubble  = new long[TAMANOS.length];
        long[] tiemposBuiltIn = new long[TAMANOS.length];

        for (int i = 0; i < TAMANOS.length; i++) {
            int n = TAMANOS[i];
            List<Transaction> datos = generarDatos(n);
            Long idBuscado = (long) (n / 2);

            // warm-up JVM obligatorio
            sortService.ordenarBuiltIn(new ArrayList<>(datos));
            searchService.buscarPorIdLineal(new ArrayList<>(datos), idBuscado);

            // busqueda lineal
            SearchResult lineal = searchService.buscarPorIdLineal(new ArrayList<>(datos), idBuscado);
            tiemposLineal[i] = lineal.tiempoBusquedaNs();

            // busqueda binaria (requiere lista ordenada)
            List<Transaction> datosOrdenados = sortService.ordenarBuiltIn(datos).listaOrdenada();
            searchService.buscarPorIdBinario(new ArrayList<>(datosOrdenados), idBuscado); // warm-up
            SearchResult binario = searchService.buscarPorIdBinario(new ArrayList<>(datosOrdenados), idBuscado);
            tiemposBinario[i] = binario.tiempoBusquedaNs();

            // bubble sort
            sortService.ordenarManual(new ArrayList<>(datos)); // warm-up
            SortResult bubble = sortService.ordenarManual(new ArrayList<>(datos));
            tiemposBubble[i] = bubble.tiempoOrdenamientoNs();

            // built-in sort
            sortService.ordenarBuiltIn(new ArrayList<>(datos)); // warm-up
            SortResult builtIn = sortService.ordenarBuiltIn(new ArrayList<>(datos));
            tiemposBuiltIn[i] = builtIn.tiempoOrdenamientoNs();
        }

        imprimirTabla(tiemposLineal, tiemposBinario, tiemposBubble, tiemposBuiltIn);
        imprimirDemoFiltros(generarDatos(1_000));
    }

    private void imprimirTabla(long[] lineal, long[] binario, long[] bubble, long[] builtIn) {
        String sep = "+------------------+---------------------+---------------------+------------------+------------------+";
        System.out.println(sep);
        System.out.printf("| %-16s | %-19s | %-19s | %-16s | %-16s |%n",
                "Tamaño entrada", "Búsqueda Lineal(ns)", "Búsqueda Binaria(ns)",
                "Bubble Sort(ns)", "Built-in Sort(ns)");
        System.out.println(sep);
        for (int i = 0; i < TAMANOS.length; i++) {
            System.out.printf("| %-16d | %19d | %19d | %16d | %16d |%n",
                    TAMANOS[i], lineal[i], binario[i], bubble[i], builtIn[i]);
        }
        System.out.println(sep);
    }

    private void imprimirDemoFiltros(List<Transaction> datos) {
        System.out.println("\n--- Demo FilterService (Strategy Pattern) ---");

        List<Transaction> depositos = filterService.filtrarPorTipo(datos, TransactionType.DEPOSITO);
        System.out.println("DEPOSITOS encontrados: " + depositos.size());

        List<Transaction> porMonto = filterService.filtrarPorRangoMonto(datos, 1_000.0, 50_000.0);
        System.out.println("Transacciones entre $1.000 y $50.000: " + porMonto.size());

        List<Transaction> porFecha = filterService.filtrarPorFecha(datos,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 30));
        System.out.println("Transacciones primer semestre 2024: " + porFecha.size());
    }

    /**
     * Genera transacciones con datos aleatorios para las pruebas de performance.
     *
     * @param cantidad numero de transacciones a generar
     * @return lista con {@code cantidad} transacciones
     */
    private List<Transaction> generarDatos(int cantidad) {
        List<Transaction> lista = new ArrayList<>();
        Random random = new Random(42);
        TransactionType[] tipos = TransactionType.values();

        for (int i = 0; i < cantidad; i++) {
            lista.add(new Transaction(
                    (long) i,
                    tipos[random.nextInt(tipos.length)],
                    Math.round(random.nextDouble() * 100_000.0 * 100.0) / 100.0,
                    LocalDate.of(2024, random.nextInt(12) + 1, random.nextInt(28) + 1),
                    "Transacción #" + i
            ));
        }
        return lista;
    }
}