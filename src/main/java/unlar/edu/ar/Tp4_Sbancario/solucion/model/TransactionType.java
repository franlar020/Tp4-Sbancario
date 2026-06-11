package unlar.edu.ar.Tp4_Sbancario.solucion.model;

public enum TransactionType {

    DEPOSITO("Deposito"),
    RETIRO("Retiro"),
    TRANSFERENCIA("Transferencia"),
    PAGO("Pago");

    /** Descripción legible del tipo de transaccion. */
    private final String descripcion;


    TransactionType(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}