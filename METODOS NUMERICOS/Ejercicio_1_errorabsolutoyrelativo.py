#Daniel Rodriguez Altamirano 24400621
#Leonardo David Robles Hinojosa 24400682
import math

class Ejercicio_1_errorabsolutoyrelativo():
    def __init__(self):
      Va = 3.14
      V = math.pi
      # Error absoluto = Ea
      # Error relativo = Er
      # Error relativo porcentual = ErP
      Ea = abs(V - Va)
      Er = abs(V-Va)/abs(V)
      Ev = V - Va
      Erp = (Ev/V)*100

      print("Error absoluto es: ", Ea)
      print("Error relativo es: ", Er)
      print("Error relativo porcentual es: ", Erp)

if __name__ == "__main__":
    app = Ejercicio_1_errorabsolutoyrelativo()