package Memorandum_Project;

import java.text.*;
import java.util.*;
import java.util.stream.*;

public class Memo {
    //集合存储账单
    static List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        //功能菜单
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("-------------------备忘录-------------------");
            System.out.println("1.添加账务  2.删除账务  3.查询账务  4.退出系统");
            System.out.println("-------------------------------------------");
            System.out.println("请输入功能码:");
            int op = input.nextInt();
            //功能码判断
            switch(op){
                case 1:
                    insert();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    System.out.println("#感谢使用！再见~~");
                    break;
                default:
                    System.out.println("#请重新输入：");
                    break;
            }
            if(4 == op)
                break;
        }
    }
    public static void insert(){
        Scanner input = new Scanner(System.in);
        Account account = new Account();
        System.out.println("备忘录>>【添加账务】");

        System.out.println("请输入消费类型：");
        account.setType(input.next());

        System.out.println("请输入消费方式：");
        account.setBank(input.next());

        System.out.println("请输入【收入】或【支出】：");
        while(true) {
            String in_ex = input.next();
            if ("收入".equals(in_ex) || "支出".equals(in_ex)) {
                account.setIo(in_ex);
                break;
            }
            System.out.println("输入有误！重新输入【收入】或【支出】");
        }

        System.out.println("请输入消费金额：");
        account.setAmt(Double.parseDouble(input.next()));

        System.out.println("请输入交易日期：");
        account.setDate(input.next());

        System.out.println("请输入备注：");
        account.setNotes(input.next());

        accounts.add(account);
        System.out.println("#账务添加成功！");
    }
    public static void delete(){
        System.out.println("备忘录>>【删除账务】");
        Scanner input = new Scanner(System.in);

        System.out.println("---------------------当前账单-------------------");
        print(accounts);
        System.out.println("-----------------------------------------------");
        System.out.println("请输入删除的账务id");
        int id = input.nextInt();
        accounts.remove(id - 1);

        System.out.println("#账务删除成功！");
    }
    public static void search(){
        System.out.println("备忘录>>【查询账务】");
        System.out.println("0.退出查询  1.查询所有  2.按时间查询  3.按收支查询");
        System.out.println("-----------------------------------------------");
        Scanner input = new Scanner(System.in);
        while(true){
            int op = input.nextInt();
            switch(op){
                case 1:
                    System.out.println("备忘录>>【查询账务】>>【查询所有】");
                    printAllAccount();
                    break;
                case 2:
                    System.out.println("备忘录>>【查询账务】>>【按时间区间查询】");
                    searchByDate();
                    break;
                case 3:
                    System.out.println("备忘录>>【查询账务】>>【按收支查询】");
                    searchByIo();
                    break;
                case 0:
                    System.out.println("#结束查询，返回主菜单");
                    break;
                default:
                    System.out.println("#请重新输入");
                    break;
            }
            if(0 == op) return;
        }
    }
    public static void print(List<Account> paramList){
        //对传入的集合进行打印，不局限于整个元素集合
        System.out.println("ID\t\t消费类型\t\t\t消费方式\t\t收支\t\t金额\t\t日期\t\t\t备注");
        if(0 == paramList.size()){
            System.out.println("- \t\t  ---  \t\t\t  ---    \t - \t\t\t -  \t\t -  \t\t\t - ");
            return;
        }
        for(int i = 0; i < paramList.size(); i++){
            System.out.print((i + 1) + "\t\t");
            System.out.print(paramList.get(i).getType() + "\t\t\t");
            System.out.print(paramList.get(i).getBank() + "\t\t");
            System.out.print(paramList.get(i).getIo() + "\t\t");
            System.out.print(paramList.get(i).getAmt() + "\t\t");
            System.out.print(paramList.get(i).getDate() + "\t\t");
            System.out.println(paramList.get(i).getNotes());
        }
    }
    public static void printAllAccount(){
        print(accounts);
        System.out.println("-----------------------------------------------");
        System.out.println("#查询完成！\n");

        System.out.println("备忘录>>【查询账务】");
        System.out.println("0.退出查询  1.查询所有  2.按时间查询  3.按收支查询");
        System.out.println("-----------------------------------------------");
    }
    public static void searchByDate() {
        Scanner date_input = new Scanner(System.in);
        System.out.print("【请输入起始时间】");
        String start = date_input.next();
        System.out.print("【请输入结束时间】");
        String end = date_input.next();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //如何在集合中根据传入的消费类型，来筛选所需的对应元素？
        //将集合转换为流，利用stream的筛选filter获得所需的元素，再转换为集合
        //通过Stream接口中的filter()进行筛选，而接口中filter()在此时需要实现
        //利用lambda表达式完成filter()的条件设置
        List<Account> list = accounts.stream().filter(account ->{
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                Date currentDate = format.parse(account.getDate());
                if(currentDate.before(endDate) && currentDate.after(startDate))
                    return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        print(list);
        System.out.println("-----------------------------------------------");
        System.out.println("#查询完成！\n");

        System.out.println("备忘录>>【查询账务】");
        System.out.println("0.退出查询  1.查询所有  2.按时间查询  3.按收支查询");
        System.out.println("-----------------------------------------------");
    }
    public static void searchByIo(){
        System.out.println("【请输入：收入/支出】");
        Scanner type_input = new Scanner(System.in);
        String gist = type_input.next();
        //如何在集合中根据传入的消费类型，来筛选所需的对应元素？
        //将集合转换为流，利用stream的筛选filter获得所需的元素，再转换为集合
        //通过Stream接口中的filter()进行筛选，而接口中filter()在此时需要实现
        //利用lambda表达式完成filter()的条件设置
        List<Account> list = accounts.stream().filter(account ->{
            String io = account.getIo();
            return io.equals(gist);
        }).collect(Collectors.toList());
        print(list);
        System.out.println("-----------------------------------------------");
        System.out.println("#查询完成！\n");

        System.out.println("备忘录>>【查询账务】");
        System.out.println("0.退出查询  1.查询所有  2.按时间查询  3.按收支查询");
        System.out.println("-----------------------------------------------");
    }
}
