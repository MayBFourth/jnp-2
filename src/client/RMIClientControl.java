package client;

import app.RMIInterface;
import app.Student;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIClientControl {
    private String serverHost = "localhost";
    private int serverPort = 3232;
    private RMIInterface rmiServer;
    private Registry registry;
    private String rmiService = "rmiServer";

    public RMIClientControl(){
        try{
            registry = LocateRegistry.getRegistry(serverHost, serverPort);
            rmiServer = (RMIInterface) (registry.lookup(rmiService));

        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }
    }

    public List<Student> remoteSearchByName(String name) throws RemoteException {
        return rmiServer.searchByName(name);
    }

    public List<Student> remoteSearchByGPA(double minGPA, double maxGPA) throws RemoteException {
        return rmiServer.searchByGPA(minGPA, maxGPA);
    }

    public void remoteUpdateStudent(Student student) throws RemoteException {
        rmiServer.updateStudent(student);
    }

    public List<Student> remoteGetAllStudents() throws RemoteException {
        return rmiServer.getAllStudents();
    }
}
