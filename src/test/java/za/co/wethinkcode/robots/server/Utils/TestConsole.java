package za.co.wethinkcode.robots.server.Utils;

import za.co.wethinkcode.robots.server.cli.ConsoleOutput;

public class TestConsole extends ConsoleOutput {
    public String lastError;
    public String lastSave;
    public String lastRestore;

    @Override
    public void printError(String message) {
        lastError = message;
    }

    @Override
    public void printSaveResponse(String worldName) {
        lastSave = worldName;
    }

    @Override
    public void printRestoreResponse(String worldName) {
        lastRestore = worldName;
    }

    public void reset() {
        lastError = null;
        lastSave = null;
        lastRestore = null;
    }
}