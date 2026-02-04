# START HERE - Comece por Aqui

Bem-vindo ao projeto **DEMO Streamflix**! Este é o ponto de partida para entender e trabalhar com o projeto.

## Leia em Ordem

### 1. Para Entender o Projeto (2-3 min)
👉 **[README.md](README.md)**
- O que é o projeto
- Estrutura básica
- Tecnologias utilizadas

### 2. Para Configurar o Ambiente (5-10 min)
👉 **[SETUP.md](SETUP.md)**
- Pré-requisitos
- Como instalar
- Como executar
- Troubleshooting

### 3. Para Entender a Estrutura (10-15 min)
👉 **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)**
- Estrutura de pastas
- Organização de código
- Padrões de arquitetura

### 4. Para Saber o Status (5 min)
👉 **[SUMMARY.md](SUMMARY.md)**
- Resumo do que foi feito
- Comparação Mobile vs TV
- Próximos passos

### 5. Para Acompanhar o Progresso (2-3 min)
👉 **[CHECKLIST.md](CHECKLIST.md)**
- O que foi feito
- O que falta fazer
- Métricas de sucesso

---

## Quick Start (30 segundos)

```bash
# 1. Sincronizar projeto
./gradlew sync

# 2. Executar Mobile
./gradlew :mobile:app:installDebug

# 3. Executar TV
./gradlew :tv:app:installDebug
```

---

## Estrutura Visual

```
DEMO/
├── mobile/          ← App Android (Celular/Tablet)
├── tv/              ← App Android TV (Landscape)
└── Documentação
    ├── README.md                   (LEIA PRIMEIRO)
    ├── SETUP.md                    (CONFIGURE AQUI)
    ├── PROJECT_STRUCTURE.md        (ENTENDA A ESTRUTURA)
    ├── SUMMARY.md                  (VEJA O RESUMO)
    ├── CHECKLIST.md                (ACOMPANHE O PROGRESSO)
    └── START_HERE.md              (VOCÊ ESTÁ AQUI!)
```

---

## Características Principais

| Aspecto | Mobile | TV |
|---------|--------|-----|
| **Namespace** | com.demo.streamflix.mobile | com.demo.streamflix.tv |
| **Orientação** | Portrait | Landscape |
| **Tela** | Telefone/Tablet | 10"+ |
| **Entrada** | Touch | D-Pad/Remote |
| **Framework** | Material Design | Material + Leanback |

---

## Arquivos Importantes

### Configuração
```
build.gradle.kts          ← Build root
settings.gradle.kts       ← Módulos (mobile + tv)
gradle.properties         ← Configurações Gradle
local.properties.example  ← Template (copie e customize)
.gitignore               ← Arquivos ignorados
```

### Mobile
```
mobile/app/build.gradle                    ← Build mobile
mobile/app/src/main/AndroidManifest.xml   ← Manifesto
mobile/app/src/main/res/layout/           ← Layouts
```

### TV
```
tv/app/build.gradle                    ← Build TV (+ Leanback)
tv/app/src/main/AndroidManifest.xml   ← Manifesto TV
tv/app/src/main/res/layout/           ← Layouts landscape
```

---

## Setup Inicial (5 passos)

### 1️⃣ Clone/Abra em Android Studio
```bash
git clone ...
# ou abra DEMO/ em Android Studio
```

### 2️⃣ Sincronize Gradle
```bash
./gradlew sync
```

### 3️⃣ Configure API Backend
```bash
cp local.properties.example local.properties
# Editar local.properties com sua URL
```

### 4️⃣ Selecione o App
- **Mobile**: Build → Select Build Variant → mobile
- **TV**: Build → Select Build Variant → tv

### 5️⃣ Execute
```bash
./gradlew :mobile:app:installDebug  # Mobile
./gradlew :tv:app:installDebug      # TV
```

---

## Troubleshooting Rápido

| Problema | Solução |
|----------|---------|
| "Gradle sync failed" | `./gradlew clean && ./gradlew sync` |
| "Cannot find SDK" | Verificar `local.properties` |
| "API connection refused" | Verificar URL em `local.properties` |
| "App crashes on start" | Verificar `AndroidManifest.xml` |

👉 Mais soluções em [SETUP.md](SETUP.md)

---

## Próximas Ações

1. **Ler**: [README.md](README.md)
2. **Configurar**: Seguir [SETUP.md](SETUP.md)
3. **Executar**: `./gradlew build`
4. **Entender**: [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
5. **Desenvolver**: [CHECKLIST.md](CHECKLIST.md)

---

## Dúvidas Frequentes

**P: Preciso compilar ambos os apps?**
A: Não, você pode compilar mobile e TV separadamente.

**P: Como compartilhar código entre mobile e TV?**
A: Coloque em `java/com/demo/streamflix/` (sem sufixo mobile/tv)

**P: Preciso de um dispositivo real?**
A: Emulador funciona, mas teste em dispositivo real depois.

**P: Como fazer build de release?**
A: `./gradlew :mobile:app:assembleRelease` e `./gradlew :tv:app:assembleRelease`

---

## Status Atual

✅ Estrutura criada
✅ Build configurado
✅ Documentação completa
✅ Layout de login implementado
⏳ Implementar ViewModels
⏳ Conectar API
⏳ Implementar player
⏳ Publicar

---

## Arquivos por Tipo

### Documentação
- ✅ README.md
- ✅ SETUP.md
- ✅ PROJECT_STRUCTURE.md
- ✅ SUMMARY.md
- ✅ CHECKLIST.md
- ✅ START_HERE.md

### Configuração
- ✅ build.gradle.kts
- ✅ settings.gradle.kts
- ✅ gradle.properties
- ✅ .gitignore
- ✅ local.properties.example

### Código
- ✅ AndroidManifest.xml (mobile)
- ✅ AndroidManifest.xml (tv)
- ⏳ ViewModels
- ⏳ Repositories
- ⏳ API Services
- ⏳ Fragments

---

## Recursos Úteis

- [Android Developer Docs](https://developer.android.com)
- [Android TV Development](https://developer.android.com/tv)
- [Kotlin Language](https://kotlinlang.org)
- [Streamflix Original](https://github.com/streamflix-reborn/streamflix)

---

## Pronto?

👉 **[Comece lendo README.md](README.md)**

---

**Última atualização**: 04/02/2026
**Versão**: 1.0
**Status**: Pronto para desenvolvimento
