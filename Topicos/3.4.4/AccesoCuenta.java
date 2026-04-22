class AccesoCuenta implements Runnable {

   private double saldo = 500.00;
   private double retiro = 100.00;

   public synchronized void run() {

      String nombre = Thread.currentThread().getName();

      for (int i = 0; i < 5; i++) {

         System.out.println(nombre + " está revisando si hay saldo suficiente...");

         if (saldo >= retiro) {

            System.out.println(nombre + " encontró saldo suficiente: $" + saldo);
            System.out.println(nombre + " va a retirar $" + retiro);

            try {
               System.out.println("Procesando petición de " + nombre + "...");

               Thread.sleep(500);
            } catch(Exception e) {
               System.out.println(e.getMessage());
            }

            saldo = saldo - retiro;
            System.out.println(nombre + " retiró $" + retiro + ". Saldo actual es: " + saldo);

         } else {
            System.out.println(nombre + " no pudo retirar. Saldo insuficiente. Saldo actual: " + saldo);
            return; // Termina el hilo si ya no hay dinero
         }
      }
   }
}