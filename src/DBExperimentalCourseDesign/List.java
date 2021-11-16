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
 * @Date ：Created in 2021/11/16 15:44
 * @Description：
 * @Modified By：
 * @Version: $
 */
public class List {
    private String normalButtonUrl;
    private String pressedButtonUrl;

    public void setNormalButtonUrl(String normalButtonUrl) {
        this.normalButtonUrl = normalButtonUrl;
    }

    public void setPressedButtonUrl(String pressedButtonUrl) {
        this.pressedButtonUrl = pressedButtonUrl;
    }

    public String getNormalButtonUrl() {
        return normalButtonUrl;
    }

    public String getPressedButtonUrl() {
        return pressedButtonUrl;
    }

    public List(){
        setNormalButtonUrl("E:\\FFOutput\\normalButton.png");
        setPressedButtonUrl("E:\\FFOutput\\pressedButton.png");
    }

    public void selectList(){
        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        frame.setLayout(null);
        JPanel panel = new JPanel();
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
        JToggleButton rb = new JToggleButton("统计登榜次数");
        JButton ret = createButtons("返回",normalButtonUrl,pressedButtonUrl,normalButtonUrl);
        ret.setBounds(1300,200,150,100);
        ret.setFont(new Font("方正粗黑宋简体", Font.BOLD, 17));
        ret.setForeground(new Color(255,255,255));

        rb.setBounds(100,640,270,50);
        rb.setFont(new Font("方正粗黑宋简体", Font.BOLD, 15));
        rb.setForeground(new Color(255,255,255));
        rb.setBackground(new Color(243,243,243));
        rb.setIcon(new ImageIcon(normalButtonUrl));
        rb.setPressedIcon(new ImageIcon(pressedButtonUrl));
        rb.setHorizontalTextPosition(SwingConstants.CENTER);
        rb.setVerticalTextPosition(SwingConstants.CENTER);
        rb.setBorderPainted(false);

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
                        table.setRowHeight(30);
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

    public void insertListProcess(){
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
                if(insertList(title,year))
                    listFrame.dispose();
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
}
