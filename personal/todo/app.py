# 1. IMPORTACIONES: Traemos las herramientas necesarias
from flask import Flask, request, jsonify, render_template
import sqlite3

# Inicializamos Flask. __name__ le dice a Python que esta es la app principal.
app = Flask(__name__)

# --- SECCIÓN DE BASE DE DATOS (SQL) ---

def ejecutar_sql(query, parametros=()):
    """
    Esta función es nuestro 'obrero'.
    Recibe una orden SQL (query) y los datos (parametros).
    """
    # Conectamos al archivo de la base de datos (se crea si no existe)
    conn = sqlite3.connect('database.db')

    # El cursor es el 'lápiz' que escribe o lee en las tablas
    cursor = conn.cursor()

    # Ejecutamos la sentencia SQL
    cursor.execute(query, parametros)

    resultado = None
    # Si la orden es una consulta (SELECT), guardamos los datos encontrados
    if query.strip().upper().startswith("SELECT"):
        resultado = cursor.fetchall() # fetchall() devuelve una lista de filas

    # Guardamos los cambios permanentemente (commit)
    conn.commit()
    # Cerramos la conexión para liberar memoria
    conn.close()

    return resultado

def init_db():
    """ Crea la tabla al inicio para que el programa no falle al buscarla """
    sql = """
    CREATE TABLE IF NOT EXISTS tareas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL
    )
    """
    ejecutar_sql(sql)
    print("✅ SQL: Base de datos lista.")

# --- SECCIÓN DE RUTAS (ENDPOINTS) ---

@app.route('/')
def home():
    """ Muestra la interfaz visual (HTML) al entrar a localhost:5000 """
    return render_template('index.html')

@app.route('/api/tareas', methods=['GET'])
def listar():
    """
    Ruta para LEER tareas.
    Cuando el HTML pide datos, ejecutamos un SELECT.
    """
    print("🔍 SQL: Consultando todas las tareas...")
    datos = ejecutar_sql("SELECT * FROM tareas")

    # Transformamos las filas (tuplas) en un formato JSON amigable para la Web
    # r[0] es el ID, r[1] es el Título
    return jsonify([{"id": r[0], "titulo": r[1]} for r in datos])

@app.route('/api/tareas', methods=['POST'])
def guardar():
    """
    Ruta para CREAR tareas.
    Recibe un JSON desde el navegador y hace un INSERT.
    """
    # Extraemos el 'titulo' que viene del cuerpo (body) de la petición
    titulo_nuevo = request.json.get('titulo')

    print(f"📝 SQL: Insertando nueva tarea: {titulo_nuevo}")
    ejecutar_sql("INSERT INTO tareas (titulo) VALUES (?)", (titulo_nuevo,))

    return jsonify({"status": "ok"}), 201

@app.route('/api/tareas/<int:id>', methods=['DELETE'])
def borrar(id):
    """
    Ruta para ELIMINAR.
    El <int:id> captura el número que viene en la URL (ej: /api/tareas/5)
    """
    print(f"🗑️ SQL: Borrando tarea con ID: {id}")
    ejecutar_sql("DELETE FROM tareas WHERE id = ?", (id,))

    return jsonify({"status": "eliminado"})

# --- ARRANQUE DEL SERVIDOR ---

if __name__ == '__main__':
    # 1. Preparamos la base de datos
    init_db()

    # 2. Encendemos el servidor en modo desarrollo (debug=True)
    # Esto hace que si cambias el código, el servidor se reinicie solo.
    app.run(debug=True)