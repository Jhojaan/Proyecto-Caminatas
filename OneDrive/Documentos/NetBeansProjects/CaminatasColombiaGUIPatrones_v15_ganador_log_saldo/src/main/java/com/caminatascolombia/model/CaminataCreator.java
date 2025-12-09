
package com.caminatascolombia.model;

public abstract class CaminataCreator {

    protected CaminataDirector director;

    public CaminataCreator() {
        this.director = new CaminataDirector(new CaminataBuilder());
    }

    public abstract Caminata crearCaminata();
}
