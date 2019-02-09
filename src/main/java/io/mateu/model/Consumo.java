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
public class Consumo {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Medicamento medicamento;

    @ManyToOne@NotNull
    private Paciente paciente;

    private double dosisConsumidas;

    public void setDosisConsumidas(double dosisConsumidas) {
        this.dosisConsumidas = dosisConsumidas;
        actualizaCredito();
    }

    private double dosisRecetadas;

    public void setDosisRecetadas(double dosisRecetadas) {
        this.dosisRecetadas = dosisRecetadas;
        actualizaCredito();
    }

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
