package Model_HoaDon;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHD {

    // Thay đổi các thông tin này thành tên tài khoản và mật khẩu email 
    private static final String username = "thanhkvph34331@fpt.edu.vn"; // Tên tài khoản Gmail 
    private static final String password = "zxqonxacgdthvjej"; // Mật khẩu Gmail 

    // Phương thức để gửi email
    public static void sendEmail(String recipient, String subject, String content) {
        // Cấu hình các thuộc tính cần thiết cho việc gửi email thông qua Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo phiên làm việc (Session) để gửi email, sử dụng Authenticator để xác thực thông tin đăng nhập
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo một đối tượng tin nhắn (Message)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Thiết lập địa chỉ người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // Thiết lập địa chỉ người nhận
            message.setSubject(subject); // Thiết lập tiêu đề email
            message.setText(content); // Thiết lập nội dung email

            // Gửi tin nhắn
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipient);
        } catch (MessagingException e) {
            // Xử lý lỗi nếu gửi email thất bại
            System.err.println("Failed to send email to " + recipient + ": " + e.getMessage());
        }
    }
}
