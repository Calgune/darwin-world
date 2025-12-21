//package agh.ics.oop;
//
//import agh.ics.oop.model.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class World {
//    public static void main(String[] args){
//        try {
//            List<MoveDirection> directions = OptionsParser.parse(args);
//            List<Vector2d> positions = List.of(new Vector2d(0,0), new Vector2d(2,3), new Vector2d(3,3));
//
//            List<Simulation> simulations = new ArrayList<>();
//
//            ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
//
//            for (int i = 0; i < 100; i++){
//                WorldMap map;
//                if (i % 2 == 0) map = new GrassField(10);
//                else map = new RectangularMap(5, 5);
//
//                map.addObserver(consoleMapDisplay);
//                simulations.add(new Simulation(positions, map ,directions));
//            }
//
//            SimulationEngine simulationEngine = new SimulationEngine(simulations);
//            simulationEngine.runAsyncInThreadPool();
//
//            simulationEngine.awaitSimulationsEnd();
//
//            IO.println("system zakonczyl dzialanie");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void run(List<MoveDirection> args){
//
//        IO.println("Start");
//
//        for(MoveDirection arg : args){
//            switch (arg){
//                case MoveDirection.FORWARD -> IO.println("Zwierzak idzie do przodu");
//                case MoveDirection.BACKWARDS -> IO.println("Zwierzak idzie do tylu");
//                case MoveDirection.RIGHT -> IO.println("Zwierzak skreca w prawo");
//                case MoveDirection.LEFT-> IO.println("Zwierzak skreca w lewo");
//            }
//        }
//        IO.println("Stop");
//    }
//}
