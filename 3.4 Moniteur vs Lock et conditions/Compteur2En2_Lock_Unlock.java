package net.cofares.ljug.moniteurs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Compteur2En2 implements Runnable{
    
    private final int DEBUT;
    private final int FIN;
    
    private static boolean isPaire = true;//0
    private final Lock lock = new ReentrantLock();
    
    public Compteur2En2(int d, int f) {
        DEBUT=d;
        FIN=f;
    }
    
    public void run () {
        System.out.printf("\nDebut du thread %s\n",Thread.currentThread());
        for (int i=DEBUT; i<FIN; i +=2) {
            printValue(i, i%2 == 0);//Avec Lock Unlock
        }
        System.out.printf("\nfin du thread %s\n",Thread.currentThread());
    }
    
    public void printValue(int i, boolean isThreadPaire) {
        lock.lock();
        try {
            while((isThreadPaire && !Compteur2En2.isPaire) || (!isThreadPaire && Compteur2En2.isPaire)) {
                lock.lock();
            }
            System.out.printf("%d ", i);
            Compteur2En2.isPaire = !isThreadPaire;
        } finally {
            lock.unlock();
        }
        
    }
}