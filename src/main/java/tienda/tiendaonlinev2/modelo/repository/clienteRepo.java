package tienda.tiendaonlinev2.modelo.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;

public interface clienteRepo extends CrudRepository <Cliente, Integer>{
    Cliente getClienteById(Integer id);

    Cliente getClienteByNickname(@Size(max = 50) @NotNull String nickname);

    Cliente getClienteByNicknameAndPassword(@Size(max = 50) @NotNull String nickname, @Size(max = 255) @NotNull @Size(min = 8,message = "La contraseña debe ser minimo de 8 caracteres") @Pattern(regexp = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}+$"),message = ("La contraseña debe tener al menos una mayuscula,minuscula y numero")) String password);
}
