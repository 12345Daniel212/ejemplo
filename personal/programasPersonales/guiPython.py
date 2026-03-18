import customtkinter as ctk
import random

class PingPongApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("Ping Pong Pro")
        self.geometry("800x600")
        self.resizable(False, False)

        # Canvas
        self.canvas = ctk.CTkCanvas(self, width=800, height=600, bg="#1a1a1a", highlightthickness=0)
        self.canvas.pack()

        # Elementos
        self.ball = self.canvas.create_oval(390, 290, 410, 310, fill="#00ffcc")
        self.paddle_left = self.canvas.create_rectangle(20, 250, 40, 350, fill="white")
        self.paddle_right = self.canvas.create_rectangle(760, 250, 780, 350, fill="white")
        self.score_text = self.canvas.create_text(400, 50, text="0  |  0", fill="white", font=("Arial", 30, "bold"))

        # Lógica
        self.ball_speed_x = 5
        self.ball_speed_y = 5
        self.score_l = 0
        self.score_r = 0

        # Diccionario para saber qué teclas están presionadas
        self.pressed_keys = {}

        # Eventos de teclado mejorados
        self.bind("<KeyPress>", self.on_key_press)
        self.bind("<KeyRelease>", self.on_key_release)

        # OBLIGAR A LA VENTANA A TENER EL FOCO
        self.focus_set()

        self.update_game()

    def on_key_press(self, event):
        self.pressed_keys[event.keysym] = True

    def on_key_release(self, event):
        self.pressed_keys[event.keysym] = False

    def move_paddles(self):
        # Movimiento paleta izquierda (W y S)
        if self.pressed_keys.get("w") or self.pressed_keys.get("W"):
            pos = self.canvas.coords(self.paddle_left)
            if pos[1] > 0: self.canvas.move(self.paddle_left, 0, -10)
        if self.pressed_keys.get("s") or self.pressed_keys.get("S"):
            pos = self.canvas.coords(self.paddle_left)
            if pos[3] < 600: self.canvas.move(self.paddle_left, 0, 10)

        # Movimiento paleta derecha (Flechas Up y Down)
        if self.pressed_keys.get("Up"):
            pos = self.canvas.coords(self.paddle_right)
            if pos[1] > 0: self.canvas.move(self.paddle_right, 0, -10)
        if self.pressed_keys.get("Down"):
            pos = self.canvas.coords(self.paddle_right)
            if pos[3] < 600: self.canvas.move(self.paddle_right, 0, 10)

    def update_game(self):
        self.move_paddles()

        ball_pos = self.canvas.coords(self.ball)
        if not ball_pos: return

        # Mover bola
        self.canvas.move(self.ball, self.ball_speed_x, self.ball_speed_y)

        # Rebotes techo/suelo
        if ball_pos[1] <= 0 or ball_pos[3] >= 600:
            self.ball_speed_y *= -1

        # Colisiones paletas
        lp = self.canvas.coords(self.paddle_left)
        rp = self.canvas.coords(self.paddle_right)

        # Lógica de colisión simplificada y efectiva
        if (ball_pos[0] <= lp[2] and lp[1] < ball_pos[3] and lp[3] > ball_pos[1] and self.ball_speed_x < 0):
            self.ball_speed_x *= -1.1

        if (ball_pos[2] >= rp[0] and rp[1] < ball_pos[3] and rp[3] > ball_pos[1] and self.ball_speed_x > 0):
            self.ball_speed_x *= -1.1

        # Puntos
        if ball_pos[0] < 0:
            self.score_r += 1
            self.reset_ball()
        elif ball_pos[2] > 800:
            self.score_l += 1
            self.reset_ball()

        self.canvas.itemconfig(self.score_text, text=f"{self.score_l}  |  {self.score_r}")
        self.after(16, self.update_game) # ~60 FPS

    def reset_ball(self):
        self.canvas.coords(self.ball, 385, 285, 415, 315)
        self.ball_speed_x = 5 * random.choice([-1, 1])
        self.ball_speed_y = 5 * random.choice([-1, 1])

if __name__ == "__main__":
    app = PingPongApp()
    app.mainloop()