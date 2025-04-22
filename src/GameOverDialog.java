import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverDialog extends JDialog {
    public GameOverDialog(JFrame parent, String winner, ActionListener playAgainListener, ActionListener backToMenuListener) {
        super(parent, "Game Over", true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // Thông báo người thắng
        JLabel message = new JLabel(winner + " wins!", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 24));
        add(message, BorderLayout.CENTER);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton playAgainButton = new JButton("Chơi lại");
        playAgainButton.addActionListener(e -> {
            dispose();
            playAgainListener.actionPerformed(e);
        });

        JButton backButton = new JButton("Về trang chủ");
        backButton.addActionListener(e -> {
            dispose();
            backToMenuListener.actionPerformed(e);
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}