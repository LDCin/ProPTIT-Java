import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeApp {
    public static void main(String[] args) {
        // Chạy giao diện trên luồng sự kiện
        SwingUtilities.invokeLater(() -> {
            // Tạo cửa sổ
            JFrame frame = new JFrame("Ứng dụng hiển thị thời gian");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout()); // Sử dụng FlowLayout để sắp xếp đơn giản

            // Tạo nhãn để hiển thị thời gian
            JLabel timeLabel = new JLabel("Nhấn nút để xem thời gian");
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Tùy chỉnh kích thước chữ

            // Tạo nút bấm
            JButton updateButton = new JButton("Cập nhật thời gian");

            // Định dạng thời gian
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

            // Thêm hành động khi nhấn nút
            updateButton.addActionListener(e -> {
                String currentTime = dateFormat.format(new Date()); // Lấy thời gian hiện tại
                timeLabel.setText(currentTime); // Cập nhật nhãn
            });

            // Thêm các thành phần vào frame
            frame.add(timeLabel);
            frame.add(updateButton);

            // Hiển thị cửa sổ
            frame.setVisible(true);
        });
    }
}