package com.ipi.jva350.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.LinkedHashSet;

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

    @ParameterizedTest
    @CsvSource({
        "'2023-12-17', '2023-12-28', 9",
        "'2023-12-17', '2024-01-08', 17"
    })
    public void testCalculeJoursDeCongeDecomptesPourPlage(String dateDebut, String dateFin, int expectedNb) {
        //Given :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Alexis",
                LocalDate.of(2023, 6, 28),
                LocalDate.now(), 20, 2.5, 9,
                1,8);
        //When :
        LinkedHashSet<LocalDate> resNb = monSalarie.calculeJoursDeCongeDecomptesPourPlage(
                LocalDate.parse(dateDebut),
                LocalDate.parse(dateFin));
        //Then :
        Assertions.assertEquals(expectedNb, resNb.size());
    }
}
