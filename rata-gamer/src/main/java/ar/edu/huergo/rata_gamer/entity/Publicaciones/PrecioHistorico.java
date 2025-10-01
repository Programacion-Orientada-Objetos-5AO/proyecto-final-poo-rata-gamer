package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor; 


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
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

}
