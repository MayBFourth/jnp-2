package app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIInterface extends Remote {
    List<Student> searchByName(String name) throws RemoteException;

    List<Student> searchByGPA(double minGPA, double maxGPA) throws RemoteException;
    void updateStudent(Student student) throws RemoteException;
    List<Student> getAllStudents() throws RemoteException;
}
