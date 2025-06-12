# Spring Batch FooBarQuix - Kata de transformation via API & Batch

Kata implementé avec **Spring Boot** et **Spring Batch**, intégrant :
- une transformation `FooBarQuix` via API REST,
- un traitement par batch avec lecture/écriture de fichiers,
- une gestion d’erreurs centralisée,
- des réponses JSON standardisées.

---

## Stack technique

| Outil / Lib        | Version     |
|--------------------|-------------|
| Java               | 17          |
| Spring Boot        | 3.5.0       |
| Spring Batch       | 5.2.2       |
| Spring Web         | 6.2.7       |
| JUnit              | 5.12.2      |
| Mockito            | 5.17.0      |
| MockMvc            | intégré     |
| H2 Database        | 2.3.232     |
| Maven              | 3.8+        |



## Lancer le projet

### Prérequis
- Java 17 installé
- Maven 3.8+ installé

### Commande pour exécuter l'application :

```bash
mvn spring-boot:run
```

### Accès aux endpoints

- API REST :
  - [http://localhost:8080/api/transform/15](http://localhost:8080/api/transform/15)
  - [http://localhost:8080/api/batch/run](http://localhost:8080/api/batch/run)

### Accès à la console H2 (facultatif)

- URL : [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL : `jdbc:h2:mem:testdb`
- User : `sa`
- Password : _(laissé vide)_


## Fonctionnalités

### 1. Transformation par API
- **GET** `/api/transform/{number}`
- Retourne une chaîne transformée selon les règles "FooBarQuix" :
    - multiple de 3 → "FOO"
    - multiple de 5 → "BAR"
    - multiple de 7 → "QUIX"
    - chaque chiffre 3, 5, 7 dans le nombre → respectivement "FOO", "BAR", "QUIX"

### 2. Lancement d’un batch
- **POST** `/api/batch/run`
- Exécute un job Spring Batch :
    - lit un fichier ligne par ligne,
    - applique la transformation,
    - écrit le résultat dans un fichier de sortie.

---

## Configuration

Dans cet exemple , le fichier **input.txt** est placé a la racine.
mais l'emplacement du fichier (input et output) ainsi que leurs noms sont configurables via le fichier application.yml

```properties
# Chemins définis dans application.yml
batch:
  input-file: input.txt
  output-file: output.txt
```

## Format de réponse JSON

Toutes les réponses HTTP sont encapsulées dans un format standardisé :

### Réponse en cas de succès :
```json
{
  "success": true,
  "message": "Succès",
  "data": "FOOBAR"
}
```


### Réponse en cas d'erreur :
```json
{
  "success": false,
  "message": "Le nombre 105 n'est pas valide. Il doit être entre 0 et 100.",
  "data": null
}
```

## Tests

Le projet inclut :

- **Tests unitaires** sur la logique métier (service de transformation),
- **Tests d'intégration** via MockMvc pour les endpoints REST.

### Outils utilisés

- [x] **JUnit 5**
- [x] **Mockito**
- [x] **MockMvc (Spring Boot Test)**

Exemples de tests :
- **GET /api/transform/3** → retourne FOOFOO en payload (champs data)

- **GET /api/transform/105** → retourne un code HTTP 400 (Bad Request)

- **POST /api/batch/run** → lance le traitement batch (succès ou erreur)

### Execution des tests

```mvn
  mvn clean test
```

## Structure du projet

```bash
src
├── main
│   ├── java/com.batch.foobarquix
│   │   ├── controller       # Endpoints REST
│   │   ├── service          # Logique métier
│   │   ├── batch            # Configuration Spring Batch
│   │   ├── config           # Configuration centralisée (@ConfigurationProperties)
│   │   ├── exception        # Gestion d'exceptions globale
│   │   └── util             # Constantes partagées
│   └── resources
│       └── application.properties
├── test                    # Tests unitaires & d'intégration
```


## Pistes d’amélioration

-  **Externaliser les règles métier** dans un fichier (XML, JSON) pour les rendre dynamiques et modifiables sans recompilation.
-  **Générer un rapport de traitement** après chaque exécution batch (succès/échecs, métriques).
-  **Ajouter un pipeline CI/CD** (ex. GitHub Actions).
-  **Intégrer une documentation Swagger/OpenAPI** ou une IHM pour le déclenchement.


## Auteur
Sifokl
