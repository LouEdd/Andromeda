package taing.tran.vivier.androidgame.Persona;

interface MySubject {
    void registerObserver(MyObserver o);
    void unregisterObserver(MyObserver o);
    void notifyObservers();
}
