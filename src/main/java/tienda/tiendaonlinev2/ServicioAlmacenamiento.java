package tienda.tiendaonlinev2;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ServicioAlmacenamiento {
     void init();
     void store(MultipartFile fichero);
     Stream<Path> loadAll();
     Path load(String nombreFichero);
     Resource loadAsResource(String nombrefichero);
     boolean delete(String nombreFichero);
     void deleteAll();
}
