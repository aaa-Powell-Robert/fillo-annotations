package aaa.powell.robert.fillo_annotations;

import java.lang.reflect.Field;

public class FilloColumnField {
    public String columnName;
    public Field field;

    public FilloColumnField (String columnName, Field field) {
        this.columnName = columnName;
        this.field = field;
    }
}