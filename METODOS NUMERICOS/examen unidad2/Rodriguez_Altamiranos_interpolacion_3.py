import numpy as np

# Puntos dados: (1,2), (2,3), (3,5)
x_puntos = np.array([1, 2, 3])
y_puntos = np.array([2, 3, 5])

# Valor a encontrar
x_objetivo = 2.5

def lagrange(x_buscado, x, y):
    n = len(x)
    resultado = 0

    for i in range(n):
        termino = y[i]
        for j in range(n):
            if i != j:
                termino = termino * (x_buscado - x[j]) / (x[i] - x[j])

        resultado = resultado + termino

    return resultado

valor_final = lagrange(x_objetivo, x_puntos, y_puntos)

print("Interpolacion de Lagrange")
print("Para x =", x_objetivo, "el valor de y es aproximadamente:", valor_final)