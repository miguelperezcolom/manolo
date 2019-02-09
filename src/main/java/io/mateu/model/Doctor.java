package io.mateu.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity@Getter@Setter
public class Doctor {

    @Id@GeneratedValue
    private long id;

    @NotEmpty
    private String nombre;


    @Override
    public String toString() {
        return nombre;
    }
}
