package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "juegos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private Double descuento;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private List<Publicacion> publicaciones;


}
