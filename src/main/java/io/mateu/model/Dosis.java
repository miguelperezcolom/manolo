package io.mateu.model;

import io.mateu.mdd.core.annotations.TextArea;
import io.mateu.mdd.core.util.Helper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Dosis {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne@NotNull
    private Medicamento medicamento;

    @ManyToOne@NotNull
    private Paciente paciente;

    private double dosisDiaria;

    private LocalDate inicio;

    private LocalDate fin;

    @TextArea
    private String observaciones;

    @Override
    public String toString() {
        return Helper.capitalize(getClass().getSimpleName()) + " " + getId();
    }

}
