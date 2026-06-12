package unlar.edu.ar.Tp4_Sbancario.solucion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unlar.edu.ar.Tp4_Sbancario.solucion.report.PerformanceReport;


@Component
public class MainRefactorizado implements CommandLineRunner {

    private final PerformanceReport performanceReport;

    public MainRefactorizado(PerformanceReport performanceReport) {
        this.performanceReport = performanceReport;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== TP4 - Sistema de Analisis de Transacciones Bancarias ===\n");
        performanceReport.generarReporteCompleto();
        System.out.println("\n=== Fin del reporte ===");
    }
}