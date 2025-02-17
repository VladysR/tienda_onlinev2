package tienda.tiendaonlinev2.modelo.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    @Pattern(regexp = ("^[a-zA-Z]$"),message = "Solo caracteres alfanumericos")
    private String nombre;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Size(max = 50)
    @NotNull
    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    @Size(min = 8,message = "La contraseña debe ser minimo de 8 caracteres")
    @Pattern(regexp = ("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}+$"),message = ("La contraseña debe tener al menos una mayuscula,minuscula y numero"))

    private String password;

    @Size(max = 15)
    @Column(name = "telefono", length = 15)
    @Pattern(regexp = ("^(6,9)[0-9]{8}$"),message = "El formato de numero no es valido")
    private String telefono;

    @Size(max = 100)
    @Column(name = "domicilio", length = 100)
    private String domicilio;

    @OneToMany(mappedBy = "cliente")
    private Set<Historial> historials = new LinkedHashSet<>();

}