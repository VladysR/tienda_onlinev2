package tienda.tiendaonlinev2.modelo.entidad;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.lang.annotation.Repeatable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @NotEmpty(message = "El nombre no puede estar vacio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true,message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull
    @Column(name = "stock", nullable = false)
    @Min(value = 0,message = "El stock no puede ser negativo")
    private Integer stock;

    @OneToMany(mappedBy = "producto")
    @JsonManagedReference("producto-historial")
    private Set<Historial> historials = new LinkedHashSet<>();

}