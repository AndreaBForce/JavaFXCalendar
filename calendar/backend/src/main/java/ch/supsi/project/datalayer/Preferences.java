package ch.supsi.project.datalayer;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Preferences {
    private String path;
    private String language;
    private File storePreferences;
    private boolean exist;

    public Preferences() {
        path = System.getProperty("user.home");
        String OpSystem = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if ((OpSystem.contains("mac")) || (OpSystem.contains("darwin"))) {
            path = path + "/Library/Application Support/.calendar";
        } else if (OpSystem.contains("win")) {
            path = path + "\\.calendar";
        } else {
            path = path + "/.calendar";
        }

        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }

        storePreferences = new File(path + File.separator + "preferences.prefs");

        if(storePreferences.exists()){
            readPreferences();
        }else{
            exist = false;
        }
    }

    public void writePreferences(String language, String path){
        if(language != "" && path != "") {

            if (!storePreferences.exists()) {
                try {
                    storePreferences.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try (FileWriter file = new FileWriter(storePreferences)) {
                String str = String.format("%s\n%s\n", language, path);
                file.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            exist = true;
        }else{
            exist = false;
        }
    }

    private void readPreferences(){
        exist = true;

        try(Scanner scanner = new Scanner(storePreferences)) {
            language = scanner.nextLine();
            path = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isExist() {
        return exist;
    }
}
