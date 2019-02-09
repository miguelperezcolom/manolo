package io.mateu.model;

import io.mateu.mdd.core.util.Helper;
import io.mateu.mdd.core.workflow.WorkflowEngine;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Receta {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Medicamento medicamento;

    @ManyToOne@NotNull
    private Paciente paciente;

    @NotNull
    private LocalDate fecha;

    private int unidades;

    @Override
    public String toString() {
        return Helper.capitalize(getClass().getSimpleName()) + " " + getId();
    }


    @PostPersist
    @PostUpdate@PostRemove
    public void post() {
        Residencia.get().actualizarSaldos();
    }
}
