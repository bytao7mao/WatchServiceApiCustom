package com.company;

/**
 * Created by taozen on 10/12/2018.
 */

import javafx.concurrent.Task;

import static com.company.ColorsClass.*;
import static com.company.watcherWithGUI.PoliceClassGUI.pathForClient;
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

public class PoliceClass extends Task implements Runnable {

    Scanner sc = new Scanner(System.in);
    int pathsNumber=0;
    boolean listening = false;
    long counter = 0;
    static WatchService watcher;
    List<Path> listDir = new ArrayList<>();
//    static Path dir,dir2,dir3;
    private static Calendar calendar = null;
    private static Date date = null;
    private static Timestamp timestamp = null;
    String currentPath=null;
    List<String> pathList = new ArrayList<>();

//    static String directory = "C:\\Users\\taozen\\Desktop";
//    static String directory3 = "C:\\Users\\taozen\\Downloads";
//    static String directory2 = "C:\\Users\\taozen\\Desktop\\ANDROID\\testingLinuxAndroid\\app\\src\\main\\java\\com\\example\\tao\\myapplication";
    private static void print(String string) {
        System.out.println(string);
    }
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public void runWatcher() throws InterruptedException {
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
    }

    @Override
    protected Object call() throws Exception {
        System.out.println("...processing");
        return null;
    }

//    @Override
//    public void run() {
//        listening = true;
//        while(listening) {
//            try {
//                System.out.println("Listening on: " + dir.getFileName());
//                runWatcher();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            watcher.close();
//        } catch (IOException e) {
//
//        }
//    }
}
