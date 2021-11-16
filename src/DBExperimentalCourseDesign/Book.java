package DBExperimentalCourseDesign;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static DBExperimentalCourseDesign.RecommendedList.createButtons;
import static JDBCTest.ExperimentUtil.*;

/**
 * @Author ：Xuan
 * @Date ：Created in 2021/11/16 15:55
 * @Description：
 * @Modified By：
 * @Version: $
 */
public class Book {
    private String normalButtonUrl;
    private String pressedButtonUrl;

    public String getNormalButtonUrl() {
        return normalButtonUrl;
    }

    public String getPressedButtonUrl() {
        return pressedButtonUrl;
    }

    public Book(){
        setNormalButtonUrl("E:\\FFOutput\\normalButton.png");
        setPressedButtonUrl("E:\\FFOutput\\pressedButton.png");
    }

    public void setNormalButtonUrl(String normalButtonUrl) {
        this.normalButtonUrl = normalButtonUrl;
    }

    public void setPressedButtonUrl(String pressedButtonUrl) {
        this.pressedButtonUrl = pressedButtonUrl;
    }

    public void insertBookProcess(){
        JFrame bookFrame = new JFrame();
        bookFrame.setSize(1920, 1080);
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
                if(insertBook(title,publish,author,press,introduction))
                    bookFrame.dispose();
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

    public void selectBook(){
        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        frame.setLayout(null);
        JPanel panel = new JPanel();
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
}
