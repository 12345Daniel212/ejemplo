class Trabajadores {
   public static void main(String args[]) {
      String taladro = "taladro";
      String desarmador = "desarmador";

      Thread trabajador1 = new Trabajador1(taladro, desarmador);
      Thread trabajador2 = new Trabajador2(taladro, desarmador);

      // Iniciamos el Trabajador 1
      trabajador1.start();

      try {
         // Esperamos a que el Trabajador 1 termine antes de que el 2 empiece.
         // Esto evita que ambos bloqueen recursos al mismo tiempo.
         trabajador1.join();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      // Iniciamos el Trabajador 2
      trabajador2.start();
   }
}

class Trabajador1 extends Thread {
   private String taladro;
   private String desarmador;

   public Trabajador1(String taladro, String desarmador) {
      this.taladro = taladro;
      this.desarmador = desarmador;
   }

   public void run() {
      // Usamos getName() para que imprima "Thread-0"
      String nombre = this.getName();
      synchronized(taladro) {
         System.out.println(nombre + " tiene el taladro.");
         try { Thread.sleep(100); } catch (Exception e) {}
         synchronized(desarmador) {
            System.out.println(nombre + " tiene ambos. Trabajando...");
         }
      }
   }
}

class Trabajador2 extends Thread {
   private String taladro;
   private String desarmador;

   public Trabajador2(String taladro, String desarmador) {
      this.taladro = taladro;
      this.desarmador = desarmador;
   }

   public void run() {
      // Usamos getName() para que imprima "Thread-1"
      String nombre = this.getName();
      synchronized(desarmador) {
         System.out.println(nombre + " tiene el desarmador.");
         try { Thread.sleep(100); } catch (Exception e) {}
         synchronized(taladro) {
            System.out.println(nombre + " tiene ambos. Trabajando...");
         }
      }
   }
}