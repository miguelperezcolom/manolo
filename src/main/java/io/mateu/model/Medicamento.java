package io.mateu.model;

import io.mateu.mdd.core.annotations.Ignored;
import io.mateu.mdd.core.annotations.Output;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Medicamento {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    private String nombre;

    private double dosisPorEnvase;

    private double precio;

    @Output
    private double stock;

    private double dosisIniciales;

    private LocalDate fechaUltimoRecuento;

    @OneToMany(mappedBy = "medicamento")@Ignored
    private List<Suministro> suministros = new ArrayList<>();

    @OneToMany(mappedBy = "medicamento")@Ignored
    private List<Consumo> consumos = new ArrayList<>();

    @OneToMany(mappedBy = "medicamento")@Ignored
    private List<Prescripcion> dosis = new ArrayList<>();

    @OneToMany(mappedBy = "medicamento")@Ignored
    private List<Dispensacion> dispensaciones = new ArrayList<>();

    @OneToMany(mappedBy = "medicamento")@Ignored
    private List<Receta> recetas = new ArrayList<>();


    @Override
    public String toString() {
        return nombre;
    }

    @PostPersist@PostUpdate@PostRemove
    public void post() {
        Residencia.get().actualizarSaldos();
    }
}
