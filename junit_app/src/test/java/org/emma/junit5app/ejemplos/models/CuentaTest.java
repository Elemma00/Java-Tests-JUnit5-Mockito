package org.emma.junit5app.ejemplos.models;

import org.emma.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    Cuenta cuenta;

    @BeforeAll
    static void beforeAllMethod() {
        System.out.println("Se ejecuta antes de iniciar los test");
    }

    @AfterAll
    static void afterAllMethod(){
        System.out.println("Se ejecuta después de terminar todos los test");
    }

    // Se ejecuta antes de cada test
    @BeforeEach
    void initMetodo() {
        cuenta = new Cuenta("Emmanuel", new BigDecimal("1000.12345"));
    }

    @Test
    @DisplayName("Probando nombre de la cuenta")
    void test_nombre_cuenta() {
        Cuenta cuenta = new Cuenta("Emmanuel", new BigDecimal("1000.12345"));
        assertEquals("Emmanuel", cuenta.getPersona());
    }

    @Test
    @DisplayName("Probando saldo de la cuenta")
    void test_saldo_cuenta() {
//        Cuenta cuenta = new Cuenta("Emmanuel", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Probando referencia de la cuenta")
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.999"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.999"));
        assertNotSame(cuenta, cuenta2);
    }

    @Test
    @DisplayName("Probando igualdad de la cuenta")
    void testIgualdadCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.999"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.999"));
        assertEquals(cuenta, cuenta2);
    }


    @Test
    @DisplayName("Probando debito en la cuenta")
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Emma", new BigDecimal("1000"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando crédito en la cuenta")
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Emma", new BigDecimal("1000"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando débito con fondos insuficientes")
    void testDebitoCuentaFondosInsuficientes() {
        Cuenta cuenta = new Cuenta("Emma", new BigDecimal("1000"));
        DineroInsuficienteException dineroInsuficienteException = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        assertEquals("Dinero insuficiente", dineroInsuficienteException.getMessage());
    }

    @Test
    @DisplayName("Probando transferencia entre cuentas")
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Emma", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("John", new BigDecimal("1500"));
        Banco banco = new Banco();
        banco.setNombre("Banco del Estado de Chile");
        banco.transferir(cuenta1, cuenta2, new BigDecimal(500));
        assertEquals("2000", cuenta1.getSaldo().toPlainString());
        assertEquals("2000", cuenta2.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Probando relacion Banco -> cuentas")
    void testRelacionCuentaBanco() {
        Cuenta cuenta = new Cuenta("Emma", new BigDecimal("1000"));
        Cuenta cuenta2 = new Cuenta("Holi", new BigDecimal("1000"));
        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);
        banco.setNombre("Banco del Estado de Chile");
        assertAll(() -> {
            assertEquals(2, banco.getCuentas().size(),"El banco no tiene las cuentas esperadas");
        }, () -> {
            assertEquals("Banco del Estado de Chile", cuenta.getBanco().getNombre());
        }, () -> {
            assertEquals("Emma", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Emma")).findFirst().get().getPersona());
        });

        assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Emma")));
    }
}