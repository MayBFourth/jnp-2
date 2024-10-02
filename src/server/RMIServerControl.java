package server;

import app.RMIInterface;
import app.Student;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RMIServerControl extends UnicastRemoteObject implements RMIInterface {
    private int serverPort = 3232;
    private Registry registry;
    private String rmiService = "rmiServer";
    static String url = "jdbc:mysql://localhost:3307/jnp"; //
    static String user = "dbuser";
    static String password = "dbpass";

    protected RMIServerControl() throws RemoteException, SQLException, ClassNotFoundException {
        getConnection();
        try{
            registry = LocateRegistry.createRegistry(serverPort);
            registry.rebind(rmiService, this);
        }catch(RemoteException e){
            throw e;
        }
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

    }

    @Override
    public List<Student> searchByName(String name) throws RemoteException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String studentCode = rs.getString("studentCode");
                String studentName = rs.getString("name");
                int yob = rs.getInt("yob");
                String address = rs.getString("address");
                double gpa = rs.getDouble("gpa");

                students.add(new Student(id, studentCode, studentName, yob, address, gpa));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return students;
    }


    @Override
    public List<Student> searchByGPA(double minGPA, double maxGPA) throws RemoteException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE gpa BETWEEN ? AND ?"; //

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, minGPA);
            pstmt.setDouble(2, maxGPA);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String studentCode = rs.getString("studentCode");
                String studentName = rs.getString("name");
                int yob = rs.getInt("yob");
                String address = rs.getString("address");
                double gpa = rs.getDouble("gpa");

                students.add(new Student(id, studentCode, studentName, yob, address, gpa));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public void updateStudent(Student student) throws RemoteException {
        String sql = "UPDATE students SET studentCode = ?, name = ?, yob = ?, address = ?, gpa = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentCode());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getYob());
            pstmt.setString(4, student.getAddress());
            pstmt.setDouble(5, student.getGpa());
            pstmt.setInt(6, student.getId());

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("Cập nhật thông tin sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + student.getId());
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() throws RemoteException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Lấy dữ liệu từ từng cột trong kết quả truy vấn
                int id = rs.getInt("id");
                String studentCode = rs.getString("studentCode");
                String name = rs.getString("name");
                int yob = rs.getInt("yob");
                String address = rs.getString("address");
                double gpa = rs.getDouble("gpa");

                // Tạo đối tượng Student và thêm vào danh sách
                Student student = new Student(id, studentCode, name, yob, address, gpa);
                students.add(student);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // In lỗi nếu có
        }

        return students;
    }
}
