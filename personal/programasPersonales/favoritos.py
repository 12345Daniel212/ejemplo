import pandas as pd
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import urllib.parse
import time

def guardar_favoritos_con_nota(ruta_excel):
    print(">>> CONECTANDO CON CHROME EN PUERTO 9222...")

    opts = Options()
    opts.add_experimental_option("debuggerAddress", "127.0.0.1:9222")

    try:
        driver = webdriver.Chrome(options=opts)
        wait = WebDriverWait(driver, 10)
    except Exception as e:
        print(f"ERROR: No se pudo conectar a Chrome.\n{e}")
        return

    archivo_errores = "direcciones_no_encontradasAvales.txt"

    try:
        df = pd.read_excel(ruta_excel)
        # Limpiamos los valores NaN de todo el Excel desde el inicio
        df = df.fillna("")
        print(f">>> Archivo cargado y celdas vacías limpiadas.")
    except Exception as e:
        print(f"ERROR: No se pudo leer el Excel.\n{e}")
        return

    for i, r in df.iterrows():
        try:
            # --- EXTRACCIÓN LIMPIA ---
            # Basado en tu imagen:
            # Col C (2) = Nombre (IGNORAR)
            # Col D (3) = Calle
            # Col E (4) = Número
            # Col F (5) = Colonia

            calle = str(r.iloc[3]).strip()
            numero = str(r.iloc[4]).strip()
            colonia = str(r.iloc[5]).strip()

            # Evitar que aparezca "0.0" si el número es tratado como decimal
            if numero.endswith(".0"):
                numero = numero[:-2]

            # Construir dirección SIN la palabra 'nan'
            partes = []
            if calle and calle.lower() != "nan": partes.append(calle)
            if numero and numero.lower() != "nan": partes.append(numero)
            if colonia and colonia.lower() != "nan": partes.append(colonia)

            direccion_busqueda = ", ".join(partes) + ", Tepic, Nayarit"

            fila_excel = i + 2
            print(f"--- [Fila {fila_excel}] Buscando: {direccion_busqueda}")

            # Buscar en Maps
            query = urllib.parse.quote(direccion_busqueda)
            driver.get(f"https://www.google.com/maps/search/{query}")

            # 1. Clic en 'Guardar'
            xpath_guardar = "//button[contains(@aria-label, 'Guardar') or contains(@aria-label, 'Save')]"
            btn_guardar = wait.until(EC.element_to_be_clickable((By.XPATH, xpath_guardar)))
            driver.execute_script("arguments[0].click();", btn_guardar)
            time.sleep(1.5)

            # 2. Clic en 'Favoritos'
            xpath_favoritos = "//div[text()='Favoritos' or text()='Favorites']"
            elemento_fav = wait.until(EC.element_to_be_clickable((By.XPATH, xpath_favoritos)))
            driver.execute_script("arguments[0].click();", elemento_fav)
            print(f"    [OK] Guardado en Favoritos.")
            time.sleep(1.5)

            # 3. AGREGAR NOTA "AVAL"
            try:
                # Buscamos el textarea para la nota
                xpath_nota = "//textarea[contains(@aria-label, 'nota') or contains(@aria-label, 'note')]"
                campo_nota = wait.until(EC.presence_of_element_located((By.XPATH, xpath_nota)))

                campo_nota.send_keys("AVAL")
                time.sleep(0.5)
                campo_nota.send_keys(Keys.ENTER)
                print(f"    [OK] Nota 'AVAL' agregada.")
            except:
                print(f"    [!] No se pudo escribir la nota.")

        except Exception as e:
            fila_excel = i + 2
            print(f"    [!] Fila {fila_excel} falló.")
            with open(archivo_errores, "a", encoding="utf-8") as f:
                f.write(f"FILA: {fila_excel} | DIRECCION: {direccion_busqueda}\n")
            continue

    print(f"\n>>> ¡PROCESO TERMINADO! <<<")


# Ejecución
archivo_ruta = r"C:\Users\rodri\Downloads\DANI 260409 aval.xlsx"
guardar_favoritos_con_nota(archivo_ruta)
