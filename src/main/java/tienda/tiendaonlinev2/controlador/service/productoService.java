package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Producto;
import tienda.tiendaonlinev2.modelo.repository.productoRepo;

import java.util.List;

@Service
public class productoService {

    private productoRepo repo;
    public productoService() {}
    @Autowired
    public productoService(productoRepo repo) {
        this.repo = repo;
    }

    public List<Producto> getProductos() {
        return (List<Producto>) repo.findAll();
    }
    public Producto getProducto(int id) {
        return repo.getProductoById(id);
    }
    public List<Producto> getProductosByNombre(String nombre) {
        return repo.getProductosByNombre(nombre);
    }
    public Producto addProducto(Producto producto) {
        return repo.save(producto);
    }
    public Producto updateProducto(Producto producto) {
        return repo.save(producto);
    }
    public void deleteProducto(int id) {
        repo.deleteById(id);
    }
}
