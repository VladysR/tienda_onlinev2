package tienda.tiendaonlinev2.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tiendaonlinev2.controlador.service.clienteService;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOCliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOLogin;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class clienteControlador {
    private clienteService service;
    public clienteControlador() {}
    @Autowired
    public clienteControlador(clienteService service) {
        this.service = service;
    }

    @GetMapping("/getAllClientes")
    public ResponseEntity<Iterable<Cliente>> getAllClientes() {
        return ResponseEntity.ok(service.getClientes());
    }
    @GetMapping("/getClienteById/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getCliente(id).get());
    }
    @GetMapping("/getClienteByNickname/{nickname}")
    public ResponseEntity<Cliente> getClienteByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(service.getClienteByNickname(nickname).get());
    }
    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody DTOLogin login) {
        return ResponseEntity.ok(service.loginCliente(login.getNickname(), login.getNickname()).get());
    }
    @PostMapping("/registro")
    public ResponseEntity<Cliente> registro(@RequestBody DTOCliente cliente) {
        Cliente usr = new Cliente();
        usr.setNickname(cliente.getNickname());
        usr.setPassword(cliente.getPassword());
        usr.setNombre(cliente.getNombre());
        usr.setApellido(cliente.getApellido());
        usr.setDomicilio(cliente.getDomicilio());
        usr.setTelefono(cliente.getTelefono());

        return ResponseEntity.ok(service.addCliente(usr).get());
    }
    @PostMapping("/modCliente")
    public ResponseEntity<Cliente> modCliente(@RequestBody Cliente cliente) {
       return ResponseEntity.ok(service.updateCliente(cliente).get());
    }
    @DeleteMapping("/deleteCliente/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteCliente(id));
    }
}
