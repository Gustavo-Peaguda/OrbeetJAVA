package app;

import ui.TelaORBEET;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaORBEET tela = new TelaORBEET();
            tela.setVisible(true);
        });
    }
}