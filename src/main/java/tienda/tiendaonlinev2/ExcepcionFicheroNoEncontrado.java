package tienda.tiendaonlinev2;

public class ExcepcionFicheroNoEncontrado extends ExcepcionAlmacenamiento{

    public ExcepcionFicheroNoEncontrado(String mensaje) {
        super(mensaje);
    }

    public ExcepcionFicheroNoEncontrado(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
