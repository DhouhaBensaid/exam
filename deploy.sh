#!/bin/bash

# Variables
APP_NAME="gestion-examens"
JAR_FILE="target/gestion-examens-0.0.1-SNAPSHOT.jar"
SERVER_USER="votre_utilisateur"
SERVER_HOST="votre_serveur.com"
SERVER_DIR="/opt/gestion-examens"

# Construire l'application
echo "Construction de l'application..."
./mvnw clean package -DskipTests

# Copier le fichier JAR sur le serveur
echo "Copie du fichier JAR sur le serveur..."
scp $JAR_FILE $SERVER_USER@$SERVER_HOST:$SERVER_DIR/$APP_NAME.jar

# Créer le fichier de service systemd
echo "Configuration du service systemd..."
cat > $APP_NAME.service << EOF
[Unit]
Description=Gestion Examens Spring Boot Application
After=syslog.target network.target

[Service]
User=gestion-examens
WorkingDirectory=$SERVER_DIR
ExecStart=/usr/bin/java -jar $SERVER_DIR/$APP_NAME.jar --spring.profiles.active=prod
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

# Copier le fichier de service sur le serveur
scp $APP_NAME.service $SERVER_USER@$SERVER_HOST:/tmp/

# Configurer le service sur le serveur
ssh $SERVER_USER@$SERVER_HOST << EOF
  sudo mv /tmp/$APP_NAME.service /etc/systemd/system/
  sudo systemctl daemon-reload
  sudo systemctl enable $APP_NAME.service
  sudo systemctl restart $APP_NAME.service
  sudo systemctl status $APP_NAME.service
EOF

echo "Déploiement terminé !"
