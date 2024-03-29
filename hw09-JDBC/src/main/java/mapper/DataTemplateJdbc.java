package mapper;

import otus.core.repository.DataTemplate;
import otus.core.repository.DataTemplateException;
import otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final EntityClassMetaData<T> entityClassMetaData;
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData,EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection,entitySQLMetaData.getSelectByIdSql(),List.of(id),resultSet -> {
            try{
                if(resultSet.next()){
                    return getObject(resultSet);
                }
                return null;
            }catch (SQLException e) {
                throw new DataTemplateException(e);
            }catch (Exception e){
            throw new RuntimeException("Невозможно создать объект");
        }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    clientList.add(getObject(rs));
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (Exception e) {
                throw new RuntimeException("Невозможно создать объект");
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        var param = new ArrayList<>();
        try {
            for(Field field:entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                param.add(field.get(client));
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), param);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        var param = new ArrayList<>();
        Object idParam = null;
        try {
            for(Field field:entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)){
                    idParam = field.get(client);
                }
                Object value = field.get(client);
                param.add(value);
            }
            param.add(idParam);
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), param);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
    private T getObject (ResultSet rs) throws Exception{
        var obj = entityClassMetaData.getConstructor().newInstance();

        Field idField = obj.getClass().getDeclaredField(entityClassMetaData.getIdField().getName());
        idField.setAccessible(true);
        var columnWithID = rs.getMetaData().getColumnLabel(1);
        idField.set(obj,rs.getObject(columnWithID));

        for(Field field:entityClassMetaData.getFieldsWithoutId()) {
            Field fields = obj.getClass().getDeclaredField(field.getName());
            fields.setAccessible(true);
            fields.set(obj,rs.getObject(field.getName()));
        }
        return obj;
    }


}
