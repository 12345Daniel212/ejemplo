class Temporizador implements Runnable {
    private int s, id;
    public Temporizador(int s, int id) { this.s = s; this.id = id; }

    public void run() {
        System.out.println("Hilo " + id + " iniciado");
        for (int i = 0; i < s; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Hilo " + id + ": " + (i + 1) + "s");
            } catch (InterruptedException e) { return; }
        }
        System.out.println("Hilo " + id + " terminó");
    }
}