package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.repository.clienteRepo;

import java.util.List;

@Service
public class clienteService {
    private clienteRepo repo;

    public clienteService() {}
    @Autowired
    public clienteService(clienteRepo repo) {
        this.repo = repo;
    }

    public List<Cliente> getClientes(){
        return (List<Cliente>)
                repo.findAll();
    }
    public Cliente getCliente(int id){
        return repo.getClienteById(id);
    }
    public Cliente getClienteByNickname(String nickname){
        return repo.getClienteByNickname(nickname);
    }

    public Cliente addCliente(Cliente cliente){
        return repo.save(cliente);
    }
    public Cliente updateCliente(Cliente cliente){
        return repo.save(cliente);
    }
    public void deleteCliente(int id){
        repo.deleteById(id);
    }

}
