
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    public Connection connectDB() {
        Connection conn = null;
        
        try {
            File databaseDir = new File("db");
            if (!databaseDir.exists()) {
                databaseDir.mkdirs();
            }

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:db/leiloes.db");
        } catch (ClassNotFoundException erro) {
            if (!GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(null, "Driver do SQLite não encontrado: " + erro.getMessage());
            }
        } catch (SQLException erro) {
            if (!GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(null, "Erro na conexão com o banco: " + erro.getMessage());
            }
        }
        return conn;
    }
    
}
