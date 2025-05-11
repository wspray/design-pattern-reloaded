package interview;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class JDK21 {
    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.runWhere(USER_ID, "User123", () -> {

        });
    }

    public static void structuredTaskScope() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // 分叉两个子任务
            var userFuture = scope.fork(() -> getUserData());
            var orderFuture = scope.fork(() -> getOrderData());

            // 等待所有子任务完成或被取消
            scope.join();

            // 检查是否有子任务失败，并抛出异常
            scope.throwIfFailed();

            // 处理子任务的结果
            String user = userFuture.get();
            String order = orderFuture.get();

            System.out.println("User: " + user + ", Order: " + order);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getUserData() throws InterruptedException {
        Thread.sleep(200); // 模拟耗时操作
        return "User123";
    }

    public static String getOrderData() throws InterruptedException {
        Thread.sleep(200); // 模拟耗时操作
        return "Order456";
    }

    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> obj.toString();
        };
    }
}