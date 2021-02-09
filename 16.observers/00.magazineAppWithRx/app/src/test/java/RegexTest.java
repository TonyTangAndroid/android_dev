import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/** Created by Juan on 7/24/2015. */
@RunWith(RobolectricTestRunner.class)
public class RegexTest {

  @Test
  public void testRegex() {
    String fileName = "zippy/hello.html";
    String pattern = "zippy\\/(.*)";

    Log.print(fileName.replaceAll(pattern, "$1"));
  }
}
