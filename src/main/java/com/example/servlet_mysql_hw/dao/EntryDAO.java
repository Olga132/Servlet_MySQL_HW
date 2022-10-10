package com.example.servlet_mysql_hw.dao;

import com.example.servlet_mysql_hw.Entry;
import com.example.servlet_mysql_hw.MovementType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntryDAO {

    private static final String INSERT_LEDGER_SQL = "INSERT INTO ledger" +
            "  (date, specification, sum, movement_type) VALUES (?, ?, ?, ?);";

    private static final String SELECT_ENTRY_BY_ID = "select id,date, specification, sum, movement_type" +
            " from ledger where id =?";
    private static final String SELECT_ALL_ENTRY = "select * from ledger";
    private static final String DELETE_ENTRY_SQL = "delete from ledger where id = ?;";
    private static final String UPDATE_ENTRY_SQL = "update ledger set date = ?, specification= ?," +
            " sum =?, movement_type =? where id = ?;";


    public void insertEntry(Entry entry) {
        try (Connection connection = DBConnection.openDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LEDGER_SQL)) {
            preparedStatement.setDate(1, entry.getDate());
            preparedStatement.setString(2, entry.getSpecification());
            preparedStatement.setDouble(3, entry.getSum());
            preparedStatement.setString(4, entry.getMovementType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Entry selectEntry(int id) {
        Entry entry = null;
        // Step 1: Establishing a Connection
        try (Connection connection = DBConnection.openDBConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENTRY_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                Date date = rs.getDate(2);
                String specification = rs.getString(3);
                double sum = rs.getDouble(4);
                String movementTypeS = rs.getString(5);
                MovementType movementType;
                if(movementTypeS.equals("ПРИХОД")){
                    movementType = MovementType.ПРИХОД;
                } else movementType = MovementType.РАСХОД;


                entry = new Entry(id, date, specification, sum, movementType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entry;
    }


    public List<Entry> selectAllEntries() {

        List<Entry> entries = new ArrayList<>();

        try (Connection connection = DBConnection.openDBConnection()) {
            assert connection != null;
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ENTRY);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Date date = resultSet.getDate(2);
                String specification = resultSet.getString(3);
                double sum = resultSet.getDouble(4);
                String movementTypeS = resultSet.getString(5);
                MovementType movementType;
                if(movementTypeS.equals("ПРИХОД")){
                    movementType = MovementType.ПРИХОД;
                } else movementType = MovementType.РАСХОД;
                Entry entry = new Entry(id, date, specification, sum, movementType);
                entries.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entries;
    }

    public boolean deleteEntry(int id) throws SQLException {
        boolean rowDeleted;

        try (Connection connection = DBConnection.openDBConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ENTRY_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateEntry(Entry entry) throws SQLException {
        boolean rowUpdated;

        try (Connection connection = DBConnection.openDBConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENTRY_SQL)) {
            statement.setDate(1, entry.getDate());
            statement.setString(2, entry.getSpecification());
            statement.setDouble(3, entry.getSum());
            statement.setString(4, entry.getMovementType().toString());
            statement.setInt(5, entry.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public double getSumEntries(){
        double sum = 0;
        List<Entry> entries = selectAllEntries();
        for (Entry e: entries) {
            if(e.getMovementType() == MovementType.ПРИХОД){
                sum = sum + e.getSum();
            } else sum = sum - e.getSum();
        }
        return sum;
    }

    public List<Entry> getEntriesByCondition(String str) {

        List<Entry> entries = new ArrayList<>();

        try (Connection connection = DBConnection.openDBConnection()) {
            assert connection != null;

            String sql_query2 = "select * from ledger where concat(id,'.', date , '.', sum, '.', specification," +
                    " '.', movement_type, '.') like '%" + str + "%';";

            PreparedStatement statement = connection.prepareStatement(sql_query2);
            ResultSet resultSet = statement.executeQuery(sql_query2);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Date date = resultSet.getDate(2);
                String specification = resultSet.getString(3);
                double sum = resultSet.getDouble(4);
                String movementTypeS = resultSet.getString(5);
                MovementType movementType;
                if(movementTypeS.equals("ПРИХОД")){
                    movementType = MovementType.ПРИХОД;
                } else movementType = MovementType.РАСХОД;

                Entry entry = new Entry(id, date, specification, sum, movementType);
                entries.add(entry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entries;

    }

}
