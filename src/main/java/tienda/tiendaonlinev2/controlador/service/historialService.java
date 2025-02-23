package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.DTOProducto;
import tienda.tiendaonlinev2.modelo.entidad.Historial;
import tienda.tiendaonlinev2.modelo.entidad.Producto;
import tienda.tiendaonlinev2.modelo.repository.clienteRepo;
import tienda.tiendaonlinev2.modelo.repository.historialRepo;
import tienda.tiendaonlinev2.modelo.repository.productoRepo;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class historialService {
    private historialRepo repo;
    private productoService productoService;
    private clienteService clienteService;

    public historialService() {
    }

    @Autowired
    public historialService(historialRepo repo, clienteService clienteService, productoService productoService) {
        this.repo = repo;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    public List<Historial> getAllHistorial() {
        return (List<Historial>) repo.findAll();
    }

    public Optional<Historial> getHistorialById(int id) {
        return Optional.ofNullable(repo.getHistorialById(id));
    }

    public Optional<List<Historial>> getHistorialByCliente(int id) {
        Cliente cliente = clienteService.getCliente(id).get();
        return Optional.ofNullable(repo.getHistorialsByCliente(cliente));
    }

    public Optional<List<Historial>> getHistorialByProducto(int productoid) {
        Producto producto = productoService.getProducto(productoid).get();
        return Optional.ofNullable(repo.getHistorialsByProducto(producto));
    }

    public Optional<List<Historial>> getHistorialByTipo(String tipo) {
        return Optional.ofNullable(repo.getHistorialsByTipo(tipo));
    }

    public Optional<Historial> addHistorial(Historial historial) {
        productoService.removeStock(historial.getProducto().getId(), historial.getCantidad());
        return Optional.of(repo.save(historial));
    }

    public Optional<Historial> updateHistorial(Historial historial) {
        return Optional.of(repo.save(historial));
    }

    public void deleteHistorial(int id) {
        Historial historial = repo.getHistorialById(id);
        productoService.addStock(historial.getProducto().getId(), historial.getCantidad());
        repo.deleteById(id);
    }


    public Optional<Historial> Compra(String nickname, DTOProducto dtoProducto) {
        Optional<Cliente> cliente = clienteService.getClienteByNickname(nickname);
        Cliente clienteEncontrado = cliente.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Optional<Producto> producto = productoService.getProductoByNombre(dtoProducto.getNombre());
        Producto productoEncontrado = producto.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Historial historial = new Historial();
        historial.setProducto(producto.get());
        historial.setCantidad(dtoProducto.getCantidad());
        historial.setCliente(cliente.get());
        historial.setTipo("Compra");
        historial.setFechaCompra(LocalDate.now());
        return addHistorial(historial);


    }


    public Optional<Historial> Devolucion(String nickname, DTOProducto dtoproducto, String descripcion) {

        Optional<Cliente> cliente = clienteService.getClienteByNickname(nickname);
        Optional<Producto> producto = productoService.getProductoByNombre(dtoproducto.getNombre()); //Saco entidades

        Cliente clienteEncontrado = cliente.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Producto productoEncontrado = producto.orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<Historial> historial = repo.getHistorialByClienteAndProductoAndTipoLikeAndDescripcionIsNull(cliente.get(), producto.get(), "Compra"); //Busco si existe el historial y busco su entidad

        Historial historialEncontrado = historial.orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        if (LocalDate.now().getYear() == historial.get().getFechaCompra().getYear() && LocalDate.now().getDayOfYear() - historial.get().getFechaCompra().getDayOfYear() <= 30) { //Compruebo la garantia de 30 dias

            historial.get().setTipo("Devolucion");
            historial.get().setDescripcion(descripcion);

            return Optional.of(repo.save(historial.get()));
        }
        else return Optional.empty();
    }
}