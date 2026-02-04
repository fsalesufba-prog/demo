# Estrutura Detalhada do Projeto DEMO

## Visão Geral

```
DEMO/
├── mobile/                  # Aplicativo Android Mobile
├── tv/                      # Aplicativo Android TV
├── build.gradle.kts        # Configuração raiz do projeto
├── settings.gradle.kts     # Definição dos módulos
├── gradle.properties       # Propriedades globais do Gradle
├── gradlew                 # Gradle Wrapper (Linux/Mac)
├── gradlew.bat             # Gradle Wrapper (Windows)
├── local.properties.example # Template de configuração local
├── .gitignore              # Arquivos ignorados pelo Git
├── README.md               # Documentação principal
├── SETUP.md                # Guia de configuração
└── PROJECT_STRUCTURE.md    # Este arquivo
```

## Estrutura do Mobile App

```
mobile/
└── app/
    ├── build.gradle        # Configuração do módulo mobile
    ├── proguard-rules.pro  # Regras ProGuard/R8
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml    # Manifesto do app
        │   ├── java/
        │   │   └── com/demo/streamflix/mobile/
        │   │       ├── MainActivity.kt
        │   │       ├── SplashActivity.kt
        │   │       ├── data/               # Camada de dados
        │   │       │   ├── model/          # Modelos de dados
        │   │       │   ├── network/        # API e Retrofit
        │   │       │   ├── repository/     # Repositórios
        │   │       │   └── local/          # Room Database
        │   │       ├── ui/                 # Camada de apresentação
        │   │       │   ├── fragment/       # Fragments
        │   │       │   ├── adapter/        # Adaptadores
        │   │       │   └── viewmodel/      # ViewModels
        │   │       └── util/               # Utilitários
        │   │
        │   └── res/
        │       ├── layout/                 # Layouts XML
        │       │   ├── activity_main.xml
        │       │   ├── fragment_login.xml
        │       │   ├── fragment_home.xml
        │       │   ├── fragment_profile.xml
        │       │   ├── fragment_player.xml
        │       │   ├── fragment_search.xml
        │       │   └── fragment_categories.xml
        │       │
        │       ├── drawable/               # Imagens e drawables
        │       │   ├── ic_logo.xml
        │       │   ├── ic_visibility.xml
        │       │   ├── ic_arrow_back.xml
        │       │   ├── ic_favorite.xml
        │       │   ├── ic_favorite_filled.xml
        │       │   ├── ic_fullscreen.xml
        │       │   ├── ic_fullscreen_exit.xml
        │       │   ├── bg_input.xml        # Shapes
        │       │   ├── bg_button_primary.xml
        │       │   ├── bg_channel_number.xml
        │       │   └── bg_player_overlay.xml
        │       │
        │       ├── values/                 # Recursos de valor
        │       │   ├── strings.xml         # Strings em português/espanhol
        │       │   ├── colors.xml          # Paleta de cores
        │       │   ├── styles.xml          # Estilos
        │       │   ├── dimens.xml          # Dimensões
        │       │   └── attrs.xml           # Atributos customizados
        │       │
        │       ├── values-night/           # Tema noturno
        │       ├── values-pt/              # Strings em português
        │       ├── values-es/              # Strings em espanhol
        │       │
        │       ├── navigation/
        │       │   └── nav_graph.xml       # Navigation graph
        │       │
        │       └── anim/                   # Animações
        │           ├── slide_in_left.xml
        │           ├── slide_out_right.xml
        │           └── fade_in.xml
        │
        └── test/
            └── java/                       # Testes unitários
```

## Estrutura do TV App

```
tv/
└── app/
    ├── build.gradle        # Configuração do módulo TV
    ├── proguard-rules.pro  # Regras ProGuard/R8
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml    # Manifesto do app TV
        │   ├── java/
        │   │   └── com/demo/streamflix/tv/
        │   │       ├── MainActivity.kt
        │   │       ├── SplashActivity.kt
        │   │       └── (estrutura similar ao mobile)
        │   │
        │   └── res/
        │       ├── layout/                 # Layouts otimizados para TV
        │       │   ├── activity_main.xml   # Landscape
        │       │   ├── fragment_login.xml  # Otimizado
        │       │   ├── fragment_home.xml   # Grid grande
        │       │   └── (outros layouts)
        │       │
        │       ├── drawable/               # Similar ao mobile
        │       ├── values/                 # Similar ao mobile
        │       │
        │       └── layout-land/            # Layouts para landscape
        │
        └── androidTest/
            └── java/                       # Testes de instrumentação
```

## Arquitetura Compartilhada

### Estrutura MVVM (Model-View-ViewModel)

```
Shared Logic:
├── Model (Data Classes)
│   ├── User
│   ├── Channel
│   ├── Category
│   └── Subscription
│
├── Repository (Data Access)
│   ├── AuthRepository
│   ├── ChannelRepository
│   ├── CategoryRepository
│   └── UserRepository
│
└── ViewModel (Lógica de Negócio)
    ├── LoginViewModel
    ├── HomeViewModel
    ├── ProfileViewModel
    ├── PlayerViewModel
    └── SearchViewModel

UI (Específico por App):
├── Mobile
│   ├── Fragment Layout Portrait
│   ├── Adaptadores para RecyclerView
│   └── Touch/Gesture Handlers
│
└── TV
    ├── Fragment Layout Landscape
    ├── Adaptadores para TV
    └── D-Pad/Remote Handlers
```

## Fluxo de Dados

```
User Action
    ↓
Fragment/Activity
    ↓
ViewModel
    ↓
Repository
    ↓
API / Database
    ↓
(Resultado)
    ↓
LiveData/Flow
    ↓
Fragment/Activity (UI Update)
```

## Dependências Principais

### Core Android
- androidx.core:core-ktx:1.12.0
- androidx.appcompat:appcompat:1.6.1

### UI
- com.google.android.material:material:1.11.0
- androidx.constraintlayout:constraintlayout:2.1.4
- androidx.navigation:navigation-*:2.7.6

### Networking
- com.squareup.retrofit2:retrofit:2.9.0
- com.squareup.okhttp3:okhttp:5.0.0-alpha.11

### Video Streaming
- com.google.android.exoplayer:exoplayer*:2.19.1

### Database & Caching
- androidx.room:room-*:2.6.1

### Dependency Injection
- com.google.dagger:hilt-android:2.50

### Image Loading
- com.github.bumptech.glide:glide:4.16.0

### Android TV (apenas TV app)
- androidx.tv:tv-foundation:1.0.0-alpha10
- androidx.tv:tv-material:1.0.0-alpha10
- androidx.leanback:leanback:1.2.0-alpha04

## Configuração de Build

### Versões
- Gradle: 8.2+
- AGP (Android Gradle Plugin): 8.2.0
- Kotlin: 1.9.22
- Java: 17

### Flavors
- **mobile**: Configuração para celular/tablet
- **tv**: Configuração para Android TV

### Build Types
- **debug**: Sem otimização, logs habilitados
- **release**: Otimizado, ProGuard ativado

## Padrões de Codificação

### Naming Conventions
```
Classes:        PascalCase (LoginViewModel)
Functions:      camelCase (getUserData())
Constants:      UPPER_CASE (API_BASE_URL)
Variables:      camelCase (userName)
XML IDs:        snake_case (et_username)
Resources:      snake_case (ic_logo, bg_input)
```

### Package Organization
```
com.demo.streamflix.{mobile|tv}
├── data
│   ├── model           (Data classes)
│   ├── network         (API & Retrofit)
│   ├── repository      (Data access)
│   └── local           (Room & SharedPrefs)
├── ui
│   ├── fragment        (Fragments)
│   ├── adapter         (RecyclerView adapters)
│   └── viewmodel       (ViewModels)
└── util
    ├── Constants.kt    (Constantes)
    ├── Extensions.kt   (Extensões)
    ├── Mapper.kt       (Mapeadores)
    └── Validator.kt    (Validadores)
```

## Testing

### Testes Unitários
```
src/test/java/
├── data/repository/
├── ui/viewmodel/
└── util/
```

### Testes de Instrumentação
```
src/androidTest/java/
├── ui/fragment/
├── ui/activity/
└── data/
```

## Build Outputs

### Mobile
```
mobile/app/build/outputs/
├── apk/
│   ├── debug/
│   │   ├── app-debug.apk
│   │   └── output-metadata.json
│   └── release/
│       └── app-release-unsigned.apk
└── bundle/
    └── release/
        └── app-release.aab
```

### TV
```
tv/app/build/outputs/
├── apk/
│   ├── debug/
│   │   ├── app-debug.apk
│   │   └── output-metadata.json
│   └── release/
│       └── app-release-unsigned.apk
└── bundle/
    └── release/
        └── app-release.aab
```

## Configuração de Recursos

### Strings Multilíngues
- `values/strings.xml` - Padrão (Espanhol)
- `values-pt/strings.xml` - Português
- `values-es/strings.xml` - Espanhol explícito

### Dimensões
- `values/dimens.xml` - Mobile
- `values-sw600dp/dimens.xml` - Tablet
- `values-land/dimens.xml` - Landscape (TV)

### Cores
- `values/colors.xml` - Paleta completa
- `values-night/colors.xml` - Tema noturno

## Integração CI/CD

### Possíveis Integrações
- GitHub Actions
- GitLab CI
- Jenkins
- Fastlane

### Verificações Recomendadas
1. Lint (Estilo de código)
2. Build (Compilação)
3. Testes unitários
4. Testes instrumentados
5. SonarQube (Qualidade)

## Segurança

### Práticas Implementadas
- HTTPS para APIs
- ProGuard/R8 em release
- Armazenamento seguro de credenciais
- Validação de entrada
- Certificado pinning (recomendado)

### Arquivos Sensíveis
- `local.properties` (ignorado via .gitignore)
- `.env` (ignorado via .gitignore)
- Keystores (ignorados)

## Performance

### Otimizações
- Lazy loading de imagens (Glide)
- Caching de dados (Room)
- Paginação em listas
- ProGuard/R8 em release
- VectorDrawables em vez de PNG

### Monitoramento
- Profiler do Android Studio
- Firebase Performance Monitoring (recomendado)
- Stetho para debugging (desenvolvimento)

## Documentação Adicional

- Veja `README.md` para visão geral
- Veja `SETUP.md` para instruções de configuração
- Veja `README.md` no backend para integração de API
