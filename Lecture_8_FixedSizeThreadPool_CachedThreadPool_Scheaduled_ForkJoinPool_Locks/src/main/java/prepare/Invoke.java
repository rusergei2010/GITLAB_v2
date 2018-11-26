package prepare;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Invoke {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService serviceExecutor = Executors.newFixedThreadPool(1);

        Collection<Callable<String>> tasks = Collections.unmodifiableCollection(Arrays.asList(callable(), callable(), callable()));

        List<Future<String>> results = serviceExecutor.invokeAll(tasks);
        results.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(System.out::println);


        System.out.println("Invoke any ...");
        String result = serviceExecutor.invokeAny(tasks);
        System.out.println(result);


        serviceExecutor.shutdown();
    }

    private static Callable<String> callable() {
        return () -> {
            return "Task XXX";
        };
    }
}
