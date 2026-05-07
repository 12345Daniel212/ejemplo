import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Properties;

public class PruebaConexion extends JFrame {
    private Connection conexion;
    private JTextField txtNombre, txtArtista, txtGenero, txtDuracion, txtCanciones, txtAnio, txtCalificacion, txtFormato;
    private int id;
    private JButton btnGuardar, btnNuevo, btnBuscar, btnEliminar, btnActualizar, btnSalir;

    public PruebaConexion() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 450);
        this.setTitle("Control de Música - Tabla Disco");
        this.setLocationRelativeTo(null);

        this.setLayout(new GridLayout(11, 2, 10, 5));

        this.add(new JLabel("  Nombre del Disco:"));
        txtNombre = new JTextField(); this.add(txtNombre);

        this.add(new JLabel("  Artista:"));
        txtArtista = new JTextField(); this.add(txtArtista);

        this.add(new JLabel("  Género:"));
        txtGenero = new JTextField(); this.add(txtGenero);

        this.add(new JLabel("  Duración (min):"));
        txtDuracion = new JTextField(); this.add(txtDuracion);

        this.add(new JLabel("  No. Canciones:"));
        txtCanciones = new JTextField(); this.add(txtCanciones);

        this.add(new JLabel("  Año:"));
        txtAnio = new JTextField(); this.add(txtAnio);

        this.add(new JLabel("  Calificación:"));
        txtCalificacion = new JTextField(); this.add(txtCalificacion);

        this.add(new JLabel("  Formato:"));
        txtFormato = new JTextField(); this.add(txtFormato);

        btnGuardar = new JButton("Guardar");
        btnNuevo = new JButton("Nuevo");
        btnBuscar = new JButton("Buscar x Nombre");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        btnSalir = new JButton("Salir");

        this.add(btnGuardar); this.add(btnNuevo);
        this.add(btnBuscar); this.add(btnEliminar);
        this.add(btnActualizar); this.add(btnSalir);

        // Configuración inicial de botones
        btnNuevo.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);

        // Listeners
        btnGuardar.addActionListener(e -> guardar());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnNuevo.addActionListener(e -> nuevo());
        btnSalir.addActionListener(e -> salir());
    }

    public void conectar() {
        String URL = "jdbc:mysql://localhost:3306/musica";
        Properties propiedades = new Properties();
        propiedades.setProperty("user", "root");
        propiedades.setProperty("password", "1234");
        propiedades.setProperty("useSSL", "false");
        propiedades.setProperty("serverTimezone", "UTC");
        propiedades.setProperty("allowPublicKeyRetrieval", "true");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, propiedades);
            System.out.println("Conexión Exitosa a base de datos MUSICA");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
            System.exit(0);
        }
    }

    private void guardar() {
        try {
            String sql = "INSERT INTO disco (nombre, artista, genero, duracion, canciones, anio, calificacion, formato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtArtista.getText());
            pst.setString(3, txtGenero.getText());
            pst.setInt(4, Integer.parseInt(txtDuracion.getText()));
            pst.setInt(5, Integer.parseInt(txtCanciones.getText()));
            pst.setInt(6, Integer.parseInt(txtAnio.getText()));
            pst.setInt(7, Integer.parseInt(txtCalificacion.getText()));
            pst.setString(8, txtFormato.getText());

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Disco guardado correctamente.");
                nuevo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar (revisa que los campos numéricos sean correctos): " + e.getMessage());
        }
    }

    private void buscar() {
        try {
            String sql = "SELECT * FROM disco WHERE nombre = ?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                txtArtista.setText(rs.getString("artista"));
                txtGenero.setText(rs.getString("genero"));
                txtDuracion.setText(String.valueOf(rs.getInt("duracion")));
                txtCanciones.setText(String.valueOf(rs.getInt("canciones")));
                txtAnio.setText(String.valueOf(rs.getInt("anio")));
                txtCalificacion.setText(String.valueOf(rs.getInt("calificacion")));
                txtFormato.setText(rs.getString("formato"));

                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Disco no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizar() {
        try {
            String sql = "UPDATE disco SET nombre=?, artista=?, genero=?, duracion=?, canciones=?, anio=?, calificacion=?, formato=? WHERE id=?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtArtista.getText());
            pst.setString(3, txtGenero.getText());
            pst.setInt(4, Integer.parseInt(txtDuracion.getText()));
            pst.setInt(5, Integer.parseInt(txtCanciones.getText()));
            pst.setInt(6, Integer.parseInt(txtAnio.getText()));
            pst.setInt(7, Integer.parseInt(txtCalificacion.getText()));
            pst.setString(8, txtFormato.getText());
            pst.setInt(9, id);

            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Disco actualizado.");
                nuevo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminar() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + txtNombre.getText() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM disco WHERE id = ?";
                PreparedStatement pst = conexion.prepareStatement(sql);
                pst.setInt(1, id);

                if (pst.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Eliminado.");
                    nuevo();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void nuevo() {
        txtNombre.setText("");
        txtArtista.setText("");
        txtGenero.setText("");
        txtDuracion.setText("");
        txtCanciones.setText("");
        txtAnio.setText("");
        txtCalificacion.setText("");
        txtFormato.setText("");
        id = 0;
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnNuevo.setEnabled(false);
        txtNombre.requestFocus();
    }

    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try { if (conexion != null) conexion.close(); } catch (SQLException ex) {}
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        PruebaConexion app = new PruebaConexion();
        app.conectar();
        app.setVisible(true);
    }
}