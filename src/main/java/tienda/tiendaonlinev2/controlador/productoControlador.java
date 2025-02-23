package tienda.tiendaonlinev2.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tienda.tiendaonlinev2.controlador.service.productoService;
import tienda.tiendaonlinev2.modelo.entidad.Producto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CacheConfig(cacheNames = "productos")
public class productoControlador {
    productoService service;

    public productoControlador() {}
    @Autowired
    public productoControlador(productoService service) {
        this.service = service;
    }
    //CONTROL MANUAL DE PRODUCTOS
    @GetMapping("/productos")
    public ResponseEntity<Iterable<Producto>> getAllProductos() {
        return ResponseEntity.ok(service.getProductos());
    }
    @GetMapping("/productoById/{id}")
    @Cacheable
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        return ResponseEntity.ok(service.getProducto(id).get());
    }
    @GetMapping("/productoByNombre/{nombre}")
    @Cacheable
    public ResponseEntity<Producto> getProductoByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.getProductoByNombre(nombre).get());
    }
    @PostMapping("/addProducto")
    public ResponseEntity<?> addProducto(@Valid @RequestBody Producto producto) {
        if (service.getProductoByNombre(producto.getNombre()).isEmpty()) {
            Optional<Producto> prodGuardado = service.addProducto(producto);
            if (prodGuardado.isPresent()) {
                return ResponseEntity.ok(prodGuardado.get());
            }
        return ResponseEntity.badRequest().body("El nombre del producto está repetido.");
        }
        else return  ResponseEntity.ofNullable("El nombre del producto esta repetido");
    }
    @PutMapping("/updateProducto")
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(service.updateProducto(producto).get());
    }
    @DeleteMapping("/deleteProd/{id}")
    public ResponseEntity<Boolean> deleteProducto(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteProducto(id));
    }

    //CONTROL MANUAL DE STOCK
    @PutMapping("/addStock/{id}/{stock}")
    public ResponseEntity<Boolean>addStock(@PathVariable int id, @PathVariable int stock) {
        return ResponseEntity.ok(service.addStock(id, stock));
    }
    @PutMapping("/removeStock/{id}/{stock}")
    public ResponseEntity<Boolean>removeStock(@PathVariable int id, @PathVariable int stock) {
        return ResponseEntity.ok(service.removeStock(id, stock));
    }
    @PutMapping("/updateStock/{id}/{stock}")
    public ResponseEntity<Boolean>updateStock(@PathVariable int id, @PathVariable int stock) {
        return ResponseEntity.ok(service.updateStock(id, stock));
    }
}
