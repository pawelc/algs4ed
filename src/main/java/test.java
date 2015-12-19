import java.util.HashMap;


public class test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, String> m = new HashMap<String, String>();
        m.put(null, null);
        System.out.println(m.get(null));
    }

   

}
