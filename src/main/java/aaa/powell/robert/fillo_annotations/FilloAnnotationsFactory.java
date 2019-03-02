package aaa.powell.robert.fillo_annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;

public class FilloAnnotationsFactory {
    private static ArrayList<FilloColumnField> getFilloColumnFields(Class annotatedClass) {
        String xlsxColumn;
        ArrayList<FilloColumnField> entries = new ArrayList<FilloColumnField>();
        Field[] fields = annotatedClass.getFields();
        for (Field field : fields) {
            //System.out.println("Field:" + field.getName());
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof FilloColumn) {
                    FilloColumn filloColumn = (FilloColumn) annotation;
                    if(filloColumn.columnName().length() == 0) {
                        xlsxColumn = field.getName();
                    } else {
                        xlsxColumn = filloColumn.columnName();
                    }
                    //System.out.println("FilloColumn annotated field:" + field.getName() + " columnName: " + filloColumn.columnName() + " -> " + xlsxColumn );
                    entries.add(new FilloColumnField(xlsxColumn, field));
                }
            }
        }
        return entries;
    }

    private static void setFilloColumnFields(Recordset recordset, Object object, ArrayList<FilloColumnField> filloColumnFields) throws FilloException, IllegalAccessException {
        for (FilloColumnField filloColumnField : filloColumnFields) {
            String value = "";
            value = recordset.getField(filloColumnField.columnName);
            filloColumnField.field.set(object, value);
        }
    }

    public static Object extractClassFromRecordset(Recordset recordset, Class annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        Class<?> c = Class.forName(annotatedClass.getName());
        Constructor<?> cons = c.getConstructor();
        Object object = cons.newInstance();

        ArrayList<FilloColumnField> filloColumnFields = getFilloColumnFields(annotatedClass);
        setFilloColumnFields(recordset, object, filloColumnFields);
        return object;
    }

    public static Object extractFirstClassFromRecordset(Recordset recordset, Class annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        Object object = null;
        if (recordset.next() ) {
            object = extractClassFromRecordset(recordset, annotatedClass);
        }
        return object;
    }

    public static Object extractAllClassesFromRecordset(Recordset recordset, Class annotatedClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FilloException {
        ArrayList<Object> objects = null;
        while(recordset.next()) {
            if (objects == null) {
                objects = new ArrayList<Object>();
            }
            Object object = extractClassFromRecordset(recordset, annotatedClass);
            objects.add(object);
        }
        return objects;
    }
}
