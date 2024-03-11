package com.ipi.jva350.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntrepriseTest {

    //TDD
    // Mon code renvoyait une erreur étant donnée que les valeurs n'avaient pas été encore implémenter dans le model.
    // Le fait de réaliser ces tests en amont permettent de visualiser au mieux quelles sont les valeurs attendues par la fonction.
    @Test
    public void testEstDansPlageDateDansPlage() {
        // Given
        LocalDate d = LocalDate.of(2024, 2, 15);
        LocalDate debut = LocalDate.of(2024, 2, 10);
        LocalDate fin = LocalDate.of(2024, 2, 20);
        // When
        boolean result = Entreprise.estDansPlage(d, debut, fin);
        // Then
        assertEquals(true, result);
    }
    @Test
    public void testEstDansPlageDateAvantPlage() {
        // Given
        LocalDate d = LocalDate.of(2024, 2, 5);
        LocalDate debut = LocalDate.of(2024, 2, 10);
        LocalDate fin = LocalDate.of(2024, 2, 20);
        // When
        boolean result = Entreprise.estDansPlage(d, debut, fin);
        // Then
        assertEquals(false, result);
    }
    @Test
    public void testEstDansPlageDateApresPlage() {
        // Given
        LocalDate d = LocalDate.of(2024, 2, 25);
        LocalDate debut = LocalDate.of(2024, 2, 10);
        LocalDate fin = LocalDate.of(2024, 2, 20);
        // When
        boolean result = Entreprise.estDansPlage(d, debut, fin);
        // Then
        assertEquals(false, result);
    }

    //Tests Unitaires
    @ParameterizedTest
    @CsvSource({
            "2024-01-01,true",  // 1er janvier	Jour de l’an
            "2024-05-01,true",  // 1er mai	Fête du Travail
            "2024-05-08,true",  // 8 mai Fête de la Victoire
            "2024-02-23,false"  // Un jour qui n'est pas férié
    })
    //Given
    public void testEstJourFerie(String jour, boolean expectedResult) {
        // When
        boolean result = Entreprise.estJourFerie(LocalDate.parse(jour));
        // Then
        assertEquals(expectedResult, result);
    }

}
