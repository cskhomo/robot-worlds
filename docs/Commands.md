## Client Commands

| Command    | Story (Narrative)                                                                 | Example Scenarios                                                                 |
|------------|-----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **launch** | As a player, I want to launch my robot into the world so that I can start playing | - Valid launch succeeds<br>- Invalid launch fails<br>- World full fails          |
| **look**   | As a player, I want to see objects and robots around me so that I can plan my next move | - Robot sees obstacles<br>- Robot sees other robots<br>- Robot sees nothing in empty world |
| **state**  | As a player, I want to get the current state of my robot so that I can see health, ammo, and position | - Robot exists, returns correct state<br>- Robot doesn’t exist, returns error     |
| **forward**| As a player, I want my robot to move forward a number of steps so that I can explore the world | - Normal move succeeds<br>- Move at edge fails<br>- Move blocked by obstacle fails |
| **back**   | As a player, I want my robot to move backward so that I can reposition strategically | - Normal backward move succeeds<br>- Move blocked fails                           |
| **turn**   | As a player, I want to turn my robot so that I can face a different direction     | - Turn left succeeds<br>- Turn right succeeds<br>- Multiple turns handled correctly |
| **fire**   | As a player, I want to fire at a target so that I can damage enemy robots         | - Fire hits target<br>- Fire misses target<br>- Fire out of range fails           |
| **mine**   | As a player, I want to lay a mine so that I can block or damage enemies           | - Mine placed correctly<br>- Mine placement fails on occupied space               |
| **reload** | As a player, I want to reload my weapon so that I can fire again                  | - Reload increases ammo<br>- Reload with full ammo handled                       |
| **repair** | As a player, I want to repair my robot so that I can continue playing             | - Repair restores health<br>- Repair fails if already full health                 |
| **world**  | As a player, I want to get the current state of the world so that I can see size, robots, obstacles, and features | - Returns correct world size<br>- Lists all robots<br>- Lists all obstacles<br>- Works for empty world |

---

## Server Commands

| Command  | Story (Narrative)                                                                 | Example Scenarios                                                                 |
|----------|-----------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **dump** | As an admin, I want to dump the world state so that I can inspect robots and obstacles | - Dump returns full world data<br>- Dump returns empty list if world empty |
| **robots**| As an admin, I want to list all robots so that I can monitor the active robots    | - Returns all robots<br>- Returns empty list if no robots                         |
| **purge** | As an admin, I want to remove all robots so that I can reset the world            | - Removes all robots<br>- Works on empty world                                    |
| **quit** | As an admin, I want to gracefully shut down the server so that no data is corrupted | - Server shuts down correctly<br>- Server ignores multiple quit requests gracefully |