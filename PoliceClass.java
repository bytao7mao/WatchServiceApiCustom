package com.company;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

class PoliceClass {
    private static void print(String string){ System.out.println(string); }
    static void runWatcher() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get("C:\\Recov\\inputs");
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            print("Watch Service registered for dir: " + dir.getFileName());
            while (true) {
                WatchKey key;
                try { key = watcher.take();
                } catch (InterruptedException ex) { return; }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    print(kind.name() + ": " + fileName);
                    if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("message_guid_callback.txt")) {
                        print("My source file has changed!!! :" + fileName);
                    } else if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("message_guid.txt")) {
                        print("My source file has changed!!! :" + fileName);
                    } else if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("cykey_SP.txt")) {
                        print("My source file has changed!!! :" + fileName);
                    }
                }
                boolean valid = key.reset();if (!valid) { break; } }
        } catch (IOException ex) { System.err.println(ex); }
    }
}
