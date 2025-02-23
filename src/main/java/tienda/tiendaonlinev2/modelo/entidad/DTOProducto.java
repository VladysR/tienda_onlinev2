package tienda.tiendaonlinev2.modelo.entidad;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DTOProducto {
    private String Nombre;
    private int Cantidad;

    public DTOProducto(String Nombre,int Cantidad) {
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
    }

}
