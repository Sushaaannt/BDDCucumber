package generic;

import io.cucumber.java.Scenario;
import utilities.FileOperations;

import java.util.Map;
import java.util.Scanner;

public class TestdataHandler {
    static String test_Id;
    static int testDataRowNumber = 0;
    static Map<String, String> fixtureData;
    public String DebugMode = null;
    public String ScreenWiseDataApproachEnabled = null;
    private static TestdataHandler instance = null;
    public static boolean readMostRecentTagName = false;
    FileOperations fileOperations = null;
    static Scenario scenario;
    public static TestdataHandler getInstance() {
        if (instance == null) {
            instance = new TestdataHandler();
        }
        return instance;
    }
    public static Scenario getScenario(){return scenario;}

    public String getTestData(String columnName) throws Exception {
        String data = null;
        columnName = columnName.substring(1);
        boolean keyPresent = fixtureData.containsKey(columnName);
        if (keyPresent) {
            data = fixtureData.get(columnName);
        } else {
            if (DebugMode.equalsIgnoreCase("true")) {
                Scanner scanner = new Scanner(System.in);
                long then = System.currentTimeMillis();
                String line = scanner.nextLine();
                long now = System.currentTimeMillis();
                data = line;
                setTestData(columnName, data);
            }
        }
        return data;
    }

    public boolean setTestData(String columnName, String data) throws Exception {
        return setTestData(columnName, data, false);
    }

    public boolean setTestData(String columnName, String data, boolean addDataInNextRow) throws Exception {
        String sheetname = null;
        if (ScreenWiseDataApproachEnabled.equalsIgnoreCase("true")) {
            try {
                sheetname = JsonHandler.getInstance().getApplicationPage();
                System.out.println("Current Data Sheet : " + sheetname);

            } catch (Exception e) {
                System.out.println("No Sheet found in Test Data Sheet " + JsonHandler.getInstance().getApplicationPage());
                throw new Exception(e);
            }
        } else {
            sheetname = fileOperations.getSheetName(0);
            System.out.println("Data Sheet at index 0 is : " + sheetname);
        }
        try {
            boolean keyPresent = fixtureData.containsKey(columnName);
            if (keyPresent) {
                System.out.println(columnName+" column name already exist in test data sheet");
            } else {
                fileOperations.addColumn(sheetname, columnName);
            }
            fixtureData.put(columnName, data);
            int rownumber = getRowNumber(fileOperations, sheetname, getTestId());
            fileOperations.setXLSXCellData(sheetname,columnName,rownumber,data);
            if (addDataInNextRow) {
                rownumber = rownumber + 1;
                fileOperations.setXLSXCellData(sheetname,columnName,rownumber,data);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public int getRowNumber(FileOperations fileOperations, String sheetName, String test_ID) {
        int row = 0;
        try {
            if (test_ID.startsWith("@")) {
                test_ID = test_ID.substring(1);
            }
            if (test_ID.startsWith("Test_Data_")) {
                test_ID = test_ID.substring(test_ID.indexOf("Test_Data_") + 10);
            }
            if (readMostRecentTagName) {
                row = fileOperations.getCellRowInDescendingOrder(sheetName, "Test_Data", test_ID.trim());
                readMostRecentTagName = false;
            } else {
                row = fileOperations.getcellRowNum(sheetName, "Test_Data", test_ID.trim());
            }
            setTestDataRowNumber(row);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return row;
    }

    public String getTestId() {
        return test_Id;
    }

    public static void setTestId(String name) {

        test_Id = name;
    }

    public static void setTestDataRowNumber(int testDataRowNumber) {
        TestdataHandler.testDataRowNumber = testDataRowNumber;
    }
    public static void setScenario(Scenario scenario1) {
        scenario = scenario1;
    }

}
