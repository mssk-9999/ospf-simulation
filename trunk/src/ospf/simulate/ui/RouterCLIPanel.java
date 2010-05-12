package ospf.simulate.ui;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;
import ospf.simulate.router.Router;

/**
 * 路由器的CLI命令行操控平台
 * 
 * @author hutushen222
 * 
 */
@SuppressWarnings("serial")
public class RouterCLIPanel extends JPanel {
	/** 当前进行配置的路由器 */
	private Router router;

	/** 用户输入的文本区 */
	private JTextArea textArea;

	/***/
	private String userInput;
	private CommandMode currentMode;
	private String prompt;
	private String resultMessage;
	private Pattern enable = Pattern.compile("en|ena|enab|enabl|enable");
	private Pattern exit = Pattern.compile("exit");
	private Pattern end = Pattern.compile("end");
	private Pattern conft = Pattern
			.compile("(conf|confi|config|configu|configur|configure)( ++)(t|te|ter|term|termi|termin|termina|terminal)");
	private Pattern showIpRouter = Pattern
			.compile("(sh|sho|show)( ++)ip rou|rout|route|router");
	private Pattern interfacePort = Pattern
			.compile("(int|inte|inter|interf|interfa|interfac|interface)( ++).*");
	private Pattern defOSPF = Pattern.compile("(router)( ++)ospf( ++)([0-9]+?)");
	private Pattern ipAdd = Pattern
			.compile("(ip)( ++)(add|addr|addre|addres|adreess)( ++)([0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+)( ++)([0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+)");
	private Pattern assignNetwork = Pattern.compile("(network)( ++)([0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+)( ++)([0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+\\.[0-9]{1,3}+)( ++)(area)( ++)([0-9]+?)");
	private Pattern noShut = Pattern
			.compile("(no)( ++)(shut|shut|shutd|shutdo|shutdow|shutdown)");
	private Interface currentPort;

	// 构造并初始化CLI
	public RouterCLIPanel(Router router) {
		this.router = router;
		switchMode(CommandMode.USER_MODE);
		textArea = new JTextArea(25, 50);
		textArea.setFont(new Font("SansSerif", Font.BOLD, 20));
		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane);
		textArea.append(prompt);
		textArea.setCaretPosition(prompt.length());
		textArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					int lineNum = textArea.getLineCount() - 1;
					String[] lines = textArea.getText().split("\n");
					String[] parts = lines[lineNum - 1].split(">|#");
					if (parts.length > 1)
						userInput = parts[1];
					else
						userInput = "";
					System.out.println(userInput);
					handleCommand();
					textArea.append(resultMessage + prompt);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	// 处理用户输入的命令
	private void handleCommand() {
		if (userInput.length() == 0)
			return;
		System.out.println(currentMode);
		switch (currentMode) {
		case USER_MODE:
			handleUserMode();
			break;
		case PRIVILEGED_MODE:
			handlePrivilegedMode();
			break;
		case CONFIG_MODE:
			handleConfigMode();
			break;
		case PORT_MODE:
			handlePortMode();
			break;
		case LOCAL_MODE:
			handleLocalMode();
		}
		System.out.println(currentMode);
	}

	// 处理用户模式下的命令
	private void handleUserMode() {
		if (enable.matcher(userInput).matches()) {
			switchMode(CommandMode.PRIVILEGED_MODE);
			return;
		}
		resultMessage = "Illegal command.\n";
	}

	// 处理特权模式下的命令
	private void handlePrivilegedMode() {
		if (exit.matcher(userInput).matches()
				|| end.matcher(userInput).matches()) {
			switchMode(CommandMode.USER_MODE);
			return;
		}
		if (conft.matcher(userInput).matches()) {
			switchMode(CommandMode.CONFIG_MODE);
			return;
		}
		if (showIpRouter.matcher(userInput).matches()) {
			resultMessage = "";
			showIpRouter();
			return;
		}
		resultMessage = "Illegal command.\n";
	}

	// 处理配置模式下的命令
	private void handleConfigMode() {
		if (exit.matcher(userInput).matches()) {
			switchMode(CommandMode.PRIVILEGED_MODE);
			return;
		}
		if (end.matcher(userInput).matches()) {
			switchMode(CommandMode.USER_MODE);
			return;
		}
		if (interfacePort.matcher(userInput).matches()) {
			if (portExists()) {
				switchMode(CommandMode.PORT_MODE);
			} else {
				resultMessage = "No such port.\n";
			}
			return;
		}
		if (defOSPF.matcher(userInput).matches()) {
			if (vaildID()) {
				switchMode(CommandMode.LOCAL_MODE);
			} else {
				resultMessage = "Unvalid process id\n";
			}
			return;
		}
		resultMessage = "Illgel command.\n";
	}

	// 处理端口模式下的命令
	private void handlePortMode() {
		if (exit.matcher(userInput).matches()) {
			switchMode(CommandMode.CONFIG_MODE);
			return;
		}
		if (end.matcher(userInput).matches()) {
			switchMode(CommandMode.USER_MODE);
			return;
		}
		if (ipAdd.matcher(userInput).matches()) {
			if (vaildIP()) {
				addIPAddress();
				resultMessage = "";
			} else {
				resultMessage = "Bad IP address or mask.\n";
			}
			return;
		}
		if (noShut.matcher(userInput).matches()) {
			resultMessage = currentPort.getType().toString()
					+ currentPort.getInterfaceNumber() + " changed state to start-up.\n";
			return;
		}
		resultMessage = "Illgel command.\n";
	}

	// 处理局部模式下的命令
	private void handleLocalMode() {
		if (exit.matcher(userInput).matches()) {
			switchMode(CommandMode.CONFIG_MODE);
			return;
		}
		if (end.matcher(userInput).matches()) {
			switchMode(CommandMode.USER_MODE);
			return;
		}
		if (assignNetwork.matcher(userInput).matches()) {
			if (vaildNetwork()) {
				assignNetwork();
			} else {
				resultMessage = "Invalid IP.\n";
			}
			return;
		}
		resultMessage = "Illgel command.\n";
	}

	// 添加IP到当前配置的端口
	private void addIPAddress() {
		System.out.println("Add ip.");
		String[] parts = userInput.toString().split(" ");
		IP ip = new IP(parts[2], parts[3]);
		currentPort.setIp(ip);
	}

	// 指派网络到特定的OSPF区域
	private void assignNetwork() {
		// 意思一下而已
		System.out.println("Assign network.");
	}

	// 显示路由表
	private void showIpRouter() {
		// 获取路由表，路由器应该添加该方法
		// String result = router.getRouter();
		String result = "router table.\n";
		textArea.append(result);
	}

	// 检测用户输入的network是否有效
	private boolean vaildNetwork() {
		// TODO Auto-generated method stub
		return true;
	}

	// 检测用户输入的IP是否有效
	private boolean vaildIP() {
		String[] parts = userInput.split(" ");
		String[] ipParts = parts[2].split("\\.");
		String[] maskParts = parts[3].split("\\.");
		for (String part : ipParts) {
			int value = Integer.parseInt(part);
			if (value < 0 || value > 255)
				return false;
		}
		for (String part : maskParts) {
			int value = Integer.parseInt(part);
			if (value < 0 || value > 255)
				return false;
		}
		return true;
	}

	// 判断用户指定的OSPF process id 是否合法
	private boolean vaildID() {
		String[] parts = userInput.toString().split(" ");
		int id = Integer.parseInt(parts[2]);
		if (id >= 0 && id <= 65535) {
			return true;
		}
		return false;
	}

	// 判断当前路由器是否有用户要配置的接口
	private boolean portExists() {
		String[] parts = userInput.toString().split(" ");
		for (Interface port : router.getInterfaces()) {
			System.out.println(port.getType().toString()
					+ port.getInterfaceNumber());
			if (parts[1].toUpperCase().equals(
					port.getType().toString() + port.getInterfaceNumber())) {
				currentPort = port;
				return true;
			}
		}
		return false;
	}

	// 切换到指定命令模式
	private void switchMode(CommandMode newMode) {
		currentMode = newMode;
		resultMessage = "";
		switch (currentMode) {
		case USER_MODE:
			prompt = router.getName() + ">";
			break;
		case PRIVILEGED_MODE:
			prompt = router.getName() + "#";
			break;
		case CONFIG_MODE:
			prompt = router.getName() + "(config)#";
			break;
		case PORT_MODE:
			prompt = router.getName() + "(config-if)#";
			break;
		case LOCAL_MODE:
			prompt = router.getName() + "(config-route)#";
		}
	}

	// 测试方法
	public static void main(String[] args) {
		JFrame frame = new JFrame("CLI");
		RouterCLIPanel panel = new RouterCLIPanel(new Router("Router"));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}

// 命令模式枚举
enum CommandMode {
	USER_MODE, // 用户模式
	PRIVILEGED_MODE, // 特权模式
	CONFIG_MODE, // 配置模式
	PORT_MODE, // 端口模式
	LOCAL_MODE, // 局部模式
}
