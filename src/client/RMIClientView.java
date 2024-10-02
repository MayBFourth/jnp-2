package client;

import app.RMIInterface;
import app.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.List;

public class RMIClientView extends JFrame{


    private JTextField nameField;
    private JTextField minGPAField;
    private JTextField maxGPAField;
    private JTextArea resultArea;

    public RMIClientView() {
        setTitle("Student Search");
        setSize(800 , 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel cho chức năng tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(5, 2));

        searchPanel.add(new JLabel("Search by Name:"));
        nameField = new JTextField();
        searchPanel.add(nameField);

        searchPanel.add(new JLabel("Min GPA:"));
        minGPAField = new JTextField();
        searchPanel.add(minGPAField);

        searchPanel.add(new JLabel("Max GPA:"));
        maxGPAField = new JTextField();
        searchPanel.add(maxGPAField);

        // Nút tìm kiếm
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());

        // Nút làm sạch
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());

        JButton getButton = new JButton("Get All");
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");  // Xóa kết quả trước đó
                RMIClientControl clientControl = new RMIClientControl();
                try {
                    List<Student> students = clientControl.remoteGetAllStudents();
                    resultArea.append("All Students:\n");
                    for (Student student : students) {
                        resultArea.append(student.toString() + "\n");
                    }
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(RMIClientView.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // Khu vực hiển thị kết quả
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        // Thêm các phần tử vào Frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(getButton);
        buttonPanel.add(updateButton);

        add(searchPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);

    }


    private void clearFields() {
        nameField.setText("");
        minGPAField.setText("");
        maxGPAField.setText("");
        resultArea.setText("");
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultArea.setText("");  // Xóa kết quả trước đó
            String name = nameField.getText();
            String minGPAText = minGPAField.getText();
            String maxGPAText = maxGPAField.getText();
            RMIClientControl clientControl = new RMIClientControl();
            try {
                // Tìm kiếm theo tên
                if (!name.isEmpty()) {
                    System.out.println(name);
                    List<Student> studentsByName = clientControl.remoteSearchByName(name);
                    System.out.println(studentsByName);
//                    resultArea.append("Search by Name:\n");
                    for (Student student : studentsByName) {
                        resultArea.append(student.toString() + "\n");
                    }
                }

                // Tìm kiếm theo khoảng GPA
                if (!minGPAText.isEmpty() && !maxGPAText.isEmpty()) {
                    double minGPA = Double.parseDouble(minGPAText);
                    double maxGPA = Double.parseDouble(maxGPAText);
                    List<Student> studentsByGPA = clientControl.remoteSearchByGPA(minGPA, maxGPA);
                    System.out.println(studentsByGPA);
                    resultArea.append("\nSearch by GPA between " + minGPA + " and " + maxGPA + ":\n");
                    for (Student student : studentsByGPA) {
                        resultArea.append(student.toString() + "\n");
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(RMIClientView.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame updateFrame = new JFrame("Cập nhật thông tin sinh viên");
            updateFrame.setSize(300, 300);
            updateFrame.setLayout(new GridLayout(7, 2)); // 6 trường + 1 nút

            JTextField idField = new JTextField();
            JTextField codeField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField gpaField = new JTextField();

            updateFrame.add(new JLabel("ID:"));
            updateFrame.add(idField);
            updateFrame.add(new JLabel("Code:"));
            updateFrame.add(codeField);
            updateFrame.add(new JLabel("Name:"));
            updateFrame.add(nameField);
            updateFrame.add(new JLabel("Year of Birth:"));
            updateFrame.add(yearField);
            updateFrame.add(new JLabel("Address:"));
            updateFrame.add(addressField);
            updateFrame.add(new JLabel("GPA:"));
            updateFrame.add(gpaField);

            JButton submitButton = new JButton("Cập nhật");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Xử lý lưu thông tin sinh viên ở đây
                    String id = idField.getText();
                    String code = codeField.getText();
                    String name = nameField.getText();
                    String year = yearField.getText();
                    String address = addressField.getText();
                    String gpa = gpaField.getText();

                    Student student = new Student(Integer.parseInt(id), code, name, Integer.parseInt(year), address, Double.parseDouble(gpa));
                    RMIClientControl clientControl = new RMIClientControl();
                    try {
                        clientControl.remoteUpdateStudent(student);
                        JOptionPane.showMessageDialog(updateFrame, "Cập nhật thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(updateFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateFrame.dispose();
                }
            });

            updateFrame.add(submitButton);
            updateFrame.setVisible(true);
        }
    }

}
