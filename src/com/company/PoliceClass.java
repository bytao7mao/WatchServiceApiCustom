package com.company;

import javafx.concurrent.Task;

import static com.company.ColorsClass.ANSI_BLACK_BACKGROUND;
import static com.company.ColorsClass.ANSI_RED;
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
import java.util.*;

class PoliceClass extends Task implements Runnable {

    private final Scanner sc = new Scanner(System.in);
    int pathsNumber=0;
    boolean listening = false;
    long counter = 0;
    WatchService watcher;
    final List<Path> listDir = new ArrayList<>();

    Calendar calendar = null;
    Date date = null;
    Timestamp timestamp = null;
    String currentPath=null;
    final List<String> pathList = new ArrayList<>();

    private static void print(String string) {
        System.out.println(string);
    }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    MyWatcher myWatcher = () -> {
        print("Welcome to tao\'s listener v.0.1 !" + "\n\n");
        print("How many paths do you want to listen ?");
        pathsNumber=Integer.parseInt(sc.nextLine());
        print("paths number: " + pathsNumber);
        print("Enter paths: ");

        for (int i=0;i<pathsNumber;i++){
            currentPath=sc.nextLine();
            pathList.add(String.valueOf(currentPath));
            print("current path: " + currentPath + "\n" + "pathlist size: " + pathList.size() + " path name: " + pathList.get(i));
        }
        try {
            watcher = FileSystems.getDefault().newWatchService();

            for (String p : pathList){
                listDir.add(Paths.get(p));
                print("pathList = " + p);
            }
            for (Path pat: listDir){
                pat.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                print("Watch Service registered for dir: " + pat.getFileName());
            }

//solved
//            dir = Paths.get(directory);
//            dir2 = Paths.get(directory2);
//            dir3 = Paths.get(directory3);
//
//            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//            dir2.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//            dir3.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//
//            print("Watch Service registered for dir: " + dir.getFileName());
//            print("Watch Service registered for dir: " + dir2.getFileName());
//            print("Watch Service registered for dir: " + dir3.getFileName());

            Thread.sleep(50);
            //creating listener
//            while (true)  same as for (;;)
            for (;;) {
                WatchKey key;
                try {
                    key = watcher.take();
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    counter++;
                    String c = "Counter: " + counter;
                    WatchEvent.Kind<?> kind = event.kind();
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    calendar = Calendar.getInstance();
                    date = calendar.getTime();
                    timestamp = new Timestamp(date.getTime());

                    print(ANSI_BLACK_BACKGROUND + ANSI_RED + kind.name() + ": " + fileName + " \ntimestamp: " + timestamp + "\n" + c);
//                    if (kind == ENTRY_MODIFY &&
//                            fileName.toString().equalsIgnoreCase("message_guid_callback.txt")) {
////                        print("My source file has changed!!! :" + fileName + "\n");
//                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    };

    @Override
    protected Object call() {
        System.out.println("...processing");
        return null;
    }

    @Override
    public void run() {

        Thread.State currState = Thread.currentThread().getState();
        if (currState != Thread.State.NEW && currState != Thread.State.TERMINATED){
            print("Thread PoliceClass run(): " + currState);
        }
        listening = true;
        while(listening) {
            try {
                myWatcher.runWatcher();
                print(Thread.getAllStackTraces().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            watcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
