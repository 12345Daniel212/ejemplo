from flask import Flask, render_template, request, jsonify
import sqlite3
import os

app = Flask(__name__)

# Configuración de base de datos
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, 'notion_clone.db')

def ejecutar_sql(query, parametros=()):
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    try:
        cursor.execute(query, parametros)
        conn.commit()
        if query.strip().upper().startswith("SELECT"):
            return [dict(row) for row in cursor.fetchall()]
    except Exception as e:
        print(f"Error SQL: {e}")
    finally:
        conn.close()
    return None

def init_db():
    # Crea la tabla con la columna 'categoria' desde el inicio
    sql = """
    CREATE TABLE IF NOT EXISTS notas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL,
        contenido TEXT,
        categoria TEXT DEFAULT 'Tareas',
        fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
    """
    ejecutar_sql(sql)

    # Truco por si ya tenías la tabla vieja sin la columna 'categoria'
    try:
        ejecutar_sql("ALTER TABLE notas ADD COLUMN categoria TEXT DEFAULT 'Tareas'")
    except:
        pass # Si ya existe, no hace nada

init_db()

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/api/notas', methods=['GET'])
def listar_notas():
    # Obtenemos todas las notas para que el JS las filtre
    datos = ejecutar_sql("SELECT * FROM notas ORDER BY id DESC")
    return jsonify(datos if datos else [])

@app.route('/api/notas', methods=['POST'])
def agregar_nota():
    data = request.get_json()
    titulo = data.get('titulo', 'Sin título')
    contenido = data.get('contenido', '')
    categoria = data.get('categoria', 'Tareas') # <--- AQUÍ CAPTURA LA CARPETA

    ejecutar_sql("INSERT INTO notas (titulo, contenido, categoria) VALUES (?, ?, ?)",
                 (titulo, contenido, categoria))
    return jsonify({"message": "Nota guardada"}), 201

@app.route('/api/notas/<int:id>', methods=['DELETE'])
def eliminar_nota(id):
    ejecutar_sql("DELETE FROM notas WHERE id = ?", (id,))
    return jsonify({"message": "Nota eliminada"}), 200

if __name__ == '__main__':
    app.run(debug=True)