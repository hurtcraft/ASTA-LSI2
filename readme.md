## Participant
- Lucas Vong
- Patrick Wu
- Julien Weng
## Lancement

## Lancement 
### Via le site (URL)
https://content-commitment-production-a926.up.railway.app/login
**Login :** admin@gmail.com
**Mot de passe :** admin

### Via docker (local)

- Placez-vous dans la racine du projet (où il y a le docker compose).
- Lancer la commande **docker compose up --build**
- Accès via http://localhost:8080/
- Inscrire un nouveau tuteur et se connecter
- Pour relancer supprimer la container et les volumes via la commande **docker compose down -v**

## Dashboard 

Une page unique pour gérer les apprentis (liste, recherche, détails en popup, CRUD) sans rechargement complet.

1) Architecture MVC → REST
- Séparation des contrôleurs de pages (MVC) et d'API (REST) ; le MVC appelent les API Rest.
- Comment : appels via WebClient, données injectées dans les vues Thymeleaf.
- Pourquoi : API réutilisables et documentées par Swagger ; meilleure testabilité.

2) Recherche dynamique avec suggestions
- Résultats en temps réel pendant la saisie.
- Comment : JavaScript + appels REST.
- Pourquoi : expérience fluide sans rechargement.

3) Affichage des détails en popup
- Visualition des détails d’un apprenti sans quitter le dashboard.
- Comment : chargement d’un fragment Thymeleaf via AJAX dans une popup Bootstrap.
- Pourquoi : garder le contexte, navigation rapide.

4) Création rapide d’entités liées
- Création d'entreprise/maître directement depuis le formulaire d’apprenti.
- Comment : popups “Ajouter” + POST REST + mise à jour de la liste déroulante.
- Pourquoi : éviter de quitter le formulaire et de perdre la saisie.

5) Message “Liste vide”
- Ce que j'ai fait : alerte claire lorsqu’il n’y a aucun apprenti.
- Comment : condition côté serveur avec Thymeleaf.
- Pourquoi : feedback immédiat, sans JavaScript.

6) Page d’édition complète
- Création de formulaire dédié pour modifier un apprenti.
- Comment : th:object / th:field ; envoi via contrôleur MVC vers l’API (PATCH).
- Pourquoi : cohérence, sécurité, redirection propre après modification.

7) Difficultés et solutions
- Iframe bloquée : autorisation same-origin dans la configuration de sécurité.

## Documentation swagger
- http://localhost:8080/swagger-ui/index.html#/
