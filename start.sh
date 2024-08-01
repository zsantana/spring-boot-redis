#!/bin/bash

# Passo 1: Iniciar os serviços do Docker
echo "Iniciando os serviços do Docker..."
docker compose up -d

# Passo 2: Verificar se o Redis está pronto para conexões (opcional, mas recomendado)
echo "Aguardando o Redis ficar disponível..."
sleep 3 # Ajuste o tempo conforme necessário para garantir que o Redis esteja pronto

# Passo 3: Executar o aplicativo Java
echo "Executando o aplicativo Java..."
java -jar ./target/demo-0.0.1-SNAPSHOT.jar

# Passo 4: Parar os serviços do Docker (opcional, se desejar parar após a execução)
# docker-compose down
