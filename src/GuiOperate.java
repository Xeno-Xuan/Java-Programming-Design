import JDBCTest.ComTest;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static JDBCTest.ComTest.*;


/**
 * @Author : Xuan
 * @Date ：Created in 2021/10/24 15:48
 * @Description：
 * @Modified By：
 * @Version: $5.0.0
 */
public class GuiOperate extends JFrame {
    private final static int DEFAULT_WIDTH = 900;
    private final static int DEFAULT_HEIGHT = 600;
    private String normalButtonUrl = "E:\\FFOutput\\normalButton.png";
    private String pressedButtonUrl = "E:\\FFOutput\\pressedButton.png";
    private JPanel mainPanel;
    private JLabel userName;
    private JLabel password;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JFrame frame;
    private ComTest ct;

    private void createMainUI() {
        //窗口：抬头，大小，安全关闭，居中显示，可视，边框可拖，空布局
        frame = new JFrame();
        frame.setTitle("GUI Form测试");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel line = new JLabel(">—————————————————<=================>—————————————————<");
        line.setForeground(new Color(8,90,242));
        line.setBounds(40, 10, 900, 30);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JLabel bottom = new JLabel(new ImageIcon("E:\\FFOutput\\bottom.png"));
        bottom.setBounds(80, 80, 710, 320);

        JLabel headline = new JLabel(new ImageIcon("E:\\FFOutput\\headline.png"));
        headline.setBounds(80,40,710,100);

        userName = new JLabel("用户名");
        password = new JLabel(" 密码");
        userName.setForeground(new Color(2, 2 ,2));
        password.setForeground(new Color(2, 2, 2));
        userName.setFont(new Font("黑体", 0, 19));
        password.setFont(new Font("黑体", 0, 19));
        userName.setBounds(290, 165, 380, 30);
        password.setBounds(290, 215, 380, 30);

        userNameField = new JTextField();
        passwordField = new JPasswordField();
        userNameField.setFont(new Font("方正粗黑宋简体", 0, 20));
        passwordField.setFont(new Font("方正粗黑宋简体", 0, 20));
        userNameField.setBounds(380, 170, 180, 22);
        passwordField.setBounds(380, 220, 180, 22);
        userNameField.setColumns(30);
        passwordField.setColumns(30);
        passwordField.setEchoChar('*');
        //创建按钮
        loginButton = createButtons("登录", normalButtonUrl, pressedButtonUrl, normalButtonUrl);

        //Setting some property of loginButton
        loginButton.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        loginButton.setForeground(new Color(255,255,255));
        loginButton.setBounds(-120, 250, 900, 100);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event:Login点击");
                if (processLogin()) {
                    //create Option Frame
                    createOptionFrame();
                }
            }
        });

        registerButton = createButtons("注册", normalButtonUrl, pressedButtonUrl, normalButtonUrl);
        registerButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        registerButton.setForeground(new Color(255,255,255));
        registerButton.setBounds(100, 250, 900, 100);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event:register点击");
                createRegisterUI();
            }
        });

        //面板对象：设置底层像素可透过、背景颜色
        mainPanel = (JPanel) frame.getContentPane();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(243,243,243));
        mainPanel.add(line);
        mainPanel.add(headline);
        mainPanel.add(userName);
        mainPanel.add(password);
        mainPanel.add(userNameField);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        frame.add(bottom);
        frame.setContentPane(mainPanel);
    }

    private void createOptionFrame() {
        JFrame optionFrame = new JFrame();
        String[] texts = new String[]{"--","添加数据", "删除数据", "修改数据", "指定查询", "导出报表"};

        optionFrame.setTitle("功能界面");
        optionFrame.setSize(400, 300);
        optionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        optionFrame.setLocationRelativeTo(null);
        optionFrame.setVisible(true);
        optionFrame.setResizable(true);
        optionFrame.setLayout(null);

        JPanel optionPanel = (JPanel) optionFrame.getContentPane();
        optionPanel.setOpaque(true);
        optionPanel.setBackground(new Color(243,243,243));

        JComboBox comboBox = new JComboBox(texts);
        comboBox.setBackground(new Color(255,255,255));
        comboBox.setEditable(false);
        comboBox.setVisible(true);
        comboBox.setBorder(BorderFactory.createTitledBorder("请选择"));
        comboBox.setBounds(50,50,150,50);
        comboBox.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String key = (String)e.getItem();
                    switch(key) {
                        case "添加数据":insertProcess();break;
                        case "删除数据":deletePress();break;
                        case "修改数据":modifyPress();break;
                        case "指定查询":selectProcess();break;
                        case "导出报表":;break;
                    }
                }
            }
        });
        optionPanel.add(comboBox);
    }

    private boolean processLogin() {
        String t1 = userNameField.getText();
        char[] pwd = passwordField.getPassword();
        if (t1.length() == 0)
            JOptionPane.showMessageDialog(null, "用户名不能为空！");
        else {
            if (pwd.length == 0)
                JOptionPane.showMessageDialog(null, "密码不能为空！");
            else {
                if (t1.length() > 0 && pwd.length > 0) {
                    String t2 = new String(pwd);
                    Map<String, String> usrLoginInfo = new HashMap<>();
                    usrLoginInfo.put("loginName", t1);
                    usrLoginInfo.put("loginPwd", t2);
                    ComTest.LoginCondition flag = ct.login(usrLoginInfo);
                    if (flag == ComTest.LoginCondition.USER_NOT_EXIST) {
                        JOptionPane.showMessageDialog(null, "用户名不存在！请重试！");
                    }
                    if (flag == ComTest.LoginCondition.NAME_OR_PWD_WRONG) {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误！请重试！");
                    }
                    if (flag == ComTest.LoginCondition.LOGIN_SUCCESS) {
                        JOptionPane.showMessageDialog(null, "登录成功！欢迎用户  " + t1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createRegisterUI() {
        JFrame rFrame = new JFrame();
        rFrame.setTitle("注册测试");
        rFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        rFrame.setLocationRelativeTo(null);
        rFrame.setVisible(true);
        rFrame.setResizable(true);
        rFrame.setLayout(null);

        JPanel panel2 = (JPanel) rFrame.getContentPane();

        JLabel newUserName = new JLabel("用户名");
        JLabel email = new JLabel("邮箱");
        JLabel newPassword = new JLabel(" 密码");
        JLabel reInput = new JLabel("确认密码");
        newUserName.setForeground(new Color(0, 0, 0));
        email.setForeground(new Color(0, 0, 0));
        newPassword.setForeground(new Color(0, 0, 0));
        reInput.setForeground(new Color(0, 0, 0));
        newUserName.setFont(new Font("黑体", 0, 20));
        email.setFont(new Font("黑体", 0, 20));
        newPassword.setFont(new Font("黑体", 0, 20));
        reInput.setFont(new Font("黑体", 0, 20));
        newUserName.setBounds(300, 140, 380, 30);
        email.setBounds(300, 180, 380, 30);
        newPassword.setBounds(300, 220, 380, 30);
        reInput.setBounds(300, 260, 380, 30);

        JTextField newUserNameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField reInputField = new JPasswordField();
        newUserNameField.setFont(new Font("黑体", 0, 19));
        emailField.setFont(new Font("黑体", 0, 19));
        newPasswordField.setFont(new Font("黑体", 0, 19));
        reInputField.setFont(new Font("黑体", 0, 19));
        newUserNameField.setBounds(420, 140, 180, 30);
        emailField.setBounds(420, 180, 180, 30);
        newPasswordField.setBounds(420, 220, 180, 30);
        reInputField.setBounds(420, 260, 180, 30);
        newUserNameField.setColumns(30);
        emailField.setColumns(30);
        newPasswordField.setColumns(30);
        reInputField.setColumns(30);
        newPasswordField.setEchoChar('*');
        reInputField.setEchoChar('*');

        JButton okButton = createButtons("确认", normalButtonUrl, pressedButtonUrl, normalButtonUrl);

        //Setting some property of loginButton
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(true);
        okButton.setContentAreaFilled(false);
        okButton.setFont(new Font("黑体", Font.BOLD, 23));
        okButton.setHorizontalTextPosition(SwingConstants.CENTER);
        okButton.setVerticalTextPosition(SwingConstants.CENTER);
        okButton.setBounds(300, 340, 100, 50);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event:ok点击");
                String t1 = newUserNameField.getText();
                char[] t2 = newPasswordField.getPassword();
                char[] t3 = reInputField.getPassword();
                if (t1.length() == 0)
                    JOptionPane.showMessageDialog(null, "用户名不能为空！");
                else {
                    String userName = new String(t1);
                    System.out.println(userName);
                    if (t2.length == 0) {
                        JOptionPane.showMessageDialog(null, "密码不能为空！");
                    } else {
                        String pwd1 = new String(t2);
                        String pwd2 = new String(t3);
                        if (pwd2.length() == 0 || !pwd2.equals(pwd1)) {
                            JOptionPane.showMessageDialog(null, "两次输入的密码不相同！");
                            return;
                        }
                        /*
                         some operations with the database!!!
                         */
                        JOptionPane.showMessageDialog(null, "注册成功");
                        System.out.println(pwd1);
                    }
                }
            }
        });

        JButton cancelButton = createButtons("取消", normalButtonUrl, pressedButtonUrl, normalButtonUrl);
        //Setting some property of loginButton
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setFont(new Font("黑体", Font.BOLD, 23));
        cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cancelButton.setVerticalTextPosition(SwingConstants.CENTER);
        cancelButton.setBounds(500, 340, 100, 50);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event:cancel点击");
                rFrame.dispose();
            }
        });

        panel2.setOpaque(true);
        panel2.setBackground(new Color(0, 0, 49));
        panel2.add(newUserName);
        panel2.add(newPassword);
        panel2.add(reInput);
        panel2.add(newUserNameField);
        panel2.add(newPasswordField);
        panel2.add(reInputField);
        panel2.add(okButton);
        panel2.add(cancelButton);
    }

    public void insertProcess(){
        JFrame insertFrame = new JFrame();
        insertFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setTitle("新增界面");
        insertFrame.setResizable(false);
        insertFrame.setVisible(true);
        insertFrame.setLayout(null);

        JPanel insertPanel = (JPanel) insertFrame.getContentPane();

        JLabel line = new JLabel(">———————————————————<选择添加数据的列表>—————————————————————<");
        line.setForeground(new Color(8, 90, 242));
        line.setBounds(10, 20, 900, 18);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JButton button1 = createButtons("<榜单> ",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton button2 = createButtons("<图书> ",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton button3 = createButtons("<出版社>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton button4 = createButtons("<所有表>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

        button1.setBounds(20,100,150,100);
        button1.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        button1.setForeground(new Color(255,255,255));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(button1.getText() + " : "+button1.isSelected());
                JFrame listFrame = new JFrame();
                listFrame.setSize(500, 500);
                listFrame.setLocationRelativeTo(null);
                listFrame.setTitle("榜单信息");
                listFrame.setResizable(false);
                listFrame.setVisible(true);
                listFrame.setLayout(null);

                JPanel listPanel = (JPanel) listFrame.getContentPane();
                JLabel label1;
                JLabel label2;
                JTextField titleField;
                JTextField yearField;
                JButton commitButton;
                JButton cancelButton;

                label1 = new JLabel("*图书名：");
                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(20, 60, 380, 30);

                titleField = new JTextField();
                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(180, 60, 180, 30);

                label2 = new JLabel("* 登榜年份：");
                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(20, 150, 380, 30);

                yearField = new JTextField(20);
                yearField.setFont(new Font("仿宋",Font.PLAIN,19));
                yearField.setBounds(180, 150, 200, 30);

                commitButton = createButtons("提交",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                commitButton.setBounds(40,200,100,100);
                commitButton.setForeground(new Color(255,255,255));
                commitButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                commitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("commit点击");
                        String title = titleField.getText();
                        String year = yearField.getText();
                        insertList(title,year);
                    }
                });

                cancelButton = createButtons("取消",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                cancelButton.setBounds(280,200,100,100);
                cancelButton.setForeground(new Color(255,255,255));
                cancelButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listFrame.dispose();
                    }
                });

                listPanel.setBackground(new Color(243,243,243));
                listPanel.setOpaque(true);
                listPanel.add(label1);
                listPanel.add(label2);
                listPanel.add(titleField);
                listPanel.add(yearField);
                listPanel.add(commitButton);
                listPanel.add(cancelButton);
            }
        });

        button2.setBounds(220,100,150,100);
        button2.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        button2.setForeground(new Color(255,255,255));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(button2.getText() +"点击");
                JFrame bookFrame = new JFrame();
                bookFrame.setSize(900, 600);
                bookFrame.setLocationRelativeTo(null);
                bookFrame.setTitle("图书信息");
                bookFrame.setResizable(false);
                bookFrame.setVisible(true);
                bookFrame.setLayout(null);

                JPanel bookPanel = (JPanel) bookFrame.getContentPane();
                JLabel label1;
                JLabel label2;
                JLabel label3;
                JLabel label4;
                JLabel label5;
                JTextArea introArea;
                JTextField pressField;
                JTextField authorField;
                JTextField titleField;
                JButton commitButton;
                JButton cancelButton;
                JTextField publishField;

                label1 = new JLabel("* 图书名：");
                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(20, 60, 380, 30);

                titleField = new JTextField();
                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(160, 60, 180, 30);

                label2 = new JLabel("出版时间");
                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(20, 140, 380, 30);

                publishField = new JTextField(20);
                publishField.setFont(new Font("仿宋",Font.PLAIN,19));
                publishField.setBounds(160, 140, 200, 30);

                label3 = new JLabel("作者");
                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(20, 100, 380, 30);

                authorField = new JTextField(15);
                authorField.setFont(new Font("仿宋",Font.PLAIN,19));
                authorField.setBounds(160, 100, 180, 30);

                label4 = new JLabel("出版商");
                label4.setFont(new Font("黑体", 0, 19));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(20, 180, 380, 30);

                pressField = new JTextField(20);
                pressField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressField.setBounds(160, 180, 180, 30);

                label5= new JLabel("内容简介");
                label5.setFont(new Font("黑体", 0, 19));
                label5.setForeground(new Color(2, 2, 2));
                label5.setBounds(20, 220, 380, 30);

                introArea = new JTextArea(10,50);
                introArea.setLineWrap(true);
                //设置自动换行方式：true(单词后换行)  false(字符后换行)
                introArea.setWrapStyleWord(true);
                introArea.setFont(new Font("仿宋",Font.PLAIN,20));
                introArea.setForeground(new Color(0,20,20));
                introArea.setSelectionColor(new Color(0, 136, 82, 255));
                introArea.setSelectedTextColor(new Color(6, 216, 219));
                introArea.setBounds(20, 250, 500,200);

                commitButton = createButtons("提交",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                commitButton.setBounds(700,100,100,100);
                commitButton.setForeground(new Color(255,255,255));
                commitButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                commitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("commit2点击");
                        String title = titleField.getText();
                        String press = pressField.getText();
                        String author = authorField.getText();
                        String publish = publishField.getText();
                        String introduction = introArea.getText();
                        insertBook(title,publish,author,press,introduction);
                    }
                });

                cancelButton = createButtons("取消",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                cancelButton.setBounds(700,300,100,100);
                cancelButton.setForeground(new Color(255,255,255));
                cancelButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        bookFrame.dispose();
                    }
                });

                bookPanel.setBackground(new Color(243,243,243));
                bookPanel.setOpaque(true);
                bookPanel.add(label1);
                bookPanel.add(label2);
                bookPanel.add(label3);
                bookPanel.add(label4);
                bookPanel.add(label5);
                bookPanel.add(titleField);
                bookPanel.add(pressField);
                bookPanel.add(introArea);
                bookPanel.add(authorField);
                bookPanel.add(publishField);
                bookPanel.add(commitButton);
                bookPanel.add(cancelButton);
            }
        });

        button3.setBounds(420,100,150,100);
        button3.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        button3.setForeground(new Color(255,255,255));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(button3.getText() +"点击");
                JFrame pressFrame = new JFrame();
                pressFrame.setSize(900, 600);
                pressFrame.setLocationRelativeTo(null);
                pressFrame.setTitle("出版社信息");
                pressFrame.setResizable(false);
                pressFrame.setVisible(true);
                pressFrame.setLayout(null);

                JPanel pressPanel = (JPanel) pressFrame.getContentPane();
                JLabel label1 = new JLabel("出版商");
                JLabel label2 = new JLabel("出版商所在地");
                JLabel label3 = new JLabel("出版商成立时间");
                JTextField pressField = new JTextField(20);
                JTextField pressLocField = new JTextField(10);
                JTextField setUpField = new JTextField(10);
                JButton commitButton;
                JButton cancelButton;

                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2,2,2));
                label1.setBounds(20, 60, 380, 30);

                pressField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressField.setBounds(160, 60, 180, 30);

                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(20, 100, 380, 30);

                pressLocField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressLocField.setBounds(160, 100, 200, 30);

                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(20, 140, 380, 30);

                setUpField.setFont(new Font("仿宋",Font.PLAIN,19));
                setUpField.setBounds(160, 140, 200, 30);

                commitButton = createButtons("提交",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                commitButton.setBounds(700,100,100,100);
                commitButton.setForeground(new Color(255,255,255));
                commitButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                commitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("commit3点击");
                        String press = pressField.getText();
                        String pressLoc = pressLocField.getText();
                        String setUpYear = setUpField.getText();
                        insertPress(press, pressLoc, setUpYear);
                    }
                });

                cancelButton = createButtons("取消",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                cancelButton.setBounds(700,300,100,100);
                cancelButton.setForeground(new Color(255,255,255));
                cancelButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pressFrame.dispose();
                    }
                });

                pressPanel.setBackground(new Color(243,243,243));
                pressPanel.setOpaque(true);
                pressPanel.add(label1);
                pressPanel.add(label2);
                pressPanel.add(label3);
                pressPanel.add(pressField);
                pressPanel.add(pressLocField);
                pressPanel.add(setUpField);
                pressPanel.add(commitButton);
                pressPanel.add(cancelButton);
            }
        });

        button4.setBounds(620,100,150,100);
        button4.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        button4.setForeground(new Color(255,255,255));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame comFrame = new JFrame();
                comFrame.setSize(900, 600);
                comFrame.setLocationRelativeTo(null);
                comFrame.setTitle("所有信息");
                comFrame.setResizable(false);
                comFrame.setVisible(true);
                comFrame.setLayout(null);

                JPanel comPanel = (JPanel) comFrame.getContentPane();
                JLabel label1 = new JLabel("* 图书名：");
                JTextField titleField = new JTextField();
                JLabel label2 = new JLabel("* 登榜年份：");
                JTextField yearField = new JTextField(20);
                JLabel label3 = new JLabel("出版商");
                JTextField pressField = new JTextField(20);
                JLabel label4 = new JLabel("作者");
                JTextField authorField = new JTextField(15);
                JLabel label5 = new JLabel("出版时间");
                JTextField publishField = new JTextField(20);
                JLabel label6 = new JLabel("出版商所在地");
                JTextField pressLocField = new JTextField(10);
                JLabel label7 = new JLabel("出版商成立时间");
                JTextField setUpField = new JTextField(10);
                JLabel label8 = new JLabel("内容简介");
                JTextArea introArea = new JTextArea(10,50);
                JButton commitButton;
                JButton cancelButton;

                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(100, 40, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(100, 70, 180, 30);

                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(350, 40, 380, 30);

                yearField.setFont(new Font("仿宋",Font.PLAIN,19));
                yearField.setBounds(350, 70, 200, 30);

                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(100, 180, 380, 30);

                pressField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressField.setBounds(100, 210, 180, 30);

                label4.setFont(new Font("黑体", 0, 19));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(100, 110, 380, 30);

                authorField.setFont(new Font("仿宋",Font.PLAIN,19));
                authorField.setBounds(100, 140, 180, 30);

                label5.setFont(new Font("黑体", 0, 19));
                label5.setForeground(new Color(2, 2, 2));
                label5.setBounds(350, 110, 380, 30);

                publishField.setFont(new Font("仿宋",Font.PLAIN,19));
                publishField.setBounds(350, 140, 200, 30);

                label6.setFont(new Font("黑体", 0, 19));
                label6.setForeground(new Color(2, 2, 2));
                label6.setBounds(350, 180, 200, 30);

                pressLocField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressLocField.setBounds(350, 210, 200, 30);

                label7.setFont(new Font("黑体", 0, 19));
                label7.setForeground(new Color(2 ,2, 2));
                label7.setBounds(600, 180, 200, 30);

                setUpField.setFont(new Font("仿宋",Font.PLAIN,19));
                setUpField.setBounds(600, 210, 200, 30);

                label8.setFont(new Font("黑体", 0, 19));
                label8.setForeground(new Color(2,2,2));
                label8.setBounds(100,250,380,30);

                introArea.setLineWrap(true);
                //设置自动换行方式：true(单词后换行)  false(字符后换行)
                introArea.setWrapStyleWord(true);
                introArea.setFont(new Font("仿宋",Font.PLAIN,20));
                introArea.setForeground(new Color(0,0,0));
                introArea.setSelectionColor(new Color(0, 136, 82, 255));
                introArea.setSelectedTextColor(new Color(6, 216, 219));
                introArea.setBounds(20, 250, 500,200);

                commitButton = createButtons("提交",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                commitButton.setBounds(700,100,100,100);
                commitButton.setForeground(new Color(255,255,255));
                commitButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                commitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("commit3点击");
                        String title = titleField.getText();
                        String year = yearField.getText();
                        String press = pressField.getText();
                        String author = authorField.getText();
                        String publish = publishField.getText();
                        String pressLoc = pressLocField.getText();
                        String setUpYear = setUpField.getText();
                        String introduction = introArea.getText();
                        insertAll(title,year,press,author,publish,pressLoc,setUpYear,introduction);
                    }
                });

                cancelButton = createButtons("取消",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                cancelButton.setBounds(700,300,100,100);
                cancelButton.setForeground(new Color(255,255,255));
                cancelButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 19));
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        comFrame.dispose();
                    }
                });

                comPanel.setBackground(new Color(243,243,243));
                comPanel.setOpaque(true);
                comPanel.add(label1);
                comPanel.add(label2);
                comPanel.add(label3);
                comPanel.add(label4);
                comPanel.add(label5);
                comPanel.add(label6);
                comPanel.add(label7);
                comPanel.add(label8);
                comPanel.add(titleField);
                comPanel.add(yearField);
                comPanel.add(pressField);
                comPanel.add(authorField);
                comPanel.add(publishField);
                comPanel.add(pressLocField);
                comPanel.add(setUpField);
                comPanel.add(introArea);
                comPanel.add(commitButton);
                comPanel.add(cancelButton);
            }
        });

        insertPanel.setOpaque(true);
        insertPanel.setBackground(new Color(243,243,243));
        insertPanel.add(line);
        insertPanel.add(button1);
        insertPanel.add(button2);
        insertPanel.add(button3);
        insertPanel.add(button4);
    }

    public void selectProcess(){
        JFrame selectFrame = new JFrame();
        selectFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        selectFrame.setLocationRelativeTo(null);
        selectFrame.setTitle("查询界面");
        selectFrame.setResizable(false);
        selectFrame.setVisible(true);
        selectFrame.setLayout(null);

        JPanel selectPanel = (JPanel) selectFrame.getContentPane();

        JLabel line = new JLabel(">———————————————————<选择查询的列表>—————————————————————<");
        line.setForeground(new Color(8, 90, 242));
        line.setBounds(10, 20, 900, 18);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JButton button1 = createButtons("查询<榜单> ",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton button2 = createButtons("查询<图书> ",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton button3 = createButtons("查询<出版社>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        frame.setLayout(null);
        JPanel panel = new JPanel();

        button1.setBounds(20,100,150,100);
        button1.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        button1.setForeground(new Color(255,255,255));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane sp = new JScrollPane();
                JTextField titleField = new JTextField();
                JTextField yearFieldL = new JTextField(10);
                JTextField yearFieldR = new JTextField(10);
                JTextField IdFieldL = new JTextField(10);
                JTextField IdFieldR = new JTextField(10);
                JLabel label1 = new JLabel("标题");
                JLabel label2 = new JLabel("年份");
                JLabel label3 = new JLabel("榜单序号");
                JLabel line1 = new JLabel("~");
                JLabel line2 = new JLabel("~");
                JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                JRadioButton rb = new JRadioButton("统计登榜次数");
                JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                ret.setBounds(1300,200,150,100);
                ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
                ret.setForeground(new Color(255,255,255));

                rb.setBounds(10,500,120,50);
                rb.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 13));
                rb.setForeground(new Color(2,2,2));
                rb.setBackground(new Color(243,243,243));

                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(1130, 60, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(1250, 60, 180, 30);

                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(1130, 110, 380, 30);

                line1.setFont(new Font("黑体", 0, 25));
                line1.setForeground(new Color(2, 2, 2));
                line1.setBounds(1370, 120, 380, 30);

                yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldL.setColumns(5);
                yearFieldL.setBounds(1250, 110, 100, 30);

                yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldR.setColumns(5);
                yearFieldR.setBounds(1400, 110, 100, 30);

                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(1130, 160, 380, 30);

                IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldL.setColumns(5);
                IdFieldL.setBounds(1250, 160, 100, 30);

                line2.setFont(new Font("黑体", 0, 25));
                line2.setForeground(new Color(2, 2, 2));
                line2.setBounds(1370, 170, 380, 30);

                IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldR.setColumns(5);
                IdFieldR.setBounds(1400, 160, 100, 30);

                commit.setBounds(1150,200,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String titleParam = titleField.getText();
                        int[] IdParam = new int[2];
                        String[] yearParam = new String[2];
                        if(yearFieldL.getText().length() <= 4)
                            yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(yearFieldR.getText().length() <= 4)
                            yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(IdFieldL.getText().length()>0)
                            IdParam[0] = Integer.parseInt(IdFieldL.getText());
                        if(IdFieldR.getText().length()>0)
                            IdParam[1] = Integer.parseInt(IdFieldR.getText());

                        JTable table = null;
                        String[] heads = null;
                        Object[][] records = null;
                        ResultSet rs = selectListCondition(IdParam,titleParam,yearParam);
                        try {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            rs.last();
                            int row = rs.getRow();
                            int count = rsmd.getColumnCount();
                            heads = new String[count];
                            records = new Object[row][count];
                            for(int i = 0; i < count; i++)
                                heads[i] = rsmd.getColumnName(i + 1);
                            rs.beforeFirst();
                            int i = 0;
                            while(rs.next()){
                                for(int j = 0; j < count;j++)
                                    records[i][j] = rs.getObject(j + 1);
                                i++;
                            }
                            table = new JTable(records,heads);
                            table.setFont(new Font("仿宋",Font.PLAIN,17));
                            table.setShowGrid(true);
                            table.setEnabled(false);
                            table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                            table.setRowHeight(50);
                            table.getColumnModel().getColumn(0).setMaxWidth(120);
                            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                            r.setHorizontalAlignment(JLabel.CENTER);
                            table.setDefaultRenderer(Object.class,r);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        sp.setViewportView(table);
                        frame.setContentPane(panel);
                        frame.setVisible(true);
                    }
                });
                rb.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         String titleParam = titleField.getText();
                         int[] IdParam = new int[2];
                         String[] yearParam = new String[2];
                         if(yearFieldL.getText().length()<=10)
                             yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                         else{
                             JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                         }
                         if(yearFieldR.getText().length()<=10)
                             yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                         else{
                             JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                         }
                         if(IdFieldL.getText().length()>0)
                             IdParam[0] = Integer.parseInt(IdFieldL.getText());
                         if(IdFieldR.getText().length()>0)
                             IdParam[1] = Integer.parseInt(IdFieldR.getText());

                         JTable table = null;
                         String[] heads = null;
                         Object[][] records = null;
                         if (rb.isSelected()) {
                             ResultSet rs = selectListGroup(IdParam,titleParam,yearParam);
                             try {
                                 ResultSetMetaData rsmd = rs.getMetaData();
                                 rs.last();
                                 int row = rs.getRow();
                                 int count = rsmd.getColumnCount();
                                 heads = new String[count];
                                 records = new Object[row][count];
                                 for (int i = 0; i < count; i++)
                                     heads[i] = rsmd.getColumnName(i + 1);
                                 rs.beforeFirst();
                                 int i = 0;
                                 while (rs.next()) {
                                     for (int j = 0; j < count; j++)
                                         records[i][j] = rs.getObject(j + 1);
                                     i++;
                                 }
                                 table = new JTable(records,heads);
                                 table.setFont(new Font("仿宋",Font.PLAIN,17));
                                 table.setShowGrid(true);
                                 table.setEnabled(false);
                                 table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                                 table.setRowHeight(50);
                                 table.getColumnModel().getColumn(0).setMaxWidth(120);
                                 DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                                 r.setHorizontalAlignment(JLabel.CENTER);
                                 table.setDefaultRenderer(Object.class,r);
                             } catch (SQLException ex) {
                                 ex.printStackTrace();
                             }
                             sp.setViewportView(table);
                         }
                     }
                 });
                ret.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.removeAll();
                        frame.dispose();
                    }
                });
                sp.setOpaque(true);
                sp.setBackground(new Color(255,255,255));
                sp.setBounds(200,20,900,600);
                panel.add(sp);
                panel.setBackground(new Color(243,243,243));
                panel.add(label3);
                panel.add(label1);
                panel.add(label2);
                panel.add(line1);
                panel.add(line2);
                panel.add(titleField);
                panel.add(yearFieldL);
                panel.add(yearFieldR);
                panel.add(IdFieldL);
                panel.add(IdFieldR);
                panel.add(commit);
                panel.add(rb);
                panel.setOpaque(true);
                panel.setLayout(null);
                panel.add(ret);
                frame.setVisible(true);
                frame.setContentPane(panel);
            }
        });

        button2.setBounds(220,100,150,100);
        button2.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        button2.setForeground(new Color(255,255,255));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane sp = new JScrollPane();
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                JTextField pressField = new JTextField();
                JTextField yearFieldL = new JTextField(10);
                JTextField yearFieldR = new JTextField(10);
                JTextField IdFieldL = new JTextField(10);
                JTextField IdFieldR = new JTextField(10);
                JLabel label1 = new JLabel("图书标题");
                JLabel label2 = new JLabel("作者");
                JLabel label3 = new JLabel("出版社");
                JLabel label4 = new JLabel("出版年份");
                JLabel label5 = new JLabel("图书序号");
                JLabel line1 = new JLabel("~");
                JLabel line2 = new JLabel("~");
                JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(1130, 60, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(1250, 60, 180, 30);

                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(1130, 110, 380, 30);

                authorField.setFont(new Font("仿宋",Font.PLAIN,19));
                authorField.setColumns(40);
                authorField.setBounds(1250, 110, 180, 30);

                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(1130, 160, 380, 30);

                pressField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressField.setColumns(40);
                pressField.setBounds(1250, 160, 180, 30);

                label4.setFont(new Font("黑体", 0, 19));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(1130, 210, 380, 30);

                line1.setFont(new Font("黑体", 0, 25));
                line1.setForeground(new Color(2, 2, 2));
                line1.setBounds(1370, 220, 380, 30);

                yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldL.setColumns(5);
                yearFieldL.setBounds(1250, 210, 100, 30);

                yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldR.setColumns(5);
                yearFieldR.setBounds(1400, 210, 100, 30);

                label5.setFont(new Font("黑体", 0, 19));
                label5.setForeground(new Color(2, 2, 2));
                label5.setBounds(1130, 260, 380, 30);

                IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldL.setColumns(5);
                IdFieldL.setBounds(1250, 260, 100, 30);

                line2.setFont(new Font("黑体", 0, 25));
                line2.setForeground(new Color(2, 2, 2));
                line2.setBounds(1370, 270, 380, 30);

                IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldR.setColumns(5);
                IdFieldR.setBounds(1400, 260, 100, 30);

                commit.setBounds(1150,300,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String titleParam = titleField.getText();
                        String authorParam = authorField.getText();
                        String pressParam = pressField.getText();
                        int[] IdParam = new int[2];
                        String[] yearParam = new String[2];

                        if(yearFieldL.getText().length() <= 4)
                            yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(yearFieldR.getText().length() <= 4)
                            yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(IdFieldL.getText().length()>0)
                            IdParam[0] = Integer.parseInt(IdFieldL.getText());
                        if(IdFieldR.getText().length()>0)
                            IdParam[1] = Integer.parseInt(IdFieldR.getText());

                        JTable table = null;
                        String[] heads = null;
                        Object[][] records = null;
                        ResultSet rs = selectBookCondition(IdParam,titleParam,authorParam, pressParam,yearParam);
                        try {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            rs.last();
                            int row = rs.getRow();
                            int count = rsmd.getColumnCount();
                            heads = new String[count];
                            records = new Object[row][count];
                            for(int i = 0; i < count; i++)
                                heads[i] = rsmd.getColumnName(i + 1);
                            rs.beforeFirst();
                            int i = 0;
                            while(rs.next()){
                                for(int j = 0; j < count;j++)
                                    records[i][j] = rs.getObject(j + 1);
                                i++;
                            }
                            table = new JTable(records,heads);
                            table.setFont(new Font("仿宋",Font.PLAIN,17));
                            table.setShowGrid(true);
                            table.setEnabled(false);
                            table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                            table.setRowHeight(50);
                            table.getColumnModel().getColumn(0).setMaxWidth(120);
                            table.getColumnModel().getColumn(4).setMaxWidth(120);
                            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                            r.setHorizontalAlignment(JLabel.CENTER);
                            table.setDefaultRenderer(Object.class,r);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        sp.setViewportView(table);
                        frame.setContentPane(panel);
                        frame.setVisible(true);
                    }
                });

                JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                ret.setBounds(1300,300,150,100);
                ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
                ret.setForeground(new Color(255,255,255));
                ret.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.removeAll();
                        frame.dispose();
                    }
                });
                sp.setOpaque(true);
                sp.setBackground(new Color(255,255,255));
                sp.setBounds(20,20,1100,800);
                panel.setOpaque(true);
                panel.setLayout(null);
                panel.setBackground(new Color(243,243,243));
                panel.add(sp);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                panel.add(label4);
                panel.add(label5);
                panel.add(line1);
                panel.add(line2);
                panel.add(commit);
                panel.add(ret);
                panel.add(titleField);
                panel.add(authorField);
                panel.add(pressField);
                panel.add(yearFieldL);
                panel.add(yearFieldR);
                panel.add(IdFieldL);
                panel.add(IdFieldR);
                frame.setContentPane(panel);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });

        button3.setBounds(420,100,150,100);
        button3.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        button3.setForeground(new Color(255,255,255));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollPane sp = new JScrollPane();
                JTextField titleField = new JTextField();
                JTextField cityField = new JTextField();
                JTextField yearFieldL = new JTextField(10);
                JTextField yearFieldR = new JTextField(10);
                JTextField IdFieldL = new JTextField(10);
                JTextField IdFieldR = new JTextField(10);
                JLabel label1 = new JLabel("出版社");
                JLabel label2 = new JLabel("所在地");
                JLabel label3 = new JLabel("成立年份");
                JLabel label4 = new JLabel("出版社序号");
                JLabel line1 = new JLabel("~");
                JLabel line2 = new JLabel("~");
                JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

                label1.setFont(new Font("黑体", 0, 19));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(1130, 60, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(1250, 60, 180, 30);

                label2.setFont(new Font("黑体", 0, 19));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(1130, 110, 380, 30);

                cityField.setFont(new Font("仿宋",Font.PLAIN,19));
                cityField.setColumns(40);
                cityField.setBounds(1250, 110, 180, 30);

                label3.setFont(new Font("黑体", 0, 19));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(1130, 160, 380, 30);

                line1.setFont(new Font("黑体", 0, 25));
                line1.setForeground(new Color(2, 2, 2));
                line1.setBounds(1370, 170, 380, 30);

                yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldL.setColumns(5);
                yearFieldL.setBounds(1250, 160, 100, 30);

                yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                yearFieldR.setColumns(5);
                yearFieldR.setBounds(1400, 160, 100, 30);

                label4.setFont(new Font("黑体", 0, 19));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(1130, 210, 380, 30);

                IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldL.setColumns(5);
                IdFieldL.setBounds(1250, 210, 100, 30);

                line2.setFont(new Font("黑体", 0, 25));
                line2.setForeground(new Color(2, 2, 2));
                line2.setBounds(1370, 220, 380, 30);

                IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
                IdFieldR.setColumns(5);
                IdFieldR.setBounds(1400, 210, 100, 30);

                commit.setBounds(1150,250,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameParam = titleField.getText();
                        int[] IdParam = new int[2];
                        String[] yearParam = new String[2];
                        String cityParam = cityField.getText();

                        if(yearFieldL.getText().length() <= 4)
                            yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(yearFieldR.getText().length() <= 4)
                            yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                        else{
                            JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                        }
                        if(IdFieldL.getText().length()>0)
                            IdParam[0] = Integer.parseInt(IdFieldL.getText());
                        if(IdFieldR.getText().length()>0)
                            IdParam[1] = Integer.parseInt(IdFieldR.getText());

                        JTable table = null;
                        String[] heads = null;
                        Object[][] records = null;
                        ResultSet rs = selectPressCondition(IdParam,nameParam,cityParam,yearParam);
                        try {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            rs.last();
                            int row = rs.getRow();
                            int count = rsmd.getColumnCount();
                            heads = new String[count];
                            records = new Object[row][count];
                            for(int i = 0; i < count; i++)
                                heads[i] = rsmd.getColumnName(i + 1);
                            rs.beforeFirst();
                            int i = 0;
                            while(rs.next()){
                                for(int j = 0; j < count;j++)
                                    records[i][j] = rs.getObject(j + 1);
                                i++;
                            }
                            table = new JTable(records,heads);
                            table.setFont(new Font("仿宋",Font.PLAIN,17));
                            table.setShowGrid(true);
                            table.setEnabled(false);
                            table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                            table.setRowHeight(50);
                            table.getColumnModel().getColumn(0).setMaxWidth(120);
                            DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                            r.setHorizontalAlignment(JLabel.CENTER);
                            table.setDefaultRenderer(Object.class,r);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        sp.setViewportView(table);
                        panel.add(sp);
                    }
                });
                JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
                ret.setBounds(1300,250,150,100);
                ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
                ret.setForeground(new Color(255,255,255));
                ret.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.removeAll();
                        frame.dispose();
                    }
                });
                sp.setOpaque(true);
                sp.setBackground(new Color(255,255,255));
                sp.setBounds(200,20,900,600);
                panel.setOpaque(true);
                panel.setBackground(new Color(243,243,243));
                panel.add(sp);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                panel.add(label4);
                panel.add(line1);
                panel.add(line2);
                panel.add(commit);
                panel.add(ret);
                panel.add(titleField);
                panel.add(cityField);
                panel.add(yearFieldL);
                panel.add(yearFieldR);
                panel.add(IdFieldL);
                panel.add(IdFieldR);
                panel.setOpaque(true);
                panel.setLayout(null);
                frame.setContentPane(panel);
                frame.setVisible(true);
            }
        });

        selectPanel.setOpaque(true);
        selectPanel.setBackground(new Color(243,243,243));
        selectPanel.add(line);
        selectPanel.add(button1);
        selectPanel.add(button2);
        selectPanel.add(button3);
    }

    public void deletePress(){
        JFrame deleteFrame = new JFrame();
        deleteFrame.setSize(1920, 1080);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setTitle("删除测试界面");
        deleteFrame.setResizable(false);
        deleteFrame.setVisible(true);
        deleteFrame.setLayout(null);

        JPanel deletePanel = (JPanel) deleteFrame.getContentPane();
        JScrollPane sp = new JScrollPane();
        JTextField titleField = new JTextField();
        JTextField cityField = new JTextField();
        JTextField yearFieldL = new JTextField(10);
        JTextField yearFieldR = new JTextField(10);
        JTextField IdFieldL = new JTextField(10);
        JTextField IdFieldR = new JTextField(10);
        JLabel label1 = new JLabel("出版社");
        JLabel label2 = new JLabel("所在地");
        JLabel label3 = new JLabel("成立年份");
        JLabel label4 = new JLabel("出版社序号");
        JLabel line1 = new JLabel("~");
        JLabel line2 = new JLabel("~");
        JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton delete = createButtons("删除",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        ret.setBounds(1300,350,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        label1.setFont(new Font("黑体", 0, 19));
        label1.setForeground(new Color(2, 2, 2));
        label1.setBounds(1130, 60, 380, 30);

        titleField.setFont(new Font("仿宋",Font.PLAIN,19));
        titleField.setColumns(40);
        titleField.setBounds(1250, 60, 180, 30);

        label2.setFont(new Font("黑体", 0, 19));
        label2.setForeground(new Color(2, 2, 2));
        label2.setBounds(1130, 110, 380, 30);

        cityField.setFont(new Font("仿宋",Font.PLAIN,19));
        cityField.setColumns(40);
        cityField.setBounds(1250, 110, 180, 30);

        label3.setFont(new Font("黑体", 0, 19));
        label3.setForeground(new Color(2, 2, 2));
        label3.setBounds(1130, 160, 380, 30);

        line1.setFont(new Font("黑体", 0, 25));
        line1.setForeground(new Color(2, 2, 2));
        line1.setBounds(1370, 170, 380, 30);

        yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldL.setColumns(5);
        yearFieldL.setBounds(1250, 160, 100, 30);

        yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldR.setColumns(5);
        yearFieldR.setBounds(1400, 160, 100, 30);

        label4.setFont(new Font("黑体", 0, 19));
        label4.setForeground(new Color(2, 2, 2));
        label4.setBounds(1130, 210, 380, 30);

        IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldL.setColumns(5);
        IdFieldL.setBounds(1250, 210, 100, 30);

        line2.setFont(new Font("黑体", 0, 25));
        line2.setForeground(new Color(2, 2, 2));
        line2.setBounds(1370, 220, 380, 30);

        IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldR.setColumns(5);
        IdFieldR.setBounds(1400, 210, 100, 30);

        commit.setBounds(1150,350,150,100);
        commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        commit.setForeground(new Color(255,255,255));
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                String cityParam = cityField.getText();

                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                ResultSet rs = selectPressCondition(IdParam,nameParam,cityParam,yearParam);
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    rs.last();
                    int row = rs.getRow();
                    int count = rsmd.getColumnCount();
                    heads = new String[count];
                    records = new Object[row][count];
                    for(int i = 0; i < count; i++)
                        heads[i] = rsmd.getColumnName(i + 1);
                    rs.beforeFirst();
                    int i = 0;
                    while(rs.next()){
                        for(int j = 0; j < count;j++)
                            records[i][j] = rs.getObject(j + 1);
                        i++;
                    }
                    table = new JTable(records,heads);
                    table.setFont(new Font("仿宋",Font.PLAIN,17));
                    table.setShowGrid(true);
                    table.setEnabled(false);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    table.setRowHeight(50);
                    table.getColumnModel().getColumn(0).setMaxWidth(120);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class,r);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                sp.setViewportView(table);
                deletePanel.add(sp);
            }
        });

        delete.setBounds(1220,430,150,100);
        delete.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        delete.setForeground(new Color(255,255,255));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                String cityParam = cityField.getText();

                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());
                int result = deletePressCondition(IdParam,nameParam,cityParam,yearParam);
                if(result == -1)
                    JOptionPane.showMessageDialog(null, "不允许无条件删除!");
                if(result >0)
                    JOptionPane.showMessageDialog(null, "删除成功!");

                deleteFrame.setContentPane(deletePanel);
                deleteFrame.setVisible(true);
            }
        });
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePanel.removeAll();
                deleteFrame.dispose();
            }
        });
        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(200,20,900,600);
        deletePanel.setOpaque(true);
        deletePanel.setBackground(new Color(243,243,243));
        deletePanel.add(sp);
        deletePanel.add(label1);
        deletePanel.add(label2);
        deletePanel.add(label3);
        deletePanel.add(label4);
        deletePanel.add(line1);
        deletePanel.add(line2);
        deletePanel.add(commit);
        deletePanel.add(delete);
        deletePanel.add(ret);
        deletePanel.add(titleField);
        deletePanel.add(cityField);
        deletePanel.add(yearFieldL);
        deletePanel.add(yearFieldR);
        deletePanel.add(IdFieldL);
        deletePanel.add(IdFieldR);
        deletePanel.setOpaque(true);
        deletePanel.setLayout(null);
        deleteFrame.setContentPane(deletePanel);
        deleteFrame.setVisible(true);
    }

    public void modifyPress(){

    }

    public void deleteBook(){
        JFrame deleteFrame = new JFrame();
        deleteFrame.setSize(1920, 1080);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setTitle("删除测试界面");
        deleteFrame.setResizable(false);
        deleteFrame.setVisible(true);
        deleteFrame.setLayout(null);

        JPanel deletePanel = (JPanel) deleteFrame.getContentPane();

        JScrollPane sp = new JScrollPane();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField pressField = new JTextField();
        JTextField yearFieldL = new JTextField(10);
        JTextField yearFieldR = new JTextField(10);
        JTextField IdFieldL = new JTextField(10);
        JTextField IdFieldR = new JTextField(10);
        JLabel label1 = new JLabel("图书标题");
        JLabel label2 = new JLabel("作者");
        JLabel label3 = new JLabel("出版社");
        JLabel label4 = new JLabel("出版年份");
        JLabel label5 = new JLabel("图书序号");
        JLabel line1 = new JLabel("~");
        JLabel line2 = new JLabel("~");
        JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton delete = createButtons("删除",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        ret.setBounds(1300,350,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        label1.setFont(new Font("黑体", 0, 19));
        label1.setForeground(new Color(2, 2, 2));
        label1.setBounds(1130, 60, 380, 30);

        titleField.setFont(new Font("仿宋",Font.PLAIN,19));
        titleField.setColumns(40);
        titleField.setBounds(1250, 60, 180, 30);

        label2.setFont(new Font("黑体", 0, 19));
        label2.setForeground(new Color(2, 2, 2));
        label2.setBounds(1130, 110, 380, 30);

        authorField.setFont(new Font("仿宋",Font.PLAIN,19));
        authorField.setColumns(40);
        authorField.setBounds(1250, 110, 180, 30);

        label3.setFont(new Font("黑体", 0, 19));
        label3.setForeground(new Color(2, 2, 2));
        label3.setBounds(1130, 160, 380, 30);

        pressField.setFont(new Font("仿宋",Font.PLAIN,19));
        pressField.setColumns(40);
        pressField.setBounds(1250, 160, 180, 30);

        label4.setFont(new Font("黑体", 0, 19));
        label4.setForeground(new Color(2, 2, 2));
        label4.setBounds(1130, 210, 380, 30);

        line1.setFont(new Font("黑体", 0, 25));
        line1.setForeground(new Color(2, 2, 2));
        line1.setBounds(1370, 220, 380, 30);

        yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldL.setColumns(5);
        yearFieldL.setBounds(1250, 210, 100, 30);

        yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldR.setColumns(5);
        yearFieldR.setBounds(1400, 210, 100, 30);

        label5.setFont(new Font("黑体", 0, 19));
        label5.setForeground(new Color(2, 2, 2));
        label5.setBounds(1130, 260, 380, 30);

        IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldL.setColumns(5);
        IdFieldL.setBounds(1250, 260, 100, 30);

        line2.setFont(new Font("黑体", 0, 25));
        line2.setForeground(new Color(2, 2, 2));
        line2.setBounds(1370, 270, 380, 30);

        IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldR.setColumns(5);
        IdFieldR.setBounds(1400, 260, 100, 30);

        commit.setBounds(1150,350,150,100);
        commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        commit.setForeground(new Color(255,255,255));
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                String authorParam = authorField.getText();
                String pressParam = pressField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];

                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                ResultSet rs = selectBookCondition(IdParam,titleParam,authorParam, pressParam,yearParam);
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    rs.last();
                    int row = rs.getRow();
                    int count = rsmd.getColumnCount();
                    heads = new String[count];
                    records = new Object[row][count];
                    for(int i = 0; i < count; i++)
                        heads[i] = rsmd.getColumnName(i + 1);
                    rs.beforeFirst();
                    int i = 0;
                    while(rs.next()){
                        for(int j = 0; j < count;j++)
                            records[i][j] = rs.getObject(j + 1);
                        i++;
                    }
                    table = new JTable(records,heads);
                    table.setFont(new Font("仿宋",Font.PLAIN,17));
                    table.setShowGrid(true);
                    table.setEnabled(false);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    table.setRowHeight(50);
                    table.getColumnModel().getColumn(0).setMaxWidth(120);
                    table.getColumnModel().getColumn(4).setMaxWidth(120);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class,r);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                sp.setViewportView(table);
                deleteFrame.setContentPane(deletePanel);
                deleteFrame.setVisible(true);
            }
        });

        delete.setBounds(1220,430,150,100);
        delete.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        delete.setForeground(new Color(255,255,255));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                String authorParam = authorField.getText();
                String pressParam = pressField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];

                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());
                int result = deleteBookCondition(IdParam,titleParam,authorParam,pressParam,yearParam);
                if(result == -1)
                    JOptionPane.showMessageDialog(null, "不允许无条件删除!");
                if(result >0)
                    JOptionPane.showMessageDialog(null, "删除成功!");

                deleteFrame.setContentPane(deletePanel);
                deleteFrame.setVisible(true);
            }
        });
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePanel.removeAll();
                deleteFrame.dispose();
            }
        });
        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(20,20,1100,800);
        deletePanel.setOpaque(true);
        deletePanel.setLayout(null);
        deletePanel.setBackground(new Color(243,243,243));
        deletePanel.add(sp);
        deletePanel.add(label1);
        deletePanel.add(label2);
        deletePanel.add(label3);
        deletePanel.add(label4);
        deletePanel.add(label5);
        deletePanel.add(line1);
        deletePanel.add(line2);
        deletePanel.add(commit);
        deletePanel.add(delete);
        deletePanel.add(ret);
        deletePanel.add(titleField);
        deletePanel.add(authorField);
        deletePanel.add(pressField);
        deletePanel.add(yearFieldL);
        deletePanel.add(yearFieldR);
        deletePanel.add(IdFieldL);
        deletePanel.add(IdFieldR);
        deleteFrame.setVisible(true);
        deleteFrame.setContentPane(deletePanel);
    }

    public void modifyBook(){
        JFrame modifyFrame = new JFrame();
        modifyFrame.setSize(1920, 1080);
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setTitle("修改测试界面");
        modifyFrame.setResizable(false);
        modifyFrame.setVisible(true);
        modifyFrame.setLayout(null);

        JPanel modifyPanel = (JPanel) modifyFrame.getContentPane();

        JScrollPane sp = new JScrollPane();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField pressField = new JTextField();
        JTextField yearFieldL = new JTextField(10);
        JTextField yearFieldR = new JTextField(10);
        JTextField IdFieldL = new JTextField(10);
        JTextField IdFieldR = new JTextField(10);
        JLabel label1 = new JLabel("图书标题");
        JLabel label2 = new JLabel("作者");
        JLabel label3 = new JLabel("出版社");
        JLabel label4 = new JLabel("出版年份");
        JLabel label5 = new JLabel("图书序号");
        JLabel line1 = new JLabel("~");
        JLabel line2 = new JLabel("~");
        JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton modify = createButtons("修改",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        ret.setBounds(1300,280,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        label1.setFont(new Font("黑体", 0, 19));
        label1.setForeground(new Color(2, 2, 2));
        label1.setBounds(1130, 60, 380, 30);

        titleField.setFont(new Font("仿宋",Font.PLAIN,19));
        titleField.setColumns(40);
        titleField.setBounds(1250, 60, 180, 30);

        label2.setFont(new Font("黑体", 0, 19));
        label2.setForeground(new Color(2, 2, 2));
        label2.setBounds(1130, 110, 380, 30);

        authorField.setFont(new Font("仿宋",Font.PLAIN,19));
        authorField.setColumns(40);
        authorField.setBounds(1250, 110, 180, 30);

        label3.setFont(new Font("黑体", 0, 19));
        label3.setForeground(new Color(2, 2, 2));
        label3.setBounds(1130, 160, 380, 30);

        pressField.setFont(new Font("仿宋",Font.PLAIN,19));
        pressField.setColumns(40);
        pressField.setBounds(1250, 160, 180, 30);

        label4.setFont(new Font("黑体", 0, 19));
        label4.setForeground(new Color(2, 2, 2));
        label4.setBounds(1130, 210, 380, 30);

        line1.setFont(new Font("黑体", 0, 25));
        line1.setForeground(new Color(2, 2, 2));
        line1.setBounds(1370, 220, 380, 30);

        yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldL.setColumns(5);
        yearFieldL.setBounds(1250, 210, 100, 30);

        yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldR.setColumns(5);
        yearFieldR.setBounds(1400, 210, 100, 30);

        label5.setFont(new Font("黑体", 0, 19));
        label5.setForeground(new Color(2, 2, 2));
        label5.setBounds(1130, 260, 380, 30);

        IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldL.setColumns(5);
        IdFieldL.setBounds(1250, 260, 100, 30);

        line2.setFont(new Font("黑体", 0, 25));
        line2.setForeground(new Color(2, 2, 2));
        line2.setBounds(1370, 270, 380, 30);

        IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldR.setColumns(5);
        IdFieldR.setBounds(1400, 260, 100, 30);

        commit.setBounds(1150,280,150,100);
        commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        commit.setForeground(new Color(255,255,255));
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                String authorParam = authorField.getText();
                String pressParam = pressField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];

                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                ResultSet rs = selectBookCondition(IdParam,titleParam,authorParam, pressParam,yearParam);
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    rs.last();
                    int row = rs.getRow();
                    int count = rsmd.getColumnCount();
                    heads = new String[count];
                    records = new Object[row][count];
                    for(int i = 0; i < count; i++)
                        heads[i] = rsmd.getColumnName(i + 1);
                    rs.beforeFirst();
                    int i = 0;
                    while(rs.next()){
                        for(int j = 0; j < count;j++)
                            records[i][j] = rs.getObject(j + 1);
                        i++;
                    }
                    table = new JTable(records,heads);
                    table.setFont(new Font("仿宋",Font.PLAIN,17));
                    table.setShowGrid(true);
                    table.setEnabled(false);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    table.setRowHeight(50);
                    table.getColumnModel().getColumn(0).setMaxWidth(120);
                    table.getColumnModel().getColumn(4).setMaxWidth(120);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class,r);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                sp.setViewportView(table);
                modifyFrame.setContentPane(modifyPanel);
                modifyFrame.setVisible(true);
            }
        });

        modify.setBounds(1220,350,150,100);
        modify.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        modify.setForeground(new Color(255,255,255));
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(500, 700);
                frame.setLocationRelativeTo(null);
                frame.setTitle("修改数据界面");
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLayout(null);

                JPanel panel = (JPanel) frame.getContentPane();
                JScrollPane sp = new JScrollPane();
                JTextField IdField = new JTextField(10);
                JTextField titleField = new JTextField(10);
                JTextField yearField = new JTextField(10);
                JTextField authorField = new JTextField();
                JTextField pressField = new JTextField();
                JTextArea introArea;

                JLabel label1 = new JLabel("欲更新记录的序号");
                JLabel label2 = new JLabel("输入新的标题");
                JLabel label3 = new JLabel("更新出版年份");
                JLabel label4 = new JLabel("更新作者");
                JLabel label5 = new JLabel("更新出版社");
                JLabel label6 = new JLabel("简介更新");
                JButton commit = createButtons("提交修改",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

                label1.setFont(new Font("黑体", 0, 17));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(10, 30, 380, 30);

                IdField.setFont(new Font("仿宋",Font.PLAIN,19));
                IdField.setColumns(5);
                IdField.setBounds(300, 30, 100, 30);

                label2.setFont(new Font("黑体", 0, 17));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(10, 80, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(300, 80, 180, 30);

                label3.setFont(new Font("黑体", 0, 17));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(10, 130, 380, 30);

                yearField.setFont(new Font("仿宋",Font.PLAIN,19));
                yearField.setColumns(5);
                yearField.setBounds(300, 130, 100, 30);

                label4.setFont(new Font("黑体", 0, 17));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(10, 180, 380, 30);

                authorField.setFont(new Font("仿宋",Font.PLAIN,19));
                authorField.setColumns(5);
                authorField.setBounds(300, 180, 100, 30);

                label5.setFont(new Font("黑体", 0, 17));
                label5.setForeground(new Color(2, 2, 2));
                label5.setBounds(10, 230, 380, 30);

                pressField.setFont(new Font("仿宋",Font.PLAIN,19));
                pressField.setColumns(15);
                pressField.setBounds(300, 230, 180, 30);

                label6.setFont(new Font("黑体", 0, 17));
                label6.setForeground(new Color(2, 2, 2));
                label6.setBounds(10, 280, 380, 30);

                introArea = new JTextArea(10,50);
                introArea.setLineWrap(true);
                introArea.setWrapStyleWord(true);
                introArea.setFont(new Font("仿宋",Font.PLAIN,20));
                introArea.setForeground(new Color(0,20,20));
                introArea.setSelectionColor(new Color(0, 136, 82, 255));
                introArea.setSelectedTextColor(new Color(6, 216, 219));

                commit.setBounds(150,520,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int idParam;
                        String titleParam = titleField.getText();
                        String yearParam = yearField.getText();
                        String authorParam = authorField.getText();
                        String pressParam = pressField.getText();
                        String introduction = introArea.getText();

                        if(IdField.getText().length()==0){
                            JOptionPane.showMessageDialog(null, "序号必须填写!");
                            return;
                        }
                        else
                            idParam = Integer.parseInt(IdField.getText());
                        if(titleParam.length() == 0) {
                            JOptionPane.showMessageDialog(null, "图书名不能为空!");
                            return;
                        }
                        if(yearParam.length() > 10){
                            JOptionPane.showMessageDialog(null, "图书出版年份不合标准!");
                            return;
                        }
                        int result = updateBook(idParam,titleParam,yearParam,authorParam,pressParam,introduction);
                        if(result > 0) {
                            JOptionPane.showMessageDialog(null, "修改成功！");
                            frame.dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(null,"修改失败！");
                    }
                });
                sp.setBounds(10, 330, 480,200);
                sp.setViewportView(introArea);
                sp.setOpaque(true);
                sp.setBackground(new Color(255,255,255));
                panel.add(IdField);
                panel.add(yearField);
                panel.add(titleField);
                panel.add(authorField);
                panel.add(pressField);
                panel.add(sp);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                panel.add(label4);
                panel.add(label5);
                panel.add(label6);
                panel.add(commit);
                frame.setContentPane(panel);
                frame.setVisible(true);
            }
        });
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyPanel.removeAll();
                modifyFrame.dispose();
            }
        });
        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(20,20,1100,800);
        modifyPanel.setOpaque(true);
        modifyPanel.setLayout(null);
        modifyPanel.setBackground(new Color(243,243,243));
        modifyPanel.add(sp);
        modifyPanel.add(label1);
        modifyPanel.add(label2);
        modifyPanel.add(label3);
        modifyPanel.add(label4);
        modifyPanel.add(label5);
        modifyPanel.add(line1);
        modifyPanel.add(line2);
        modifyPanel.add(commit);
        modifyPanel.add(modify);
        modifyPanel.add(ret);
        modifyPanel.add(titleField);
        modifyPanel.add(authorField);
        modifyPanel.add(pressField);
        modifyPanel.add(yearFieldL);
        modifyPanel.add(yearFieldR);
        modifyPanel.add(IdFieldL);
        modifyPanel.add(IdFieldR);
        modifyFrame.setVisible(true);
        modifyFrame.setContentPane(modifyPanel);
    }

    public void deleteList(){
        JFrame deleteFrame = new JFrame();
        deleteFrame.setSize(1920, 1080);
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setTitle("删除测试界面");
        deleteFrame.setResizable(false);
        deleteFrame.setVisible(true);
        deleteFrame.setLayout(null);

        JPanel deletePanel = (JPanel) deleteFrame.getContentPane();

        JScrollPane sp = new JScrollPane();
        JTextField titleField = new JTextField();
        JTextField yearFieldL = new JTextField(10);
        JTextField yearFieldR = new JTextField(10);
        JTextField IdFieldL = new JTextField(10);
        JTextField IdFieldR = new JTextField(10);
        JLabel label1 = new JLabel("标题");
        JLabel label2 = new JLabel("年份");
        JLabel label3 = new JLabel("榜单序号");
        JLabel line1 = new JLabel("~");
        JLabel line2 = new JLabel("~");
        JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton delete = createButtons("删除",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JRadioButton rb = new JRadioButton("统计登榜次数");
        ret.setBounds(1300,200,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        rb.setBounds(10,1000,120,50);
        rb.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 13));
        rb.setForeground(new Color(2,2,2));
        rb.setBackground(new Color(243,243,243));

        label1.setFont(new Font("黑体", 0, 19));
        label1.setForeground(new Color(2, 2, 2));
        label1.setBounds(1130, 60, 380, 30);

        titleField.setFont(new Font("仿宋",Font.PLAIN,19));
        titleField.setColumns(40);
        titleField.setBounds(1250, 60, 180, 30);

        label2.setFont(new Font("黑体", 0, 19));
        label2.setForeground(new Color(2, 2, 2));
        label2.setBounds(1130, 110, 380, 30);

        line1.setFont(new Font("黑体", 0, 25));
        line1.setForeground(new Color(2, 2, 2));
        line1.setBounds(1370, 120, 380, 30);

        yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldL.setColumns(5);
        yearFieldL.setBounds(1250, 110, 100, 30);

        yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldR.setColumns(5);
        yearFieldR.setBounds(1400, 110, 100, 30);

        label3.setFont(new Font("黑体", 0, 19));
        label3.setForeground(new Color(2, 2, 2));
        label3.setBounds(1130, 160, 380, 30);

        IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldL.setColumns(5);
        IdFieldL.setBounds(1250, 160, 100, 30);

        line2.setFont(new Font("黑体", 0, 25));
        line2.setForeground(new Color(2, 2, 2));
        line2.setBounds(1370, 170, 380, 30);

        IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldR.setColumns(5);
        IdFieldR.setBounds(1400, 160, 100, 30);

        commit.setBounds(1150,200,150,100);
        commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        commit.setForeground(new Color(255,255,255));
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                ResultSet rs = selectListCondition(IdParam,titleParam,yearParam);
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    rs.last();
                    int row = rs.getRow();
                    int count = rsmd.getColumnCount();
                    heads = new String[count];
                    records = new Object[row][count];
                    for(int i = 0; i < count; i++)
                        heads[i] = rsmd.getColumnName(i + 1);
                    rs.beforeFirst();
                    int i = 0;
                    while(rs.next()){
                        for(int j = 0; j < count;j++)
                            records[i][j] = rs.getObject(j + 1);
                        i++;
                    }
                    table = new JTable(records,heads);
                    table.setFont(new Font("仿宋",Font.PLAIN,17));
                    table.setShowGrid(true);
                    table.setEnabled(false);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    table.setRowHeight(50);
                    table.getColumnModel().getColumn(0).setMaxWidth(120);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class,r);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                sp.setViewportView(table);
                deleteFrame.setContentPane(deletePanel);
                deleteFrame.setVisible(true);
            }
        });

        delete.setBounds(1220,280,150,100);
        delete.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        delete.setForeground(new Color(255,255,255));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());
                int result = deleteListCondition(IdParam,titleParam,yearParam);
                if(result == -1)
                    JOptionPane.showMessageDialog(null, "不允许无条件删除!");
                if(result >0)
                    JOptionPane.showMessageDialog(null, "删除成功!");

                deleteFrame.setContentPane(deletePanel);
                deleteFrame.setVisible(true);
            }
        });

        rb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                if(yearFieldL.getText().length()<=10)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length()<=10)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                if (rb.isSelected()) {
                    ResultSet rs = selectListGroup(IdParam,titleParam,yearParam);
                    try {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        rs.last();
                        int row = rs.getRow();
                        int count = rsmd.getColumnCount();
                        heads = new String[count];
                        records = new Object[row][count];
                        for (int i = 0; i < count; i++)
                            heads[i] = rsmd.getColumnName(i + 1);
                        rs.beforeFirst();
                        int i = 0;
                        while (rs.next()) {
                            for (int j = 0; j < count; j++)
                                records[i][j] = rs.getObject(j + 1);
                            i++;
                        }
                        table = new JTable(records,heads);
                        table.setFont(new Font("仿宋",Font.PLAIN,17));
                        table.setShowGrid(true);
                        table.setEnabled(false);
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                        table.setRowHeight(50);
                        table.getColumnModel().getColumn(0).setMaxWidth(120);
                        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                        r.setHorizontalAlignment(JLabel.CENTER);
                        table.setDefaultRenderer(Object.class,r);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    sp.setViewportView(table);
                }
            }
        });
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePanel.removeAll();
                deleteFrame.dispose();
            }
        });
        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(200,20,900,600);
        deletePanel.add(sp);
        deletePanel.setOpaque(true);
        deletePanel.setBackground(new Color(243,243,243));
        deletePanel.add(label3);
        deletePanel.add(label1);
        deletePanel.add(label2);
        deletePanel.add(line1);
        deletePanel.add(line2);
        deletePanel.add(titleField);
        deletePanel.add(yearFieldL);
        deletePanel.add(yearFieldR);
        deletePanel.add(IdFieldL);
        deletePanel.add(IdFieldR);
        deletePanel.add(commit);
        deletePanel.add(rb);
        deletePanel.add(delete);
        deletePanel.add(ret);
        deletePanel.setOpaque(true);
        deletePanel.setLayout(null);
        deleteFrame.setVisible(true);
        deleteFrame.setContentPane(deletePanel);
    }

    public void modifyList(){
        JFrame modifyFrame = new JFrame();
        modifyFrame.setSize(1920, 1080);
        modifyFrame.setLocationRelativeTo(null);
        modifyFrame.setTitle("修改测试界面");
        modifyFrame.setResizable(false);
        modifyFrame.setVisible(true);
        modifyFrame.setLayout(null);

        JPanel modifyPanel = (JPanel) modifyFrame.getContentPane();

        JScrollPane sp = new JScrollPane();
        JTextField titleField = new JTextField();
        JTextField yearFieldL = new JTextField(10);
        JTextField yearFieldR = new JTextField(10);
        JTextField IdFieldL = new JTextField(10);
        JTextField IdFieldR = new JTextField(10);
        JLabel label1 = new JLabel("标题");
        JLabel label2 = new JLabel("年份");
        JLabel label3 = new JLabel("榜单序号");
        JLabel line1 = new JLabel("~");
        JLabel line2 = new JLabel("~");
        JButton commit = createButtons("查询",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton modify = createButtons("修改",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        ret.setBounds(1300,200,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        label1.setFont(new Font("黑体", 0, 19));
        label1.setForeground(new Color(2, 2, 2));
        label1.setBounds(1130, 60, 380, 30);

        titleField.setFont(new Font("仿宋",Font.PLAIN,19));
        titleField.setColumns(40);
        titleField.setBounds(1250, 60, 180, 30);

        label2.setFont(new Font("黑体", 0, 19));
        label2.setForeground(new Color(2, 2, 2));
        label2.setBounds(1130, 110, 380, 30);

        line1.setFont(new Font("黑体", 0, 25));
        line1.setForeground(new Color(2, 2, 2));
        line1.setBounds(1370, 120, 380, 30);

        yearFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldL.setColumns(5);
        yearFieldL.setBounds(1250, 110, 100, 30);

        yearFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        yearFieldR.setColumns(5);
        yearFieldR.setBounds(1400, 110, 100, 30);

        label3.setFont(new Font("黑体", 0, 19));
        label3.setForeground(new Color(2, 2, 2));
        label3.setBounds(1130, 160, 380, 30);

        IdFieldL.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldL.setColumns(5);
        IdFieldL.setBounds(1250, 160, 100, 30);

        line2.setFont(new Font("黑体", 0, 25));
        line2.setForeground(new Color(2, 2, 2));
        line2.setBounds(1370, 170, 380, 30);

        IdFieldR.setFont(new Font("仿宋",Font.PLAIN,19));
        IdFieldR.setColumns(5);
        IdFieldR.setBounds(1400, 160, 100, 30);

        commit.setBounds(1150,200,150,100);
        commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        commit.setForeground(new Color(255,255,255));
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titleParam = titleField.getText();
                int[] IdParam = new int[2];
                String[] yearParam = new String[2];
                if(yearFieldL.getText().length() <= 4)
                    yearParam[0] = (yearFieldL.getText().length()>0)?yearFieldL.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(yearFieldR.getText().length() <= 4)
                    yearParam[1] = (yearFieldR.getText().length()>0)?yearFieldR.getText():"";
                else{
                    JOptionPane.showMessageDialog(null, "年份输入不符合标准!");
                }
                if(IdFieldL.getText().length()>0)
                    IdParam[0] = Integer.parseInt(IdFieldL.getText());
                if(IdFieldR.getText().length()>0)
                    IdParam[1] = Integer.parseInt(IdFieldR.getText());

                JTable table = null;
                String[] heads = null;
                Object[][] records = null;
                ResultSet rs = selectListCondition(IdParam,titleParam,yearParam);
                try {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    rs.last();
                    int row = rs.getRow();
                    int count = rsmd.getColumnCount();
                    heads = new String[count];
                    records = new Object[row][count];
                    for(int i = 0; i < count; i++)
                        heads[i] = rsmd.getColumnName(i + 1);
                    rs.beforeFirst();
                    int i = 0;
                    while(rs.next()){
                        for(int j = 0; j < count;j++)
                            records[i][j] = rs.getObject(j + 1);
                        i++;
                    }
                    table = new JTable(records,heads);
                    table.setFont(new Font("仿宋",Font.PLAIN,17));
                    table.setShowGrid(true);
                    table.setEnabled(false);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
                    table.setRowHeight(50);
                    table.getColumnModel().getColumn(0).setMaxWidth(120);
                    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
                    r.setHorizontalAlignment(JLabel.CENTER);
                    table.setDefaultRenderer(Object.class,r);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                sp.setViewportView(table);
                modifyFrame.setContentPane(modifyPanel);
                modifyFrame.setVisible(true);
            }
        });

        modify.setBounds(1220,280,150,100);
        modify.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        modify.setForeground(new Color(255,255,255));
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(500, 300);
                frame.setLocationRelativeTo(null);
                frame.setTitle("修改数据界面");
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLayout(null);

                JPanel panel = (JPanel) frame.getContentPane();
                JTextField IdField = new JTextField(10);
                JTextField yearField = new JTextField(10);
                JTextField titleField = new JTextField(10);
                JLabel label1 = new JLabel("欲更新记录的序号");
                JLabel label2 = new JLabel("输入新的标题");
                JLabel label3 = new JLabel("输入新的年份");
                JButton commit = createButtons("提交修改",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

                label1.setFont(new Font("黑体", 0, 17));
                label1.setForeground(new Color(2, 2, 2));
                label1.setBounds(10, 30, 380, 30);

                IdField.setFont(new Font("仿宋",Font.PLAIN,19));
                IdField.setColumns(5);
                IdField.setBounds(300, 30, 100, 30);

                label2.setFont(new Font("黑体", 0, 17));
                label2.setForeground(new Color(2, 2, 2));
                label2.setBounds(10, 80, 380, 30);

                titleField.setFont(new Font("仿宋",Font.PLAIN,19));
                titleField.setColumns(40);
                titleField.setBounds(300, 80, 180, 30);

                label3.setFont(new Font("黑体", 0, 17));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(10, 130, 380, 30);

                yearField.setFont(new Font("仿宋",Font.PLAIN,19));
                yearField.setColumns(5);
                yearField.setBounds(300, 130, 100, 30);

                commit.setBounds(150,180,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int idParam;
                        String titleParam = titleField.getText();
                        String yearParam = yearField.getText();

                        if(IdField.getText().length()==0){
                            JOptionPane.showMessageDialog(null, "序号必须填写!");
                            return;
                        }else
                            idParam = Integer.parseInt(IdField.getText());
                        if(titleParam.length() == 0) {
                            JOptionPane.showMessageDialog(null, "图书名不能为空!");
                            return;
                        }
                        if(yearParam.length() == 0) {
                            JOptionPane.showMessageDialog(null, "登榜的年份不能为空!");
                            return;
                        }else if(yearParam.length() > 4){
                            JOptionPane.showMessageDialog(null, "输入的年份不合标准!");
                            return;
                        }
                        int result = updateList(idParam,titleParam,yearParam);
                        if(result >0) {
                            JOptionPane.showMessageDialog(null, "修改成功！");
                            frame.dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(null,"修改失败！");
                    }
                });
                panel.add(IdField);
                panel.add(yearField);
                panel.add(titleField);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                panel.add(commit);
                frame.setContentPane(panel);
                frame.setVisible(true);
            }
        });
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyPanel.removeAll();
                modifyFrame.dispose();
            }
        });
        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(200,20,900,600);
        modifyPanel.add(sp);
        modifyPanel.setOpaque(true);
        modifyPanel.setBackground(new Color(243,243,243));
        modifyPanel.add(label3);
        modifyPanel.add(label1);
        modifyPanel.add(label2);
        modifyPanel.add(line1);
        modifyPanel.add(line2);
        modifyPanel.add(titleField);
        modifyPanel.add(yearFieldL);
        modifyPanel.add(yearFieldR);
        modifyPanel.add(IdFieldL);
        modifyPanel.add(IdFieldR);
        modifyPanel.add(commit);
        modifyPanel.add(modify);
        modifyPanel.add(ret);
        modifyPanel.setOpaque(true);
        modifyPanel.setLayout(null);
        modifyFrame.setVisible(true);
        modifyFrame.setContentPane(modifyPanel);
    }

    public static void main(String[] args) {
        new GuiOperate().createMainUI();
    }
    /**
     * @param normalIconUrl:the   absolute path of the icon which is used when the button in normal.
     * @param pressedIconUrl:the  absolute path of the icon which is used when the button is pressed.
     * @param rolloverIconUrl:the absolute path of the icon which is used when the button is rolled over.
     * @return an instance of JButton which has been set normal icon/pressed icon/rollover icon.
     * p.s. Other property of the button must be set manually.
     */
    public static JButton createButtons(String text, String normalIconUrl, String pressedIconUrl, String rolloverIconUrl) {
        JButton button = new JButton();
        Icon normalIcon = null, pressedIcon = null, rolloverIcon = null;
        if (text != null) {
            button.setText(text);
        }
        if (normalIconUrl != null) {
            normalIcon = new ImageIcon(normalIconUrl);
            button.setIcon(normalIcon);
            button.setSize(normalIcon.getIconWidth(), normalIcon.getIconWidth());
        }
        if (pressedIconUrl != null) {
            pressedIcon = new ImageIcon(pressedIconUrl);
            button.setPressedIcon(pressedIcon);
        }
        if (rolloverIconUrl != null) {
            rolloverIcon = new ImageIcon(rolloverIconUrl);
            button.setRolloverIcon(rolloverIcon);
        }
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}