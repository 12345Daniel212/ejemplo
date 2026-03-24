def Ejercicio1_gauss_seidel(iteraciones=10, tolerancia=1e-6):
    # Valores iniciales
    x = 0
    y = 0
    z = 0

    print(f"{'Iter':<5} | {'x':<10} | {'y':<10} | {'z':<10}")
    print("-" * 45)

    for i in range(1, iteraciones + 1):
        x_viejo, y_viejo, z_viejo = x, y, z

        # Despejamos cada variable usando los valores más recientes:
        # 1) 3x - z = 4  => x = (4 + z) / 3
        x = (4 + z) / 3

        # 2) x + 2y = 5  => y = (5 - x) / 2
        y = (5 - x) / 2

        # 3) y - 3z = 5  => z = (y - 5) / 3
        z = (y - 5) / 3

        print(f"{i:<5} | {x:<10.6f} | {y:<10.6f} | {z:<10.6f}")

        # Verificamos si el cambio es lo suficientemente pequeño (convergencia)
        error = max(abs(x - x_viejo), abs(y - y_viejo), abs(z - z_viejo))
        if error < tolerancia:
            print(f"\nConvergencia alcanzada en la iteración {i}")
            break

    return x, y, z

# Ejecutar el método
sol_x, sol_y, sol_z = gauss_seidel()

print("\nResultados finales:")
print(f"x ≈ {sol_x:.4f}")
print(f"y ≈ {sol_y:.4f}")
print(f"z ≈ {sol_z:.4f}")