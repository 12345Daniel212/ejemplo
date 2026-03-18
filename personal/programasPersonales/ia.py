import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras import layers
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

# =================================================================
# 1. PREPARACIÓN DE TUS PROPIOS DATOS
# =================================================================
def cargar_datos():
    # Aquí es donde pondrías tu CSV o base de datos
    # Por ahora inventamos datos: X = Características, y = Resultado
    X = np.random.rand(1000, 5) # 1000 ejemplos con 5 variables cada uno
    y = (X[:, 0] * 2 + X[:, 1] * 0.5 > 1).astype(int) # Una regla lógica simple

    # Dividir en entrenamiento (80%) y prueba (20%)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

    # Escalar datos (Vital para que la IA no se confunda con números grandes)
    scaler = StandardScaler()
    X_train = scaler.fit_transform(X_train)
    X_test = scaler.transform(X_test)

    return X_train, X_test, y_train, y_test

# =================================================================
# 2. ARQUITECTURA DE LA IA (EL CEREBRO)
# =================================================================
def crear_cerebro(input_dim):
    model = tf.keras.Sequential([
        # Capa de entrada: ajusta 'input_shape' a tus variables
        layers.Dense(64, activation='relu', input_shape=(input_dim,)),
        layers.Dropout(0.2), # Evita que la IA memorice de más (Overfitting)

        # Capas ocultas: donde ocurre la "magia" del aprendizaje
        layers.Dense(32, activation='relu'),
        layers.Dense(16, activation='relu'),

        # Capa de salida: 1 neurona con 'sigmoid' para clasificación binaria (Si/No)
        # O 'linear' si quieres predecir un precio/número continuo
        layers.Dense(1, activation='sigmoid')
    ])

    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    return model

# =================================================================
# 3. ENTRENAMIENTO Y VISUALIZACIÓN
# =================================================================
def entrenar_ia():
    X_train, X_test, y_train, y_test = cargar_datos()
    ia = crear_cerebro(X_train.shape[1])

    print("--- INICIANDO APRENDIZAJE ---")
    # history guarda el progreso para graficarlo después
    history = ia.fit(
        X_train, y_train,
        epochs=50,
        batch_size=32,
        validation_data=(X_test, y_test),
        verbose=0
    )

    # --- GRÁFICAS DE APRENDIZAJE ---
    plt.figure(figsize=(12, 5))

    # Gráfica de Precisión (Accuracy)
    plt.subplot(1, 2, 1)
    plt.plot(history.history['accuracy'], label='Entrenamiento')
    plt.plot(history.history['val_accuracy'], label='Validación')
    plt.title('¿Qué tan bien aprendió?')
    plt.legend()

    # Gráfica de Error (Loss)
    plt.subplot(1, 2, 2)
    plt.plot(history.history['loss'], label='Error Entrenamiento')
    plt.plot(history.history['val_loss'], label='Error Validación')
    plt.title('Reducción de Errores')
    plt.legend()

    plt.show()

    return ia

if __name__ == "__main__":
    mi_ia = entrenar_ia()
    # Guardar tu modelo para usarlo en una App o Web
    mi_ia.save("mi_primer_ia.h5")
    print("¡IA lista y guardada!")