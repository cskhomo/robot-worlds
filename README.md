# 🤖 Robot World

> *A multiplayer robot combat and exploration game*

Robot Worlds is a multiplayer robot combat and exploration game where players control robots in a shared, obstacle-filled world. The game is designed to challenge your strategy, navigation, and survival skills as you compete with other robots in real-time.

🎯 **Game Highlights:**
- Up to **9 robots** can battle simultaneously
- Real-time combat and exploration
- Strategic obstacle navigation
- Terminal-based command interface
- Survive and dominate through tactical gameplay

Players interact through typed commands, controlling their robot to move, turn, attack, and gather intel. The goal is to survive and dominate the arena by using smart tactics and outmaneuvering your opponents.

## 📋 Project Overview

Robot World is a **client-server application** built in Java that simulates a virtual world where multiple robots can interact. The server manages the world state, handles robot movements, combat, and persistence, while clients connect to control individual robots through text-based commands.

### ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🌍 **Multi-robot simulation** | Support for up to 9 concurrent robots |
| ⚡ **Real-time communication** | Instant client-server updates |
| ⚔️ **Combat system** | Weapons, health, and tactical combat |
| 🗺️ **Obstacle navigation** | Pits, mountains, and lakes to navigate |
| 💾 **World persistence** | SQLite database for world state |
| ⚙️ **Configurable worlds** | Customizable world sizes and settings |
| 👨‍💼 **Admin commands** | Server management and control |

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+** for building the project
- **Git** for cloning the repository
- **Docker** (optional, for containerized deployment)

## 🚀 Installation / Setup

### 📥 1. Clone the Repository

```bash
git clone <repository-url>
cd brownfields-robot-worlds-cjc-04
```

### 🔨 2. Build the Project

```bash
# Clean and compile the project
mvn clean compile

# Package the project (creates JAR files)
mvn package -DskipTests=true
```

### ⚙️ 3. Run the Server with Arguments

The server accepts several command-line arguments:

```bash
# Run with custom port and world size
java -jar target/robot-world-0.0.2-jar-with-dependencies.jar -p 5000 -s 10

# Or using Maven
mvn exec:java -Dexec.mainClass="za.co.wethinkcode.robots.server.Server" -Dexec.args="-p 5000 -s 10"
```

**Available Arguments:**
- `-p, --port <number>`: Server port (default: 5000)
- `-s, --size <number>`: World size (creates square world, default: 10x10)

### ▶️ 4. Run the Server Normally

```bash
# Run with default settings (port 5000, 10x10 world)
java -jar target/robot-world-0.0.2-jar-with-dependencies.jar

# Or using Maven
mvn exec:java -Dexec.mainClass="za.co.wethinkcode.robots.server.Server"
```

## 🎮 How to Play / Usage

### 🔌 Starting a Client

1. **Ensure the server is running** on the configured port (default: 5000)

2. **Run the client:**
   ```bash
   mvn exec:java -Dexec.mainClass="za.co.wethinkcode.robots.client.Client"
   ```

3. **You should see:** `Connected to Robot World server`

### 🎯 Client Commands

Once connected, you can control your robot using these commands:

#### 🔗 Connection Commands
```bash
MyRobot launch 2 2                 # Launch your robot into the world
quit                                # Disconnect from the server
```

#### 🏃 Movement Commands
```bash
<robot_name> forward <steps>       # Move forward (1-8 steps)
<robot_name> back <steps>          # Move backward (1-8 steps)  
<robot_name> turn left             # Turn 90 degrees left
<robot_name> turn right            # Turn 90 degrees right
```

#### 📊 Information Commands
```bash
<robot_name> look                  # See objects and robots around you
<robot_name> state                 # Get your robot's current status
<robot_name> world                 # Get world information
```

#### ⚔️ Combat Commands
```bash
<robot_name> fire                  # Fire your weapon
<robot_name> reload                # Reload ammunition
<robot_name> repair                # Repair your robot's health
```

### 🎯 Example Gameplay Session

```bash
# 1. Launch your robot
MyRobot launch 2 2

# 2. Look around
MyRobot look

# 3. Get world information
MyRobot world

# 4. Check your status
MyRobot state

# 5. Move forward
MyRobot forward 3

# 6. Move backward
MyRobot back 2

# 7. Turn to face a different direction
MyRobot turn left
MyRobot turn right

# 8. Fire at enemies (if any in range)
MyRobot fire

# 9. Reload when out of ammo
MyRobot reload

# 10. Repair when damaged
MyRobot repair

# 11. Disconnect from server
quit
```

### 👨‍💼 Server Administrative Commands

When running the server, you can use these commands in the server console:

```bash
dump                               # Display all world data
robots                             # List all active robots
purge                              # Remove all robots from world
save <world_name>                  # Save current world state
restore <world_name>               # Load a saved world state
quit                               # Shutdown the server
```

## 🛠️ Using the Makefile

The project includes a comprehensive Makefile with various commands:

### 🔧 Basic Commands
```bash
make build                         # Compile and package the project
make package                       # Package the project into JAR files
```

### 🧪 Testing Commands
```bash
make run-unittests                 # Run unit tests
make run-ref-tests                 # Run acceptance tests against reference server
make run-own-tests                 # Run acceptance tests against your server
make all                           # Build, test, and package everything
```

### 🐳 Docker Commands
```bash
make docker-build                  # Build Docker image
make docker-run                    # Run server in Docker container
make docker-stop                   # Stop and remove Docker container
make docker-logs                   # View Docker container logs
make docker-shell                  # Open shell in Docker container
make test-docker                   # Run tests against Docker container
```

### 🚀 Release Commands
```bash
make version                       # Prepare version for release
make tag                           # Create Git tag
make release                       # Full release process
make push                          # Push Docker image to registry
```

### ❓ Help
```bash
make help                          # Display all available commands
```

## ⚙️ Configuration

### 💻 Client Configuration
Edit `src/main/resources/client/json/client-config.json`:
```json
{
    "host": "localhost",
    "port": 5000
}
```

### 🖥️ Server Configuration
Edit `src/main/resources/server/json/server-config.json`:
```json
{
    "port": 5000
}
```

### 🌍 World Configuration
Edit `src/main/resources/server/json/world-config.json`:
```json
{
    "width": 10,
    "height": 10,
    "visibility": 3,
    "setMineTime": 3,
    "reloadTime": 3,
    "repairTime": 3,
    "obstacles": [...]
}
```

## 🧪 Testing

### 🏃‍♂️ Run All Tests
```bash
make all                           # Comprehensive test suite
```

### 🎯 Run Specific Test Types
```bash
unit_tests                         # Unit tests only
ref_tests             # Test against reference server
own_tests                 # Test against your implementation
```


### 📋 Test Scenarios
The project includes tests for various world configurations:
- **1x1**: Minimal world
- **2x2**: Small world  
- **2x2_Obs**: Small world with obstacles
- **2x2_Obs1**: Small world with different obstacle placement

## 🔧 Troubleshooting

### ⚠️ Common Issues

**Server won't start:**
- Check if port 5000 is already in use
- Try a different port: `java -jar target/robot-world-0.0.2-jar-with-dependencies.jar -p 5001`

**Client can't connect:**
- Ensure server is running first
- Check client configuration matches server port
- Verify firewall settings

**Build failures:**
- Ensure Java 21+ is installed: `java -version`
- Update Maven: `mvn -version`
- Clean and rebuild: `mvn clean package`

**Database issues:**
- Delete `robotworld.db` file and restart server
- Check file permissions in project directory

### 🆘 Getting Help

1. Use `make help` to see all available commands
2. Check the `docs/` folder for detailed command documentation
3. Review test files in `src/test/` for usage examples
4. Examine configuration files in `src/main/resources/`

## 📁 Project Structure

```
├── src/main/java/          # Source code
├── src/test/java/          # Test files  
├── src/main/resources/     # Configuration files
├── docs/                   # Documentation
├── libs/                   # External JAR files
├── target/                 # Compiled output
├── Makefile               # Build automation
├── pom.xml                # Maven configuration
└── README.md              # This file
```