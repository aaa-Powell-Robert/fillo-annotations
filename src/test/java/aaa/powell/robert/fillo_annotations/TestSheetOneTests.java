package aaa.powell.robert.fillo_annotations;

import java.lang.reflect.InvocationTargetException;

import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class TestSheetOneTests {
    @FilloColumn
    public String TestSheetOneColumnOne;

    @FilloColumn
    public String TestSheetOneColumnTwo;

    @FilloColumn
    public String TestSheetOneColumnThree;

    @FilloColumn (columnName="TestSheetOneColumnFour BooleanDropdownValues")
    public String TestSheetOneColumnFour;

    public String toString() {
        return "TestSheetOneTests(" +
                "TestSheetOneColumnOne='"+ TestSheetOneColumnOne + "', " +
                "TestSheetOneColumnTwo='"+ TestSheetOneColumnTwo + "', " +
                "TestSheetOneColumnThree='"+ TestSheetOneColumnThree + "', " +
                "TestSheetOneColumnFour='"+ TestSheetOneColumnFour + "')";
    }

    @Test
    public void testFilloTestSheetOne() throws FilloException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String xlsxFilename = "src/test/resources/TestWorkbook001.xlsx";
        System.out.println("xlsx=" + xlsxFilename);

        Fillo fillo=new Fillo();
        Connection connection=fillo.getConnection(xlsxFilename);
        Recordset recordset=connection.executeQuery("Select * From TestSheetOne");
        while(recordset.next()) {
        	TestSheetOneTests testSheetOneTests = (TestSheetOneTests) FilloAnnotationsFactory.extractClassFromRecordset(recordset, TestSheetOneTests.class);
            System.out.println(testSheetOneTests.toString());
        }
        recordset.close();
        connection.close();
    }
}
