import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Properties;

public class PruebaConexion extends JFrame {
    private Connection conexion;
    private JTextField txtNombre, txtApellido;
    private int id;
    private JButton btnGuardar, btnNuevo, btnBuscar, btnEliminar, btnActualizar, btnSalir;

    public PruebaConexion() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 200);
        this.setTitle("Prueba BD - Control de Alumnos");
        this.setLocationRelativeTo(null);

        this.setLayout(new GridLayout(5, 2, 10, 5));

        this.add(new JLabel("  Nombre:"));
        txtNombre = new JTextField();
        this.add(txtNombre);

        this.add(new JLabel("  Apellido:"));
        txtApellido = new JTextField();
        this.add(txtApellido);

        btnGuardar = new JButton("Guardar");
        btnNuevo = new JButton("Nuevo");
        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        btnSalir = new JButton("Salir");

        this.add(btnGuardar);
        this.add(btnNuevo);
        this.add(btnBuscar);
        this.add(btnEliminar);
        this.add(btnActualizar);
        this.add(btnSalir);

        btnNuevo.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);

        btnGuardar.addActionListener(e -> guardar());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnNuevo.addActionListener(e -> nuevo());

        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                if (conexion != null) {
                    try {
                        conexion.close();
                        System.out.println("Conexión cerrada.");
                    } catch (SQLException ex) {
                        System.out.println("Error al cerrar: " + ex.getMessage());
                    }
                }
                System.exit(0);
            }
        });
    }

    public void conectar() {
        String URL = "jdbc:mysql://localhost:3306/prueba";
        Properties propiedades = new Properties();
        propiedades.setProperty("user", "root");
        propiedades.setProperty("password", "1234");
        propiedades.setProperty("useSSL", "false");
        propiedades.setProperty("serverTimezone", "UTC");
        propiedades.setProperty("allowPublicKeyRetrieval", "true");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, propiedades);
            System.out.println("Conexión Exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    private void guardar() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos vacíos");
            return;
        }
        try {
            String sql = "INSERT INTO alumnos (nombre, apellido) VALUES (?, ?)";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellido.getText());

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
                nuevo();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    private void buscar() {
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre para buscar");
            return;
        }
        try {
            String sql = "SELECT * FROM alumnos WHERE nombre = ?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                txtApellido.setText(rs.getString("apellido"));

                // Activar/Desactivar botones para edición
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Registro no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizar() {
        try {
            String sql = "UPDATE alumnos SET nombre = ?, apellido = ? WHERE id = ?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellido.getText());
            pst.setInt(3, id);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado.");
                nuevo();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + txtNombre.getText() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM alumnos WHERE id = ?";
                PreparedStatement pst = conexion.prepareStatement(sql);
                pst.setInt(1, id);

                if (pst.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado.");
                    nuevo();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void nuevo() {
        txtNombre.setText("");
        txtApellido.setText("");
        id = 0;
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnNuevo.setEnabled(false);
        txtNombre.requestFocus();
    }

    public static void main(String args[]) {
        PruebaConexion app = new PruebaConexion();
        app.conectar();
        app.setVisible(true);
    }
}