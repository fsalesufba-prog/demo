# Streamflix Admin Panel

## Overview
Streamflix is a streaming TV platform admin panel for Peruvian television channels. The project consists of:
- **Frontend**: Node.js/Express static server serving the admin panel on port 5000
- **Backend**: Spring Boot + Kotlin API (originally designed for port 8080) - MySQL migrated to PostgreSQL

## Project Architecture

### Directory Structure
```
/
├── server.js           # Express server for admin panel frontend (port 5000)
├── public/             # Static frontend assets
│   └── index.html      # Admin panel UI
├── backend/            # Spring Boot Kotlin backend (not running in Replit)
│   ├── build.gradle.kts
│   └── src/main/kotlin/com/demo/streamflix/
├── admin_panel/        # Original Kotlin/JS frontend (complex build, replaced)
├── database/           # MySQL schema files (migrated to PostgreSQL)
└── android_app/        # Android client source
```

### Current State
- The admin panel frontend runs on port 5000 using Express.js serving static HTML
- The Spring Boot backend is configured but requires Gradle build which is resource-intensive
- Database is PostgreSQL (Replit built-in)

## Running the Project
The project starts automatically via the workflow "Streamflix Admin" which runs:
```
node server.js
```

## Database
Using Replit's built-in PostgreSQL. Environment variables:
- DATABASE_URL
- PGHOST, PGPORT, PGUSER, PGPASSWORD, PGDATABASE

## Recent Changes
- 2026-02-04: Migrated from MySQL to PostgreSQL
- 2026-02-04: Replaced Kotlin/JS admin panel with static HTML/Express server
- 2026-02-04: Updated Spring Boot backend to use PostgreSQL driver

## Features
- Dashboard with statistics (channels, categories, users)
- Channel management (Nacional, Actualidad, Infantil, Regional categories)
- User management
- Category management

## Notes
- The original backend uses Spring Boot 3.2.3 with Kotlin 1.9.22
- JWT authentication is configured for the API
- CORS is configured to allow all origins for development
