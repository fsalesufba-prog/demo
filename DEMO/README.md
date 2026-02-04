# DEMO - Streamflix Mobile e Android TV

Versão adaptada e ampliada do projeto Streamflix com suporte para aplicativos mobile (telefone/tablet) e Android TV.

## Estrutura do Projeto

```
DEMO/
├── mobile/               # App Android para telefone/tablet
│   └── app/
│       ├── src/
│       │   └── main/
│       │       ├── java/com/demo/streamflix/mobile/
│       │       └── res/
│       └── build.gradle
│
├── tv/                   # App Android TV
│   └── app/
│       ├── src/
│       │   └── main/
│       │       ├── java/com/demo/streamflix/tv/
│       │       └── res/
│       └── build.gradle
│
├── build.gradle.kts      # Configuração raiz do projeto
├── settings.gradle.kts   # Configuração dos módulos
└── gradle.properties     # Propriedades do Gradle

```

## Características

### Mobile App (`com.demo.streamflix.mobile`)
- Orientação Portrait
- Interface otimizada para celulares e tablets
- Suporta navegação com toque
- Funcionalidades completas de streaming

### TV App (`com.demo.streamflix.tv`)
- Orientação Landscape
- Interface otimizada para TV (10+ polegadas)
- Suporta navegação com controle remoto e D-Pad
- Dimensões maiores de fonte e touch targets
- Dependências Android TV Framework

## Telas Implementadas

### 1. Login
- Email e senha
- Opção "Recordar accesos"
- Recuperação de contraseña
- Validação em tempo real

### 2. Validação de Membresía
- Tela de loading com mensagem "Validando membresía activa"
- Verificação automática de subscrição

### 3. Home
- Categorias: Todos, Nacional, Actualidad, Infantil, Regional
- Grid de canais com logos
- Navegação entre categorias
- Publicidade/Conteúdo destacado

### 4. Perfil
- Dados do usuário (Nome, Email, Teléfono)
- Botões de ação:
  - Actualizar mis datos
  - Cambiar contraseña
  - Cerrar sesión
- Último canal visualizado

### 5. Player
- ExoPlayer para streaming
- Controles customizados
- Suporta HLS
- Fullscreen adaptado para cada tipo de dispositivo

## Tecnologias Utilizadas

### Core Framework
- Android SDK 34
- Kotlin 1.9.22
- Java 17

### Dependências Principais
- **AndroidX**: Core, AppCompat, Constraint Layout
- **Material Design**: Material Components 1.11.0
- **Networking**: Retrofit 2.9.0, OkHttp 5.0
- **Player**: ExoPlayer 2.19.1
- **Injeção de Dependência**: Dagger Hilt 2.50
- **Persistência**: Room Database 2.6.1
- **Image Loading**: Glide 4.16.0
- **Android TV**: TV Foundation, TV Material, Leanback

## Build

### Mobile
```bash
./gradlew :mobile:app:build
./gradlew :mobile:app:assembleDebug
./gradlew :mobile:app:assembleRelease
```

### TV
```bash
./gradlew :tv:app:build
./gradlew :tv:app:assembleDebug
./gradlew :tv:app:assembleRelease
```

### Ambos
```bash
./gradlew build
```

## Configuração Backend

O projeto utiliza Spring Boot como backend. Endpoints esperados:

- `POST /api/auth/login` - Login de usuário
- `GET /api/subscriptions/validate` - Validação de subscrição
- `GET /api/categories` - Lista de categorias
- `GET /api/channels` - Lista de canais
- `GET /api/channels/{id}` - Detalhes do canal
- `GET /api/users/profile` - Dados do usuário
- `PUT /api/users/profile` - Atualizar perfil

## Requisitos de Sistema

- Android 5.0+ (API 23+)
- Conexão com Internet
- Espaço de armazenamento local (Room Database)
- Para TV: Android 5.0+ com suporte Leanback

## Permissões Requeridas

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## Estrutura de Código

Ambas os apps (mobile e TV) compartilham a mesma estrutura:

```
app/src/main/
├── java/
│   └── com/demo/streamflix/
│       ├── data/           # Data layer (Repository, API, Database)
│       ├── ui/             # UI layer (Activities, Fragments, Adapters)
│       └── util/           # Utilitários e constantes
│
└── res/
    ├── layout/             # Layouts XML
    ├── drawable/           # Drawables e formas
    ├── values/             # Cores, strings, estilos
    └── navigation/         # Navigation graph
```

## Diferenças Mobile vs TV

| Aspecto | Mobile | TV |
|---------|--------|-----|
| Orientação | Portrait | Landscape |
| Namespace | .mobile | .tv |
| App ID | com.demo.streamflix.mobile | com.demo.streamflix.tv |
| Dependências TV | Não | Sim (Leanback, TV Foundation) |
| Touch Targets | Menores (48dp) | Maiores (64dp+) |
| Fonts | Menores (14-18sp) | Maiores (18-24sp) |
| Launcher | Default | Leanback Launcher |
| Navegação | Touch | D-Pad / Controle Remoto |

## Próximos Passos

1. Implementar camada de Data (API, Repository)
2. Criar ViewModels com MVVM
3. Adicionar testes unitários e de UI
4. Otimizar performance para TV
5. Implementar autenticação segura
6. Adicionar cache local com Room
7. Testes em dispositivos reais

## Build & Run

### Android Studio
1. Abrir projeto em Android Studio
2. Sincronizar Gradle
3. Selecionar flavor (mobile ou tv)
4. Executar em emulador ou dispositivo físico

### Command Line
```bash
# Build e instalar Mobile
./gradlew :mobile:app:installDebug

# Build e instalar TV
./gradlew :tv:app:installDebug
```

## Notas Importantes

- O projeto mantém 100% do código reutilizável entre mobile e TV
- Adaptações específicas de UI são feitas em nível de layout XML
- A lógica de negócio é compartilhada entre ambas as plataformas
- Backend é único para ambas as aplicações
- Segurança: Credenciais nunca devem ser commitadas, usar arquivos .env

## Suporte

Para issues ou dúvidas sobre o projeto, consulte a documentação do Streamflix original e a documentação Android oficial.
