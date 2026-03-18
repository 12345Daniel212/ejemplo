//ejercicio 1 TOPICOS AVANZADOS DE PROGRAMACION
// 1.9 LAYOUTS

import javax.swing.*;


public class LayoutEjercicio1 extends JFrame{
  public LayoutEjercicio1(){
    componentesVentana();
  }

  private void componentesVentana(){
    // CONFIGURACION DE LA VENTANA
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("-Ejercicio 1-");
    this.setSize(400, 500);
    this.setLocationRelativeTo(null);

    this.setLayout(new java.awt.BorderLayout());

    JTextField areaTexto = new JTextField(20);
    this.add(areaTexto, java.awt.BorderLayout.NORTH);

    JList lista = new JList<>(new String[]{});
    this.add(lista, java.awt.BorderLayout.CENTER);

    JComboBox<String> combo = new JComboBox<>(new String[]{});
    this.add(combo, java.awt.BorderLayout.SOUTH);

  }
  public static void main(String[] args) {
    LayoutEjercicio1 ventana = new LayoutEjercicio1();
    ventana.setVisible(true);
  }
}