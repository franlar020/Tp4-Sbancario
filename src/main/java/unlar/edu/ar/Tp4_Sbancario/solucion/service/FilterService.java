package unlar.edu.ar.Tp4_Sbancario.solucion.service;


import unlar.edu.ar.Tp4_Sbancario.solucion.model.Transaction;
import unlar.edu.ar.Tp4_Sbancario.solucion.model.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface FilterService {

    @FunctionalInterface
    interface FiltroPredicate {
        boolean cumple(Transaction transaction);
    }

    List<Transaction> filtrar(List<Transaction> transacciones, List<FiltroPredicate> filtros);

    List<Transaction> filtrarPorTipo(List<Transaction> transacciones, TransactionType tipo);

    List<Transaction> filtrarPorRangoMonto(List<Transaction> transacciones,double montoMin, double montoMax);

    List<Transaction> filtrarPorFecha(List<Transaction> transacciones,LocalDate desde, LocalDate hasta);
}