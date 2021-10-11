package graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.concurrent.TimeUnit;

/**
 * @author :modige
 * @description :用于统计学习时间的一个计时器
 * @date :2021/10/10 15:31
 */
public class TimerBox {
    /**
     * frame : 窗口
     * jp   ：表盘
     * j10  :内容标签
     * flag :用来区分暂停与开始
     * count:用来记录秒数
     * */


    private JFrame frame;
    private JPanel jp;
    private JLabel jl0;
    private boolean flag;
    private int count;

    private ScheduledThreadPoolExecutor scheduled;

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mybatis?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        TimerBox tb = new TimerBox();
        tb.timer();

    }

    public TimerBox() {
        scheduled = new ScheduledThreadPoolExecutor(2);
        init();
    }
    /**
     * 计时方法
     * */
    public void timer(){ //计时开始
        scheduled.scheduleAtFixedRate(new Runnable() { //使用匿名类作为参数
            @Override
            public void run() {



                if (flag){ //如果flag为真，说明是开始状态，count每秒加一
                    count++;
                    long hour = count / 3600;
                    long minute = ( count - hour * 3600) / 60;
                    long seconds =  count- hour * 3600 - minute * 60;
                    StringBuilder timer = new StringBuilder();
                    timer.append("<html><br><h1>已经开始<br>").append(hour).append("时 ").append(minute).append("分 ").append(seconds).append("秒 ")
                            .append("</h1></br></html>");
                    jl0.setText(timer.toString());
                }
                //否则为暂停状态，count不变
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /** 生成窗口
    *  */
    private void init() {

        frame = new JFrame("计时器");  //指定窗口名
        /**
         * j10指定一条标签及其内容
         * 例如
         * <html>
         *     <h1>hello world</h1>
         * </html>
         * */
        jl0 = new JLabel();

        JButton jbBegin = new JButton("开始");     //开始按钮

        JButton jbPause = new JButton("暂停");      //暂停按钮
        jbPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                flag = false;
                System.out.println("已暂停，时间为："+new Date());
                String sql = "insert into timer values('"+new Date()+"','"+"暂停')";
                SqlConnect sc = new SqlConnect(sql);
                sc.connect();

            }
        });

        jbBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                flag = true;

                String sql = "insert into timer values('"+new Date()+"','"+"开始')";
                SqlConnect sc = new SqlConnect(sql);
                sc.connect();
            }
        });


        jp = new JPanel();
        jp.add(jl0);
        flag = true;
        jp.add(jbBegin);
        jp.add(jbPause);
        frame.add(jp);
        count = 0;

        //设置窗口及其位置
        frame.setVisible(true);
        frame.setLocation(300, 400);
        frame.setSize(330, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * @author :modige
     * @description :数据库访问，用于将暂停与开始时间发生的时间记录到数据库
     * @date :2021/10/11 9:39
     */
       class SqlConnect {

        private String sqlQuery;

        static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://localhost:3306/mybatis?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        static final String USER = "root";
        static final String PASS = "123456";

        public SqlConnect(String s){
            sqlQuery = s;
        }

        public void connect(){
            Connection conn = null;
            Statement stmt = null;
            try{
                // 注册 JDBC 驱动
                Class.forName(JDBC_DRIVER);

                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);

                // 执行查询
                System.out.println(" 实例化Statement对象...");
                stmt = conn.createStatement();
                //查询专用语句
//            ResultSet rs = stmt.executeQuery(sqlQuery);
                //插入语句
                stmt.execute(sqlQuery);

                // 完成后关闭

                stmt.close();
                conn.close();
            }catch(SQLException se){
                // 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e){
                // 处理 Class.forName 错误
                e.printStackTrace();
            }finally{
                // 关闭资源
                try{
                    if(stmt!=null) stmt.close();
                }catch(SQLException se2){
                }// 什么都不做
                try{
                    if(conn!=null) conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
            }
            System.out.println("Goodbye!");
        }
    }


}



