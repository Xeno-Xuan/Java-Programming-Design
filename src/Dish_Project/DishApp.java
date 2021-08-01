package Dish_Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DishApp {
    static List<Dish> dishList = new ArrayList<>();
    static List<Dish> usrList = new ArrayList<>();

    public static void main(String[] args) {
        initDishList();

        //接收用户输入功能编号
        Scanner s = new Scanner(System.in);
        //主菜单展示
        showMainMenu();

        System.out.println("请输入编号:");
        while(true){
            int uid = s.nextInt();
            switch(uid){
                case 0:
                    System.out.println("退出功能菜单");
                    break;
                case 1:
                    showDishList();
                    break;
                case 2:
                    order();
                    showUsrList();
                    break;
                case 3:
                    showUsrList();
                    break;
                case 4:
                    checkOut();
                    break;
            }
            if(uid == 0) break;
            if(uid == 4) break;
        }
        System.out.println("感谢您的光临！");
    }
    public static void showMainMenu(){
        System.out.println("******主菜单******");
        System.out.println("\t0.Quit");
        System.out.println("\t1.Menu");
        System.out.println("\t2.Order");
        System.out.println("\t3.Customer's Dish");
        System.out.println("\t4.Check out");
        System.out.println(" *****************");
    }
    public static void initDishList(){
        dishList.add(new Dish(1,"尖椒土豆丝",15.00));
        dishList.add(new Dish(2,"鱼香肉丝",39.00));
        dishList.add(new Dish(3,"糖醋里脊",48.00));
        dishList.add(new Dish(4,"蒸羊羔",55.00));
        dishList.add(new Dish(5,"晾肉香肠",36.00));
    }
    public static void showDishList(){
        System.out.println("本店的菜品如下：");
        for(Dish dish: dishList)
            System.out.println(dish.id + " " + dish.name + "\t\t" + dish.price);
    }
    public static void order(){
        System.out.println("输入菜品序号点菜，按0返回上一级");
        Scanner s = new Scanner(System.in);
        while(true) {
            int id = s.nextInt();
            if (id == 0) {
                System.out.println("点菜结束！");
                return;
            }
            Dish dish = dishList.get(id - 1);
            usrList.add(dish);
            System.out.println("点菜：" + dish.name + "一份");
        }
    }
    public static void showUsrList(){
        System.out.println("******您的点菜单******");
        if(usrList.size() == 0){
            System.out.println("\t\t  -");
            return;
        }
        int i = 0;
        for(Dish u_dish: usrList)
            System.out.println("\t "+"(" + (++i) + ")" + u_dish.name);
        System.out.println("\t\t  -");
    }
    public static void checkOut(){
        double sum = 0f;
        for(Dish dish: usrList)
            sum += dish.price;
        System.out.println("您的消费总金额：" + sum + "元");
    }
}
