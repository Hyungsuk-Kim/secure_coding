import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CommandInjection {
	
	public void nonSafeExec(String input) throws IOException {
		// 기본 쉘에서 수행할 수 있는 명령어 세트를 입력된 값을 아무런 처리없이 그대로 사용하여 동적으로 생성한다.
		String[] command = null;
		
		String osType = System.getProperty("os.name");
		
		if (osType.toLowerCase().startsWith("window")) { // Windows 계열 운영체제
			command = new String[] {"cmd.exe", "/c", input};
		} else { // 기타 운영체제 (Unix, Linux, Mac OS, Solaris, FreeBSD 등)
			command = new String[] {"/bin/sh", input};
		}
		
		Process process = Runtime.getRuntime().exec(command); // 입력된 명령어 세트 수행.
		
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
	
	public void safeExec(String input) throws Exception {
		// 기본 쉘에서 수행할 수 있는 명령어 세트를 입력된 값을 필터링(화이트리스트에 등록된 값만 허용)하여 동적으로 생성한다.
		String[] command = this.creationCmd(input);
		
		Process process = Runtime.getRuntime().exec(command); // 입력된 명령어 세트 수행.
		
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
	
	public String[] creationCmd(String input) throws Exception {
		String[] command = null;
		
		String[] availableCmdForWindows = {/* 윈도 계열 명령어 중 허용할 수 있는 명령어 배열 */};
		String[] availableCmdForOthers = {/* 기타 OS의 명령어 중 허용할 수 있는 명령어 배열 */};
		
		String osType = System.getProperty("os.name");
		
		if (osType.toLowerCase().startsWith("window")) { // Windows 계열 운영체제
			// 화이트리스트에 등록된 명령어인 경우에만 수행가능한 명령어 생성
			for (String cmd : availableCmdForWindows) {
				if (input.equals(cmd)) {
					command = new String[] {"cmd.exe", "/c", input};
				}
			}
		} else { // 기타 운영체제
			// 화이트리스트에 등록된 명령어인 경우에만 수행가능한 명령어 생성
			for (String cmd : availableCmdForOthers) {
				if (input.equals(cmd)) {
					command = new String[] {"/bin/sh", input};
				}
			}
		}
		// 화이트리스트에 등록되지 않은 명령어일 경우(명령어 생성되지 않은 경우) 예외 발생
		if (command == null) {
			throw new Exception("사용할 수 없는 명령어입니다.");
		}
		return command;
	}
	
	public static void main(String[] args) throws IOException {
		CommandInjection ci = new CommandInjection();
		
		// request.getParameter()와 같이 사용자의 입력을 받아올 수 있는 코드
		String inputValue = args[0]; // Command-Line arguments로 부터 값을 받아온다.
		
		// 안전하지 않은 코드
		ci.nonSafeExec(inputValue);
		
		// 안전한 코드
		try {
			ci.safeExec(inputValue);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
