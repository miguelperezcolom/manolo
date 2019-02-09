package io.mateu.vistas;

import io.mateu.mdd.core.MDD;
import io.mateu.mdd.core.annotations.DependsOn;
import io.mateu.mdd.core.util.Helper;
import io.mateu.model.Dispensacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.xml.ws.Action;
import java.time.LocalDate;
import java.util.List;

@Getter
public class Carrito {

    @Setter
    private boolean vistaResumida;

    private List<LineaCarrito> lineas;

    private List<LineaCarritoResumen> resumen;

    @DependsOn("vistaResumida")
    public boolean isLineasVisible() {
        return !vistaResumida;
    }

    @DependsOn("vistaResumida")
    public boolean isResumenVisible() {
        return vistaResumida;
    }

    public Carrito() {
        try {
            lineas = Helper.selectObjects("select new io.mateu.vistas.LineaCarrito(x.medicamento, x.paciente, x.dosisDiaria, x.observaciones) from Dosis x where x.fin is null order by x.medicamento.nombre, x.paciente.nombre");
            resumen = Helper.selectObjects("select new io.mateu.vistas.LineaCarritoResumen(x.medicamento, sum(x.dosisDiaria)) from Dosis x where x.fin is null group by x.medicamento order by x.medicamento.nombre");
        } catch (Throwable throwable) {
            MDD.alert(throwable);
        }
    }

    @Action
    public void dispensar(EntityManager em) {
        lineas.forEach(l -> {
            Dispensacion d = new Dispensacion();
            d.setDosis(l.getDosis());
            d.setFecha(LocalDate.now());
            d.setMedicamento(l.getMedicamento());
            d.setPaciente(l.getPaciente());
            em.persist(d);
        });
    }

}
