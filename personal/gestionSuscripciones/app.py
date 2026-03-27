# app para gestionar suscripciones personales
from flask import Flask, render_template, request, jsonify
import sqlite3
import os

app = Flask(__name__)

# CONFIGURACIÓN DE RUTAS PARA PYTHONANYWHERE
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, 'suscripciones.db')

def ejecutar_sql(query, parametros=()):
    # SIEMPRE usamos DB_PATH
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    cursor.execute(query, parametros)
    resultado = None
    if query.strip().upper().startswith("SELECT"):
        resultado = [dict(row) for row in cursor.fetchall()]
    conn.commit()
    conn.close()
    return resultado

def init_db():
    sql = """
    CREATE TABLE IF NOT EXISTS suscripciones (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        servicio TEXT NOT NULL,
        costo REAL NOT NULL
    )
    """
    ejecutar_sql(sql)

# EJECUTAR INIT_DB FUERA DEL MAIN PARA PYTHONANYWHERE
# Esto asegura que la tabla se cree al arrancar el servidor web
init_db()

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/api/suscripciones', methods=['GET'])
def listar_suscripciones():
    datos = ejecutar_sql("SELECT * FROM suscripciones")
    return jsonify(datos)

@app.route('/api/suscripciones', methods=['POST'])
def agregar_suscripcion():
    data = request.get_json()
    if not data:
        return jsonify({"error": "No se proporcionaron datos"}), 400

    servicio = data.get('servicio', '').strip()
    costo = data.get('costo')

    if not servicio or costo is None:
        return jsonify({"error": "Faltan campos requeridos"}), 400

    try:
        costo = float(costo)
    except (ValueError, TypeError):
        return jsonify({"error": "El costo debe ser un número"}), 400

    # USAR ejecutar_sql aquí también para evitar errores de ruta
    ejecutar_sql("INSERT INTO suscripciones (servicio, costo) VALUES (?, ?)", (servicio, costo))

    return jsonify({"message": "Suscripción agregada"}), 201

@app.route('/api/suscripciones/<int:id>', methods=['DELETE'])
def eliminar_suscripcion(id):
    ejecutar_sql("DELETE FROM suscripciones WHERE id = ?", (id,))
    return jsonify({"message": "Eliminada"}), 200

if __name__ == '__main__':
    app.run(debug=True)