package ch.supsi.project.servicelayer;

import ch.supsi.project.datalayer.Preferences;

public class PreferencesService {
    private Preferences preferences;

    public PreferencesService() {
        this.preferences = new Preferences();
    }

    public void setPreferences(String language, String extension, String path){
        preferences.writePreferences(language,path);
    }

    public boolean isExist() {
        return preferences.isExist();
    }
}
