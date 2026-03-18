import pandas as pd
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import urllib.parse
import time
import os

def guardar_favoritos_desde_inicio(ruta_excel):
    print(">>> CONECTANDO CON CHROME (PUERTO 9222)...")

    opts = Options()
    opts.add_experimental_option("debuggerAddress", "127.0.0.1:9222")

    try:
        driver = webdriver.Chrome(options=opts)
        wait = WebDriverWait(driver, 10)
    except Exception as e:
        print(f"ERROR: No se pudo conectar. ¿Abriste Chrome con --remote-debugging-port=9222?\n{e}")
        return

    # Archivo de texto para guardar los que fallen
    archivo_errores = "direcciones_no_encontradas.txt"

    try:
        # Cargar el Excel completo
        df = pd.read_excel(ruta_excel)
        print(f">>> Archivo cargado correctamente.")

        # PROCESAR DESDE EL INICIO
        # Pandas toma la fila 1 como encabezado, así que el primer dato es el índice 0
        df_proceso = df

    except Exception as e:
        print(f"ERROR: No se pudo leer el archivo Excel.\n{e}")
        return

    print(f">>> Iniciando proceso desde el primer registro...")

    for i, r in df_proceso.iterrows():
        # Construcción de la dirección
        # Usamos str() por si hay números en las celdas y evitar errores de concatenación
        domicilio = str(r['DIRECCION DOMICILIO CLIENTE/AVAL']).strip()
        ext = str(r['EXT']).strip()
        colonia = str(r['COLONIA']).strip()

        direccion_completa = f"{domicilio} {ext}, {colonia}, Tepic, Nayarit"

        # En Excel: Fila 1 = Encabezados, Fila 2 = Primer Registro
        # En Pandas: Índice 0 = Primer Registro. Por eso sumamos 2.
        fila_excel = i + 2

        print(f"--- [Fila Excel: {fila_excel}] Procesando: {direccion_completa}")

        try:
            # Codificar y buscar en Maps
            query = urllib.parse.quote(direccion_completa)
            driver.get(f"https://www.google.com/maps/search/{query}")

            # 1. Esperar y clic en el botón 'Guardar'
            xpath_guardar = "//button[contains(@aria-label, 'Guardar') or contains(@aria-label, 'Save')]"
            btn_guardar = wait.until(EC.element_to_be_clickable((By.XPATH, xpath_guardar)))
            driver.execute_script("arguments[0].click();", btn_guardar)

            time.sleep(1) # Espera breve para que cargue el menú de listas

            # 2. Esperar y clic en 'Favoritos'
            xpath_favoritos = "//div[text()='Favoritos' or text()='Favorites']"
            elemento_fav = wait.until(EC.presence_of_element_located((By.XPATH, xpath_favoritos)))
            driver.execute_script("arguments[0].click();", elemento_fav)

            print(f"    [OK] Fila {fila_excel} guardada.")
            time.sleep(1)

        except Exception:
            # Si algo falla (no se encontró dirección o botón), se anota en el TXT
            print(f"    [!] Fila {fila_excel} NO encontrada. Guardando en lista de pendientes...")

            with open(archivo_errores, "a", encoding="utf-8") as f:
                f.write(f"FILA: {fila_excel} | DIRECCION: {direccion_completa}\n")

            continue

    print(f"\n>>> ¡PROCESO TERMINADO! <<<")
    print(f">>> Revisa '{archivo_errores}' para ver los domicilios que faltaron.")

# Ruta del archivo
archivo_ruta = r"C:\Users\rodri\Downloads\DANY 260313 PUERTA DE LA LAGUNA (162).xlsx"
guardar_favoritos_desde_inicio(archivo_ruta)