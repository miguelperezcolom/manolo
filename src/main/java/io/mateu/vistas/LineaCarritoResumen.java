package io.mateu.vistas;

import io.mateu.model.Medicamento;
import io.mateu.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class LineaCarritoResumen {

    private Medicamento medicamento;

    private double dosis;

}
