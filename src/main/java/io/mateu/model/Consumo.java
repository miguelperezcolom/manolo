package io.mateu.model;

import io.mateu.mdd.core.annotations.*;
import io.mateu.mdd.core.util.Helper;
import io.mateu.mdd.core.workflow.WorkflowEngine;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter@NewNotAllowed@Indelible
public class Consumo {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull@Output
    @MainSearchFilter
    private Medicamento medicamento;

    @ManyToOne@NotNull@Output
    @MainSearchFilter
    private Paciente paciente;

    @Output@Sum
    private double dosisConsumidas;

    public void setDosisConsumidas(double dosisConsumidas) {
        this.dosisConsumidas = dosisConsumidas;
        actualizaCredito();
    }

    @Output@Sum
    private double dosisRecetadas;

    public void setDosisRecetadas(double dosisRecetadas) {
        this.dosisRecetadas = dosisRecetadas;
        actualizaCredito();
    }

    @Output@Sum
    private double credito;

    private void actualizaCredito() {
        credito = ((dosisConsumidas - dosisRecetadas) / medicamento.getDosisPorEnvase()) * medicamento.getPrecio();
    }

    @Override
    public String toString() {
        return Helper.capitalize(getClass().getSimpleName()) + " " + getId();
    }

    @PostPersist@PostUpdate@PostRemove
    public void post() {
        Residencia.get().actualizarSaldos();
    }
}
