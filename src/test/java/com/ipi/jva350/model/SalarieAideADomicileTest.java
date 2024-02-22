package com.ipi.jva350.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SalarieAideADomicileTest {
    @Test
    public void testALegalementDroitADesCongesPayesTrue() {
        // Given :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile(
                "Alexis", LocalDate.of(2023, 6, 28), LocalDate.now(),
                80, 10, 5,
                20,5);
        // When :
        boolean res = monSalarie.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(true, res);
    }

    @Test
    public void testALegalementDroitADesCongesPayesFalse() {
        // Given :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile(
                "Alexis", LocalDate.of(2023, 6, 28),
                LocalDate.now(), 20, 10, 4,
                1,8);
        // When :
        boolean res = monSalarie.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res, "Avec 4 jours travaillés en N-1, le résultat doit être faux");
    }
    @Test
    public void testEstHabituellementTravaille() {
        SalarieAideADomicile travaille = new SalarieAideADomicile();
        boolean res = travaille.estHabituellementTravaille(LocalDate.now());
        Assertions.assertEquals(true, res);
    }
}
