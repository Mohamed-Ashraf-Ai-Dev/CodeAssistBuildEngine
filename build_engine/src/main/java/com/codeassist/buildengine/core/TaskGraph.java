package com.codeassist.buildengine.core;
import java.util.*;import java.util.concurrent.*;import java.util.function.Consumer;
public final class TaskGraph {
 public static final class Task { final String id; final List<String> deps; final Runnable action; Task(String i,List<String>d,Runnable a){id=i;deps=d;action=a;} }
 private final Map<String,Task> tasks=new LinkedHashMap<>();
 public TaskGraph add(String id,Runnable action,String... deps){tasks.put(id,new Task(id,Arrays.asList(deps),action));return this;}
 public void run() { Set<String> done=new HashSet<>(); while(done.size()<tasks.size()){List<Task> ready=new ArrayList<>();for(Task t:tasks.values())if(!done.contains(t.id)&&done.containsAll(t.deps))ready.add(t);if(ready.isEmpty())throw new IllegalStateException("Cyclic or missing task dependency"); ready.parallelStream().forEach(t->t.action.run());ready.forEach(t->done.add(t.id));} }
}
