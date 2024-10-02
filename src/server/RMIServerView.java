package server;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class RMIServerView {
    public RMIServerView() throws RemoteException, SQLException, ClassNotFoundException {
        new RMIServerControl();
        showMessage("Server is running...");
    }

    public void showMessage(String message){
        System.out.println(message);
    }
}
