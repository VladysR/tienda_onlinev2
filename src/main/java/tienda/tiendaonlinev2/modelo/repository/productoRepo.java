package tienda.tiendaonlinev2.modelo.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import tienda.tiendaonlinev2.modelo.entidad.Producto;

import java.util.List;

public interface productoRepo extends CrudRepository <Producto, Integer>{
    Producto getProductoById(Integer id);

    List<Producto> getProductosByNombre(@Size(max = 100) @NotNull String nombre);
}
