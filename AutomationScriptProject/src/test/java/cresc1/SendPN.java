package cresc1;

import java.util.ArrayList;

import org.testng.TestNG;

public class SendPN {

	public static void main(String[] args) {
		TestNG testng = new TestNG();
		ArrayList<String> file = new ArrayList<String>();
		file.add("D:\\SendPN.xml");//path to xml..
		testng.setTestSuites(file);
		testng.run();

	}

}
