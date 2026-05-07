class EjercicioPizza {
    public static void main(String args[]) {
        Deposito deposito = new Deposito();

        Cliente cliente = new Cliente(deposito);
        Thread hiloCliente = new Thread(cliente);
        hiloCliente.start();


        Pizzero pizzero = new Pizzero(deposito);
        Thread hiloPizzero = new Thread(pizzero);
        hiloPizzero.start();
    }
}

class Deposito {
    private int productos = 0;

    public int total() {
        System.out.println("Depósito: Ahora hay " + productos + " pizza(s)");
        return productos;
    }

    public void agregar() {
        System.out.println("Depósito: Pizza recibida.");
        productos++;
        System.out.println("Depósito: Ahora hay " + productos + " pizza(s)");
    }

    public void despachar() {
        System.out.println("Depósito: Pizza extraida.");
        productos--;
        System.out.println("Depósito: Ahora hay " + productos + " pizza(s)");
    }
}

class Pizzero implements Runnable {
    Deposito deposito;

    public Pizzero(Deposito deposito) {
        this.deposito = deposito;
    }

    public void run() {
        synchronized(deposito) {
            System.out.println("Pizzero: Horneando pizza...");

            try { Thread.sleep(1000); } catch(Exception e){ }

            System.out.println("Pizzero: Pizza terminada.");
            System.out.println("Pizzero: Agrega pizza al depósito.");
            deposito.agregar();

            deposito.notify(); // Despierta al cliente
        }
    }
}

class Cliente implements Runnable {
    Deposito deposito;

    public Cliente(Deposito deposito) {
        this.deposito = deposito;
    }

    public void run() {
        synchronized(deposito) {
            System.out.println("Cliente 1: Pide pizza.");

            if (deposito.total() == 0) {
                System.out.println("Cliente 1: Espera pizza...");
                try {
                    deposito.wait();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Cliente 1: Toma pizza...");
            System.out.println("Cliente 1: Es feliz :)");
            deposito.despachar();
        }
    }
}