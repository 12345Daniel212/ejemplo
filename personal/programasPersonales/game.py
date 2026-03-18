import pygame
import random
import sys

# --- CONFIGURACIÓN ---
WIDTH, HEIGHT = 1280, 720
FPS = 60
WHITE = (255, 255, 255)
BLACK = (20, 20, 20)
BLUE = (50, 50, 200)
RED = (200, 50, 50)
GREEN = (50, 200, 50)
GOLD = (255, 215, 0)

# --- CLASES DEL JUEGO ---
class CartaVisual:
    def __init__(self, nombre, atk, hp, costo, color):
        self.nombre = nombre
        self.atk = atk
        self.hp = hp
        self.costo = costo
        self.color = color
        self.rect = pygame.Rect(0, 0, 120, 170)
        self.objetivo_pos = [0, 0]
        self.pos_actual = [0, 0]
        self.dragging = False

    def draw(self, screen, font, x, y):
        self.rect.topleft = (x, y)
        # Sombra y cuerpo
        pygame.draw.rect(screen, (40, 40, 40), (x+5, y+5, 120, 170), border_radius=10)
        pygame.draw.rect(screen, self.color, self.rect, border_radius=10)
        pygame.draw.rect(screen, WHITE, self.rect, 3, border_radius=10)

        # Texto
        txt_nom = font.render(self.nombre, True, WHITE)
        txt_costo = font.render(f"MP: {self.costo}", True, GOLD)
        txt_stats = font.render(f"{self.atk} / {self.hp}", True, BLACK)

        screen.blit(txt_nom, (x + 10, y + 10))
        screen.blit(txt_costo, (x + 10, y + 35))
        pygame.draw.circle(screen, WHITE, (x + 60, y + 140), 25)
        screen.blit(txt_stats, (x + 40, y + 130))

class JuegoVisual:
    def __init__(self):
        pygame.init()
        self.screen = pygame.display.set_mode((WIDTH, HEIGHT))
        pygame.display.set_caption("Gemini Card Battle TCG")
        self.clock = pygame.time.Clock()
        self.font = pygame.font.SysFont("Arial", 18, bold=True)
        self.font_big = pygame.font.SysFont("Arial", 40, bold=True)

        self.hp_jugador = 30
        self.hp_cpu = 30
        self.mana_jugador = 1
        self.mana_max = 1

        self.mano = [self.generar_carta() for _ in range(5)]
        self.board_jugador = []
        self.board_cpu = [self.generar_carta() for _ in range(2)]

        self.mensaje = "TU TURNO - JUEGA UNA CARTA"
        self.selected_card = None

    def generar_carta(self):
        nombres = ["Guerrero", "Mago", "Bestia", "Arquero", "Golem", "Dragón"]
        color = random.choice([BLUE, RED, GREEN, (100, 50, 150)])
        atk = random.randint(1, 8)
        hp = random.randint(1, 10)
        costo = (atk + hp) // 2
        return CartaVisual(random.choice(nombres), atk, hp, costo, color)

    def draw_ui(self):
        self.screen.fill(BLACK)

        # Dibujar Zonas
        pygame.draw.rect(self.screen, (30, 30, 30), (50, 50, WIDTH-100, 200)) # Zona CPU
        pygame.draw.rect(self.screen, (30, 30, 30), (50, 400, WIDTH-100, 200)) # Zona Jugador

        # Stats
        txt_hp = self.font_big.render(f"TU HP: {self.hp_jugador}", True, GREEN)
        txt_cpu = self.font_big.render(f"CPU HP: {self.hp_cpu}", True, RED)
        txt_mana = self.font_big.render(f"MANA: {self.mana_jugador}/{self.mana_max}", True, BLUE)

        self.screen.blit(txt_hp, (50, 620))
        self.screen.blit(txt_cpu, (50, 10))
        self.screen.blit(txt_mana, (WIDTH - 300, 620))

        # Dibujar Cartas Mano
        for i, carta in enumerate(self.mano):
            carta.draw(self.screen, self.font, 300 + (i * 130), 620 if self.selected_card != carta else 580)

        # Dibujar Tablero Jugador
        for i, carta in enumerate(self.board_jugador):
            carta.draw(self.screen, self.font, 100 + (i * 140), 415)

        # Dibujar Tablero CPU
        for i, carta in enumerate(self.board_cpu):
            carta.draw(self.screen, self.font, 100 + (i * 140), 65)

        # Botón Pasar Turno
        self.btn_next = pygame.Rect(WIDTH - 200, HEIGHT // 2 - 25, 150, 50)
        pygame.draw.rect(self.screen, GOLD, self.btn_next, border_radius=10)
        txt_btn = self.font.render("PASAR TURNO", True, BLACK)
        self.screen.blit(txt_btn, (WIDTH - 185, HEIGHT // 2 - 10))

    def handle_click(self, pos):
        # Click en mano
        for carta in self.mano:
            if carta.rect.collidepoint(pos):
                if self.mana_jugador >= carta.costo:
                    self.mana_jugador -= carta.costo
                    self.board_jugador.append(carta)
                    self.mano.remove(carta)
                    return

        # Click en pasar turno
        if self.btn_next.collidepoint(pos):
            self.turno_cpu()

    def turno_cpu(self):
        # Lógica simple de ataque CPU
        for c_cpu in self.board_cpu:
            self.hp_jugador -= c_cpu.atk

        # CPU roba y juega
        if len(self.board_cpu) < 5:
            self.board_cpu.append(self.generar_carta())

        self.mana_max = min(10, self.mana_max + 1)
        self.mana_jugador = self.mana_max
        if len(self.mano) < 7: self.mano.append(self.generar_carta())

    def run(self):
        running = True
        while running:
            self.draw_ui()

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                if event.type == pygame.MOUSEBUTTONDOWN:
                    self.handle_click(event.pos)

            pygame.display.flip()
            self.clock.tick(FPS)
        pygame.quit()

if __name__ == "__main__":
    juego = JuegoVisual()
    juego.run()