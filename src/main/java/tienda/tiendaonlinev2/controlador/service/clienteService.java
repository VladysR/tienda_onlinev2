package tienda.tiendaonlinev2.controlador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tiendaonlinev2.modelo.entidad.Cliente;
import tienda.tiendaonlinev2.modelo.repository.clienteRepo;

import java.util.List;
import java.util.Optional;

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
    public Optional<Cliente> getCliente(int id){
        return Optional.ofNullable(repo.getClienteById(id));
    }
    public Optional<Cliente> getClienteByNickname(String nickname){
        return Optional.ofNullable(repo.getClienteByNickname(nickname));
    }

    public Optional<Cliente> addCliente(Cliente cliente){
        return Optional.of(repo.save(cliente));
    }
    public Optional<Cliente> updateCliente(Cliente cliente){
        return Optional.of(repo.save(cliente));
    }
    public Boolean deleteCliente(int id){
        repo.deleteById(id);
        return !repo.existsById(id);
    }

    public Optional<Cliente> loginCliente(String nickname, String password){
        return Optional.of(repo.getClienteByNicknameAndPassword(nickname, password));
    }


}
