package mapper;

import otus.core.repository.DataTemplate;
import otus.core.repository.DataTemplateException;
import otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
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
                    var obj = entityClassMetaData.getConstructor().newInstance();

                    Field idField = obj.getClass().getDeclaredField(entityClassMetaData.getIdField().getName());
                    idField.setAccessible(true);
                    var columnWithID = resultSet.getMetaData().getColumnLabel(1);
                    idField.set(obj,resultSet.getObject(columnWithID));

                    for(Field field:entityClassMetaData.getFieldsWithoutId()) {
                        Field fields = obj.getClass().getDeclaredField(field.getName());
                        fields.setAccessible(true);
                        fields.set(obj,resultSet.getObject(field.getName()));
                    }
                    return obj;
                }
                return null;
            }catch (SQLException e) {
                throw new DataTemplateException(e);
            }catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e){
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
                    long id = rs.getLong(1);
                    var obj = (T) findById(connection,id);
                    clientList.add(obj);
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
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
}
