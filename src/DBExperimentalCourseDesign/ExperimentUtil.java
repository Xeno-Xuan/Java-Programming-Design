package DBExperimentalCourseDesign;

import JDBCTest.utils.DBUtil;

import javax.sql.rowset.serial.SerialClob;
import javax.swing.*;
import java.sql.*;
import java.util.Map;

/**
 * @Author ：Xuan
 * @Date ：Created in 2021/9/15 22:53
 * @Description： Comprehensive test of database
 * @Modified By：
 * @Version: 1.1.0$
 */
public class ExperimentUtil {
    public enum LoginCondition {USER_NOT_EXIST, NAME_OR_PWD_WRONG, LOGIN_SUCCESS};
    public static Connection connection = null;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;

        //驱动注册、获取连接
    static{
        try {
            connection = DBUtil.getConnection("test_db");
            //事务机制开启
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LoginCondition login(Map<String, String> usrLoginInfo) {
        LoginCondition lc = null;
        //原有Statement类修改为PreparedStatement（预编译的数据库操作对象）
        String loginName = usrLoginInfo.get("loginName");
        String loginPwd = usrLoginInfo.get("loginPwd");

        try{
            //获取预编译的数据库操作对象
            //sql语句定义的位置提前，使其可以作为预编译方法的参数，并且在填充位置用直接的 ? 作为占位符
            /*
            登录功能1：检查用户名对应的用户是否有注册记录，即查询tbl_user是否有login_name = loginName的记录
             */
            String sql = "select * from tbl_user where login_name = ? ";
            ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setString(1,loginName);

            rs = ps.executeQuery();
            /*
            若查询得到对应的用户，无需再次预编译+运行sql语句，直接利用查询结果集
            rs.getString();
             */
            if(!rs.next())
                lc = LoginCondition.USER_NOT_EXIST;
            else{
                //获取查询结果集rs中login_pwd属性的值，即密码
                //登录成功
                if(!loginPwd.equals(rs.getString("login_pwd")))
                    lc = LoginCondition.NAME_OR_PWD_WRONG;
                else
                    lc = LoginCondition.LOGIN_SUCCESS;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            DBUtil.close(ps, rs);
        }
        return lc;
    }
    public static boolean insertPress(String press,String pressLoc, String setUpYear){
        try {
            if (press.length() > 0) {
                String sql = "SELECT * FROM press WHERE pressName=?";
                ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, press);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    sql = "INSERT INTO press(pressName,pressLoc,setUp) VALUES(?,?,?)";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, press);
                    ps.setString(2, pressLoc.length()==0?"\\":pressLoc);
                    if(setUpYear.length() <= 10)
                        ps.setString(3, setUpYear.length()==0?"\\":setUpYear);
                    else {
                        JOptionPane.showMessageDialog(null, "出版社成立时间不符合要求！");
                        connection.rollback();
                        return false;
                    }
                    if (ps.executeUpdate() == 0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "新数据插入完成！");
                    connection.commit();
                    return true;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "出版社名称不能为空！");
                connection.rollback();
                return false;
            }
        } catch (Exception ex) {
            try {
                System.out.println("我回滚了！");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps,rs);
        }
        return false;
    }
    public static boolean insertBook(String title,String publish, String author, String press, String introduction){
        try {
            if (title.length() > 0) {
                String sql = "SELECT bookid FROM book where title=?";
                ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, title);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    if(press.length() > 0){
                        sql = "SELECT pressId FROM press where pressName=?";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1, press);
                        rs = ps.executeQuery();
                        if(!rs.next()){
                            sql = "INSERT INTO press(pressName) VALUES(?)";
                            ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            ps.setString(1, press);
                            if (ps.executeUpdate() == 0) {
                                JOptionPane.showMessageDialog(null, "新数据插入失败！");
                                connection.rollback();
                                return false;
                            }
                            sql = "SELECT pressId FROM press where pressName=?";
                            ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            ps.setString(1, press);
                            rs = ps.executeQuery();
                        }
                        rs.next();
                    }
                    /*insert into 'book'*/
                    if(press.length()>0){
                        sql = "INSERT INTO book(title,introduction,publish,author,pressId) VALUES(?,?,?,?,?)";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setInt(5,rs.getInt("pressId"));
                    }
                    else{
                        sql = "INSERT INTO book(title,introduction,publish,author) VALUES(?,?,?,?)";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    }
                    ps.setString(1, title);
                    Clob clob = connection.createClob();
                    clob.setString(1,introduction);
                    ps.setClob(2, clob);
                    if(publish.length() <= 10)
                        ps.setString(3, publish.length()==0?"\\":publish);
                    else {
                        JOptionPane.showMessageDialog(null, "出版时间不符合要求！");
                        connection.rollback();
                        return false;
                    }
                    ps.setString(4, author);
                    if(ps.executeUpdate()==0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "新数据插入完成！");
                    connection.commit();
                    return true;
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "图书名不能为空！");
                connection.rollback();
                return false;
            }
        } catch (Exception ex) {
            try {
                System.out.println("我回滚了！");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps,rs);
        }
        return false;
    }
    public static boolean insertList(String title, String year) {
        try {
            if (title.length() > 0) {
                String sql = "SELECT bookid FROM book where title=?";
                ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, title);
                rs = ps.executeQuery();
                if (!rs.next()) {
                        sql = "INSERT INTO book(title) VALUES(?)";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1, title);
                        if (ps.executeUpdate() == 0) {
                            JOptionPane.showMessageDialog(null, "新数据插入失败！");
                            connection.rollback();
                            return false;
                        }
                        sql = "SELECT bookid FROM book where title=?";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1, title);
                        rs = ps.executeQuery();
                }
                if (year.length() > 0 && year.length() < 5) {
                    rs.beforeFirst();
                    rs.next();
                    sql = "INSERT INTO list(bookid,yearOn) VALUES(?,?)";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setInt(1, rs.getInt("bookid"));
                    ps.setString(2, year);
                    if (ps.executeUpdate() == 0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "新数据插入完成！");
                    connection.commit();
                    return true;
                } else {
                    if (year.length() <= 0)
                        JOptionPane.showMessageDialog(null, "登榜年份不能为空！");
                    else
                        JOptionPane.showMessageDialog(null, "登榜年份不符合要求！");
                    connection.rollback();
                    return false;
                }
            }else {
                JOptionPane.showMessageDialog(null, "图书名不能为空！");
                connection.rollback();
                return false;
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            DBUtil.close(ps,rs);
        }
        return false;
    }
    public static void insertAll(String title, String year, String press, String author,
                              String publishYear, String pressLoc, String setUpYear, String introduction){
        try {
            if (title.length() > 0) {
                String sql = "SELECT bookid FROM book where title=?";
                ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, title);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    sql = "SELECT pressId FROM press where pressName=?";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, press);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        sql = "INSERT INTO press(pressName,pressLoc,setUp) VALUES(?,?,?)";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1, press);
                        ps.setString(2, pressLoc.length()==0?"\\":pressLoc);
                        if(setUpYear.length() <= 10)
                            ps.setString(3, setUpYear.length()==0?"\\":setUpYear);
                        else {
                            JOptionPane.showMessageDialog(null, "出版社成立时间不符合要求！");
                            connection.rollback();
                            return;
                        }
                        if (ps.executeUpdate() == 0) {
                            JOptionPane.showMessageDialog(null, "新数据插入失败！");
                            connection.rollback();
                            return;
                        }
                        sql = "SELECT pressId FROM press where pressName=?";
                        ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1, press);
                        rs = ps.executeQuery();
                    }
                    /*insert into 'book'*/
                    rs.next();
                    sql = "INSERT INTO book(title,introduction,publish,pressId,author) VALUES(?,?,?,?,?)";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, title);
                    ps.setClob(2, new SerialClob(introduction.toCharArray()));
                    if(publishYear.length() <= 10)
                        ps.setString(3, publishYear.length()==0?"\\":publishYear);
                    else {
                        JOptionPane.showMessageDialog(null, "出版社成立时间不符合要求！");
                        connection.rollback();
                        return;
                    }
                    ps.setInt(4, rs.getInt("pressId"));
                    ps.setString(5, author);
                    if(ps.executeUpdate()==0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return;
                    }
                    //then re-select;
                    sql = "SELECT bookid FROM book where title=?";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, title);
                    rs = ps.executeQuery();
                }
                /*finish the insertion of 'list'*/
                if(year.length() > 0 && year.length() < 5) {
                    rs.next();
                    sql = "INSERT INTO list(bookid,yearOn) VALUES(?,?)";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setInt(1, rs.getInt("bookid"));
                    ps.setString(2, year);
                    if (ps.executeUpdate() == 0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "新数据插入完成！");
                    connection.commit();
                }
                else {
                    if(year.length() <= 0)
                        JOptionPane.showMessageDialog(null, "登榜年份不能为空！");
                    else
                        JOptionPane.showMessageDialog(null, "登榜年份不符合要求！");
                    connection.rollback();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "图书名不能为空！");
                connection.rollback();
            }
        } catch (Exception ex) {
            try {
                System.out.println("我回滚了！");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps,rs);
        }
    }
    public static ResultSet selectListGroup(int[] listid, String title, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("select 图书标题,count(图书标题) as 登榜次数 from listview ");
        String index,key  ="",yearOn;

        if(listid[0] == 0 && listid[1] == 0)
            index = "";
        else if((listid[0] * listid[1])==0){
            flag = true;
            index = listid[0]>0?(" (榜单序号= " + String.valueOf(listid[0]) + ") "):(" (榜单序号= " + String.valueOf(listid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (榜单序号 between " + String.valueOf(listid[0]) + " and " + String.valueOf(listid[1]) + ") ";
        }
        if(title.length() > 0){
            key = " (图书标题 like '%"+ title + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            yearOn = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            yearOn = " (登榜年份= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            yearOn = " (登榜年份= " + year[0] + ") ";
        }else{
            flag = true;
            yearOn = " (登榜年份 between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            sql.append(" ");
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!yearOn.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(yearOn);
        }
        sql.append(" group by 图书标题");
        try{
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }
    public static ResultSet selectListCondition(int[] listid, String title, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("select * from listview ");
        String index,key  ="",yearOn;

        if(listid[0] == 0 && listid[1] == 0)
            index = "";
        else if((listid[0] * listid[1])==0){
            flag = true;
            index = listid[0]>0?(" (榜单序号= " + String.valueOf(listid[0]) + ") "):(" (榜单序号= " + String.valueOf(listid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (榜单序号 between " + String.valueOf(listid[0]) + " and " + String.valueOf(listid[1]) + ") ";
        }
        if(title.length() > 0){
            key = " (图书标题 like '%"+ title + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            yearOn = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            yearOn = " (登榜年份= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
                flag = true;
                yearOn = " (登榜年份= " + year[0] + ") ";
            }else{
                flag = true;
                yearOn = " (登榜年份 between " + year[0] + " and " + year[1] + ") ";
            }

        if (flag) {
            sql.append(" where ");
        }else{
            sql.append(" ");
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!yearOn.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(yearOn);
            before = true;
        }
        try{
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }
    public static int deletePressCondition(int[] pressid, String name, String city, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("DELETE FROM press ");
        String index,key = "",setUp,loc = "";

        if(pressid[0] == 0 && pressid[1] == 0)
            index = "";
        else if((pressid[0] * pressid[1])==0){
            flag = true;
            index = pressid[0]>0?(" (pressId= " + String.valueOf(pressid[0]) + ") "):(" (pressId= " + String.valueOf(pressid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (pressId between " + String.valueOf(pressid[0]) + " and " + String.valueOf(pressid[1]) + ") ";
        }
        if(name.length() > 0){
            key = " (pressName like '%"+ name + "%') ";
            flag = true;
        }
        if(city.length() > 0){
            loc = " (pressLoc like '%"+ city + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            setUp = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            setUp = " (setUp= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            setUp = " (setUp= " + year[0] + ") ";
        }else{
            flag = true;
            setUp = " (setUp between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            return -1;
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!setUp.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(setUp);
            before = true;
        }
        if(!loc.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(loc);
            before = true;
        }
        try{
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int count = ps.executeUpdate();
            if(count==0){
                JOptionPane.showMessageDialog(null, "删除操作失败!");
                connection.rollback();
                return 0;
            }
            sql.delete(0,sql.length());
            sql.append("DROP VIEW IF EXISTS pressview");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sql.delete(0,sql.length());
            sql.append("CREATE VIEW pressview AS select press.pressId AS 出版社序号,press.pressName AS 出版社," +
                    "press.pressLoc AS 所在地,press.setUp AS 成立时间 from press");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return count;
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    public static int deleteBookCondition(int[] bookid, String title, String author,String press, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("DELETE FROM book");
        String index,key = "",publish,pressName = "",writer="";

        if(bookid[0] == 0 && bookid[1] == 0)
            index = "";
        else if((bookid[0] * bookid[1])==0){
            flag = true;
            index = bookid[0]>0?(" (bookid= " + String.valueOf(bookid[0]) + ") "):(" (bookid= " + String.valueOf(bookid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (bookid between " + String.valueOf(bookid[0]) + " and " + String.valueOf(bookid[1]) + ") ";
        }
        if(title.length() > 0){
            key = " (title like '%"+ title + "%') ";
            flag = true;
        }
        if(press.length() > 0){
            pressName = " (press like '%"+ press + "%') ";
            flag = true;
        }
        if(author.length() > 0){
            writer = " (author like '%"+ author + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            publish = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            publish = " (publish= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            publish = " (publish= " + year[0] + ") ";
        }else{
            flag = true;
            publish = " (publish between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            return -1;
        }
        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!publish.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(publish);
            before = true;
        }
        if(!pressName.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(pressName);
            before = true;
        }
        if(!writer.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(writer);
            before = true;
        }
        try{
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int count = ps.executeUpdate();
            if(count==0){
                JOptionPane.showMessageDialog(null, "删除操作失败!");
                connection.rollback();
                return 0;
            }
            sql.delete(0,sql.length());
            sql.append("DROP VIEW IF EXISTS bookview");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sql.delete(0,sql.length());
            sql.append("CREATE VIEW bookview AS select book.bookid AS 图书编号,book.title AS 图书标题," +
                    "book.author AS 作者,press.pressName AS 出版社,book.publish AS 出版时间,book.introduction AS 简介" +
                    " from (book left join press on((book.pressId = press.pressId)))");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return count;
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    public static int updateBook(int bookid,String title,String publish, String author, String press, String introduction){
        StringBuilder sql = new StringBuilder();
        boolean before = false;
        sql.append("UPDATE book set ");
        if(title.length()>0){
            sql.append("title='").append(title).append("'");
            before = true;
        }
        if(publish.length()>0){
            if(before)
                sql.append(",");
            sql.append("publish='").append(publish).append("'");
            before = true;
        }
        if(author.length()>0){
            if(before)
                sql.append(",");
            sql.append("author='").append(author).append("'");
            before = true;
        }
        if(introduction.length()>0){
            if(before)
                sql.append(",");
            sql.append("introduction='").append(introduction).append("'");
            before = true;
        }
        if(press.length()>0){
            if(before)
                sql.append(",");
            try{
                String nsql = "SELECT pressId FROM press where pressName=?";
                ps = connection.prepareStatement(nsql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, press);
                rs = ps.executeQuery();
                if(!rs.next()){
                    nsql = "INSERT INTO press(pressName) VALUES(?)";
                    ps = connection.prepareStatement(nsql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, press);
                    if (ps.executeUpdate() == 0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return 0;
                    }
                    nsql = "SELECT pressId FROM press where pressName=?";
                    ps = connection.prepareStatement(nsql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, press);
                    rs = ps.executeQuery();
                }
                rs.next();
                sql.append("pressId=").append(rs.getInt("pressId"));
            }catch(SQLException e){
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(before){
            sql.append(" where bookid=").append(bookid);
            try{
                ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                int count = ps.executeUpdate();
                String drop = "DROP VIEW IF EXISTS bookview";
                ps = connection.prepareStatement(drop,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                //ps.executeUpdate();
                String create = "CREATE VIEW bookview AS select book.bookid AS 图书编号,book.title AS 图书标题," +
                        "book.author AS 作者,press.pressName AS 出版社,book.publish AS 出版时间,book.introduction AS 简介" +
                        " from (book left join press on((book.pressId = press.pressId)))";
                ps = connection.prepareStatement(create,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                //ps.executeUpdate();
                connection.commit();
                return count;
            }catch(SQLException e){
                e.printStackTrace();
                try{
                    connection.rollback();
                }catch(SQLException ex){}
            }
        }
        return 0;
    }
    public static int updateList(int listId,String title, String year){
        try {
            if (title.length() > 0) {
                String sql = "SELECT bookid FROM book where title=?";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, title);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    sql = "INSERT INTO book(title) VALUES(?)";
                    ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, title);
                    if (ps.executeUpdate() == 0) {
                        JOptionPane.showMessageDialog(null, "新数据插入失败！");
                        connection.rollback();
                        return 0;
                    }
                    sql = "SELECT bookid FROM book where title=?";
                    ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1, title);
                    rs = ps.executeQuery();
                    rs.next();
                }
                else{
                    sql = "update book set title=? where bookid=?";
                    ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.setString(1,title);
                    ps.setInt(2,rs.getInt("bookid"));
                    if(ps.executeUpdate()==0){
                        connection.rollback();
                        return 0;
                    }
                }
                if (year.length() > 0 && year.length() <5) {
                    sql = "UPDATE list SET bookid = ?, yearOn = ?  where listId = ?";
                    ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ps.setInt(1, rs.getInt("bookid"));
                    ps.setString(2, year);
                    ps.setInt(3, listId);
                    int count = ps.executeUpdate();
                    sql = "DROP VIEW IF EXISTS listview";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.executeUpdate();
                    sql = "CREATE VIEW listview AS SELECT list.listId AS 榜单序号,book.title AS 图书标题,list.yearOn AS 登榜年份 from (list left join book on((book.bookid = list.bookid)))";
                    ps = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ps.executeUpdate();
                    connection.commit();
                    return count;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBUtil.close(ps,rs);
        }
        return 0;
    }
    public static int updatePress(int pressId,String name,String city,String year){
        StringBuilder sql = new StringBuilder();
        boolean before = false;
        sql.append("UPDATE press SET ");
        if(name.length() > 0){
            if(before)
                sql.append(",");
            sql.append("pressName='").append(name).append("'");
            before = true;
        }
        if(city.length() > 0){
            if(before)
                sql.append(",");
            sql.append("pressLoc='").append(city).append("'");
            before = true;
        }
        if(year.length() > 0){
            if(before)
                sql.append(",");
            sql.append("setUp='").append(year).append("'");
            before = true;
        }
        if(before){
            sql.append("  where pressId=").append(pressId);
            try{
                ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                int count = ps.executeUpdate();
                String drop = "DROP VIEW IF EXISTS pressview";
                ps = connection.prepareStatement(drop,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                String create = "CREATE VIEW pressview AS select press.pressId AS 出版社序号,press.pressName AS 出版社," +
                        "press.pressLoc AS 所在地,press.setUp AS 成立时间 from press";
                ps = connection.prepareStatement(create,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                connection.commit();
                return count;
            }catch(SQLException e){
                e.printStackTrace();
                try{
                    connection.rollback();
                }catch(SQLException ex){}
            }
        }
        return 0;
    }
    public static int deleteListCondition(int[] listid, String title, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("DELETE FROM list ");
        String index,key  ="",yearOn;

        if(listid[0] == 0 && listid[1] == 0)
            index = "";
        else if((listid[0] * listid[1])==0){
            flag = true;
            index = listid[0]>0?(" (listId= " + String.valueOf(listid[0]) + ") "):(" (listId= " + String.valueOf(listid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (listId between " + String.valueOf(listid[0]) + " and " + String.valueOf(listid[1]) + ") ";
        }
        if(title.length() > 0){
            key = " bookid in (select bookid from book where (title like '%"+ title + "%')) ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            yearOn = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            yearOn = " (yearOn= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            yearOn = " (yearOn= " + year[0] + ") ";
        }else{
            flag = true;
            yearOn = " (yearOn between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            return -1;
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!yearOn.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(yearOn);
            before = true;
        }
        try{
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int count = ps.executeUpdate();
            if(count==0){
                JOptionPane.showMessageDialog(null, "删除操作失败!");
                connection.rollback();
                return 0;
            }
            sql.delete(0,sql.length());
            sql.append("DROP VIEW IF EXISTS listview");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sql.delete(0,sql.length());
            sql.append("CREATE VIEW listview AS SELECT list.listId AS 榜单序号,book.title AS 图书标题,list.yearOn AS 登榜年份 from (list left join book on((book.bookid = list.bookid)))");
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            return count;
        }catch(SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    public static ResultSet selectPressCondition(int[] pressid, String name, String city, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("select * from pressview ");
        String index,key = "",setUp,loc = "";

        if(pressid[0] == 0 && pressid[1] == 0)
            index = "";
        else if((pressid[0] * pressid[1])==0){
            flag = true;
            index = pressid[0]>0?(" (出版社序号= " + String.valueOf(pressid[0]) + ") "):(" (出版社序号= " + String.valueOf(pressid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (出版社序号 between " + String.valueOf(pressid[0]) + " and " + String.valueOf(pressid[1]) + ") ";
        }
        if(name.length() > 0){
            key = " (出版社 like '%"+ name + "%') ";
            flag = true;
        }
        if(city.length() > 0){
            loc = " (所在地 like '%"+ city + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            setUp = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            setUp = " (成立时间= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            setUp = " (成立时间= " + year[0] + ") ";
        }else{
            flag = true;
            setUp = " (成立时间 between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            sql.append(" ");
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!setUp.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(setUp);
            before = true;
        }
        if(!loc.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(loc);
            before = true;
        }
        try{
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }
    public static ResultSet selectBookCondition(int[] bookid, String title, String author,String press, String[] year){
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        boolean before = false;
        sql.append("select * from bookview");
        String index,key = "",publish,pressName = "",writer="";

        if(bookid[0] == 0 && bookid[1] == 0)
            index = "";
        else if((bookid[0] * bookid[1])==0){
            flag = true;
            index = bookid[0]>0?(" (图书编号= " + String.valueOf(bookid[0]) + ") "):(" (图书编号= " + String.valueOf(bookid[1]) + ") ");
        }
        else {
            flag = true;
            index = " (图书编号 between " + String.valueOf(bookid[0]) + " and " + String.valueOf(bookid[1]) + ") ";
        }
        if(title.length() > 0){
            key = " (图书标题 like '%"+ title + "%') ";
            flag = true;
        }
        if(press.length() > 0){
            pressName = " (出版社 like '%"+ press + "%') ";
            flag = true;
        }
        if(author.length() > 0){
            writer = " (作者 like '%"+ author + "%') ";
            flag = true;
        }

        if(year[0].length() == 0 && year[1].length() == 0){
            publish = "";
        }
        else if(year[0].length() == 0 && year[1].length() > 0){
            flag = true;
            publish = " (出版时间= " + year[1] + ") ";
        }else if(year[0].length() > 0 && year[1].length() == 0){
            flag = true;
            publish = " (出版时间= " + year[0] + ") ";
        }else{
            flag = true;
            publish = " (出版时间 between " + year[0] + " and " + year[1] + ") ";
        }

        if (flag) {
            sql.append(" where ");
        }else{
            sql.append(" ");
        }

        if(!index.equals("")){
            sql.append(index);
            before = true;
        }
        if(!key.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(key);
            before = true;
        }
        if(!publish.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(publish);
            before = true;
        }
        if(!pressName.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(pressName);
            before = true;
        }
        if(!writer.equals("")){
            if(before){
                sql.append(" and ");
            }
            sql.append(writer);
            before = true;
        }
        try{
            System.out.println(sql);
            ps = connection.prepareStatement(String.valueOf(sql),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }
}