package edu.school21;

import edu.school21.Annotations.DataSource;
import edu.school21.Annotations.OrmManager;
import edu.school21.User.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            OrmManager manager = new OrmManager(DataSource.getConnection());
            User user1 = new User(1L, "Oleg", "Zaharov", 165);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}