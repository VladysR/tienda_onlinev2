package tienda.tiendaonlinev2.modelo.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;

public interface clienteRepo extends CrudRepository <Cliente, Integer>{
    Cliente getClienteById(Integer id);

    Cliente getClienteByNickname(@Size(max = 50) @NotNull String nickname);
}
