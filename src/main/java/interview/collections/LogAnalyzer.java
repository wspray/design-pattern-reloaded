package interview.collections;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    public Map<String, Integer> countLogTypes(List<String> logs) {
        Map<String, Integer> logTypeCount = new HashMap<>();
        for (String log : logs) {
            String logType = log.split(" ")[0];
            logTypeCount.put(logType, logTypeCount.getOrDefault(logType, 0) + 1);
        }
        return logTypeCount;
    }

    public Map<String, Integer> countLogTypes2(List<String> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(
                        log -> log.split(" ")[0], // 提取日志类型
                        Collectors.summingInt(e -> 1) // 计数
                ));
    }

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(11);
        list.add(21);
        System.out.println(list.get(1));
    }
}