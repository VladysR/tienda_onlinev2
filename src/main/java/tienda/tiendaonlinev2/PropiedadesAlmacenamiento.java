package tienda.tiendaonlinev2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class PropiedadesAlmacenamiento {

    private String location = "directorio-subidas";

    public String getUbicacion(){
        return location;
    }

    public void setUbicacion(String location){
        this.location=location;
    }
}
