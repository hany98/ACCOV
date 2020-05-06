package net.cofares.ljug.moniteurs;

public class Compteur2En2 implements Runnable{
    
    private final int DEBUT;
    private final int FIN;
    
    private static boolean isPaire = true;//0
    private static Object lock = new Object();
    
    public Compteur2En2(int d, int f) {
        DEBUT=d;
        FIN=f;
    }
    
    public void run () {
        System.out.printf("\nDebut du thread %s\n",Thread.currentThread());
        for (int i=DEBUT; i<FIN; i +=2) {
            printValue(i, i%2 == 0);//Avec Synchronized
        }
        System.out.printf("\nfin du thread %s\n",Thread.currentThread());
    }
    
    public void printValue(int i, boolean isThreadPaire) {
        while ((isThreadPaire && !Compteur2En2.isPaire) || (!isThreadPaire && Compteur2En2.isPaire)) {
            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.printf("%d ", i);
        Compteur2En2.isPaire = !isThreadPaire;
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}