import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Use List interface
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

        //To perform a union, I can use the bulk operation, addAll() method
        // I've lost some information where I had multiples, the other phones numbers and emails
        Set<Contact> unionAB = new HashSet<>();
        unionAB.addAll(emailContacts);
        unionAB.addAll(phoneContacts);
        printData("(A \u222A B) Union of emails (A) with phones (B)", unionAB);

        // Perform intersect A & B
        // I'm passing my first set to the constructor of my new set.
        /*
        - Notice that the intersection all have emails, because multiple records weren't added.
        - In other words, a duplicate element won't replace the current element.
        - When I added the phone contacts for Mickey, Minnie and Robin, those records were ignored, because the set already had records for them in it.
         */
        Set<Contact> intersectAB = new HashSet<>(emailContacts);
        intersectAB.retainAll(phoneContacts);
        printData("(A \u2229 B) Intersect emails (A) and phones (B)", intersectAB);

        // Intersect between B & A
        // A first contact records added for these three, had the phone data on them.
        Set<Contact> intersectBA = new HashSet<>(phoneContacts);
        intersectBA.retainAll(emailContacts);
        printData("(B \u2229 A) Intersect phones (B) and emails (A)",intersectBA);

        // Difference between A & B
        Set<Contact> AMinusB = new HashSet<>(emailContacts);
        AMinusB.removeAll(phoneContacts);
        printData("(A - B) emails (A) - phones (B)",AMinusB);

        //Difference between B & A
        Set<Contact> BMinusA = new HashSet<>(phoneContacts);
        BMinusA.removeAll(emailContacts);
        printData("(B - A) phones (B) - emails (A)",BMinusA);

        // Symmetric Difference between A & B
        Set<Contact> symmetricDifferent = new HashSet<>(AMinusB);
        symmetricDifferent.addAll(BMinusA);
        printData("Symmetric Difference: phones and emails", symmetricDifferent );

        Set<Contact> symmetricDiff2 = new HashSet<>(unionAB);
        symmetricDiff2.removeAll(intersectAB);
        printData( "Symmetric Difference: phones and emails", symmetricDiff2);

    }

    public static void printData(String header, Collection<Contact> contacts) {
        System.out.println("--------------------------------------------");
        System.out.println(header);
        System.out.println(" ");
        contacts.forEach(System.out::println);
    }
}
