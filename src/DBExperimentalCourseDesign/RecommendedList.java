package DBExperimentalCourseDesign;

import JDBCTest.ExperimentUtil;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.PdfPageSize;
import com.spire.pdf.graphics.*;
import com.spire.pdf.tables.PdfHeaderSource;
import com.spire.pdf.tables.PdfTable;
import com.spire.pdf.tables.PdfTableDataSourceType;
import com.spire.pdf.tables.PdfTableLayoutFormat;
import com.spire.pdf.tables.table.DataTable;
import com.spire.pdf.tables.table.common.JdbcAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static SpirePdfUtils.HeaderFooterUtil.drawFooter;


/**
 * @Author : Xuan
 * @Date ：Created in 2021/10/24 15:48
 * @Description：
 * @Modified By：
 * @Version: $9.2.0
 */
public class RecommendedList extends JFrame {
    private final static int DEFAULT_WIDTH = 900;
    private final static int DEFAULT_HEIGHT = 600;
    private String normalButtonUrl;
    private String pressedButtonUrl;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private List list;
    private Book book;
    private Press press;

    public RecommendedList(){
        normalButtonUrl = "E:\\FFOutput\\normalButton.png";
        pressedButtonUrl = "E:\\FFOutput\\pressedButton.png";
        list = new List();
        book = new Book();
        press = new Press();
    }

    private void createMainUI() {
        JPanel mainPanel;
        JLabel userName;
        JLabel password;
        JButton loginButton;
        JFrame frame;
        //窗口：抬头，大小，安全关闭，居中显示，可视，边框可拖，空布局
        frame = new JFrame();
        frame.setTitle("GUI Form测试");
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        userNameField.setBounds(380, 170, 180, 25);
        passwordField.setBounds(380, 220, 180, 25);
        userNameField.setColumns(30);
        passwordField.setColumns(30);
        passwordField.setEchoChar('*');
        //创建按钮
        loginButton = createButtons("登录", normalButtonUrl, pressedButtonUrl, normalButtonUrl);

        //Setting some property of loginButton
        loginButton.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        loginButton.setForeground(new Color(255,255,255));
        loginButton.setBounds(-20, 250, 900, 100);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event:Login点击");
                if (processLogin()) {
                    createOptionFrame();
                    frame.dispose();
                }
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

        frame.add(bottom);
        frame.setContentPane(mainPanel);
    }

    private void createOptionFrame() {
        JFrame optionFrame = new JFrame();
        String[] texts = new String[]{"--","推荐榜信息管理", "图书信息管理", "出版社信息管理","报表生成"};

        optionFrame.setTitle("功能界面");
        optionFrame.setSize(900, 600);
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
        comboBox.setBounds(50,50,200,50);
        comboBox.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String key = (String)e.getItem();
                    switch(key) {
                        case "推荐榜信息管理":listProcess();break;
                        case "图书信息管理":bookProcess();break;
                        case "出版社信息管理":pressProcess();break;
                        case "报表生成":{
                            String sql = "SELECT * FROM recommendedListView";
                            String fileName = "好书推荐榜单";
                            String filePath = statementPrint(sql,fileName);
                            if(!"".equals(filePath))
                                JOptionPane.showMessageDialog(null,"成功生成报表!\n文件位置：" + filePath);
                            else
                                JOptionPane.showMessageDialog(null,"生成报表失败!");
                        }break;
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
                    ExperimentUtil.LoginCondition flag = ExperimentUtil.login(usrLoginInfo);
                    if (flag == ExperimentUtil.LoginCondition.USER_NOT_EXIST) {
                        JOptionPane.showMessageDialog(null, "用户名不存在！请重试！");
                    }
                    if (flag == ExperimentUtil.LoginCondition.NAME_OR_PWD_WRONG) {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误！请重试！");
                    }
                    if (flag == ExperimentUtil.LoginCondition.LOGIN_SUCCESS) {
                        JOptionPane.showMessageDialog(null, "登录成功！欢迎用户  " + t1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void listProcess(){
        JFrame insertFrame = new JFrame();
        insertFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setTitle("榜单管理界面");
        insertFrame.setResizable(false);
        insertFrame.setVisible(true);
        insertFrame.setLayout(null);

        JPanel insertPanel = (JPanel) insertFrame.getContentPane();

        JLabel line = new JLabel(">———————————————————<Choose Your Operation>—————————————————————<");
        line.setForeground(new Color(8, 90, 242));
        line.setBounds(10, 20, 900, 18);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JButton insertButton = createButtons("<添加数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton deleteButton = createButtons("<删除数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton updateButton = createButtons("<修改数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton selectButton = createButtons("<查询数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

        insertButton.setBounds(20,100,150,100);
        insertButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        insertButton.setForeground(new Color(255,255,255));
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(insertButton.getText() + " : "+insertButton.isSelected());
                list.insertListProcess();
            }
        });
        deleteButton.setBounds(220,100,150,100);
        deleteButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        deleteButton.setForeground(new Color(255,255,255));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(deleteButton.getText() +"点击");
                list.deleteList();
            }
        });
        updateButton.setBounds(420,100,150,100);
        updateButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        updateButton.setForeground(new Color(255,255,255));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(updateButton.getText() +"点击");
                list.modifyList();
            }
        });
        selectButton.setBounds(620,100,150,100);
        selectButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        selectButton.setForeground(new Color(255,255,255));
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame comFrame = new JFrame();
                list.selectList();
            }
        });
        insertPanel.setOpaque(true);
        insertPanel.setBackground(new Color(243,243,243));
        insertPanel.add(line);
        insertPanel.add(insertButton);
        insertPanel.add(deleteButton);
        insertPanel.add(updateButton);
        insertPanel.add(selectButton);
    }

    public void bookProcess(){
        JFrame insertFrame = new JFrame();
        insertFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setTitle("图书管理界面");
        insertFrame.setResizable(false);
        insertFrame.setVisible(true);
        insertFrame.setLayout(null);

        JPanel insertPanel = (JPanel) insertFrame.getContentPane();

        JLabel line = new JLabel(">———————————————————<Choose Your Operation>—————————————————————<");
        line.setForeground(new Color(8, 90, 242));
        line.setBounds(10, 20, 900, 18);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JButton insertButton = createButtons("<添加数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton deleteButton = createButtons("<删除数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton updateButton = createButtons("<修改数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton selectButton = createButtons("<查询数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

        insertButton.setBounds(20,100,150,100);
        insertButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        insertButton.setForeground(new Color(255,255,255));
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(insertButton.getText() + " : "+insertButton.isSelected());
                book.insertBookProcess();
            }
        });
        deleteButton.setBounds(220,100,150,100);
        deleteButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        deleteButton.setForeground(new Color(255,255,255));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(deleteButton.getText() +"点击");
                book.deleteBook();
            }
        });
        updateButton.setBounds(420,100,150,100);
        updateButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        updateButton.setForeground(new Color(255,255,255));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(updateButton.getText() +"点击");
                book.modifyBook();
            }
        });
        selectButton.setBounds(620,100,150,100);
        selectButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        selectButton.setForeground(new Color(255,255,255));
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame comFrame = new JFrame();
                book.selectBook();
            }
        });
        insertPanel.setOpaque(true);
        insertPanel.setBackground(new Color(243,243,243));
        insertPanel.add(line);
        insertPanel.add(insertButton);
        insertPanel.add(deleteButton);
        insertPanel.add(updateButton);
        insertPanel.add(selectButton);
    }

    public void pressProcess(){
        JFrame insertFrame = new JFrame();
        insertFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        insertFrame.setLocationRelativeTo(null);
        insertFrame.setTitle("出版社管理界面");
        insertFrame.setResizable(false);
        insertFrame.setVisible(true);
        insertFrame.setLayout(null);

        JPanel insertPanel = (JPanel) insertFrame.getContentPane();

        JLabel line = new JLabel(">———————————————————<Choose Your Operation>—————————————————————<");
        line.setForeground(new Color(8, 90, 242));
        line.setBounds(10, 20, 900, 18);
        line.setFont(new Font("微软雅黑", Font.BOLD, 15));

        JButton insertButton = createButtons("<添加数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton deleteButton = createButtons("<删除数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton updateButton = createButtons("<修改数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton selectButton = createButtons("<查询数据>",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

        insertButton.setBounds(20,100,150,100);
        insertButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        insertButton.setForeground(new Color(255,255,255));
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(insertButton.getText() + " : "+insertButton.isSelected());
                press.insertPressProcess();
            }
        });
        deleteButton.setBounds(220,100,150,100);
        deleteButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        deleteButton.setForeground(new Color(255,255,255));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(deleteButton.getText() +"点击");
                press.deletePress();
            }
        });
        updateButton.setBounds(420,100,150,100);
        updateButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        updateButton.setForeground(new Color(255,255,255));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(updateButton.getText() +"点击");
                press.modifyPress();
            }
        });
        selectButton.setBounds(620,100,150,100);
        selectButton.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        selectButton.setForeground(new Color(255,255,255));
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame comFrame = new JFrame();
                press.selectPress();
            }
        });
        insertPanel.setOpaque(true);
        insertPanel.setBackground(new Color(243,243,243));
        insertPanel.add(line);
        insertPanel.add(insertButton);
        insertPanel.add(deleteButton);
        insertPanel.add(updateButton);
        insertPanel.add(selectButton);
    }

    private String statementPrint(String sql,String fileName){
        //create a PDF document
        PdfDocument doc = new PdfDocument();

        //set page margins
        doc.getPageSettings().setMargins(15f,15f,15f,30f);

        //add a page
        PdfPageBase page = doc.getPages().add(PdfPageSize.A4);

        //initialize y coordinate
        float y = 0;

        //create a brush
        PdfBrush brush = PdfBrushes.getBlack();

        //create four types of fonts
        PdfTrueTypeFont titleFont = new PdfTrueTypeFont(new Font("宋体", Font.PLAIN, 15));
        PdfTrueTypeFont tableFont= new PdfTrueTypeFont(new Font("宋体", 0, 9));
        PdfTrueTypeFont headerFont= new PdfTrueTypeFont(new Font("宋体", Font.BOLD, 11));
        PdfTrueTypeFont textFont= new PdfTrueTypeFont(new Font("宋体", 0, 12));

        //draw title on the center of the page
        PdfStringFormat format = new PdfStringFormat(PdfTextAlignment.Center);
        page.getCanvas().drawString("好书推荐榜", titleFont, brush, page.getCanvas().getClientSize().getWidth() / 2, y, format);

        //calculate y coordinate
        y = y + (float) titleFont.measureString("好书推荐榜", format).getHeight();
        y = y + 5;

        //create a PdfTable instance
        PdfTable table = new PdfTable();

        //set the default cell style and row style
        table.getStyle().setCellPadding(5);
        table.getStyle().setBorderPen(new PdfPen(brush, 0.75f));
        table.getStyle().getDefaultStyle().setBackgroundBrush(PdfBrushes.getWhite());
        table.getStyle().getDefaultStyle().setFont(tableFont);
        table.getStyle().getDefaultStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));
        table.getStyle().getAlternateStyle().setBackgroundBrush(PdfBrushes.getLightGray());
        table.getStyle().getAlternateStyle().setFont(tableFont);
        table.getStyle().getAlternateStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

        //set the header style
        table.getStyle().setHeaderSource(PdfHeaderSource.Column_Captions);
        table.getStyle().getHeaderStyle().setBackgroundBrush(PdfBrushes.getLightBlue());
        table.getStyle().getHeaderStyle().setFont(headerFont);
        table.getStyle().getHeaderStyle().setTextBrush(PdfBrushes.getWhite());
        table.getStyle().getHeaderStyle().setStringFormat(new PdfStringFormat(PdfTextAlignment.Center));

        //show header at every page
        table.getStyle().setShowHeader(true);

        //connect to database
        DataTable dataTable = new DataTable();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db","root","89830533");
                Statement sta = conn.createStatement();
                String drop = "DROP VIEW IF EXISTS recommendedListView";
                sta.executeUpdate(drop);
                String create = "CREATE VIEW recommendedListView AS " +
                        "select list.listId AS 序号,book.title AS 图书名,list.yearOn AS 上榜年份," +
                        "book.author AS 作者,press.pressName AS 出版单位,book.publish AS 出版时间" +
                        " from ((list left join book on((list.bookid = book.bookid))) left join press " +
                        "on((book.pressId = press.pressId)))";
                sta.executeUpdate(create);
                ResultSet resultSet = sta.executeQuery(sql);
                JdbcAdapter jdbcAdapter = new JdbcAdapter();
                //export data from database to datatable
                jdbcAdapter.fillDataTable(dataTable, resultSet);
                table.setDataSourceType(PdfTableDataSourceType.Table_Direct);
                //fill the table with datatable
                table.setDataSource(dataTable);
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        //paginate table
        PdfTableLayoutFormat tableLayout = new PdfTableLayoutFormat();
        tableLayout.setLayout(PdfLayoutType.Paginate);

        //draw table at the specified x, y coordinates
        PdfLayoutResult result = table.draw(page, new Point2D.Float(0, y), tableLayout);

        //calculate y coordinate
        y = (float) result.getBounds().getHeight() + 5;

        //draw text under the table
        //result.getPage().getCanvas().drawString(String.format("* %1$s employees in the list.", table.getRows().getCount()), textFont, brush, 5, y);

        //draw footer
        drawFooter(doc);

        //save pdf file.
        String path = "D:\\"+ fileName +".pdf";
        doc.saveToFile(path);
        return path;
    }

    public static void main(String[] args) {
        new RecommendedList().createMainUI();
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