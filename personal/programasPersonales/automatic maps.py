import customtkinter as ctk
from tkinter import filedialog, messagebox
import pandas as pd
import os
import threading
import urllib.parse
import time
import pyperclip
import re

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")

class App(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("Maps Automation")
        self.geometry("900x650")
        self.running = False
        self.paused = False
        self.filepath = None
        self.driver = None

        # --- Interfaz (Panel Izquierdo) ---
        self.left_panel = ctk.CTkFrame(self, width=450)
        self.left_panel.pack(side="left", fill="both", expand=True, padx=10, pady=10)

        self.header = ctk.CTkLabel(self.left_panel, text="INSTRUCCIONES", font=("Arial", 16, "bold"))
        self.header.pack(pady=10)

        self.cmd_frame = ctk.CTkFrame(self.left_panel, fg_color="#333")
        self.cmd_frame.pack(fill="x", padx=15, pady=5)
        self.cmd_label = ctk.CTkLabel(self.cmd_frame, text="1. Copiar -> 2. Win+R -> 3. Pegar y Enter", font=("Arial", 11))
        self.cmd_label.pack(pady=5)

        self.btn_copy = ctk.CTkButton(self.cmd_frame, text="Copiar Comando Chrome", command=self.copiar_comando, height=28)
        self.btn_copy.pack(pady=10)

        self.btn_select = ctk.CTkButton(self.left_panel, text="📁 Cargar Excel", command=self.cargar_archivo, fg_color="#1f6aa5")
        self.btn_select.pack(pady=15)

        self.status_label = ctk.CTkLabel(self.left_panel, text="Esperando archivo...", text_color="gray")
        self.status_label.pack()

        self.controls_frame = ctk.CTkFrame(self.left_panel, fg_color="transparent")
        self.controls_frame.pack(pady=20)

        self.btn_run = ctk.CTkButton(self.controls_frame, text="🚀 INICIAR", command=self.start_thread, state="disabled", fg_color="green")
        self.btn_run.grid(row=0, column=0, padx=5, pady=5)

        self.btn_pause = ctk.CTkButton(self.controls_frame, text="⏸️ PAUSAR", command=self.toggle_pause, state="disabled", fg_color="orange", text_color="black")
        self.btn_pause.grid(row=0, column=1, padx=5, pady=5)

        self.btn_stop = ctk.CTkButton(self.left_panel, text="🛑 DETENER PROCESO", command=self.stop_process, state="disabled", fg_color="red")
        self.btn_stop.pack(pady=5)

        # --- Panel Derecho (Lista de Errores) ---
        self.right_panel = ctk.CTkFrame(self, width=400)
        self.right_panel.pack(side="right", fill="both", expand=True, padx=10, pady=10)

        self.error_header = ctk.CTkLabel(self.right_panel, text="📍 PENDIENTES (NO ENCONTRADOS)", font=("Arial", 14, "bold"), text_color="#ff7b7b")
        self.error_header.pack(pady=10)

        self.error_box = ctk.CTkTextbox(self.right_panel, font=("Arial", 12))
        self.error_box.pack(fill="both", expand=True, padx=10, pady=10)

    def copiar_comando(self):
        cmd = r'chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\temp_chrome"'
        pyperclip.copy(cmd)
        messagebox.showinfo("Copiado", "Comando copiado. Pégalo en la ventana de Ejecutar (Win+R).")

    def cargar_archivo(self):
        file = filedialog.askopenfilename(filetypes=[("Excel files", "*.xlsx *.xls")])
        if file:
            self.filepath = file
            self.status_label.configure(text=f"✅ {os.path.basename(file)}", text_color="#1fef95")
            self.btn_run.configure(state="normal")

    def toggle_pause(self):
        if not self.paused:
            self.paused = True
            self.btn_pause.configure(text="▶️ REANUDAR", fg_color="yellow")
            self.status_label.configure(text="PAUSADO", text_color="orange")
        else:
            self.paused = False
            self.btn_pause.configure(text="⏸️ PAUSAR", fg_color="orange")
            self.status_label.configure(text="REANUDANDO...", text_color="#1fef95")

    def stop_process(self):
        self.running = False
        self.paused = False
        self.status_label.configure(text="PROCESO DETENIDO", text_color="red")

    def start_thread(self):
        self.running = True
        self.paused = False
        self.btn_run.configure(state="disabled")
        self.btn_pause.configure(state="normal")
        self.btn_stop.configure(state="normal")
        threading.Thread(target=self.ejecutar_logica, daemon=True).start()

    def ejecutar_logica(self):
        opts = Options()
        opts.add_experimental_option("debuggerAddress", "127.0.0.1:9222")

        try:
            self.driver = webdriver.Chrome(options=opts)
            wait = WebDriverWait(self.driver, 8)

            df = pd.read_excel(self.filepath)

            # Buscar columna "Domicilio"
            columna_objetivo = None
            for col in df.columns:
                if re.search(r'domicilio', str(col), re.IGNORECASE):
                    columna_objetivo = col
                    break

            if columna_objetivo is None:
                messagebox.showerror("Error", "No encontré la columna 'Domicilio'.")
                self.btn_run.configure(state="normal")
                return

            df = df.fillna("")

            for i, r in df.iterrows():
                if not self.running: break
                while self.paused:
                    time.sleep(0.5)
                    if not self.running: break

                try:
                    direccion_cruda = str(r[columna_objetivo]).strip()
                    if not direccion_cruda or direccion_cruda.lower() == "nan": continue

                    direccion_completa = f"{direccion_cruda}, Tepic, Nayarit"
                    self.status_label.configure(text=f"Procesando ({i+1}/{len(df)}): {direccion_cruda[:25]}...")

                    query = urllib.parse.quote(direccion_completa)
                    self.driver.get(f"https://www.google.com/maps/search/{query}")

                    # 1. Click en Guardar
                    xpath_guardar = "//button[contains(@aria-label, 'Guardar') or contains(@aria-label, 'Save')]"
                    btn_guardar = wait.until(EC.element_to_be_clickable((By.XPATH, xpath_guardar)))
                    self.driver.execute_script("arguments[0].click();", btn_guardar)
                    time.sleep(1.2)

                    # 2. Click en Favoritos
                    xpath_favoritos = "//div[text()='Favoritos' or text()='Favorites']"
                    elemento_fav = wait.until(EC.element_to_be_clickable((By.XPATH, xpath_favoritos)))
                    self.driver.execute_script("arguments[0].click();", elemento_fav)

                    # Se eliminó la sección de la nota "AVAL" por petición del usuario.
                    time.sleep(0.5)

                except Exception:
                    self.error_box.insert("end", f"• Fila {i+2}: {direccion_cruda}\n")
                    self.error_box.see("end")

            messagebox.showinfo("Fin", "¡Proceso terminado con éxito, jefa!")

        except Exception as e:
            messagebox.showerror("Error", f"Error de conexión con Chrome.\n{e}")

        self.btn_run.configure(state="normal")
        self.btn_pause.configure(state="disabled")
        self.btn_stop.configure(state="disabled")

if __name__ == "__main__":
    app = App()
    app.mainloop()