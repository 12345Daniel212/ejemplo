class Temporizador implements Runnable{
  private int s;
  private int id;

////////////////////
public Temporizador(int s, int id){
  this.s = s;
  this.id = id;
}

///////////////
public void run(){
  for(int i = 0; i < s; i++){
    try{
      Thread.sleep(1000);
    }catch(InterruptedException e){
      System.out.println("Hilo " + id + " interrumpido");
    }
  }
}
}