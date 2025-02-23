package tienda.tiendaonlinev2.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tiendaonlinev2.controlador.service.clienteService;
import tienda.tiendaonlinev2.controlador.service.historialService;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOProducto;
import tienda.tiendaonlinev2.modelo.entidad.Historial;

import java.util.Optional;

@RestController
@RequestMapping("/historial")
@CacheConfig(cacheNames = "historial")
public class historialControlador {
    historialService service;
    clienteService clienteService;

    public historialControlador() {}

    @Autowired
    public historialControlador(historialService service, clienteService clienteService) {
        this.service = service;
        this.clienteService = clienteService;
    }
//CONTROL MANUAL HISTORIAL
    @GetMapping("/historiales")
    public ResponseEntity<Iterable<Historial>>getAllHistoriales(){
        return ResponseEntity.ok(service.getAllHistorial());
    }
    @GetMapping("/historialById/{id}")
    @Cacheable
    public ResponseEntity<Historial>getHistorialById(@PathVariable int id){
        return ResponseEntity.ok(service.getHistorialById(id).get());
    }
    @GetMapping("/historialByCliente/{id}")
    @Cacheable
    public ResponseEntity<Iterable<Historial>>getHistorialByCliente(@PathVariable int id){
        return ResponseEntity.ok(service.getHistorialByCliente(id).get());
    }
    @GetMapping("/historialByProducto/{id}")
    @Cacheable
    public ResponseEntity<Iterable<Historial>>getHistorialByProducto(@PathVariable int id){
        return ResponseEntity.ok(service.getHistorialByProducto(id).get());
    }
    @GetMapping("/historialByTipo/{tipo}")
    @Cacheable
    public ResponseEntity<Iterable<Historial>>getHistorialByTipo(@PathVariable String tipo){
        return ResponseEntity.ok(service.getHistorialByTipo(tipo).get());
    }
    @PostMapping("/addHistorial")
    public ResponseEntity<Historial>addHistorial(@Valid @RequestBody Historial historial){
        return ResponseEntity.ok(service.addHistorial(historial).get());
    }
    @PutMapping("/updateHistorial")
    public ResponseEntity<Historial>updateHistorial(@Valid @RequestBody Historial historial){
        return ResponseEntity.ok(service.updateHistorial(historial).get());
    }
    //CONTROL USUARIO HISTORIAL
    @PostMapping("/compra/{nickname}")
    public ResponseEntity<?> compra(@PathVariable String nickname, @RequestBody DTOProducto producto) {
        Optional <Historial> comprado = service.Compra(nickname,producto);
        if (comprado.isPresent()) {
            return ResponseEntity.ok(comprado.get());
        } else{
            Optional<Cliente>Cliente = clienteService.getClienteByNickname(nickname);
            if (Cliente.isPresent()) {
                return ResponseEntity.badRequest().body("El producto no existe");
            } else return ResponseEntity.badRequest().body("El usuario no existe");
        }
    }
    @PostMapping("/devolucion/{nickname}/{motivo}")
    public ResponseEntity<?> devolver(@PathVariable String nickname, @PathVariable String motivo, @RequestBody DTOProducto producto) {
        Optional<Historial> existe= service.Devolucion(nickname,producto,motivo);
        if (existe.isPresent()) {
            return ResponseEntity.ok(existe.get());
        }else return ResponseEntity.badRequest().body("La fecha de devolucion se ha pasado");
    }

 }
