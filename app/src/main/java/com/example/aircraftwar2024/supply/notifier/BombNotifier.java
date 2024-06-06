package com.example.aircraftwar2024.supply.notifier;

import java.util.LinkedList;
import java.util.List;

public class BombNotifier {
    List<IBombObserver> observerList;
    int scoreGains;
    private static BombNotifier bombNotifier;
    private BombNotifier(){
        observerList = new LinkedList<>();
        scoreGains = 0;
    }
    public static BombNotifier getInstance() {
        if (bombNotifier == null) {
            bombNotifier = new BombNotifier();
        }
        return bombNotifier;
    }

    public void addObserver(IBombObserver observer) {
        observerList.add(observer);
    }
    public void removeObserver(IBombObserver observer) {
        observerList.remove(observer);
    }
    public void removeAllObservers() {
        observerList.clear();
    }
    public void addAllObservers(List<? extends IBombObserver> observers) {
        observerList.addAll(observers);
    }
    public void notifyAllObservers() {
        for (IBombObserver observer : observerList) {
            scoreGains += observer.transact();
        }
    }
    public int syncScore() {
        int tmp = scoreGains;
        scoreGains = 0;
        return scoreGains;
    }
}
