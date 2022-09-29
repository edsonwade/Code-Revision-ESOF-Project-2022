package ufp.esof.project.models;

public enum Language {
    PORTUGUESE,
    ENGLISH,
    SPANISH,
    ITALIAN;


    public void add(Language language) {
        language.add(this);
    }
}
