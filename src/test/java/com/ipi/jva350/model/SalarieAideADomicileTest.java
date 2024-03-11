package com.ipi.jva350.model;


import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import com.ipi.jva350.service.SalarieAideADomicileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedHashSet;

public class SalarieAideADomicileTest {

    @Test
    public void testALegalementDroitADesCongesPayesNominal() {
        // GIVEN :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Paul", LocalDate.of(2023, 6, 20), LocalDate.now(), 20, 5, 120, 15, 8);
        // WHEN :
        boolean res = monSalarie.aLegalementDroitADesCongesPayes();
        // THEN :
        Assertions.assertTrue(res);
    }

    @Test
    public void testALegalementDroitADesCongesPayesDefaultValue() {
        // GIVEN :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Paul", LocalDate.of(2023, 6, 20), LocalDate.now(), 20, 5, 120, 15, 8);
        // WHEN :
        boolean res = monSalarie.aLegalementDroitADesCongesPayes();
        // THEN :
        Assertions.assertTrue(res);
    }

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

    @Test
    public void testEstHabituellementTravailleFalse() {
        // GIVEN :
        SalarieAideADomicile jourTravaille = new SalarieAideADomicile();
        // WHEN :
        boolean res = jourTravaille.estHabituellementTravaille(LocalDate.now( ));
        // THEN :
        Assertions.assertFalse(res, "si le jour est un jour de fin de semaine, le résultat doit être faux");
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

    @Mock
    private SalarieAideADomicileRepository salarieAideADomicileRepository;
    @InjectMocks
    private SalarieAideADomicileService salarieService = new SalarieAideADomicileService();
    @Test
    public void testAjouteConge() throws SalarieException {
        // Given :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Paul",
                LocalDate.of(2022, 6, 28), LocalDate.of(2023, 11, 1),
                9, 2.5,
                80, 20, 8);
        // When :
        salarieService.ajouteConge(monSalarie, LocalDate.of(2024, 12, 17),
                LocalDate.of(2024, 12, 18));
        // Then :
        ArgumentCaptor<SalarieAideADomicile> salarieAideADomicileCaptor = ArgumentCaptor.forClass(SalarieAideADomicile.class);
        Mockito.verify(salarieAideADomicileRepository, Mockito.times(1)).save(salarieAideADomicileCaptor.capture());
        Assertions.assertEquals(1L, salarieAideADomicileCaptor.getValue().getCongesPayesPrisAnneeNMoins1());
    }

    @Test
    public void testCalculeLimiteEntrepriseCongesPermis() {
        LocalDate moisEnCours = LocalDate.now();
        double congesPayesAcquisAnneeNMoins1 = 20.0;
        LocalDate moisDebutContrat = LocalDate.of(2023, 1, 1);
        LocalDate premierJourDeConge = LocalDate.of(2024, 7, 1);
        LocalDate dernierJourDeConge = LocalDate.of(2024, 7, 15);

        SalarieAideADomicileRepository salarieAideADomicileRepository = Mockito.mock(SalarieAideADomicileRepository.class);
        Mockito.when(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1()).thenReturn(0.0);

        SalarieAideADomicileService salarieAideADomicileService = new SalarieAideADomicileService();
        long limiteConges = salarieAideADomicileService.calculeLimiteEntrepriseCongesPermis(moisEnCours, congesPayesAcquisAnneeNMoins1, moisDebutContrat, premierJourDeConge, dernierJourDeConge);

        double proportionPondereeDuConge = Math.max(Entreprise.proportionPondereeDuMois(premierJourDeConge),
                Entreprise.proportionPondereeDuMois(dernierJourDeConge));
        double limiteAttendue = proportionPondereeDuConge * congesPayesAcquisAnneeNMoins1;
        limiteAttendue += 0.2 * congesPayesAcquisAnneeNMoins1;
        limiteAttendue += 0.1 * congesPayesAcquisAnneeNMoins1;
        limiteAttendue += Math.min(moisEnCours.getYear() - moisDebutContrat.getYear(), 10);

        BigDecimal limiteAttendueBd = new BigDecimal(Double.toString(limiteAttendue));
        limiteAttendueBd = limiteAttendueBd.setScale(3, RoundingMode.HALF_UP);
        long limiteAttendueArrondi = Math.round(limiteAttendueBd.doubleValue());

        Assertions.assertEquals(limiteAttendueArrondi, limiteConges);
    }
}
