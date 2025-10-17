package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @ManyToOne  // <-- Asumiendo que muchos Publicacion pueden tener un mismo Juego
    private Juego juego;

    @NotNull
    @ManyToOne  // <-- Similar para Plataforma
    private Plataforma plataforma;  

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publicacion") // Otras opciones si es bidireccional
    private List<PrecioHistorico> preciosHistoricos;

}
