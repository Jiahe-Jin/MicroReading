import javax.swing.*;
import java.awt.*;

public class ColorGenerator extends JFrame {

    public ColorGenerator(){
        super("ColorGenerator");
        JPanel panel = new JPanel();
        Color color = new Color(133, 99, 210);
        panel.setBackground(color);
        setSize(150,150);
        add(panel);
        show();
        System.out.println(color.getRed());
    }


    public static void main(String[] args){
        ColorGenerator generator = new ColorGenerator();

    }
}
