package unlar.edu.ar.Tp4_Sbancario.inicial;

public class Transaction {

    public Long id;
    public String tipo;       // "DEPOSITO", "RETIRO", "TRANSFERENCIA", "PAGO"
    public double monto;
    public String fecha;      // formato "YYYY-MM-DD"
    public String descripcion;

    /**
     * Constructor completo de la transacción legacy.
     *
     * @param id          identificador único de la transacción
     * @param tipo        tipo como String (sin validación de compilación)
     * @param monto       monto de la operación
     * @param fecha       fecha en formato String "YYYY-MM-DD"
     * @param descripcion descripción textual de la transacción
     */
    public Transaction(Long id, String tipo, double monto, String fecha, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    /**
     * Retorna una representación en cadena de la transacción.
     *
     * @return String con los datos de la transacción
     */
    @Override
    public String toString() {
        return "Transaction{id=" + id + ", tipo='" + tipo + "', monto=" + monto +
               ", fecha='" + fecha + "', descripcion='" + descripcion + "'}";
    }
}