package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "juegos")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private Juego juego;

    @NotNull
    private Plataforma plataforma;  

    @NotNull
    private List<PrecioHistorico> preciosHistoricos;

}
