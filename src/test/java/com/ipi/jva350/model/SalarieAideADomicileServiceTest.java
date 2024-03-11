package com.ipi.jva350.model;

import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import com.ipi.jva350.service.SalarieAideADomicileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class SalarieAideADomicileServiceTest {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Autowired
    private SalarieAideADomicileRepository salarieRepository;

    @Test
    public void testCalculeLimiteEntrepriseCongesPermis() {
        // GIVEN
        LocalDate moisEnCours = LocalDate.of(2024, 3, 1);
        double congesPayesAcquisAnneeNMoins1 = 20;
        LocalDate moisDebutContrat = LocalDate.of(2023, 6, 28);
        LocalDate premierJourDeConge = LocalDate.of(2024, 3, 1);
        LocalDate dernierJourDeConge = LocalDate.of(2024, 3, 5);

        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Paul",
                LocalDate.of(2023, 6, 28), LocalDate.of(2023, 11, 1),
                9, 2.5,
                80, 20, 8);
        monSalarie.setCongesPayesAcquisAnneeNMoins1(congesPayesAcquisAnneeNMoins1);
        salarieRepository.save(monSalarie);

        // WHEN
        long limiteConges = salarieAideADomicileService.calculeLimiteEntrepriseCongesPermis(moisEnCours,
                congesPayesAcquisAnneeNMoins1, moisDebutContrat, premierJourDeConge, dernierJourDeConge);

        // THEN
        BigDecimal expectedLimiteConges = new BigDecimal(16);
        Assertions.assertEquals(expectedLimiteConges.longValue(), limiteConges);
    }

    // En prenant donc en compte le nombre de congés pris et acquis, l'ancienneté du salarié Paul ainsi que sa période de congé spécifique.
    // On en conclut que sa limite de jours de congès permis est donc égal à 16.

}
