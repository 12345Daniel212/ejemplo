#Daniel Rodriguez Altamirano 24400621
#Leonardo David Robles Hinojosa 24400682
import math

class Ejercicio_2_errordetruncamiento():
    def __init__(self):
      x = 0.5
      # serie de Taylor
      e_x = 1 + x + x**2/ math.factorial(2)

      print("El valor de e^x con la serie de Taylor es: ", e_x)

      vV = math.exp(x)
      print("El valor de e^x usando la funcion exp es: ", vV)

      eV = (vV - e_x)
      print("El error de truncamiento es: ", eV)


if __name__ == "__main__":
    app = Ejercicio_2_errordetruncamiento()
