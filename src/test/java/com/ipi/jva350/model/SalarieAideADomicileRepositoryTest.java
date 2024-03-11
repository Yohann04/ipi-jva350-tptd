package com.ipi.jva350.model;

import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class SalarieAideADomicileRepositoryTest {

    @Autowired
    private SalarieAideADomicileRepository salarieRepository;

    @Test
    public void testPartCongesPrisTotauxAnneeNMoins1() {
        //GIVEN :
        SalarieAideADomicile monSalarie = new SalarieAideADomicile("Paul",
                LocalDate.of(2022, 6, 28), LocalDate.of(2023, 11, 1),
                9, 2.5,
                80, 20, 12);
        monSalarie.setCongesPayesPrisAnneeNMoins1(12);
        monSalarie.setCongesPayesAcquisAnneeNMoins1(20);
        salarieRepository.save(monSalarie);

        // WHEN :
        Double partCongesPrisTotaux = salarieRepository.partCongesPrisTotauxAnneeNMoins1();

        //THEN :
        boolean res = partCongesPrisTotaux > 0.5;
        Assertions.assertTrue(res, "si le salarie à déjà pris plus de 50% de ces jours de congés acquis, le résultat doit être vrai");

        //Ce test permet donc de savoir si oui ou non le salarie à déjà pris plus des 50% de ces jours de congés acquis
    }

}
