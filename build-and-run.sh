#!/bin/bash

# Nome do container Docker e tag
CONTAINER_NAME="edsonwade126/university-management-system"
TAG="latest"

# Separador de execução
separator() {
  echo "========================================"
}

# Etapa 1: Limpar e construir o projeto Maven
separator
echo "Executando 'mvn clean install'..."
separator
mvn clean install
# shellcheck disable=SC2181
if [ $? -ne 0 ]; then
  echo "Erro durante 'mvn clean install'. Verifique os logs acima."
  exit 1
fi

# Etapa 2: Construir a imagem Docker
separator
echo "Construindo a imagem Docker..."
separator
docker buildx build -t "$CONTAINER_NAME:$TAG" .
# shellcheck disable=SC2181
if [ $? -ne 0 ]; then
  echo "Erro durante 'docker buildx'. Verifique os logs acima."
  exit 1
fi

# Etapa 3: Subir os containers com Docker Compose
separator
echo "Subindo os containers com Docker Compose..."
separator
docker compose up -d
# shellcheck disable=SC2181
if [ $? -ne 0 ]; then
  echo "Erro durante 'docker compose up'. Verifique os logs acima."
  exit 1
fi

# Mensagem de sucesso
separator
echo "Aplicação construída e rodando com sucesso!"
separator