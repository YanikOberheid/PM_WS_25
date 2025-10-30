package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTestShowTables {

    public static void main(String[] args) {
        showTables();
    }

    public static void showTables() {
        String url = "jdbc:mysql://sr-labor.ddns.net:3306/PM_Gruppe_C";
        String user = "PM_Gruppe_C";
        String password = "123456789";

        try {
            // Schritt 1: Treiber laden
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Schritt 2: Verbindung herstellen
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Verbindung hergestellt!");

            // Schritt 3: Statement erstellen
            Statement stmt = conn.createStatement();

            // Schritt 4: SQL-Befehl "SHOW TABLES" ausführen
            ResultSet rs = stmt.executeQuery("SHOW TABLES");

            // Schritt 5: Ergebnisse anzeigen
            System.out.println("\nTabellen in der Datenbank:");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println(count + ". " + rs.getString(1));
            }

            if (count == 0) {
                System.out.println("(Keine Tabellen gefunden)");
            }

            // Schritt 6: Ressourcen schließen
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("\nVerbindung geschlossen.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

