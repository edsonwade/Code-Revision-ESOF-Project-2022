# University Management System - Build and Run Paco Style 🚀
# This script builds the Maven project and starts the Docker environment.

$ErrorActionPreference = "Stop"

function Write-Separator {
    Write-Host "========================================" -ForegroundColor Cyan
}

# Step 1: Clean and Build Maven Project
Write-Separator
Write-Host "Running './mvnw clean install'..." -ForegroundColor Yellow
Write-Separator

try {
    .\mvnw.cmd clean install
} catch {
    Write-Host "`n[ERROR] Maven build failed. Check the logs above." -ForegroundColor Red
    exit 1
}

# Step 2: Build Docker Images Paco Style (Fresh & High-Grade)
Write-Separator
Write-Host "Rebuilding Docker images from scratch..." -ForegroundColor Yellow
Write-Separator

try {
    # Force rebuild of the frontend and backend to avoid stale cache Paco style
    docker compose down --remove-orphans
    docker compose build --no-cache
} catch {
    Write-Host "`n[ERROR] Docker build failed. Check your Docker Desktop status." -ForegroundColor Red
    exit 1
}

# Step 3: Orchestrate with Docker Compose (Clean Start)
Write-Separator
Write-Host "Starting high-grade containers..." -ForegroundColor Yellow
Write-Separator

try {
    docker compose up -d --force-recreate
} catch {
    Write-Host "`n[ERROR] Failed to start containers." -ForegroundColor Red
    exit 1
}

Write-Separator
Write-Host "Application is BUILT and RUNNING paco style! 🎓" -ForegroundColor Green
Write-Separator
Write-Host "Frontend: http://localhost" -ForegroundColor Green
Write-Host "Backend API: http://localhost:8081/api/v1" -ForegroundColor Green
Write-Separator
