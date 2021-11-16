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
 * @Date ：Created in 2021/11/16 15:57
 * @Description：
 * @Modified By：
 * @Version: $
 */
public class Press {
    private String normalButtonUrl;
    private String pressedButtonUrl;

    public void setNormalButtonUrl(String normalButtonUrl) {
        this.normalButtonUrl = normalButtonUrl;
    }

    public void setPressedButtonUrl(String pressedButtonUrl) {
        this.pressedButtonUrl = pressedButtonUrl;
    }

    public String getPressedButtonUrl() {
        return pressedButtonUrl;
    }

    public String getNormalButtonUrl() {
        return normalButtonUrl;
    }

    public Press(){
        setNormalButtonUrl("E:\\FFOutput\\normalButton.png");
        setPressedButtonUrl("E:\\FFOutput\\pressedButton.png");
    }
    public void insertPressProcess(){
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
                if(insertPress(press, pressLoc, setUpYear))
                    pressFrame.dispose();
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

    public void selectPress(){
        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        frame.setLayout(null);
        JPanel panel = new JPanel();
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
        sp.setBounds(100,20,900,600);
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
        sp.setBounds(100,20,900,600);
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
        JButton modify = createButtons("修改",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);

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
                modifyPanel.add(sp);
            }
        });

        modify.setBounds(1220,320,150,100);
        modify.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
        modify.setForeground(new Color(255,255,255));
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setTitle("修改数据界面");
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLayout(null);

                JPanel panel = (JPanel) frame.getContentPane();
                JTextField IdField = new JTextField(10);
                JTextField yearField = new JTextField(10);
                JTextField nameField = new JTextField(10);
                JTextField cityField = new JTextField(10);
                JLabel label1 = new JLabel("欲更新记录的序号");
                JLabel label2 = new JLabel("输入新的出版社");
                JLabel label3 = new JLabel("更新所在地");
                JLabel label4 = new JLabel("更新成立时间");
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

                nameField.setFont(new Font("仿宋",Font.PLAIN,19));
                nameField.setColumns(40);
                nameField.setBounds(300, 80, 180, 30);

                label3.setFont(new Font("黑体", 0, 17));
                label3.setForeground(new Color(2, 2, 2));
                label3.setBounds(10, 130, 380, 30);

                cityField.setFont(new Font("仿宋",Font.PLAIN,19));
                cityField.setColumns(5);
                cityField.setBounds(300, 130, 100, 30);

                label4.setFont(new Font("黑体", 0, 17));
                label4.setForeground(new Color(2, 2, 2));
                label4.setBounds(10, 180, 380, 30);

                yearField.setFont(new Font("仿宋",Font.PLAIN,19));
                yearField.setColumns(5);
                yearField.setBounds(300, 180, 100, 30);

                commit.setBounds(150,180,150,100);
                commit.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 17));
                commit.setForeground(new Color(255,255,255));
                commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int idParam;
                        String nameParam = nameField.getText();
                        String yearParam = yearField.getText();
                        String cityParam = cityField.getText();

                        if(IdField.getText().length()==0){
                            JOptionPane.showMessageDialog(null, "序号必须填写!");
                            return;
                        }else
                            idParam = Integer.parseInt(IdField.getText());

                        if(cityParam.length() > 50){
                            JOptionPane.showMessageDialog(null, "填写的所在地不合标准!");
                            return;
                        }
                        if(nameParam.length() == 0) {
                            JOptionPane.showMessageDialog(null, "出版社名不能为空!");
                            return;
                        }
                        if(yearParam.length() > 10){
                            JOptionPane.showMessageDialog(null, "输入的年份不合标准!");
                            return;
                        }
                        int result = updatePress(idParam,nameParam,cityParam,yearParam);
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
                panel.add(nameField);
                panel.add(cityField);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);
                panel.add(label4);
                panel.add(commit);
                frame.setContentPane(panel);
                frame.setVisible(true);
            }
        });

        ret.setBounds(1300,250,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyPanel.removeAll();
                modifyFrame.dispose();
            }
        });

        sp.setOpaque(true);
        sp.setBackground(new Color(255,255,255));
        sp.setBounds(100,20,900,600);
        modifyPanel.setOpaque(true);
        modifyPanel.setBackground(new Color(243,243,243));
        modifyPanel.add(sp);
        modifyPanel.add(label1);
        modifyPanel.add(label2);
        modifyPanel.add(label3);
        modifyPanel.add(label4);
        modifyPanel.add(line1);
        modifyPanel.add(line2);
        modifyPanel.add(commit);
        modifyPanel.add(modify);
        modifyPanel.add(ret);
        modifyPanel.add(titleField);
        modifyPanel.add(cityField);
        modifyPanel.add(yearFieldL);
        modifyPanel.add(yearFieldR);
        modifyPanel.add(IdFieldL);
        modifyPanel.add(IdFieldR);
        modifyPanel.setOpaque(true);
        modifyPanel.setLayout(null);
        modifyFrame.setContentPane(modifyPanel);
        modifyFrame.setVisible(true);
    }

}
