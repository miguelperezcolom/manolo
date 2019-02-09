package io.mateu.model;

import io.mateu.mdd.core.util.Helper;

import java.util.List;
import java.util.Optional;

public class Residencia {

    private static Residencia instancia = new Residencia();
    private boolean actualizando;

    public static Residencia get() {
        return instancia;
    }


    public void actualizarSaldos() {
        if (!actualizando) {

            actualizando = true;

            try {
                Helper.transact(em -> {

                    // ponemos los pacientes a 0
                    ((List<Consumo>)em.createQuery("select x from Consumo x").getResultList()).forEach(m -> {
                        m.setDosisConsumidas(0);
                        m.setDosisRecetadas(0);
                        m.setCredito(0);
                    });


                    ((List<Medicamento>)em.createQuery("select x from Medicamento x").getResultList()).forEach(m -> {
                        m.setStock(m.getDosisIniciales());
                        // aplicar suministros
                        m.getSuministros().forEach(s -> m.setStock(m.getStock() + s.getUnidades() * m.getDosisPorEnvase()));
                        // aplicar dispensaciones
                        m.getDispensaciones().forEach(d -> {
                            // actualizamos stock
                            m.setStock(m.getStock() - d.getDosis());
                            // actualizamos consumo
                            Optional<Consumo> consumo = d.getPaciente().getConsumos().stream().filter(c -> c.getMedicamento().equals(m)).findFirst();
                            consumo.ifPresent(c -> {
                                c.setDosisConsumidas(c.getDosisConsumidas() - d.getDosis());
                            });
                            if (!consumo.isPresent()) {
                                Consumo c;
                                d.getPaciente().getConsumos().add(c = new Consumo());
                                c.setMedicamento(m);
                                c.setPaciente(d.getPaciente());
                                c.setDosisConsumidas(d.getDosis());
                            }
                        });
                        m.getRecetas().forEach(r -> {
                            // actualizamos consumo
                            Optional<Consumo> consumo = r.getPaciente().getConsumos().stream().filter(c -> c.getMedicamento().equals(m)).findFirst();
                            consumo.ifPresent(c -> {
                                c.setDosisRecetadas(c.getDosisRecetadas() + r.getUnidades() * m.getDosisPorEnvase());
                            });
                            if (!consumo.isPresent()) {
                                Consumo c;
                                r.getPaciente().getConsumos().add(c = new Consumo());
                                c.setMedicamento(m);
                                c.setPaciente(r.getPaciente());
                                c.setDosisRecetadas(r.getUnidades() * m.getDosisPorEnvase());
                            }
                        });
                    });

                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            actualizando = false;
        }

    }

}
