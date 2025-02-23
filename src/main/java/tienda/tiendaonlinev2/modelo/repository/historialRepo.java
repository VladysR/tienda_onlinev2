package tienda.tiendaonlinev2.modelo.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.Historial;
import tienda.tiendaonlinev2.modelo.entidad.Producto;

import java.util.List;
import java.util.Optional;

public interface historialRepo extends CrudRepository <Historial, Integer> {

    List<Historial> getHistorialsByTipo(@Size(max = 100) @NotNull String tipo);

    List<Historial> getHistorialsByCliente(@NotNull Cliente cliente);

    List<Historial> getHistorialsByProducto(@NotNull Producto producto);

    Historial getHistorialById(int id);

    Optional<Historial> getHistorialByClienteAndProductoAndTipoLikeAndDescripcionIsNull(@NotNull Cliente cliente, @NotNull Producto producto, @Size(max = 100) @NotNull String tipo);
}
