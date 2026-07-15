<table align="center">
  <tr>
    <td align="center">
      <img width="100" height="100" alt="CrudFeed CLI" src="https://github.com/user-attachments/assets/bfd3482e-64d4-4a1c-98b4-cdfa91d20fcb"><br>
      <sub><b>CrudFeed CLI</b></sub>
    </td>
  </tr>
</table>

<br>

<p align="center">
  Terminal-first client for CrudFeed ecosystem.<br>
  Full-featured messaging and social platform in your shell.
</p>

<br>

<p align="center">
  <a href="https://github.com/CrudFeed/CrudFeed-CORE"><img src="https://img.shields.io/badge/Dependency-CrudFeed_Core-brightgreen?style=for-the-badge&logo=git&logoColor=white" alt="Core Version"><a>
  <img src="https://img.shields.io/badge/Version-2.0-blue?style=for-the-badge&logo=git&logoColor=white" alt="CLI Version">
</p>

<p align="center">
  <sub>💻 Terminal-based, no UI bloat. Just you and the content.</sub>
</p>

<br>

<p align="center">
  <font size="7"><b>Powered by</b></font>
</p>

<p align="center">
  <img width="40" src="https://img.shields.io/badge/%20-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java">
  <img width="40" src="https://img.shields.io/badge/%20-4169E1?style=flat-square&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img width="40" src="https://img.shields.io/badge/%20-59666C?style=flat-square&logo=hibernate&logoColor=white" alt="Hibernate">
  <img width="40" src="https://img.shields.io/badge/%20-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven">
  <img width="40" src="https://img.shields.io/badge/%20-000000?style=flat-square&logo=intellijidea&logoColor=white" alt="IDEA">
  <img width="40" src="https://img.shields.io/badge/%20-FCC624?style=flat-square&logo=linux&logoColor=black" alt="Linux">
</p>

---

## 🎮 What You Can Do

**Accounts**
- Register, login, logout
- View and edit profile (name, password)
- Subscribe/unsubscribe to users
- Delete account

**Posts**
- Create, edit, delete posts
- View feed from subscriptions
- Like/dislike posts
- Search by ID or keywords
- See who liked your post

**Messages**
- Write direct messages
- View conversation
- Edit and delete messages

**System**
- Built-in help
- Status check
- Whoami (current user)
- Graceful quit

---

## 🏗️ Architecture

Command-driven interface with secure SSL communication to the server:

```
                CLI Client
                     │
    ┌────────────────┼────────────────┐
    ▼                ▼                ▼
MenuRouter   Handlers & Commands   UI Card Creators
    │                │                 |
    └────────────────┬─────────────────┘
                     │
                     ▼ (SSL/TLS encrypted)
                CrudFeed Server  (localhost:7777)
                     │
                     ▼
                CrudFeed Core    (Services & Repositories)
                     │
                     ▼
                 PostgreSQL

```

User input → CLI Handler → ResponseClient (SSL) → Server dispatcher → Core services → Database

---

## 📦 Getting Started

### Prerequisites

- Java 26+
- Maven 4.0+
- CrudFeed <a href="https://github.com/CrudFeed/CrudFeed-CORE">Core</a> library

### Build

```bash
git clone https://github.com/CrudFeed/CrudFeed-CLI.git
cd CrudFeed-CLI
mvn clean install
```

Run the CLI:

```bash
java -jar target/"name_of_java_jar_file".jar
```

### First Time

On startup, you'll be prompted to login or register. Choose your path and follow the prompts.

---

## 📂 Module Structure

```
org.crudfeed.cli/
├── command/
│   ├── Command.java              ← Interface for all commands
│   └── impl/
│       ├── account/              ← Login, register, profile, subscriptions
│       ├── post/                 ← Feed, create, search, like, dislike
│       ├── message/              ← Write, show, edit, delete
│       └── system/               ← Help, status, whoami, quit
├── handler/
│   ├── AccountHandler.java       ← Orchestrate account commands
│   ├── PostHandler.java          ← Orchestrate post commands
│   └── MessageHandler.java       ← Orchestrate message commands
├── menu/
│   └── MenuRouter.java           ← Parse input, route to correct handler
├── network/
│   └── ResponseClient.java       ← Talk to Server
├── ui/
│   ├── AccountCardCreator.java   ← Format user profiles
│   ├── PostCardCreator.java      ← Format post display
│   └── MessageCardCreator.java   ← Format message display
└── CrudFeedApp.java              ← Main entry point

```

---

## 🔒 Network

**ResponseClient** (Singleton) handles all communication with Core:

- **SSL/TLS encrypted** — All traffic secured with keystore
- **Command-response pattern** — Send UTF-8 commands, receive UTF-8 responses
- **User parsing** — Deserialize server responses into domain objects
- **Timeout** — 5-second socket timeout to prevent hangs

Connection details:

```
Host: localhost
Port: 7777
Protocol: SSL/TLS
```
---

## 💡 Design

- **Command Pattern** — Every action is a pluggable command
- **Handlers** — Group related commands, reduce boilerplate
- **Cards** — Consistent, clean output formatting
- **Single Responsibility** — Command knows logic, Card knows display, Handler knows flow
- **Core Agnostic** — All domain logic stays in Core, CLI is pure presentation + routing

---

## 🚧 Status

Version 2.0 is ready. Stable core and server.

---

## 📄 License

Same as parent CrudFeed organization.
