package mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    public Class<T> clazz;
    private final List<Field> fields;

    public EntityClassMetaDataImpl(){
        this.clazz = this.getClassT();
        this.fields = Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try{
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        for(Field field:fields){
            if(field.isAnnotationPresent(Id.class)){
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        var fieldsWithOutId = new ArrayList<Field>();
        for(Field field:fields){
            if(!(field.isAnnotationPresent(Id.class))){
                fieldsWithOutId.add(field);
            }
        }
        return fieldsWithOutId;
    }

    Class<T> getClassT(){
        Type genericSuperClass = getClass().getGenericSuperclass();
        ParameterizedType superType = (ParameterizedType) genericSuperClass;
        return  (Class<T>) superType.getActualTypeArguments()[0];
    }
}
