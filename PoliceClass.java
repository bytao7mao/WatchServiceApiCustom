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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

class PoliceClass {
    static Calendar calendar = Calendar.getInstance();
    static Date date = calendar.getTime();
    static Timestamp timestamp = new Timestamp(date.getTime());
    private static void print(String string){ System.out.println(string); }
    static void runWatcher() throws InterruptedException {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get("C:\\Recov\\inputs");
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            print("Watch Service registered for dir: " + dir.getFileName());
            Thread.sleep( 50 );
            //creating listener
            while (true) {
                WatchKey key;
                try { key = watcher.take();
                    Thread.sleep( 50 );
                } catch (InterruptedException ex) { return; }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    print(kind.name() + ": " + fileName + " \ntimestamp: " + timestamp);
                    if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("message_guid_callback.txt")) {
                        print("My source file has changed!!! :" + fileName + "\n");
                    } else if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("message_guid.txt")) {
                        print("My source file has changed!!! :" + fileName + "\n");
                    } else if (kind == ENTRY_MODIFY &&
                            fileName.toString().equalsIgnoreCase("cykey_SP.txt")) {
                        print("My source file has changed!!! :" + fileName + "\n");
                    }
                }
                boolean valid = key.reset();if (!valid) { break; } }
        } catch (IOException ex) { System.err.println(ex); }
    }
}
