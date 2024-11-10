package store.view;

public class OutputView {

    private static final String HEADER = "[ERROR] ";

    public void printError(Exception e) {
        System.out.println(HEADER + e.getMessage());
    }
}
