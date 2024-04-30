package org.emma.junit5app.ejemplos.models;

import org.emma.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase que representa una cuenta bancaria
 * @version 1.0
 * @author Elemma00
 */

public class Cuenta {
    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    /**
     * Constructor de la clase
     * @param persona Nombre de la persona
     * @param saldo Saldo de la cuenta
     */

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    /**
     * Método que permite debitar un monto de la cuenta
     * @param monto Monto a debitar
     * @throws DineroInsuficienteException Si el monto a debitar es mayor al saldo de la cuenta
     */
    public void debito(BigDecimal monto) throws DineroInsuficienteException{
        BigDecimal saldoFinal = this.saldo.subtract(monto);
        if(saldoFinal.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero insuficiente");
        }
        this.saldo = saldoFinal;
    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(persona, cuenta.persona) && Objects.equals(saldo, cuenta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, saldo);
    }
}
