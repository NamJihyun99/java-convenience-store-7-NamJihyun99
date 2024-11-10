package store.domain;

public abstract class Promotion {

    private String name = "";

    public Promotion(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
