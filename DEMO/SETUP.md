# Guia de Configuração - DEMO Streamflix

## Pré-requisitos

1. Android Studio 2023.1 ou superior
2. Android SDK 34
3. JDK 17
4. Gradle 8.0+
5. Git

## Setup Inicial

### 1. Clonar e Sincronizar Projeto

```bash
# Abrir em Android Studio
# Ou sincronizar via terminal:
./gradlew sync
```

### 2. Configurar local.properties

```bash
# Copiar template
cp local.properties.example local.properties

# Editar local.properties com seus caminhos:
sdk.dir=/Users/seu-usuario/Library/Android/sdk  # macOS
# ou
sdk.dir=C:\\Users\\seu-usuario\\AppData\\Local\\Android\\sdk  # Windows
```

### 3. Backend API

Configurar a URL da API no arquivo `local.properties`:

```properties
api.base.url=http://seu-backend:8080/api
```

## Build & Run

### Mobile App

```bash
# Debug
./gradlew :mobile:app:installDebug

# Release
./gradlew :mobile:app:assembleRelease

# Em Android Studio:
# 1. Selecionar "mobile" em "Run"
# 2. Clicar em "Run" (Shift+F10)
```

### TV App

```bash
# Debug
./gradlew :tv:app:installDebug

# Release
./gradlew :tv:app:assembleRelease

# Em Android Studio:
# 1. Selecionar "tv" em "Run"
# 2. Clicar em "Run" (Shift+F10)
```

## Estrutura de Módulos

### mobile:app
- Namespace: `com.demo.streamflix.mobile`
- Package ID: `com.demo.streamflix.mobile`
- Orientação: Portrait
- Target: Celulares e tablets

### tv:app
- Namespace: `com.demo.streamflix.tv`
- Package ID: `com.demo.streamflix.tv`
- Orientação: Landscape
- Target: Android TV 10"+

## Configurar Emulador

### Mobile
```
Versão: API 34
Dispositivo: Pixel 5
Orientação: Portrait
Tamanho: 1080x2340
```

### TV
```
Versão: API 34
Dispositivo: Android TV (1080p)
Orientação: Landscape
Tamanho: 1920x1080
```

## Executar Testes

```bash
# Testes unitários
./gradlew test

# Testes de instrumentação (require emulator/device)
./gradlew connectedAndroidTest

# Verificação de build
./gradlew check

# Lint
./gradlew lint
```

## Troubleshooting

### Erro: "Gradle sync failed"
```bash
./gradlew clean
./gradlew sync
```

### Erro: "Could not find method android()"
- Verificar versão do AGP em `build.gradle.kts`
- Atualizar Gradle: `./gradlew wrapper --gradle-version=8.2`

### Erro: "Connection refused" API
- Verificar URL backend em `local.properties`
- Se usar emulator: mudar `localhost` para `10.0.2.2`

### App não inicia em TV
- Verificar se emulator tem suporte Leanback
- Checar dependências Android TV em `build.gradle`

## Código vs Recursos Compartilhados

### Compartilhados (100%)
- Modelos de dados (`data/model`)
- Repositórios (`data/repository`)
- Serviços (`service`)
- ViewModels (`ui/viewmodel`)
- Utilitários (`util`)

### Específicos de Mobile
- Layouts em `mobile/app/src/main/res/layout`
- Fragments UI em `mobile/app/src/main/java/ui/fragments`

### Específicos de TV
- Layouts em `tv/app/src/main/res/layout`
- Fragments UI em `tv/app/src/main/java/ui/fragments`
- Dependências TV (Leanback)

## Fluxo de Desenvolvimento

1. **Lógica de Negócio**: Criar em módulo `data`
2. **ViewModel**: Criar em módulo `ui/viewmodel`
3. **UI Mobile**: Criar em `mobile/app/src/main/res/layout`
4. **UI TV**: Adaptar layout para landscape em `tv/app/src/main/res/layout`
5. **Teste**: Executar em ambos os emuladores

## Publicar APK

### Gerar assinatura de debug (para testing)
```bash
./gradlew :mobile:app:assembleDebug
./gradlew :tv:app:assembleDebug

# APKs gerados em:
# mobile/app/build/outputs/apk/debug/app-debug.apk
# tv/app/build/outputs/apk/debug/app-debug.apk
```

### Release (requer keystore)
```bash
# Criar keystore (primeira vez)
keytool -genkey -v -keystore ~/streamflix-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias streamflix-alias

# Build release
./gradlew :mobile:app:assembleRelease
./gradlew :tv:app:assembleRelease
```

## Documentação Referência

- [Android Developers](https://developer.android.com)
- [Android TV Development](https://developer.android.com/tv)
- [Kotlin Documentation](https://kotlinlang.org/docs)
- [Streamflix Original](https://github.com/streamflix-reborn/streamflix)

## Suporte

Para problemas ou dúvidas específicas, consultar a documentação original do Streamflix.
