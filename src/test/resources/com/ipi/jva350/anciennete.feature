Feature: Calcul de la limite de congés permis par l'entreprise en fonction de l'ancienneté

  Scenario: Calcul de la limite de congés permis avec une ancienneté inférieure à 10 ans
    Given un mois de référence "2023-05-01"
    And un nombre de congés payés acquis l'année précédente de 20
    And un mois de début de contrat "2022-03-15"
    And un premier jour de congé "2023-06-01"
    And un dernier jour de congé "2023-06-05"
    When je calcule la limite de congés permis par l'entreprise
    Then la limite de congés permis est 21

  Scenario: Calcul de la limite de congés permis avec une ancienneté de 10 ans ou plus
    Given un mois de référence "2023-05-01"
    And un nombre de congés payés acquis l'année précédente de 20
    And un mois de début de contrat "2013-03-15"
    And un premier jour de congé "2023-06-01"
    And un dernier jour de congé "2023-06-05"
    When je calcule la limite de congés permis par l'entreprise
    Then la limite de congés permis est 26