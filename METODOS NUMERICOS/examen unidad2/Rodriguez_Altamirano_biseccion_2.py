import numpy as np

# La funcion del problema
def f(x):
    return x**3 - x - 2

# Valores iniciales
a = 1
b = 2
tol = 0.00001
iteracion = 0

print("# Iteracion | a | b | c | f(c)")

while (b - a) / 2 > tol:
    iteracion = iteracion + 1
    c = (a + b) / 2
    fc = f(c)

    # Imprimir los datos
    print(iteracion, "|", a, "|", b, "|", c, "|", fc)

    if fc == 0:
        break

    if f(a) * fc < 0:
        b = c
    else:
        a = c

print("---------------------------")
print("Total de iteraciones:", iteracion)
print("La raiz es:", c)