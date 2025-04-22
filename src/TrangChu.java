import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class TrangChu extends JPanel {
    private Image backgroundImage;
    private JFrame parentFrame;

    public TrangChu(JFrame frame) {
        this.parentFrame = frame;

        // 1. Tải ảnh nền
        loadBackgroundImage();

        // 2. Thiết lập layout
        setLayout(new GridBagLayout());

        // 3. Tạo giao diện
        initUI();
    }

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getResource("/image/nencovua.jpg");
            if (imageUrl != null) {
                backgroundImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Không tìm thấy file ảnh!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        // Panel chứa nội dung chính (để căn giữa)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Tiêu đề
        JLabel titleLabel = new JLabel("CỜ VUA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(new Color(255, 215, 0)); // Màu vàng
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 50, 0));

        // Các nút chức năng
        JButton btnVsHuman = createStyledButton("CHƠI VỚI BẠN", new Color(70, 130, 180));
        JButton btnVsAI = createStyledButton("CHƠI VỚI MÁY", new Color(60, 179, 113));
        JButton btnSettings = createStyledButton("CÀI ĐẶT", new Color(186, 85, 211));
        JButton btnExit = createStyledButton("THOÁT", new Color(220, 20, 60));

        // Thêm action listener
        btnVsHuman.addActionListener(e -> startGame(false));
        btnVsAI.addActionListener(e -> startGame(true));
        btnExit.addActionListener(e -> System.exit(0));

        // Thêm các component vào contentPanel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(btnVsHuman);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(btnVsAI);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(btnSettings);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(btnExit);

        // Thêm contentPanel vào trang chủ
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(contentPanel, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 60));
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 0, 0, 80), 2),
                        BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2),
                        BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }
        });

        return button;
    }

    private void startGame(boolean vsAI) {
        parentFrame.getContentPane().removeAll();
        try {
            parentFrame.add(new BanCo(parentFrame, vsAI));
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi khởi tạo bàn cờ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ ảnh nền
        if (backgroundImage != null) {
            // Scale ảnh vừa với kích thước panel
            Image scaledImage = backgroundImage.getScaledInstance(
                    getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, this);
        }

        // Lớp phủ màu đen với độ trong suốt
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}