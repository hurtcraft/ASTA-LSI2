
-- Full test data for asta_bd (generated, updated)
SET FOREIGN_KEY_CHECKS=0;

-- entreprise
INSERT INTO `entreprise` (`entreprise_id`, `entreprise_adresse`, `info_acces_locaux`, `raison_sociale`) VALUES
(1, '10 Rue de la Paix, Paris', 'Accès par interphone', 'Capgemini'),
(2, '5 Avenue des Champs, Paris', 'Entrée principale au RDC', 'Thales'),
(3, '22 Boulevard Victor, Paris', 'Badge obligatoire', 'Sopra Steria'),
(4, '1 Rue des Lilas, Paris', 'Accueil au 1er étage', 'Atos'),
(5, '14 Rue de l''Université, Paris', 'Porte C, réception', 'Orange Business');

-- majeur
INSERT INTO `majeur` (`majeur_id`, `label`) VALUES
(1, 'Cybersécurité'),
(2, 'IA & Data'),
(3, 'Développement Web'),
(4, 'Systèmes Embarqués'),
(5, 'Réseaux & Télécoms');

-- poste
INSERT INTO `poste` (`poste_id`, `label`) VALUES
(1, 'Ingénieur Logiciel'),
(2, 'Architecte Cloud'),
(3, 'Responsable Sécurité'),
(4, 'Lead Développeur'),
(5, 'Chef de Projet');

-- maitre_apprentissage
INSERT INTO `maitre_apprentissage` (`ma_id`, `ma_email`, `ma_nom`, `ma_prenom`, `ma_remarque`, `ma_telephone`, `password`, `poste_id`) VALUES
(1, 'jean.dupont@capgemini.com', 'Dupont', 'Jean', 'Encadre les projets Java', '0601020304', NULL, 4),
(2, 'marie.lefebvre@thales.com', 'Lefebvre', 'Marie', 'Focus sécurité embarquée', '0602030405', NULL, 3),
(3, 'philippe.martin@soprasteria.com', 'Martin', 'Philippe', 'Machine learning', '0603040506', NULL, 2),
(4, 'laura.moreau@atos.com', 'Moreau', 'Laura', 'Devops & CI/CD', '0604050607', NULL, 1),
(5, 'nicolas.bernard@orange.com', 'Bernard', 'Nicolas', 'Réseaux et support', '0605060708', NULL, 5);

-- user (TuteurEnseignant with password 'admin')
INSERT INTO `user` (`dtype`, `id`, `email`, `nom`, `password`, `prenom`) VALUES
('TuteurEnseignant', 1, 'paul.durand@efrei.fr', 'Durand', 'admin', 'Paul'),
('TuteurEnseignant', 2, 'claire.rousseau@efrei.fr', 'Rousseau', 'admin', 'Claire'),
('TuteurEnseignant', 3, 'marc.lefevre@efrei.fr', 'Lefevre', 'admin', 'Marc'),
('TuteurEnseignant', 4, 'sophie.morel@efrei.fr', 'Morel', 'admin', 'Sophie'),
('TuteurEnseignant', 5, 'nicolas.bernard@efrei.fr', 'Bernard', 'admin', 'Nicolas');

-- user_roles
INSERT INTO `user_roles` (`user_id`, `roles`) VALUES
(1, 'ROLE_TUTEUR_ENSEIGNANT'),
(2, 'ROLE_TUTEUR_ENSEIGNANT'),
(3, 'ROLE_TUTEUR_ENSEIGNANT'),
(4, 'ROLE_TUTEUR_ENSEIGNANT'),
(5, 'ROLE_TUTEUR_ENSEIGNANT');

-- apprenti (5 étudiants EFREI, years 2025-2027 as requested)
INSERT INTO `apprenti` (`apprenti_id`, `annee_academique_debut`, `annee_academique_fin`, `apprenti_email`, `apprenti_name`, `apprenti_prenom`, `programme`, `telephone`, `entreprise_id`, `ma_id`, `majeur_id`) VALUES
(1, 2025, 2026, 'luc.aslan@efrei.fr', 'Aslan', 'Luc', 'I2', '0611122233', 1, 1, 3),
(2, 2025, 2027, 'sophie.martin@efrei.fr', 'Martin', 'Sophie', 'I1', '0612233445', 2, 2, 1),
(3, 2026, 2027, 'amine.ben@efrei.fr', 'Ben', 'Amine', 'I3', '0613344556', 3, 3, 2),
(4, 2025, 2027, 'claire.dufour@efrei.fr', 'Dufour', 'Claire', 'I2', '0614455667', 4, 4, 4),
(5, 2026, 2027, 'thomas.leclerc@efrei.fr', 'Leclerc', 'Thomas', 'I1', '0615566778', 5, 5, 5);

-- rendu
INSERT INTO `rendu` (`rendu_id`, `commentaire`, `note_rendu`, `theme`, `type_rendu`) VALUES
(1, 'Prototype backend pour API', 15.5, 'API Account Management', 1),
(2, 'Analyse de vulnérabilités', 16.0, 'Audit sécurité réseau', 2),
(3, 'Modèle de scoring', 14.0, 'ML pipeline', 1),
(4, 'Application mobile', 13.5, 'App contrôle embarqué', 1),
(5, 'Optimisation routeurs', 12.5, 'QoS & Routage', 2);

-- soutenance
INSERT INTO `soutenance` (`soutenance_id`, `commentaires`, `date`, `note`) VALUES
(1, 'Soutenance réussie', '2024-06-12 14:00:00', 16),
(2, 'Très bon audit', '2024-06-15 10:30:00', 17),
(3, 'Bon travail sur ML', '2025-06-20 09:00:00', 15),
(4, 'Démo hardware solide', '2025-06-22 11:00:00', 14),
(5, 'Prestation réseau correcte', '2024-06-25 15:00:00', 13);

-- evaluation_ecole
INSERT INTO `evaluation_ecole` (`evaluation_ecole_id`, `feedback_enseignant`, `remarque`, `apprenti_id`, `rendu_id`, `soutenance_id`) VALUES
(1, 'Bonnes compétences backend', 'Peut améliorer la documentation', 1, 1, 1),
(2, 'Excellent sens sécurité', 'Très rigoureux', 2, 2, 2),
(3, 'Approche ML pertinente', 'Besoin d''optimisation', 3, 3, 3),
(4, 'Travail intégration', 'Tests à renforcer', 4, 4, 4),
(5, 'Compétences réseau solides', 'Améliorer performance', 5, 5, 5);

-- mission
INSERT INTO `mission` (`mission_id`, `commentaires`, `metier_cible`, `mot_cle`, `apprenti_id`, `entreprise_id`, `ma_id`) VALUES
(1, 'Développement d''API REST', 'Développeur Backend', 'Java, Spring', 1, 1, 1),
(2, 'Test d''intrusion et rapport', 'Ingénieur Sécurité', 'Pentest, Kali', 2, 2, 2),
(3, 'Construction pipeline ML', 'Data Scientist', 'Python, MLflow', 3, 3, 3),
(4, 'Déploiement CI/CD', 'DevOps', 'Docker, Jenkins', 4, 4, 4),
(5, 'Optimisation réseaux d''entreprise', 'Ingénieur Réseau', 'BGP, QoS', 5, 5, 5);

-- visite
INSERT INTO `visite` (`visite_id`, `commentaires`, `format`, `visite_date`) VALUES
(1, 'Visite de suivi', 1, '2024-03-10 09:00:00'),
(2, 'Visite technique', 2, '2024-04-15 14:30:00'),
(3, 'Visite client', 1, '2024-05-20 11:00:00'),
(4, 'Audit sécurité', 2, '2024-09-12 10:00:00'),
(5, 'Jury de projet', 1, '2025-06-10 09:30:00');

SET FOREIGN_KEY_CHECKS=1;
