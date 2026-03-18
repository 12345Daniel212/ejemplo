package Topicos.ventanaColor;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.Color;

public class VentanaColor extends JFrame{
  public VentanaColor(){
    inicializarComponentes();
  }
  private void inicializarComponentes(){

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Ventana de Color");
    this.setSize(400, 400);
    this.setLocationRelativeTo(null);
    this.setLayout(new FlowLayout());

    JButton azulBtn = new JButton("Azul");
    this.add(azulBtn);

    JButton rojoBtn = new JButton("Rojo");
    this.add(rojoBtn);

    JButton verdeBtn = new JButton("Verde");
    this.add(verdeBtn);

    azulBtn.addActionListener(evento -> this.getContentPane().setBackground(new Color(0, 0, 255)));
    rojoBtn.addActionListener(evento -> this.getContentPane().setBackground(new Color(255, 0, 0)));
    verdeBtn.addActionListener(evento -> this.getContentPane().setBackground(new Color(0, 255, 0)));

  }
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new VentanaColor().setVisible(true);
      }
    });
  }
}
