package tienda.tiendaonlinev2.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tiendaonlinev2.controlador.service.clienteService;
import tienda.tiendaonlinev2.controlador.service.historialService;
import tienda.tiendaonlinev2.modelo.entidad.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CacheConfig(cacheNames = "usr")
public class usuarioController {
    private historialService histService;
    private clienteService cliService;
    @Autowired
    public usuarioController(historialService histService, clienteService cliService) {
        this.histService = histService;
        this.cliService = cliService;
    }
    public usuarioController() {}

    //CONTROL USUARIO
    @GetMapping("/login")
    @Cacheable
    public ResponseEntity<?> login(@RequestBody DTOLogin cliente) {
        Optional<Cliente> existe = cliService.getClienteByNickname(cliente.getNickname());
        if (existe.isPresent()) {
           Optional<Cliente> coincide = cliService.loginCliente(cliente.getNickname(),cliente.getPassword());
           if (coincide.isPresent()) {
               return ResponseEntity.ok(coincide.get());
           }else return ResponseEntity.badRequest().body("La contraseña no es correcta");
        }else return ResponseEntity.badRequest().body("El usuario no existe");
    }
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody Cliente cliente) {
        if (cliService.getClienteByNickname(cliente.getNickname()).isEmpty()) {
            Optional<Cliente> guardado = cliService.addCliente(cliente);
            if (guardado.isPresent()) {
        return ResponseEntity.ok(guardado.get());
            }else return ResponseEntity.badRequest().body("Hubo un error al guardar");
        }  else return ResponseEntity.badRequest().body("Usuario con ese nickname ya existente");
    }
    @DeleteMapping("/eliminarCuenta")
    public ResponseEntity<Boolean> eliminarCuenta(@RequestBody DTOLogin cliente) {
        Optional<Cliente> usr = cliService.loginCliente(cliente.getNickname(),cliente.getPassword());
        if (usr.isPresent()) {
            return ResponseEntity.ok(cliService.deleteCliente(usr.get().getId()));
        }else return ResponseEntity.ok(false);
    }

    //CONTROL COMPRA/DEVOLUCION
    @PostMapping("/compra/{nickname}")
    public ResponseEntity<?> compra(@PathVariable String nickname, @RequestBody DTOProducto producto) {
        Optional <Historial> comprado = histService.Compra(nickname,producto);
        if (comprado.isPresent()) {
        return ResponseEntity.ok(comprado.get());
        } else{
            Optional<Cliente>Cliente = cliService.getClienteByNickname(nickname);
            if (Cliente.isPresent()) {
                return ResponseEntity.badRequest().body("El producto no existe");
            } else return ResponseEntity.badRequest().body("El usuario no existe");
        }
        }
    @PostMapping("/devolver/{nickname}/{motivo}")
    public ResponseEntity<?> devolver(@PathVariable String nickname, @PathVariable String motivo, @RequestBody DTOProducto producto) {
        Optional<Historial> existe= histService.Devolucion(nickname,producto,motivo);
        if (existe.isPresent()) {
            return ResponseEntity.ok(existe.get());
        }else return ResponseEntity.badRequest().body("La fecha de devolucion se ha pasado");
    }

}
