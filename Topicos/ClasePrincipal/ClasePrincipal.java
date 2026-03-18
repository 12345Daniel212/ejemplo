import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClasePrincipal extends JFrame implements ActionListener {
  JButton btn1, btn2, btn3;

  public ClasePrincipal() {
    ini();
  }

  private void ini() {
    setTitle("Manejo de evento con la clase principal");
    setSize(1100, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    btn1 = new JButton("Botón 1");
    btn2 = new JButton("Botón 2");
    btn3 = new JButton("Botón 3");

    this.add(btn1);
    this.add(btn2);
    this.add(btn3);

    btn1.addActionListener(this);
  }
  public static void main(String[] args) {
    ClasePrincipal cp = new ClasePrincipal();
    cp.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btn1) {
      JOptionPane.showMessageDialog(this, "Hse preciono el botón 1");
    }
    else if (e.getSource() == btn2) {
      JOptionPane.showMessageDialog(this, "Hse preciono el botón 2");
    }
    else if (e.getSource() == btn3) {
      JOptionPane.showMessageDialog(this, "Hse preciono el botón 3");
    }
  }
}