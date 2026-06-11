package unlar.edu.ar.Tp4_Sbancario.solucion.service;


import org.springframework.stereotype.Service;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.Transaction;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de {@link FilterService}.
 * Aplica filtros encadenados mediante patrón Strategy con Stream API.
 */
@Service
public class FilterServiceImpl implements FilterService {

    @Override
    public List<Transaction> filtrar(List<Transaction> transacciones, List<FiltroPredicate> filtros) {
        return transacciones.stream()
                .filter(t -> filtros.stream().allMatch(f -> f.cumple(t)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> filtrarPorTipo(List<Transaction> transacciones, TransactionType tipo) {
        return filtrar(transacciones, List.of(t -> t.getTipo() == tipo));
    }

    @Override
    public List<Transaction> filtrarPorRangoMonto(List<Transaction> transacciones, double montoMin, double montoMax) {
        return filtrar(transacciones, List.of(
                t -> t.getMonto() >= montoMin,
                t -> t.getMonto() <= montoMax
        ));
    }

    @Override
    public List<Transaction> filtrarPorFecha(List<Transaction> transacciones, LocalDate desde, LocalDate hasta) {
        return filtrar(transacciones, List.of(
                t -> !t.getFecha().isBefore(desde),
                t -> !t.getFecha().isAfter(hasta)
        ));
    }
}