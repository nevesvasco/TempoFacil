# TempoFácil

TempoFácil é uma aplicação de meteorologia que permite aos utilizadores acederem a informações atualizadas sobre o tempo e a previsão meteorológica. O objetivo do projeto é integrar uma API de dados meteorológicos e funcionalidades com o Firebase para autenticação de utilizadores. Atualmente, a aplicação está na fase inicial de desenvolvimento e apenas a funcionalidade de login está implementada.

---

## 🚀 Funcionalidades Planeadas

- **Autenticação de Utilizadores:**
  - Login e registo usando Firebase Authentication.
  - Suporte para email e password.

- **Consulta Meteorológica:**
  - Integração com uma API de meteorologia (ex.: OpenWeather ou WeatherAPI).
  - Visualização da temperatura, condições atuais e previsão para os próximos dias.

- **Personalização:**
  - Escolha de localização manual ou deteção automática baseada na localização GPS.
  - Interface intuitiva e responsiva.

---

## 📱 Estado Atual

- **Funcionalidade concluída:**
  - **Login**: Implementação de uma interface estilizada e funcional para autenticação de utilizadores.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Kotlin (Compose)
- **Plataforma:** Android
- **Autenticação:** Firebase Authentication
- **UI/UX:** Jetpack Compose
- **Futuro:** Integração com uma API de meteorologia

---

## 📂 Estrutura do Projeto

```plaintext
src/
├── login/
│   ├── LoginScreen.kt         # Tela de login com autenticação no Firebase
│   └── RegisterScreen.kt      # (Planejado) Tela de registo
├── main/
│   ├── MainActivity.kt        # Configuração inicial e navegação
│   └── AppNavigation.kt       # Gerenciamento de navegação entre telas
├── ui/
│   ├── theme/                 # Estilos e temas visuais do Jetpack Compose
└── resources/
    ├── drawable/              # Logotipo, wallpaper e outros recursos visuais
    └── values/                # Cores, strings e dimensões
