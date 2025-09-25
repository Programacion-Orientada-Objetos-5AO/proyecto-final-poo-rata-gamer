package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull; 


@Entity
@Table(name = "precios_historicos")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PrecioHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private LocalDate fechaInicio;
    

    private LocalDate fechaFin;

    @NotNull
    private Double precio;

}
