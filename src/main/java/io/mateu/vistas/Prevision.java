package io.mateu.vistas;

import io.mateu.mdd.core.annotations.SameLine;
import io.mateu.mdd.core.util.Helper;
import io.mateu.model.Dosis;
import io.mateu.model.Medicamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Prevision {

    @Min(value = 0)
    @Max(value = 100)
    private int dias;

    public void setDias(int dias) {
        this.dias = dias;
        hastaFecha = LocalDate.now().plusDays(dias);
        actualizar();
    }

    @SameLine
    private boolean soloDeficit;

    public void setSoloDeficit(boolean soloDeficit) {
        this.soloDeficit = soloDeficit;
        actualizar();
    }

    private void actualizar() {
        necesidades = new ArrayList<>();
        try {
            ((List<Dosis>)Helper.selectObjects("select x from Dosis x order by x.medicamento.nombre")).forEach(d -> {
                Optional<LineaPrevision> found = necesidades.stream().filter(l -> l.getMedicamento().equals(d.getMedicamento())).map(l -> {
                    l.setNecesidad(l.getNecesidad() + d.getDosisDiaria() * dias);
                    return l;
                }).findFirst();
                if (!found.isPresent()) necesidades.add(new LineaPrevision(d.getMedicamento(), 0, d.getDosisDiaria() * dias, 0));
            });
            necesidades.forEach(l -> l.setStock(l.getMedicamento().getStock()));
            necesidades.forEach(l -> l.setSaldo(l.getMedicamento().getStock() - l.getNecesidad()));
            if (soloDeficit) necesidades.removeIf(l -> l.getSaldo() >= 0);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @SameLine
    private LocalDate hastaFecha;

    private List<LineaPrevision> necesidades = new ArrayList();

    public Prevision() {
        actualizar();
    }

    @Data@AllArgsConstructor
    public class LineaPrevision {
        private Medicamento medicamento;

        private double stock;

        private double necesidad;

        private double saldo;
    }
}
