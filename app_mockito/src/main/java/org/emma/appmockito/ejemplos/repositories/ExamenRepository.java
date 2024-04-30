package org.emma.appmockito.ejemplos.repositories;

import org.emma.appmockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
}
