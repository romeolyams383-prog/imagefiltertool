# Image Filter Tool

Un outil Web moderne de traitement d'images en temps réel développé avec **Java** et **Spring Boot**. Cette application permet d'importer une image, d'y appliquer divers filtres visuels (filtres basiques, effets de couleur et filtres style iOS) et de télécharger le résultat instantanément.

---

##  Fonctionnalités

- **Upload Drag & Drop** : Déposez directement vos images ou parcourez vos fichiers.
- **Traitement d'Image Pixels par Pixels** : Algorithmes faits maison en Java pur (`BufferedImage`) sans bibliothèque externe de traitement d'image.
- **Large Choix de Filtres** :
  - **Classiques** : Mono, Noir & Blanc, Négatif, Vintage, Flou, Contraste, Luminosité, Psychédélique.
  - **Style iOS (iPhone)** : Vivid, Vivid Warm, Vivid Cool, Dramatic, Silvertone.
- **Aperçu en Temps Réel** : Comparaison côte à côte de l'image originale et de l'image filtrée.
- **Interface Sombre & Minimaliste** : Design moderne et réactif (Dark Slate & Emerald).
- **Téléchargement Direct** : Export au format PNG en un clic.

---

## Technologies Utilisées

* **Backend** : Java 17+, Spring Boot (Spring Web), Apache Maven
* **Frontend** : HTML5, CSS3, JavaScript (Vanilla ES6)
* **API** : Restful / Multi-part FormData

---
Installation et Exécution Localement
Prérequis
JDK 17 ou version supérieure installée.

Maven installé (ou via le wrapper ./mvnw).

Étapes
1 Cloner le dépôt
git clone [https://github.com/votre-compte/imagefiltertool.git](https://github.com/votre-compte/imagefiltertool.git)
cd imagefiltertool

2 Lancer l'application
mvn spring-boot:run

3 Accéder à l'application
Ouvrez votre navigateur et rendez-vous sur :
http://localhost:8080/

Exposition Publique avec Ngrok
Pour partager l'application en ligne temporairement :

Démarrez Spring Boot sur le port 8080.

Dans un autre terminal, lancez le tunnel Ngrok :
( de preference.. l'invite des commandes)
ngrok http 8080
Partagez l'URL sécurisée générée (https://xxxx.ngrok-free.app).

 Licence
Ce projet est sous licence MIT — vous êtes libre de l'utiliser et de le modifier.
