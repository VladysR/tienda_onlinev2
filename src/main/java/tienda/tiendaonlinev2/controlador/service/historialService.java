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
    private productoService stockService;
    private clienteService clienteService;
    private tienda.tiendaonlinev2.modelo.repository.productoRepo productoRepo;

    public historialService() {}
    @Autowired
    public historialService(historialRepo repo, productoService stockService, clienteService clienteService, productoRepo productoRepo) {
        this.repo = repo;
        this.stockService = stockService;
        this.clienteService = clienteService;
        this.productoRepo = productoRepo;
    }

    public List<Historial> getAllHistorial(){
        return (List<Historial>) repo.findAll();
    }
    public Historial getHistorialById(int id){
        return repo.getHistorialById(id);
    }
    public List<Historial> getHistorialByCliente(Cliente cliente){
        return repo.getHistorialsByCliente(cliente);
    }

    public List<Historial> getHistorialByProducto(Producto producto){
        return repo.getHistorialsByProducto(producto);
    }
    public List<Historial> getHistorialByTipo(String tipo){
        return repo.getHistorialsByTipo(tipo);
    }
    public Historial addHistorial(Historial historial){
        stockService.removeStock(historial.getProducto(),historial.getCantidad());
        return repo.save(historial);
    }
    public Historial updateHistorial(Historial historial){
        return repo.save(historial);
    }
    public void deleteHistorial(int id){
        Historial historial = repo.getHistorialById(id);
        stockService.addStock(historial.getProducto(),historial.getCantidad());
        repo.deleteById(id);
    }


    public Historial Compra (String nickname,DTOProducto dtoProducto){
        Optional<Cliente> cliente = clienteService.getClienteByNickname(nickname);
        if(cliente.isPresent()){
        Optional<Producto> producto = stockService.getProductoByNombre(dtoProducto.getNombre());
        if(producto.isPresent()){
            Historial historial = new Historial();
            historial.setProducto(producto.get());
            historial.setCantidad(dtoProducto.getCantidad());
            historial.setCliente(cliente.get());
            historial.setTipo("Compra");
            historial.setFechaCompra(LocalDate.now());
            return addHistorial(historial);
        }else return null;
        }else return null;

    }


    public Historial Devolucion (String nickname, DTOProducto dtoproducto,String descripcion) {

        Optional<Cliente> cliente = clienteService.getClienteByNickname(nickname);
        Optional<Producto> producto = stockService.getProductoByNombre(dtoproducto.getNombre()); //Saco entidades

        if (cliente.isPresent() && producto.isPresent()) {

        Optional<Historial> historial = repo.getHistorialByClienteAndProductoAndTipoLikeAndDescripcionIsNull(cliente.get(),producto.get(),"Compra",""); //Busco si existe el historial y busco su entidad

        if (historial.isPresent()) {
                if (LocalDate.now().getYear() == historial.get().getFechaCompra().getYear() && LocalDate.now().getDayOfYear() - historial.get().getFechaCompra().getDayOfYear() <= 30) { //Compruebo la garantia de 30 dias

                historial.get().setTipo("Devolucion");
                historial.get().setDescripcion(descripcion);

            return repo.save(historial.get());
                }else return null;
            }else return null;
        }else return null;

    }

}
