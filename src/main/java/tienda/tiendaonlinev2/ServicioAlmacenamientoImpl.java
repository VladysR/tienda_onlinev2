package tienda.tiendaonlinev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class ServicioAlmacenamientoImpl implements  ServicioAlmacenamiento{

    private final Path directorioRaiz;

    @Autowired
    public ServicioAlmacenamientoImpl(PropiedadesAlmacenamiento propiedades) {
        if(propiedades.getUbicacion().trim().length()==0){
            throw  new ExcepcionAlmacenamiento("El fichero no puede estar vacío");
        }
        this.directorioRaiz = Paths.get(propiedades.getUbicacion());
        System.out.println("ubicacion directorio raíz: "+this.directorioRaiz.toAbsolutePath().toString());
    }

    @Override
    public void init() {
        try {
            //crea la carpeta donde se almacenarán los ficheros subidos en caso de que no existiese previamente
            Files.createDirectories(directorioRaiz);
        } catch (IOException e) {
            throw new ExcepcionAlmacenamiento("No se ha podido inicializar el almacenamiento",e);
        }
    }

    @Override
    public void store(MultipartFile fichero) {
        if(fichero.isEmpty()){
            throw new ExcepcionAlmacenamiento("Fallo al almacenar un fichero vacío.");
        }
        Path rutaFichero = Paths.get(fichero.getOriginalFilename());
        Path rutaDestinoCompleta = this.directorioRaiz.resolve(rutaFichero).normalize().toAbsolutePath();

        if(!rutaDestinoCompleta.getParent().equals(this.directorioRaiz.toAbsolutePath())){
            //comprobación de seguridad
            throw new ExcepcionAlmacenamiento("No se debe almacenar fuera del directorio actual");
        }
        try(InputStream inputStream = fichero.getInputStream()){
            Files.copy(inputStream,rutaDestinoCompleta, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
                throw new ExcepcionAlmacenamiento("Fallo al almacenar el fichero");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.directorioRaiz,1)
                    .filter(ruta->!ruta.equals(this.directorioRaiz))
                    .map(this.directorioRaiz::relativize);
        } catch (IOException e) {
            throw new ExcepcionAlmacenamiento("Fallo al leer ficheros",e);
        }
    }

    @Override
    public Path load(String nombrefichero){
        return directorioRaiz.resolve(nombrefichero).toAbsolutePath();
    }

    @Override
    public Resource loadAsResource(String nombrefichero) {
        Path fichero = load(nombrefichero);
        try {
            Resource recurso = new UrlResource(fichero.toUri());
            if(recurso.exists() || recurso.isReadable()){
                return recurso;
            }
            else {
                throw new ExcepcionFicheroNoEncontrado("No se ha podido leer el fichero: "+nombrefichero);
            }
        } catch (MalformedURLException e) {
            throw new ExcepcionFicheroNoEncontrado("No se ha podido leer el fichero: "+nombrefichero, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(directorioRaiz.toFile());
    }

    @Override
    public boolean delete(String nombreFichero){
        if (nombreFichero == null || nombreFichero.isEmpty()) {
            throw new IllegalArgumentException("El nombre del fichero no puede ser nulo o vacío");
        }
        try {
            //1. concatena el directorio raiz "directorio-subidas" + nombreFichero
            //2. Crea un objeto Path.
            Path ruta = this.directorioRaiz.resolve(nombreFichero);
            boolean resultado = Files.deleteIfExists(ruta);

            System.out.println("info resultado de borrado del fichero destino: "+resultado);
            return resultado;
        } catch (IOException e) {
            throw new RuntimeException("Error al intentar eliminar el archivo: " + nombreFichero, e);
        }
    }
}
