package cresc1;

import java.util.ArrayList;

import org.testng.TestNG;

public class runChangePermission {

	public static void main(String[] args) {
		TestNG testng = new TestNG();
		ArrayList<String> file = new ArrayList<String>();
		file.add("C:\\QA\\ChangePermission.xml");//path to xml..
		testng.setTestSuites(file);
		testng.run();

	}

}
