import interview.queue.MedianFinder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedianFinderTest {

    @Test
    public void testSingleElement() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        assertEquals(1.0, mf.findMedian(), 0.001);
    }

    @Test
    public void testTwoElementsEven() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        assertEquals(1.5, mf.findMedian(), 0.001);
    }

    @Test
    public void testThreeElementsOdd() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(6);
        mf.addNum(10);
        mf.addNum(2);
        assertEquals(6.0, mf.findMedian(), 0.001);
    }

    @Test
    public void testDuplicateNumbers() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(6);
        mf.addNum(10);
        mf.addNum(2);
        mf.addNum(6);
        assertEquals(6.0, mf.findMedian(), 0.001);
    }

    @Test
    public void testNegativeNumbers() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(-1);
        mf.addNum(-2);
        mf.addNum(-3);
        assertEquals(-2.0, mf.findMedian(), 0.001);
    }

    @Test
    public void testMultipleAdditionsOdd() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        mf.addNum(3);
        mf.addNum(4);
        mf.addNum(5);
        assertEquals(3.0, mf.findMedian(), 0.001);
    }

    @Test
    public void testUnorderedInsertions() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(5);
        mf.addNum(2);
        mf.addNum(10);
        mf.addNum(1);
        assertEquals(3.5, mf.findMedian(), 0.001);
    }

    @Test
    public void testEvenLargeSet() {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        mf.addNum(3);
        mf.addNum(4);
        mf.addNum(5);
        mf.addNum(6);
        assertEquals(3.5, mf.findMedian(), 0.001);
    }
}
