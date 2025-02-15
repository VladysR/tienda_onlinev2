package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.entidad.Historial;
import tienda.tiendaonlinev2.modelo.entidad.Producto;
import tienda.tiendaonlinev2.modelo.repository.historialRepo;

import java.util.List;

@Service
public class historialService {
    private historialRepo repo;
    public historialService() {}
    @Autowired
    public historialService(historialRepo repo) {
        this.repo = repo;
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
        return repo.save(historial);
    }
    public Historial updateHistorial(Historial historial){
        return repo.save(historial);
    }
    public void deleteHistorial(int id){
        repo.deleteById(id);
    }

}
