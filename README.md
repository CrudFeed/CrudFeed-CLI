<p align="center">
  <img width="120" height="120" alt="CrudFeed" src="https://github.com/user-attachments/assets/bfd3482e-64d4-4a1c-98b4-cdfa91d20fcb">
</p>

<h1 align="center">CrudFeed CLI</h1>

<p align="center">
  Terminal-first client for CrudFeed ecosystem.<br>
  Full-featured messaging and social platform in your shell.
</p>

<br>

<p align="center">
  <a href="https://github.com/CrudFeed/CrudFeed-CORE"><img src="https://img.shields.io/badge/Dependency-CrudFeed_Core-brightgreen?style=for-the-badge&logo=git&logoColor=white" alt="Core Version"></a>
  <img src="https://img.shields.io/badge/Version-3.0-blue?style=for-the-badge&logo=git&logoColor=white" alt="CLI Version">
</p>

<br>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=intellijidea&logoColor=white" alt="IntelliJ IDEA">
  <img src="https://img.shields.io/badge/Linux-FCC624?style=flat-square&logo=linux&logoColor=black" alt="Linux">
</p>

<p align="center">
  <sub>💻 Terminal-based, no UI bloat. Just you and the content.</sub>
</p>


---

## 🎮 What You Can Do

**Accounts**
- Register, login, logout
- Edit email, nickname, display name, password and bio
- View other profiles
- Subscribe/unsubscribe to users
- Add/remove friend
- Show friends list
- Delete account

**Posts**
- Create, edit, delete posts
- View feed
- Like/dislike posts
- Search by ID or keywords
- See your likes and posts

**Messages**
- Write direct messages
- View conversation
- Edit and delete messages

**System**
- Built-in help
- Change server
- Status check
- Whoami
- Quit

**Moderators**
- View user info
- Ban/unban users
- Soft-delete and restore accounts or posts
- Promote and demote user roles
- Built-in special help

---

## 🏗️ Architecture

Command-driven interface with communication to the server:

```
                    CLI Client (3.0)
                          │
       ┌──────────────────┼──────────────────┐
       ▼                  ▼                  ▼
  CommandRouter     CommandHandlers     Formatters / DTOs
   (input/help)     (acc/post/msg/mod)   (cards / output)
       │                  │                  │
       └──────────────────┼──────────────────┘
                          │
                          ▼
               CrudFeed Server (Spring Boot)
           (Security / Auth / Roles / Controllers)
                          │
                          ▼
                    CrudFeed Core
            (Services / Repositories / Records)
                          │
                          ▼
                      PostgreSQL

```

User input → CLI Handler → ResponseClient → Spring Server → Core services → Core repositories → Database

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
│   ├── Command.java                     ← Interface for all commands
│   └── impl/
│       ├── mod/
│       │   ├── account/                 ← Ban, unban, delete/restore account, promote/demote, show
│       │   ├── message/                 ← Mod message commands
│       │   ├── post/                    ← Delete/restore posts
│       │   ├── system/                  ← ModHelpCommand
│       │   └── ModCommand.java
│       └── user/
│           ├── account/                 ← Auth, profile, email, friends, subscriptions, delete
│           ├── message/                 ← Write, show, edit, delete
│           ├── post/                    ← Create, edit, delete, feed, likes/dislikes, search
│           └── system/                  ← ChangeServer, help, quit, status, whoami
├── handler/
│   ├── AccountHandler.java              ← Orchestrate user account commands
│   ├── MessageHandler.java              ← Orchestrate message commands
│   ├── ModHandler.java                  ← Orchestrate moderation commands
│   └── PostHandler.java                 ← Orchestrate post commands
├── menu/
│   └── MenuRouter.java                  ← Parse input, route to handlers
├── network/
│   └── ResponseClient.java              ← Talk to Server
├── session/
│   └── Session.java                     ← Active session / client state
├── ui/
│   ├── AccountCardCreator.java          ← Format user profiles
│   ├── FriendsListCardCreator.java      ← Format friends list
│   ├── MessageCardCreator.java          ← Format messages
│   ├── PostCardCreator.java             ← Format posts
│   └── ShowInfoCardCreator.java         ← Format general info / help cards
└── CrudFeedApp.java                     ← Main entry point

```

## 💡 Design

- **Command Pattern** — Every action is a standalone command
- **Handlers** — Group commands by domain (`Account`, `Post`, `Message`, `Mod`), cut boilerplate
- **Formatters & DTOs** — Consistent output and typed contracts from `Core`
- **Transport & State** — `ResponseClient` and active server/session state kept separate from UI
- **Single Responsibility** — Command executes action, Handler routes/flow, Formatter displays result
- **Core Agnostic** — Business rules stay in `Core` services; CLI only handles input, transport, and rendering
---

## 🚧 Status

Version 3.0 is ready. Now time for the <a href="https://github.com/CrudFeed/CrudFeed-GUI">GUI</a> 1.0

---

## 📄 License

This project is licensed under the GNU General Public License v3.0 — see the [LICENSE](LICENSE) file for details.

