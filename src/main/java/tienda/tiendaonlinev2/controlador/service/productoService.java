package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Producto;
import tienda.tiendaonlinev2.modelo.repository.productoRepo;

import java.math.BigDecimal;
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
        return Optional.ofNullable(repo.getProductoByNombre(nombre));
    }

    public Optional<Producto> addProducto(Producto producto) {
        BigDecimal LIMITE_OFERTA = new BigDecimal(10);
        BigDecimal LIMITE_CALIDAD = new BigDecimal(200);

        if (producto.getPrecio() != null) {
            StringBuilder descripcion = new StringBuilder(producto.getDescripcion());
            if (producto.getPrecio().compareTo(LIMITE_OFERTA) < 0) {
                descripcion.append(" producto de oferta");
            } else if (producto.getPrecio().compareTo(LIMITE_CALIDAD) > 0) {
                descripcion.append(" producto de calidad");
            }
            producto.setDescripcion(descripcion.toString());
        } else {
            System.out.println("Error: El precio del producto no puede ser nulo.");
            return Optional.empty(); // O lanzar una excepción
        }

        try {
            return Optional.of(repo.save(producto));
        } catch (Exception e) {
            System.out.println("Error al guardar el producto: " + e.getMessage());
            return Optional.empty(); // O lanzar una excepción
        }
    }


    public Optional<Producto> updateProducto(Producto producto) {
        return Optional.of(repo.save(producto));
    }

    public Boolean deleteProducto(int id) {
        repo.deleteById(id);
        return !repo.existsById(id);
    }
//CONTROL DE STOCK
    public Boolean checkStock(int producto) {
        Producto existe = repo.getProductoById(producto);
        if (existe != null) {
            return existe.getStock() > 0;
        }
        return false;
    }
    public Boolean addStock(int producto_id, int stock) {

        if (checkStock(producto_id)) {
            Producto producto = repo.getProductoById(producto_id);
            producto.setStock(producto.getStock() + stock);
            repo.save(producto);
            return true;
        }else return false;

    }

    public Boolean removeStock(int producto_id, int stock) {
        if (checkStock(producto_id)) {
            Producto producto = repo.getProductoById(producto_id);
            producto.setStock(producto.getStock() - stock);
            repo.save(producto);
            return true;
        }else return false;
    }

    public Boolean updateStock(int producto_id, int stock) {
        if (checkStock(producto_id)) {
            Producto producto = repo.getProductoById(producto_id);
            producto.setStock(producto.getStock() - stock);
            repo.save(producto);
            return true;
        }else return false;
    }
}

