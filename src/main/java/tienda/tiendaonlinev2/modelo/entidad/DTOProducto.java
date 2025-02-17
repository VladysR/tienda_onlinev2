package tienda.tiendaonlinev2.modelo.entidad;

public class DTOProducto {
    private String Nombre;
    private String Descripcion;
    private int Cantidad;

    public DTOProducto(String Nombre, String Descripcion,int Cantidad) {
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Cantidad = Cantidad;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
