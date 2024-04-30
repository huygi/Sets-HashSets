import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Contact> emails = ContactData.getData("email");
        List<Contact> phones = ContactData.getData("phone");
        printData("Phones List",phones);
        printData("Emails List",emails);

        /* I have a list of phones and emails contact, I want to combine these contacts, merging any duplicated into a single contact.
        - With multiple emails and phone numbers, on a single record.
        - To do this, I'll create two HashSets, one for phone, and one for emails.
        - I start with the interface type as the variable type. With a type argument of <Contact>
        - Most constructors of classes implementing the Collection interface, support a constructor that accepts a Collection
         */
        Set<Contact> emailContacts = new HashSet<>(emails);
        Set<Contact> phoneContacts = new HashSet<>(phones);
        printData("Phones Contact",phoneContacts);
        printData("Emails Contact",emailContacts);

        int index = emails.indexOf(new Contact("Robin Hood"));
        Contact robinHood = emails.get(index);
        robinHood.addEmail("Sherwood Forest");
        robinHood.addEmail("Sherwood Forest");
        robinHood.replaceEmailIfExists("RHood@sherwoodforest.com","RHood@sherwoodforest.org");
        System.out.println(robinHood);

    }
    public static void printData(String header, Collection<Contact> contacts) {
        System.out.println("--------------------------------------------");
        System.out.println(header);
        System.out.println(" ");
        contacts.forEach(System.out::println);
    }
}
