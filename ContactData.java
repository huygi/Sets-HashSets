import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ContactData {
    private static final String phoneData = """
            Charlie Brown, 3334445555
            Maid Marion, 1234567890
            Mickey Mouse, 9998887777
            Mickey Mouse, 1247489758
            Minnie Mouse, 4567805666
            Robin Hood, 5647893000
            Robin Hood, 7899028222
            Lucy Van Pelt, 5642086852
            Mickey Mouse, 9998887777
            """;

    private static final String emailData = """
            Mickey Mouse, mckmouse@gmail.com
            Mickey Mouse, micky1@aws.com
            Minnie Mouse, minnie@verizon.net
            Robin Hood, rhood@gmail.com
            Linus Van Pelt, lvpelt2015@gmail.com
            Daffy Duck, daffy@google.com
            """;

    /*I want a method to read this data, emulating reading it from a file, or retrieving it from an external source.
    - Everytime I call getData on this class. I'll get new list with new instances of contacts.
    */
    public static List<Contact> getData (String type) {
        List<Contact> dataList = new ArrayList<>();
        /* If the type is phone, I'll pass in the phoneData text block to the constructor, vice versa.
        - Scanner can also work with multi-String text block
        - Data is read by a Scanner instance, constructed with one of these text blocks.
        */
        Scanner scanner = new Scanner (type.equals("phone") ? phoneData : emailData);

        /* Use the Scanner functionality to treat the string like any input stream
        - This is going to trim leading and trailing whitespace, from each of the strings in the text block
        */
        while (scanner.hasNext()) {
            String[] data = scanner.nextLine().split(",");
            Arrays.asList(data).replaceAll(String::trim);
            if (type.equals("phone")) {
                dataList.add(new Contact(data[0], Long.parseLong(data[1])));
            } else if (type.equals("email")) {
                dataList.add(new Contact(data[0], data[1]));
            }
        }
        return dataList;
    }
}
