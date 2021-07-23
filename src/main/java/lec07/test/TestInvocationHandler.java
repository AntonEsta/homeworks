package lec07.test;

import lec07.annotations.DefaultPath;
import lec07.util.stopwatch.StopwatchResult;
import lec07.annotations.Test;
import lec07.annotations.TimeMeasure;
import lec07.proxy.InvocationHandlerWithSourceObject;
import lec07.util.Annotations;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
public class TestInvocationHandler extends InvocationHandlerWithSourceObject {

    int testCount;
    final boolean timeMeasure;
    String[] compareMethodsByTimeRun = new String[0];
    final String resultPath;
    final boolean rewriteResultFile;

    /**
     * The constructor initializes the source object.
     * @param sourceObject the source object
     */
    public TestInvocationHandler(@NonNull Object sourceObject) {
        super(sourceObject);
        // Get @TimeMeasure value
        this.timeMeasure = Annotations.isContainsAnnotation(getSourceObject(), TimeMeasure.class);
        // Get @Test fields
        if (Annotations.isContainsAnnotation(getSourceObject(), Test.class)) {
           this.testCount = getSourceObject().getClass().getAnnotation(Test.class).testCount();
           this.compareMethodsByTimeRun = getSourceObject().getClass().getAnnotation(Test.class).CompareMethodsByTimeRun();
        }
        // Get @DefaultPath fields
        if (Annotations.isContainsAnnotation(getSourceObject(), DefaultPath.class)) {
            String path = getSourceObject().getClass().getAnnotation(DefaultPath.class).value();
            this.resultPath = (path.endsWith("\\")) ? path : path.concat("\\");
            this.rewriteResultFile = getSourceObject().getClass().getAnnotation(DefaultPath.class).rewriteFile();
        } else {
            Class<?> aClass = getSourceObject().getClass();
            String defDirName = "/TestResults/";
            String aClassPackageName = aClass.getPackage().getName().replace('.', '/');
            String aClassLocationName = aClass.getProtectionDomain().getCodeSource().getLocation().getPath();
            this.resultPath = aClassLocationName.concat(aClassPackageName).concat(defDirName);
            this.rewriteResultFile = false;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isComparedMethod(method)) {
            doCompareByTimeTest(method, args);
            return null;
        }
        if (isMeasuredByTimeMethod(method)) {
            doMeasureByTimeTest(method, args);
            return null;
        }
        return method.invoke(getSourceObject(), args);
    }

    /**
     * Checks if a method is annotated by @TimeMeasure
     * @param method Method to be check.
     * @return true if annotated or false if not.
     */
    private boolean isMeasuredByTimeMethod(Method method) {
        return Annotations.isAnnotatedMethod(getSourceObject(), method, TimeMeasure.class);
    }

    /**
     * Compares the methods by time.
     * @param method The method for test.
     * @param args Arguments of the method for test.
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private void doCompareByTimeTest(Method method, Object[] args) throws NoSuchMethodException, IOException {
        Map<Method, TestUnit> methodsForCompareMap = new HashMap<>();
        setMethodsToCompareMap(method, args, methodsForCompareMap);
        executeMethods(methodsForCompareMap);
        for (TestUnit testUnit : methodsForCompareMap.values()) {
            timeMeasureResults(testUnit);
        }
        writeResultTableToFile(methodsForCompareMap);
    }

    /**
     * Measures time run of the method.
     * @param method The method for test.
     * @param args Arguments of the method for test.
     */
    @SneakyThrows
    private void doMeasureByTimeTest(Method method, Object[] args) {
        TestUnit unit = new TestUnit(getSourceObject(), method, args);
        unit.testByTimekeeper(testCount);
        timeMeasureResults(unit);
    }

    /**
     * Creates and writes result table in the file.
     * @param methodsForCompareMap Map of methods and test units.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private void writeResultTableToFile(Map<Method, TestUnit> methodsForCompareMap) throws IOException {
        String fileName = createResultsFileName(getMethodsFromCompareMap(methodsForCompareMap).toArray(new Method[0]));
        Collection<String> s = createResultTableCompareByTime(methodsForCompareMap.values());
        Objects.requireNonNull(s);
        writeResultsToFile(fileName, s);
    }

    /**
     * Returns list of methods from 'methodsForCompareMap'.
     * @param methodsForCompareMap Map of methods and test units.
     * @return List of methods.
     */
    private List<Method> getMethodsFromCompareMap(Map<Method, TestUnit> methodsForCompareMap) {
        return methodsForCompareMap.values().stream().map(TestUnit::getMethod).collect(Collectors.toList());
    }

    /**
     * Executes methods for test.
     * @param methodsForCompareMap Map of methods and test units for executing.
     */
    private void executeMethods(Map<Method, TestUnit> methodsForCompareMap) {
        // Execute methods
        ExecutorService executorService = Executors.newCachedThreadPool();
        // Do test for each method in 'methodsForCompareMap'
        Collection<TestUnit> values = methodsForCompareMap.values();
        for (TestUnit value : values) {
            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    value.testByTimekeeper(testCount);
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){}
    }

    /**
     * Fills map of {@link TestUnit}.
     * @param method Current method.
     * @param args Current arguments for current method.
     * @param methodsForCompareMap Map<Method, TestUnit> for test results.
     * @throws NoSuchMethodException Thrown when a particular method cannot be found.
     */
    private void setMethodsToCompareMap(Method method, Object[] args, Map<Method, TestUnit> methodsForCompareMap) throws NoSuchMethodException {
        for (String methodName : compareMethodsByTimeRun) {
            Method sourceMethod = getSourceObject().getClass().getMethod(methodName, method.getParameterTypes());
            methodsForCompareMap.put(sourceMethod, new TestUnit(getSourceObject(), sourceMethod, args));
        }
    }

    /**
     * Checks method for comparison.
     * @param method Method to be check.
     * @return true if for comparison or false if not.
     */
    private boolean isComparedMethod(Method method) {
        return compareMethodsByTimeRun.length > 1 && Arrays.stream(compareMethodsByTimeRun).anyMatch((x) -> x.equals(method.getName()));
    }

    /**
     * Creates results file name.
     * @param methods Array of {@link Method} names which must be present in the file name.
     * @return Created file name {@link String}.
     */
    private String createResultsFileName(Method[] methods) {
        StringBuilder fileName = new StringBuilder();
        fileName.append("TimeRunCompareMethods_");
        for (Method m : methods) {
            fileName.append("_")
                    .append(m.getName());
        }
        return fileName.toString();
    }

    /**
     * Creates table of results of compare by time.
     * @param units Collection<TestUnits> for build result table.
     * @return Collection of strings result table.
     */
    private Collection<String> createResultTableCompareByTime(@NonNull Collection<TestUnit> units){        
        if (units.size() == 0) return null;
        final Duration minAvgTime = getMinAvgTimeFromTestUnits(units);
        final String date = "Date: " + LocalDateTime.now();
        final String title = "Compare time table for methods: " + units.stream().map(u->u.getMethod().getName()).collect(Collectors.joining(", "));
        // Get sizes of columns
        final int sizeMethodNameColumn = getMaxLengthFromStrings(units.stream().map(u->u.getMethod().getName()).collect(Collectors.toList()));
        int sizeAvgTimeColumn = getMaxLengthFromStrings(units.stream().map(u->u.getMaxResult().toString()).collect(Collectors.toList()));
        if (sizeAvgTimeColumn < 12) sizeAvgTimeColumn = 12;
        // Head of table
        final String tableHead = String.format("| %-" + sizeMethodNameColumn + "s | %-" + sizeAvgTimeColumn + "s | %-" + sizeAvgTimeColumn + "s |", "Method Name", "Average Time", "Difference");
        // Terminal line
        final char[] charsTerminalLine = new char[tableHead.length()];
        Arrays.fill(charsTerminalLine, '-');
        final String terminalLine = String.valueOf(charsTerminalLine);
        // Build table
        Collection<String> table = new ArrayList<>();
        table.add(date);
        table.add(title);
        table.add("");
        table.add(terminalLine);
        table.add(tableHead);
        table.add(terminalLine);
        int finalMaxLengthAvgTime = sizeAvgTimeColumn;
        units.forEach(u->{
            table.add(String.format("| %-" + sizeMethodNameColumn + "s | %-" + finalMaxLengthAvgTime + "s | %-" + finalMaxLengthAvgTime + "s |",
                u.getMethod().getName(),
                u.getAverageResult(),
                Duration.of(u.getAverageResult().toNanos() - minAvgTime.toNanos(), ChronoUnit.NANOS)));
            table.add(terminalLine);
        });

        return table;
    }

    /**
     * Returns the maximum length of string from a collection.
     * @param units Collection of strings.
     * @return The maximum length of elements.
     */
    private int getMaxLengthFromStrings(Collection<String> units) {
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        Optional<String> maxLengthMethodName = units.stream().max(comparator);
        return maxLengthMethodName.map(String::length).orElse(0);
    }

    /**
     * Returns the arithmetic mean from test results.
     * @param units Elements of {@link TestUnit} for calculation.
     * @param <T> Types extends {@link TestUnit}
     * @return Average {@link Duration}
     */
    private <T extends TestUnit> Duration getMinAvgTimeFromTestUnits(Collection<T> units) {
        Comparator<T> comparator = Comparator.comparingLong(u -> u.getAverageResult().toNanos());
        Optional<T> maxLengthMethodName = units.stream().min(comparator);
        return maxLengthMethodName.map(TestUnit::getAverageResult).orElse(Duration.ZERO);
    }

    /**
     * {@link TimeMeasure} annotation handling
     * @param testUnit {@link TestUnit}
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    private void timeMeasureResults(TestUnit testUnit) throws IOException {
        if ( isMeasuredByTimeMethod(testUnit.getMethod()) ) {
            Collection<StopwatchResult> results = testUnit.getTimeMeasureResults();
            String fileName = getSourceObject() + "." + testUnit.getMethod().getName();
            List<String> strings = new ArrayList<>();
            strings.add("DateTime: " + LocalDateTime.now());
            strings.add("Results of time measure of method " + fileName);
            for (int i = 0; i < results.size(); i++) {
                strings.add("Result #" + (i+1) + " -> " + (new ArrayList<>(results)).get(i).getDifferenceTime().toString());
            }
            strings.add("MAX TIME: " + testUnit.getMaxResult());
            strings.add("MIN TIME: " + testUnit.getMinResult());
            strings.add("AVERAGE TIME: " + testUnit.getAverageResult());
            writeResultsToFile(fileName, strings);
        }
    }

    /**
     * Writes results to file.
     * @param fileName Results file name for writing.
     * @param results Result strings for writing.
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */    
    private void writeResultsToFile(@NonNull String fileName, @NonNull Collection<String> results) throws IOException {
        if (results.size() == 0) results.add("No any results.");
        Collection<String> outLines = new ArrayList<>(results);
        outLines.add("");
        Path path = Paths.get(resultPath);
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {

        } finally {
            path = Paths.get(resultPath + fileName);
        }
        OpenOption[] openOption = (rewriteResultFile) ? new OpenOption[]{StandardOpenOption.CREATE} : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND};
        Files.write(path, outLines, openOption);
    }

}
