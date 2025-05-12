package interview.queue;

import java.util.*;

/**
 * 题目 6：任务调度器（带优先级）
 * 场景：给定一组任务 tasks 和优先级列表，高优先级的任务必须优先执行。若优先级相同，则按到达时间执行。
 * 示例：
 * 输入：tasks = [(1, "A", 3), (2, "B", 1), (3, "C", 2)]（格式：(id, name, priority)）
 * 输出：["B", "C", "A"]（按优先级从高到低执行）
 * 要求：
 *
 * 使用优先级队列实现任务调度。
 */
public class TaskScheduler {
    record Task(int id, String name, int priority) {}

    public List<String> scheduleTasks(List<Task> tasks) {
        PriorityQueue<Task> queue = new PriorityQueue<>(
            (a, b) -> b.priority() - a.priority() // 优先级高的先出队
        );
        queue.addAll(tasks);
        
        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll().name());
        }
        return result;
    }
}