package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Producto;
import tienda.tiendaonlinev2.modelo.repository.productoRepo;

import java.util.List;
import java.util.Optional;

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
    public Optional<Producto> getProducto(int id) {
        return Optional.ofNullable(repo.getProductoById(id));
    }
    public Optional<Producto> getProductoByNombre(String nombre) {
        return Optional.of(repo.getProductoByNombre(nombre));
    }

    public Optional<Producto> addProducto(Producto producto) {
        return Optional.of(repo.save(producto));
    }

    public Optional<Producto> updateProducto(Producto producto) {
        return Optional.of(repo.save(producto));
    }

    public Boolean deleteProducto(int id) {
        repo.deleteById(id);
        return !repo.existsById(id);
    }
//CONTROL DE STOCK
    public Boolean checkStock(Producto producto) {
        Producto existe = repo.getProductoById(producto.getId());
        if (existe != null) {
            return existe.getStock() > 0;
        }
        return false;
    }
    public Boolean addStock(Producto producto, int stock) {
        if (checkStock(producto)) {
            producto.setStock(producto.getStock() + stock);
            repo.save(producto);
            return true;
        }else return false;

    }

    public Boolean removeStock(Producto producto, int stock) {
        if (checkStock(producto)) {
            producto.setStock(producto.getStock() - stock);
            repo.save(producto);
            return true;
        }else return false;
    }

    public Boolean updateStock(Producto producto, int stock) {
        if (checkStock(producto)) {
            producto.setStock(producto.getStock() - stock);
            repo.save(producto);
            return true;
        }else return false;
    }
}

