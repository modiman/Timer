# 新建一个桌面计时器

## 1. 初始化窗口

```java
 private void init() {
        //指定窗口名
        frame = new JFrame("计时器");
        /**
         * j10指定一条标签及其内容
         * 例如
         * <html>
         *     <h1>hello world</h1>
         * </html>
         * */
        jl0 = new JLabel();

        JPanel jp = new JPanel();
        jp.add(jl0);
        frame.add(jp);
        
        //设置窗口及其位置
        frame.setVisible(true);
        frame.setLocation(300, 400);
        frame.setSize(330, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
```

## 2.准备构造方法

通过构造方法声明窗口

```java
    public TimerBox() {
        //直接调用窗口初始化方法
        init();
    }
```

## 3. 设置窗口内容

通过标签以及j10为窗口设置需要显示的内容

```java
  public void timer(){
        String s = "<html><h1>hello world</h1></html>";
        jl0.setText(s);
    }

```

运行效果

![image-20211010155137439](E:\gitfile\notes\Java\image-20211010155137439.png)

完整代码

```java
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class TimerBox {
    private JFrame frame;
    private JLabel jl0;
    public static void main(String[] args) {
        TimerBox tb = new TimerBox();
        tb.timer();
    }
    public TimerBox() {
        init();
    }
    public void timer(){
        String s = "<html><h1>hello world</h1></html>";
        jl0.setText(s);
    }
    private void init() {
        frame = new JFrame("计时器");   
        jl0 = new JLabel();
        JPanel jp = new JPanel();
        jp.add(jl0);
        frame.add(jp);
        frame.setVisible(true);
        frame.setLocation(300, 400);
        frame.setSize(330, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
```



## 4. 实例：桌面计时器

### 添加点击事件

1. 首先声明按钮组件

```java
 		JButton jb = new JButton("暂停");

        ActionListener actionListener = new ClickAction();
        jb.addActionListener(actionListener);

        JPanel jp = new JPanel();
        jp.add(jb);
```

2. JButton对象有一个添加点击事件的方法addActionListener（），该方法的参数是一个实现了ActionListener接口的类
3. 因此需要自定义一个实现了ActionListener接口的类，并把点击处理逻辑放在其actionPerformed方法中

**建议将 ClickAction定义为内部类**

```java
package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author :modige
 * @description :此类用于实现处理点击事件的方法
 * @date :2021/10/10 16:17
 */
public class ClickAction implements ActionListener {
    public ClickAction(){

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("clicked");
    }
}

```

**此时如果点击”暂停“按钮，程序会按ClickAction类actionPerformed方法中写的输出clicked**

