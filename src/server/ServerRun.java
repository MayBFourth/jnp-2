package server;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class ServerRun {
    public static void main(String[] args) throws RemoteException, SQLException, ClassNotFoundException {
        RMIServerView view = new RMIServerView();
    }
}
