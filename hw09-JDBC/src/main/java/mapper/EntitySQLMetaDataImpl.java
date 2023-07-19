package mapper;

import com.google.common.base.Joiner;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class EntitySQLMetaDataImpl<S> implements EntitySQLMetaData{

    private final EntityClassMetaData<S> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<S> entityClassMetaData){
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM" + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        var out =  "SELECT * FROM " + entityClassMetaData.getName() + " WHERE id = ?";
        return out;
    }

    @Override
    public String getInsertSql() {
        var listFields = new ArrayList<>();
        String delim = ",";
        StringBuilder formatDelim = new StringBuilder();

        for(int count = entityClassMetaData.getFieldsWithoutId().size(); count > 0; count--){
            if(count == 1){
                formatDelim.append("?");
                continue;
            }
            formatDelim.append("?, ");
        }

        for(Field field: entityClassMetaData.getFieldsWithoutId()){
            listFields.add(field.getName());
        }
        return "INSERT INTO " + entityClassMetaData.getName() + " (" + Joiner.on(delim).join(listFields) + ") " + "VALUES(" + formatDelim + ")";
    }

    @Override
    public String getUpdateSql() {
        StringBuilder result= new StringBuilder("UPDATE " + entityClassMetaData.getName() + "SET");
        for(int count = 0; count < entityClassMetaData.getFieldsWithoutId().size(); count++) {
            result.append(entityClassMetaData.getFieldsWithoutId().get(count).getName()).append("=?");
        }
        result.append(" WHERE ID = ?");
        return result.toString();
    }
}
