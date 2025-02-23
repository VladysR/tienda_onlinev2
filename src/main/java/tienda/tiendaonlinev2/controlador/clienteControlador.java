package tienda.tiendaonlinev2.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tiendaonlinev2.controlador.service.clienteService;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOCliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOLogin;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CacheConfig(cacheNames = "cliente")
public class clienteControlador {
    private clienteService service;
    public clienteControlador() {}
    @Autowired
    public clienteControlador(clienteService service) {
        this.service = service;
    }
    //CONTROL MANUAL DE CLIENTES
    @GetMapping("/getAllClientes")
    public ResponseEntity<Iterable<Cliente>> getAllClientes() {
        return ResponseEntity.ok(service.getClientes());
    }
    @GetMapping("/getClienteById/{id}")
    @Cacheable
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getCliente(id).get());
    }
    @GetMapping("/getClienteByNickname/{nickname}")
    @Cacheable
    public ResponseEntity<Cliente> getClienteByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(service.getClienteByNickname(nickname).get());
    }
    @PostMapping("/modCliente")
    public ResponseEntity<Cliente> modCliente(@Valid @RequestBody Cliente cliente) {
       return ResponseEntity.ok(service.updateCliente(cliente).get());
    }
    @DeleteMapping("/deleteCliente/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteCliente(id));
    }

    //CONTROL CLIENTES USUARIO
    @PostMapping("/login")
    @Cacheable
    public ResponseEntity<?> login(@RequestBody DTOLogin cliente) {
        Optional<Cliente> existe = service.getClienteByNickname(cliente.getNickname());
        if (existe.isPresent()) {
            Optional<Cliente> coincide = service.loginCliente(cliente.getNickname(),cliente.getPassword());
            if (coincide.isPresent()) {
                return ResponseEntity.ok(coincide.get());
            }else return ResponseEntity.badRequest().body("La contraseña no es correcta");
        }else return ResponseEntity.badRequest().body("El usuario no existe");
    }
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody Cliente cliente) {
        if (service.getClienteByNickname(cliente.getNickname()).isEmpty()) {
            Optional<Cliente> guardado = service.addCliente(cliente);
            if (guardado.isPresent()) {
                return ResponseEntity.ok(guardado.get());
            }else return ResponseEntity.badRequest().body("Hubo un error al guardar");
        }  else return ResponseEntity.badRequest().body("Usuario con ese nickname ya existente");
    }
    @DeleteMapping("/eliminarCuenta")
    public ResponseEntity<?> eliminarCuenta(@RequestBody DTOLogin login) {
        Optional<Cliente> usr = service.loginCliente(login.getNickname(), login.getPassword());
        if (usr.isPresent()) {
            return ResponseEntity.ok(service.deleteCliente(usr.get().getId()));
        }else return ResponseEntity.ok(false);
    }
}
