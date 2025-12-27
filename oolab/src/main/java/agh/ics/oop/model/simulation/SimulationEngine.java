package agh.ics.oop.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final static ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync(){
        for(Simulation simulation : simulations){
            simulation.run();
        }
    }

    // kolejność wypisywania bez awaitSimulationsEnd()
    // jest pomieszana mapy są na przemian bo wykonują się
    // współbierznie ruchy zwierząt i czasem wypisanie jeden mapy (lub linie tekstu)
    // pokryje się z wypisywaniem innego stringa do terminala
    public void runAsync(){
        for(Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
    }

    // jest to lepsze rozwiązanie bo wykonuje tylko 4 wątki naraz reszta w kolejce (nie przydusi procesora
    // przy dużej liczbie wątków), brak przeciążenia, stabilniejsza praca, nie występuje przełączanie kontekstu
    public void runAsyncInThreadPool(){
        for(Simulation simulation : simulations)
            executorService.submit(simulation);
    }

    public void awaitSimulationsEnd() throws Exception{
        for(Thread thread : threads)
            thread.join();
        threads.clear();

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

    }

}
