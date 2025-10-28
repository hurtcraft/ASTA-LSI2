# Rapport de Projet ASTA-LSI2

**Participants :** Lucas Vong, Patrick Wu, Julien Weng

---

## 1. Identifiants de test

**Login :** A mettre 
**Mot de passe :** A mettre

---

## 2. Outillage

### 2.1. IDE utilisé
- **IntelliJ IDEA Ultimate** (recommandé pour Spring Boot)
- **Visual Studio Code** avec extensions Java

### 2.2. SGBD
- **MySQL 8.0**
- Base de données : `asta_bd`

---

## 3. Instructions de lancement

### 3.1. Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- MySQL
- Docker

### 3.2. Configuration de la base de données

La base de donnée se configure automatiquement via hibernate après avoir entré la commande : docker compose up --build
### 3.3. Lancement de l'application 
#### 3.3.1 Lancement via docker
- Exécuter la commande "docker compose up --build" et laissez l'application se lancer.
#### 3.3.2 Lancement via maven

- Exécuter la commande maven clean package
- Exécuter la commande java -jar target/ASTA_LSI2-0.0.1-SNAPSHOT.jar
- Un jeu de test est disponible dans ressources/data.sql



### 3.4. Accès à l'application

- **Interface web :** http://localhost:8080
- **Documentation API (Swagger) :** http://localhost:8080/swagger-ui/index.html
- **Page de connexion :** http://localhost:8080/login

### 3.5. Données de test

Au premier lancement, Spring Data JPA crée automatiquement les tables. Vous pouvez :
- Créer des apprentis via l'interface web
- Importer des données via la fonctionnalité d'import (si disponible)
- Utiliser les endpoints REST documentés sur Swagger

Si vous utilisez docker, celui-ci importera automatiquement des données de test (data.sql)

---

## 4. Réponses aux questions

### a) Aspects du travail à mettre en avant

**1. Architecture MVC → REST cohérente**
- **Ce que nous avons fait :** Séparation stricte entre contrôleurs MVC (vues Thymeleaf) et contrôleurs REST (API documentées).
- **Comment :** Les contrôleurs MVC appellent les API REST internes via WebClient pour récupérer/modifier les données.
- **Pourquoi :** Réutilisabilité des API (mobile, autre frontend), documentation avec Swagger, meilleure testabilité.

**2. Recherche dynamique avec suggestions en temps réel**
- **Ce que nous avons fait :** Barre de recherche qui affiche les résultats instantanément pendant la frappe.
- **Comment :** JavaScript avec debounce (300ms) + appels AJAX vers endpoints REST + filtrage multi-critères (nom, entreprise, mission, année).
- **Pourquoi :** Expérience utilisateur fluide sans rechargement de page.

**3. Création rapide d'entités liées**
- **Ce que nous avons fait :** Possibilité de créer une entreprise ou un maître d'apprentissage directement depuis le formulaire d'apprenti.
- **Comment :** pop ups Bootstrap + appels REST + mise à jour automatique des listes déroulantes.
- **Pourquoi :** Évite de quitter le formulaire et de perdre sa saisie, workflow plus productif.

**4. Affichage des détails en pop up**
- **Ce que nous avons fait :** Consultation rapide des détails d'un apprenti sans quitter le dashboard.
- **Comment :** Fragments Thymeleaf chargés via AJAX dans une pop up Bootstrap.
- **Pourquoi :** Navigation fluide, contexte préservé, pas de rechargement complet.

**5. Séparation des environnements**
- **Séparation de l'envrionnement de dev et de prod via application-dev.properties application-prod.properties et application-secret.properties

---

### b) Plus grande difficulté rencontrée et solution

**Problème principal : Authentification perdue dans les appels REST internes**

**Symptôme :** Lors des appels MVC → REST internes via WebClient, nous obtenions des erreurs 401 Unauthorized systématiquement.

**Cause :** Spring Security utilise un cookie JSESSIONID pour identifier l'utilisateur connecté. WebClient ne transmettait pas automatiquement ce cookie lors des appels HTTP internes.

**Solution implémentée :**
1. Extraction manuelle du cookie JSESSIONID depuis la requête HTTP entrante
2. Ajout explicite du cookie dans chaque appel WebClient :
   ```java
   .cookies(cookies -> cookies.add("JSESSIONID", jsessionId))
   ```
3. Configuration du WebClient pour accepter et transmettre les cookies

**Difficulté secondaire : Iframe bloquée par Spring Security**

**Symptôme :** La page `register.html` refusait de se charger dans l'iframe ("localhost refused to connect").

**Cause :** Spring Security bloque par défaut les iframes (protection contre le clickjacking).

**Solution :** Configuration de Spring Security pour autoriser les iframes same-origin :
```java
.frameOptions(frameOptions -> frameOptions.sameOrigin())
```

---

### c) Contribution de chaque membre de l'équipe

**À compléter selon votre répartition réelle :**

- **Lucas Vong :** 

- Dashboard (année en cours, tuteur)
- Message “La liste est vide. Ajoutez au moins un apprenti”
- Détails apprenti
- Édition des champs (page dédiée)

- **Patrick Wu :** 

- Implémentation de l'architecture global du site, frontend

- **Julien Weng :**

-gestion des connexions/inscriptions sécurisé via spring security
-sécurisation des routes
-code flexible pour rajouter des utilisateurs qui peuvent se connecter (extends de USER)
-configuration d'une RestApi en plus des controllers,  cela reduit la dépendance entre le BACK et le FRONT
---

### d) Trois points à retenir du cours et du projet
- Thymeleaf natif c'est moche
- Le cours est excellent : il explore tous les détails, jusqu’au fonctionnement interne de la JVM et de Spring.
- La puissance de Spring réside dans sa simplicité d’implémentation et le grand nombre de modules déjà disponibles.



### e) Fonctionnalités Bonus non implémentées

**1. Messages de confirmation/erreur après modification**
- **Raison :** Contrainte de temps, priorité donnée aux fonctionnalités CRUD de base
- **Solution prévue :** FlashAttributes de Spring pour afficher des messages temporaires après redirection

**3. Pagination de la liste des apprentis**
- **Raison :** Nombre limité d'apprentis dans le contexte académique, la recherche dynamique suffit
- **Solution future :** Spring Data JPA offre la pagination nativement (Pageable)

**4. Gestion complète des visites et soutenances**
- **État actuel :** Modèles créés (Visite, Soutenance, EvaluationEcole) mais pas d'interface utilisateur
- **Priorité :** Focalisé sur la gestion des apprentis (CRUD complet)

**5. Export / Import de données (Excel, PDF)**
- **Raison :** Fonctionnalité bonus, pas prioritaire

**6. Inscription/connexion apprenti et maitre d'apprentissage**
- **État actuel :** Le code est assez modulable pour implémenter ces fonctionnalitées facilement, certaines routes sont deja prête 
- **Raison :** Fonctionnalité bonus, pas prioritaire

**7. Liste déroulante dynamique**
- **État actuel :** L'affichage se fait bien, toutefois la logique métier reste à implémenter
- **Raison :** Fonctionnalité bonus, pas prioritaire



---

### f) Respect des principes SOLID

**S - Single Responsibility Principle — Respecté**
- Chaque classe a une seule responsabilité clairement définie
- Exemples :
  - `ApprentiService` : uniquement la logique métier des apprentis
  - `ApprentiRepository` : uniquement l'accès aux données
  - `ApprentiController` (REST) : uniquement les endpoints API
  - `DashboardController` (MVC) : uniquement le rendu des vues

**O - Open/Closed Principle — Partiellement respecté**
- Utilisation d'interfaces (`JpaRepository`) permet l'extension sans modification
- Les contrôleurs ne sont pas extensibles, ajout de fonctionnalités nécessite modification directe
- **Amélioration possible :** Créer des interfaces pour les services

**L - Liskov Substitution Principle — Respecté**
- Tous les repositories héritent de `JpaRepository` et peuvent être substitués sans problème
- Les implémentations respectent les contrats de leurs interfaces parentes
- Exemple : `ApprentiRepository` peut être remplacé par un mock en test sans casser le code

**I - Interface Segregation Principle — Partiellement respecté**
- Repositories spécialisés par entité (pas de "super-repository" avec trop de méthodes)
- Pas d'interfaces explicites pour les services (dépendance directe sur les classes concrètes)
- **Amélioration possible :** Créer `IApprentiService`, `IAuthService`, etc.

**D - Dependency Inversion Principle — Respecté**
- Injection de dépendances systématique via constructeur
- Les contrôleurs dépendent des abstractions (`JpaRepository`) et non des implémentations
- Configuration Spring gère l'instanciation et l'injection automatiquement
- Facilite les tests unitaires (injection de mocks)

Le projet respecte bien les principes fondamentaux (S, L, D). Les améliorations concernent principalement la création d'interfaces explicites pour les services, ce qui renforcerait les principes O et I.

---

## 5. Stack technique complète

**Backend :**
- Spring Boot 3.5.6
- Spring MVC (contrôleurs de vues)
- Spring WebFlux (WebClient pour appels REST internes)
- Spring Security (authentification JWT)
- Spring Data JPA (persistence)
- MySQL Connector

**Frontend :**
- Thymeleaf (template engine)
- Bootstrap 5.3.2 (UI)
- JavaScript Vanilla (interactions dynamiques)
- Fetch API (appels AJAX)

**Documentation :**
- SpringDoc OpenAPI 2.8.13 (Swagger UI)

**Outils de développement :**
- Lombok (réduction boilerplate)
- Spring Boot DevTools (hot reload)
- Maven (gestion de dépendances)
- Docker (Image)


---
