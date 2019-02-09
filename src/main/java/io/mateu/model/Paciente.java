package io.mateu.model;

import io.mateu.mdd.core.annotations.Ignored;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    private String nombre;

    @NotNull
    private LocalDate alta;

    private LocalDate baja;


    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)@Ignored
    private List<Consumo> consumos = new ArrayList<>();


    @Override
    public String toString() {
        return nombre;
    }
}
