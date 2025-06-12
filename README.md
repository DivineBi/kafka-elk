#  **README - Application Kafka + Spring Boot + Elasticsearch + Kibana**  

## 🚀 **Introduction**  
Cette application **Spring Boot** utilise **Kafka** pour traiter les taux de change en temps réel, **Elasticsearch** pour stocker les données, et **Kibana** pour les visualiser.  

###  **Objectifs**  
 **Collecter les taux de change** depuis une API externe  
 **Envoyer les données dans Kafka** pour un traitement asynchrone  
 **Stocker les taux de change dans Elasticsearch**  
 **Visualiser les données avec Kibana**  

---

## 🏗 **Architecture du projet**  
 **Flux de données** :  
1️⃣ **Spring Boot récupère les taux de change** via `WebClientConfig.java`  
2️⃣ **Les données sont envoyées à Kafka** via `MessageProducer.java`  
3️⃣ **Kafka les transmet à un consommateur** (`MessageConsumer.java`)  
4️⃣ **Les taux de change sont stockés dans Elasticsearch** (`ExchangeRate.java`)  
5️⃣ **Kibana permet de visualiser les données**  

 **Diagramme simplifié** :  
```
[API Taux de Change] → [Kafka Producer] → [Kafka Topic] → [Kafka Consumer] → [Elasticsearch] → [Kibana Dashboard]
```

---

## 📂 **Structure du projet**  
```
/java/com.learn.kafka
│── /config
│   ├── WebClientConfig.java          # Configuration du client Web pour récupérer les taux de change
│── /consumer
│   ├── KafkaConsumerConfig.java      # Configuration du consommateur Kafka
│   ├── MessageConsumer.java          # Consommateur Kafka qui traite les messages
│── /controller
│   ├── ExchangeRateController.java   # API REST pour récupérer les taux de change
│── /dto
│   ├── ExchangeRateResponse.java     # Data Transfer Object pour structurer la réponse des taux de change
│── /model
│   ├── ExchangeRate.java             # Modèle de données stocké dans Elasticsearch
│── /producer
│   ├── KafkaProducerConfig.java      # Configuration du producteur Kafka
│   ├── MessageProducer.java          # Producteur Kafka qui envoie les taux de change
│── /scheduler
│   ├── ScheduledExchangePublisher.java # Tâche planifiée pour récupérer les taux de change régulièrement
```

---

## ⚙️ **Installation et configuration**  

### 🔹 **1. Prérequis**  
 **Java 17+**  
 **Docker & Docker Compose**  
 **Kafka & Zookeeper**  
 **Elasticsearch & Kibana**  

### 🔹 **2. Cloner le projet**  
```bash
git clone https://github.com/DivineBi/kafka-elk.git
cd kafka-elk
```

### 🔹 **3. Lancer les services avec Docker**  
```bash
docker-compose up -d
```
💡 **Cela démarre Kafka, Zookeeper, Elasticsearch et Kibana.**

### 🔹 **4. Application Spring Boot**  
```IDE
IntelliJ avec Maven
```

---

## 🚀 **Utilisation de l'API**  

### 🔹 **1. Récupérer les taux de change via l’API REST**  
```bash
curl -X GET "http://localhost:8080/api/exchange/USD"
```
 **Réponse JSON attendue :**  
```json
{
  "base": "USD",
  "date": "2025-06-12",
  "rates": {
    "EUR": 0.92,
    "GBP": 0.78,
    "JPY": 144.78
  }
}
```

### 🔹 **2. Vérifier les données dans Elasticsearch**  
```bash
curl -X GET "http://localhost:9200/exchange-rates/_search?pretty"
```

### 🔹 **3. Visualiser les données dans Kibana**  
📌 **Accéder à Kibana :**  
```
http://localhost:5601
```
💡 **Configurer une Data View (`exchange-rates*`) et explorer les données dans Discover.**

---

##  **Développement et amélioration**  


### 🔹 **Intégrer un système d’alerte**  
Utilise **Kafka Streams** pour détecter des variations importantes et envoyer des notifications.

---

##  **Ressources utiles**  
🔗 [Spring Boot + Kafka Guide](https://www.baeldung.com/spring-kafka)  
🔗 [Spring Data Elasticsearch](https://www.baeldung.com/spring-data-elasticsearch-tutorial)  
🔗 [Kibana Dashboard](https://www.elastic.co/guide/en/kibana/current/dashboard.html)  

---

##  **Conclusion**  
Cette application **automatise la collecte, le traitement et la visualisation des taux de change** en utilisant **Kafka, Spring Boot, Elasticsearch et Kibana**. 🚀  

💡 **Tu peux maintenant tester, améliorer et déployer cette solution en production !**   

---

### ✨ **Que la force soit avec toi!** 🔥
