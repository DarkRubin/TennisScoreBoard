package DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import model.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Optional.*;


class DAOTest {



    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        Player player = new Player();
        player.setName("Nastya");

        String sql = """
                INSERT INTO
                %s
                (%s)
                VALUES
                (%s)
                """;

        String tableName = ofNullable(player.getClass().getAnnotation(Table.class))
                .map(Table::name)
                .orElse(player.getClass().getName());
        Field[] declaredFields = player.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(","));
        String columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted( tableName, columnNames, columnValues));
        for (Field declaretedField : declaredFields) {
            declaretedField.setAccessible(true);
            preparedStatement.setObject(1, declaretedField.get(player));
        }
    }
}