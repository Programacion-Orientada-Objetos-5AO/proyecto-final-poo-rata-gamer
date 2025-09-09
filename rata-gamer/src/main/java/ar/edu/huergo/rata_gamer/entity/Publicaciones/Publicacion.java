package ar.edu.huergo.rata_gamer.entity.publicaciones;
<<<<<<< HEAD

import java.util.List;
=======
>>>>>>> parent of c02589a (Revert "Merge branch 'feat/Juegos' of https://github.com/Programacion-Orientada-Objetos-5AO/proyecto-final-poo-rata-gamer into feat/Juegos")

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Long id;

    // private Juego Juego;
    // private Plataforma Plataforma;  
    // private List<PrecioHistorico> preciosHistoricos;
}
