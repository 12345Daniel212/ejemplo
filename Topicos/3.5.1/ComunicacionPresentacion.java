class ComunicacionPresentacion {

    public static void main(String args[]) {
       Almacen almacen = new Almacen();

       Thread hiloTienda = new Thread(new Tienda(almacen));
       Thread hiloFabrica = new Thread(new Fabrica(almacen));

       hiloTienda.start();
       hiloFabrica.start();
    }
}

class Almacen {
    private int productos = 0;

    public synchronized int total() {
      return productos;
    }

    public synchronized void agregar() {
      productos++;
      System.out.println("Almacen: Producto recibido. Total: " + productos);
    }

    public synchronized void despachar() {
      productos--;
      System.out.println("Almacen: Producto despachado. Total: " + productos);
    }
}


class Fabrica implements Runnable {
    Almacen almacen;

    public Fabrica(Almacen almacen) {
       this.almacen = almacen;
    }

    public void run() {
       for(int i = 0; i < 3; i++) {
           synchronized(almacen) {
              System.out.println("Fábrica: Construyendo producto " + (i+1) + "...");
              try { Thread.sleep(1000); } catch(Exception e){ }

              almacen.agregar();

              System.out.println("Fábrica: Notificando a la tienda...");
              almacen.notify(); // se notifica que ya se creo el producto para que tienda salga de wait
           }
       }
    }
}

class Tienda implements Runnable {
    Almacen almacen;

    public Tienda(Almacen almacen) {
       this.almacen = almacen;
    }

    public void run() {
       for(int i = 0; i < 3; i++) {
           synchronized(almacen) {
              System.out.println("Tienda: Intentando comprar producto...");

              // Si no hay stock, espera
              while (almacen.total() == 0) {
                 System.out.println("Tienda: Almacén vacío. Esperando...");
                 try {
                    almacen.wait(); // Suelta el candado y espera el notify
                 } catch(Exception e) {
                    e.printStackTrace();
                 }
              }

              System.out.println("Tienda: ¡Producto detectado!");
              almacen.despachar();
           }
       }
    }
}