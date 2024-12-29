
# README - Application de Résolution d'Équations Polynômiales Basée sur les Microservices

##  **Description du Projet**
Cette application repose sur une architecture microservices pour la résolution d'équations polynomiales, combinant des méthodes analytiques, numériques et des technologies avancées d'intelligence artificielle.

L'application repose sur des microservices Spring pour implémenter les méthodes de résolution analytiques (linéaire, quadratique, Cardano et Ferrari) et numériques (Newton-Raphson, Bisection, Bairstow, Points Fixes et Müller) pour la résolution et la factorisation des équations polynomiales. Elle inclut également un microservice qui communique avec Gemini Flash 1.5 à travers le framework REST d'OpenAI pour fournir des explications détaillées du processus de résolution des équations.

L'application permet de :
- Saisir directement des équations polynomiales.
- Télécharger une image contenant une équation.
- Fournir une écriture manuscrite.
- Choisir le domaine de résolution (réel ou complexe).
- Sélectionner la méthode de résolution (analytique ou numérique).

Grâce à l'intégration de modèles de langage avancés (LLM, Gemini 1.5 Flash) et des API OCR (Google Cloud Vision), l'application garantit précision, modularité et scalabilité.

---

##  **Technologies Utilisées**
- **Backend :** Spring Boot
- **Frontend :** Angular
- **Passerelle API :** Spring Cloud Gateway
- **Découverte de Services :** Netflix Eureka
- **Conteneurisation :** Docker
- **OCR :** Google Cloud Vision API
- **IA :** Gemini 1.5 Flash

---

##  **Architecture du Système**
L'application suit une architecture microservices modulaire avec les composants principaux suivants :
1. **Client REST API (Frontend)** : Interface utilisateur intuitive pour saisir et afficher les résultats.
2. **Spring Cloud API Gateway (Passerelle API)** : Point d'entrée central pour toutes les requêtes.
3. **Service Discovery (Netflix Eureka)** : Découverte dynamique des microservices.
4. **Microservices Spécialisés :**
    - **OCR Service** : Extraction de texte à partir d'images.
    - **Analytical Services** : Résolution via des méthodes analytiques (LinearResolution, QuadraticResolution, CardanosResolution, FerrariResolution).
    - **Numerical Services** : Approximation des racines avec des algorithmes numériques (NewtonResolution, BisectionResolution, MüllerResolution, BairstowResolution, FixedPointResolution).
    - **LLM Service (Gemini)** : Analyse et explication pédagogique des solutions.
  ![architecture](https://github.com/user-attachments/assets/a646174a-ea61-486d-9f37-66f19df5fca1)
    

---

## **Flux de Travail Typique**
1. **Entrée Utilisateur :** Saisie directe, image ou écriture manuscrite.
2. **Traitement API Gateway :** Acheminement vers les microservices appropriés.
3. **Analyse & Résolution :** Application des méthodes analytiques ou numériques.
4. **Interaction avec LLM :** Explication détaillée et itérative des résultats.
5. **Affichage des Résultats :** Retour structuré au frontend.
s

https://github.com/user-attachments/assets/45f6d4d8-d6df-41b6-9f15-d7195be1340b


---
## Interfaces Utilisateurs

### 1. *Page d'accueil*
![Homepage](homepage.png)

- *Description* : Cette interface accueille les utilisateurs avec un message "Welcomes You". Les utilisateurs peuvent choisir de commencer ou d'apprendre davantage sur la résolution d'équations polynômiales .
- *Boutons principaux* : 
  - "Get Started" : Pour accéder directement aux fonctionnalités de résolution.
  - "Learn More" : Pour explorer les concepts de base des polynômes, leurs types et les méthodes de résolution.

### 2. *Galerie d'explications interactives*
![Learn More](learnmore.png)

- *Description* : Une section détaillant les concepts de base des polynômes, leurs types et les méthodes de résolution.
- *Catégories* :
  - Qu'est-ce qu'un polynôme ?
  - Types de polynômes.
  - Méthodes analytiques.
  - Méthodes numériques.

### 3. *Saisie manuscrite et OCR*
![Saisie Manuscrite](saisie_manuscrite.png)

- *Description* : Une interface intuitive permettant aux utilisateurs de dessiner une équation ou d’importer une image. La reconnaissance OCR transforme ces saisies en texte exploitable.
- *Fonctionnalités principales* :
  - Bouton "Clear" : Efface la saisie.
  - Bouton "Save" : Enregistre l’équation pour traitement.

### 4. *Résolution d'équations*
![Solver Interface](Screenshot%202024-12-29%20122927.png)

- *Description* : Une interface pour entrer directement une équation ou en sélectionner une depuis l’OCR.
- *Options* :
  - Choix de méthodes : Numérique ou Analytique.
  - Type de racines : Réelles ou Complexes.
- *Résultats* : Affiche les solutions, les racines, et les formes factorisées avec une option pour une résolution détaillée.

---

## **Déploiement**
### Prérequis :
- Docker
- Node.js (pour Angular)
- Java 11+ (pour Spring Boot)

### Étapes :
1. Clonez le dépôt :
   ```bash
   git clone <https://github.com/Imane049/ResolutionPolynomes>
   ```

2. Service OCR (Google Cloud Vision API)

   - Créez un compte de service Google Cloud.

   - Générez un fichier JSON d'authentification et un token d'accès.

   - Placez le fichier JSON et le token dans les propriétés de l'application OCR (`application.properties`).



3. Service Gemini (Gemini 1.5 Flash)

   - Générez une clé d'API pour Gemini.

   - Ajoutez cette clé dans les propriétés du service Gemini (`application.properties`).






---
4. Construisez les microservices avec Maven :
   ```bash
   mvn clean package
   ```
5. Démarrez les conteneurs Docker :
   ```bash
   docker-compose build -d
   ```
6. Démarrez le client FrontEnd :
   ```bash
   npm install
   ng serve
   ```
7. Accédez à l'application :
   ```
   http://localhost:4200
   ```



## **Auteurs**

- **Imane BARAKATE**
- **Oumaima DAGOUN**
- **Leila MEKIANI**
- **Aya NOR ELYAKINE**
- **Badr Eddine SLIOUI**



