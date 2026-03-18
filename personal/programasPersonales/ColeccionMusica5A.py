import customtkinter as ctk
from tkinter import messagebox  # Importamos la librería de ventanas emergentes

class ColeccionMusica5A(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.inicializar_com()

    def inicializar_com(self):
        # --- CONFIGURACIÓN DE LA VENTANA ---
        ctk.set_appearance_mode("System")
        ctk.set_default_color_theme("green")

        self.title("-Colección de Música-")
        self.geometry("600x650")

        # Layout principal
        main_frame = ctk.CTkFrame(self)
        main_frame.pack(pady=20, padx=20, fill="both", expand=True)

        # Titulo
        titulo = ctk.CTkLabel(main_frame, text="-Mi Colección de Música-", font=("Arial", 20, "bold"))
        titulo.grid(row=0, column=0, columnspan=2, pady=(10, 20))

        # 1. Apartado nombre del disco (AGREGADO self.)
        lbl_nombre_dis = ctk.CTkLabel(main_frame, text="Nombre del disco:")
        lbl_nombre_dis.grid(row=1, column=0, padx=10, pady=10, sticky="e")

        self.txt_nombre_dis = ctk.CTkEntry(main_frame, width=200)
        self.txt_nombre_dis.grid(row=1, column=1, padx=10, pady=10, sticky="w")

        # 2. Apartado nombre del artista (AGREGADO self.)
        lbl_nombre_ar = ctk.CTkLabel(main_frame, text="Nombre del artista:")
        lbl_nombre_ar.grid(row=2, column=0, padx=10, pady=10, sticky="e")

        self.txt_nombre_ar = ctk.CTkEntry(main_frame, width=200)
        self.txt_nombre_ar.grid(row=2, column=1, padx=10, pady=10, sticky="w")

        # 3. Apartado del genero (AGREGADO self.)
        lbl_genero = ctk.CTkLabel(main_frame, text="Género musical:")
        lbl_genero.grid(row=3, column=0, padx=10, pady=10, sticky="e")

        generos = ["Rock", "Pop", "Metal", "Indie", "Punk", "Electronica", "Clasica", "Otro"]
        self.combo_genero = ctk.CTkComboBox(main_frame, values=generos, width=200)
        self.combo_genero.set("Rock")
        self.combo_genero.grid(row=3, column=1, padx=10, pady=10, sticky="w")

        # 4. Apartado de la duración (AGREGADO self.)
        lbl_duracion = ctk.CTkLabel(main_frame, text="Duración (minutos):")
        lbl_duracion.grid(row=4, column=0, padx=10, pady=10, sticky="e")

        self.txt_duracion = ctk.CTkEntry(main_frame, width=200)
        self.txt_duracion.insert(0, "30")
        self.txt_duracion.grid(row=4, column=1, padx=10, pady=10, sticky="w")

        # 5. Apartado del no. de canciones (AGREGADO self.)
        lbl_canciones = ctk.CTkLabel(main_frame, text="Número de canciones:")
        lbl_canciones.grid(row=5, column=0, padx=10, pady=10, sticky="e")

        self.txt_canciones = ctk.CTkEntry(main_frame, width=200)
        self.txt_canciones.insert(0, "10")
        self.txt_canciones.grid(row=5, column=1, padx=10, pady=10, sticky="w")

        # 6. Apartado del año de lanzamiento (AGREGADO self.)
        lbl_anio = ctk.CTkLabel(main_frame, text="Año de lanzamiento:")
        lbl_anio.grid(row=6, column=0, padx=10, pady=10, sticky="e")

        self.txt_anio = ctk.CTkEntry(main_frame, width=200)
        self.txt_anio.insert(0, "2000")
        self.txt_anio.grid(row=6, column=1, padx=10, pady=10, sticky="w")

        # 7. Calificación
        lbl_calificacion = ctk.CTkLabel(main_frame, text="Calificación:")
        lbl_calificacion.grid(row=7, column=0, padx=10, pady=10, sticky="e")

        frame_radios = ctk.CTkFrame(main_frame, fg_color="transparent")
        frame_radios.grid(row=7, column=1, padx=10, pady=10, sticky="w")

        self.calificacion_var = ctk.IntVar(value=1) # Value=1 para que empiece seleccionado el 1

        for i in range(1, 6):
            radio = ctk.CTkRadioButton(
                frame_radios,
                text=str(i),
                variable=self.calificacion_var,
                value=i,
                width=40
            )
            radio.pack(side="left", padx=5)

        # 8. Formato (RadioButtons)
        self.var_formato = ctk.StringVar(value="Fisico")

        lbl_formato = ctk.CTkLabel(main_frame, text="Formato:")
        lbl_formato.grid(row=8, column=0, padx=10, pady=10, sticky="e")

        radio_fisico = ctk.CTkRadioButton(main_frame, text="Fisico",
                                          variable=self.var_formato, value="Fisico")
        radio_fisico.grid(row=8, column=1, padx=10, pady=5, sticky="w") # Ajusté pady

        radio_digital = ctk.CTkRadioButton(main_frame, text="Digital",
                                           variable=self.var_formato, value="Digital")
        radio_digital.grid(row=9, column=1, padx=10, pady=5, sticky="w")

        # 9. Botones
        # Usamos command=self.mostrar_info para llamar a la función de abajo
        # Ajusté un poco el grid para que los botones queden centrados pero separados
        # Usamos un frame para los botones para alinearlos mejor
        botones_frame = ctk.CTkFrame(main_frame, fg_color="transparent")
        botones_frame.grid(row=10, column=0, columnspan=2, pady=20)

        aceptar_btn = ctk.CTkButton(main_frame, text="Aceptar", command=self.mostrar_info)
        aceptar_btn.pack(in_=botones_frame, side="left", padx=10)



        salir_btn = ctk.CTkButton(botones_frame, text="Salir", command=self.quit, fg_color="red", hover_color="darkred")
        salir_btn.pack(side="left", padx=10)

    # --- ESTA ES LA FUNCIÓN QUE HACE LA MAGIA ---
    def mostrar_info(self):
        # 1. Obtenemos los datos con .get()
        info = f"""--- DATOS REGISTRADOS ---

            Disco: {self.txt_nombre_dis.get()}
            Artista: {self.txt_nombre_ar.get()}
            Género: {self.combo_genero.get()}
            Duración: {self.txt_duracion.get()} min
            Canciones: {self.txt_canciones.get()}
            Año: {self.txt_anio.get()}
            Calificación: {self.calificacion_var.get()}/5
            Formato: {self.var_formato.get()}
                    """

        # 2. Mostramos el mensaje (JOptionTime / MessageBox)
        messagebox.showinfo("Información del Disco", info)

if __name__ == "__main__":
    app = ColeccionMusica5A()
    app.mainloop()