package org.emma.appmockito.ejemplos.repositories;

import org.emma.appmockito.ejemplos.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositorioOtro implements ExamenRepository{

    @Override
    public List<Examen> findAll() {
        try{
            System.out.println("ExamenRespitorioOtro");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}


