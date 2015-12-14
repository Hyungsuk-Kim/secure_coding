import java.io.IOException;
import java.io.InputStream;
//import java.util.Properties;
import java.util.Scanner;

public class Temp {
	public static void main(String[] args) throws IOException {
		/*Properties props = System.getProperties();
		for (String pName : props.stringPropertyNames()) {
			System.out.print("Property : " + pName + " - ");
			System.out.println(props.getProperty(pName).toString());
		}*/
		
		//String[] command = {"/bin/sh", "-c", "echo $SHELL; pwd; touch .temp.tmp; ls -a; rm .temp.tmp; ls -a"};
		//String command = "open -a Terminal";
		
		// Web 환경에서 HTTPRequest.getParmeter()와 같은 메서드의 리턴 값을 사용하지만
		// 로컬 환경에서 간단하게 결과 확인을 위해 키보드로부터 값을 입력 받도록 임시 구현
		
		Scanner parameter = new Scanner(System.in); // 시스템의 기본 입력 장치(키보드)로부터의 입력 받음.
		String[] command = null; // 프로세스가 수행할 명령어 세트
		if (parameter.hasNext()) {
			// 기본 쉘에서 수행할 수 있는 명령어 세트를 입력된 값을 사용하여 동적으로 생성한다.
			command = new String[] {"/bin/sh", "-c", parameter.nextLine()};
		}
		
		Process process = Runtime.getRuntime().exec(command); // 입력된 명령어 세트 수행.
		
		parameter.close();
		
		// 결과 출력부분
		InputStream in = process.getInputStream();
		Scanner scanner = new Scanner(in, "UTF-8");
		StringBuilder builder = new StringBuilder();
		while (scanner.hasNextLine()) {
			builder.append(scanner.nextLine() + "\n");
		}
		System.out.println(builder.toString());
		scanner.close();
	}
}
