from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
import time

# --- CONFIGURACIÓN EDITABLE ---
busqueda = "Desayunos en Tepic Nayarit"
limite_resultados = 10  # Cuántos negocios quieres intentar extraer
# ------------------------------

def extraer_datos():
    # Configuración del navegador
    options = webdriver.ChromeOptions()
    # options.add_argument("--headless") # Descomenta para que no se vea la ventana
    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    try:
        driver.get("https://www.google.com/maps")
        time.sleep(3)

        # Buscar el cuadro de texto e ingresar la búsqueda
        search_box = driver.find_element(By.ID, "searchboxinput")
        search_box.send_keys(busqueda)
        search_box.send_keys(Keys.ENTER)
        time.sleep(5)

        print(f"Buscando prospectos para: {busqueda}...")

        # Lista para guardar resultados
        prospectos = []

        # Localizar contenedores de resultados (selectores pueden variar según actualización de Google)
        resultados = driver.find_elements(By.CLASS_NAME, "Nv261")[:limite_resultados]

        for r in resultados:
            try:
                # Intentar hacer clic para ver detalles (donde sale el teléfono)
                r.click()
                time.sleep(2)

                nombre = r.get_attribute("aria-label")

                # Buscar el elemento que contiene el teléfono (patrón común en Maps)
                # Nota: El scraping de teléfonos es sensible a cambios de diseño
                try:
                    telefono_elem = driver.find_element(By.XPATH, '//button[contains(@data-item-id, "phone:tel:")]')
                    telefono = telefono_elem.get_attribute("data-item-id").replace("phone:tel:", "")
                except:
                    telefono = "No encontrado"

                prospectos.append({"Nombre": nombre, "Telefono": telefono})
                print(f"Encontrado: {nombre} - Tel: {telefono}")

            except Exception as e:
                continue

        print("\n--- LISTA DE LLAMADAS PARA MAÑANA ---")
        for p in prospectos:
            print(f"{p['Nombre']}: {p['Telefono']}")

    finally:
        driver.quit()

if __name__ == "__main__":
    extraer_datos()