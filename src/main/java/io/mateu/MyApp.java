package io.mateu;

import io.mateu.mdd.core.MDD;
import io.mateu.mdd.core.annotations.Action;
import io.mateu.mdd.core.app.SimpleMDDApplication;
import io.mateu.mdd.core.util.Helper;
import io.mateu.model.*;
import io.mateu.vistas.Carrito;
import io.mateu.vistas.Prevision;

import java.util.List;

public class MyApp extends SimpleMDDApplication {

    @Action(order = 1)
    public Class doctores() { return Doctor.class; }

    @Action(order = 2)
    public Class medicamentos() { return Medicamento.class; }

    @Action(order = 3)
    public Class suministros() { return Suministro.class; }

    @Action(order = 4)
    public Class pacientes() { return Paciente.class; }


    @Action(order = 5)
    public Class dispensaciones() { return Dispensacion.class; }

    @Action(order = 6)
    public Class dosis() { return Dosis.class; }

    @Action(order = 8)
    public Class consumos() { return Consumo.class; }

    @Action(order = 9)
    public Class recetas() { return Receta.class; }

    @Action(order = 10)
    public Prevision prevision() { return new Prevision(); }

    @Action(order = 11)
    public Carrito carrito() { return new Carrito(); }


}