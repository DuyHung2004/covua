import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        JFrame frame= new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setLocationRelativeTo(null);

        BanCo banCo= new BanCo();
        frame.add(banCo);

        frame.setVisible(true);
    }
}