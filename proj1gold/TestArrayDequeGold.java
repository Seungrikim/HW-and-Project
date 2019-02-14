import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test
    public void testArray() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        int testRunTime = 0;
        String testResult = "\n";

        while (testRunTime != 1000) {
            int functionChoice = StdRandom.uniform(4); //generate random number for choose method
            Integer item = StdRandom.uniform(1000); //generate random number for item
            switch (functionChoice) {
                case 0:
                    sad.addFirst(item);
                    ads.addFirst(item);
                    testResult = testResult + "addFirst(" + item + ")\n";
                    assertEquals(testResult, ads.get(0), sad.get(0));
                    break;
                case 1:
                    sad.addLast(item);
                    ads.addLast(item);
                    testResult = testResult + "addLast(" + item + ")\n";
                    assertEquals(testResult,
                            ads.get(ads.size() - 1), sad.get(sad.size() - 1));
                    break;
                case 2:
                    if (sad.isEmpty() || ads.isEmpty()) {
                        break;
                    } else {
                        Integer x = sad.removeFirst();
                        Integer y = ads.removeFirst();
                        testResult = testResult + "removeFirst()\n";
                        assertEquals(testResult, y, x);
                        break;
                    }
                case 3:
                    if (sad.isEmpty() || ads.isEmpty()) {
                        break;
                    } else {
                        Integer x = sad.removeLast();
                        Integer y = ads.removeLast();
                        testResult = testResult + "removeLast()\n";
                        assertEquals(testResult, y, x);
                        break;
                    }
                default:
                    break;
            }
            assertEquals(testResult + "size()\n",ads.size(),sad.size());
            testRunTime += 1;
        }
    }
}

