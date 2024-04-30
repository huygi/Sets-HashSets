import java.util.HashSet;
import java.util.Set;

public class Contact {
    private String name;
    private Set<String> emails = new HashSet<>();   //HashSet because I don't want duplicates email in contact data.
    private Set<String> phones = new HashSet<>();
    public Contact (String name) {
        this (name, null);
    }
    public Contact (String name, String email) {
        this (name, email, 0);
    }
    public Contact (String name, long phone) {
        this (name, null, phone);
    }
    public Contact (String name, String email, long phone) {
        this.name = name;
        if (email != null) {
            emails.add(email);
        }
        // Phone is long, but it needs to be a String, formatted in a specific way.
        if (phone > 0) {
            String p = String.valueOf(phone);
            p = "(%s) %s-%s".formatted(p.substring(0,3), p.substring(3,6), p.substring(6));
            phones.add(p);
        }
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "%s: %s %s".formatted(name, emails, phones); // Like lists, I can pass Sets right to System.out.println, or to the formatted() method.
    }

    public Contact mergeContactData (Contact contact) {
        // Create local variable
        Contact newContact = new Contact (this.name);

        // This populates the new contact, first with the data on the current instance

        newContact.emails = new HashSet<>(this.emails); // This code is really just cloning the data, by using all the data from the current instance, to create a new contact
        newContact.phones = new HashSet<>(this.phones);

        // Then merges emails and phone numbers, from the contact that's passed to this method.
        // To make it a merge, I add the emails and phones from the contact passed to this method
        // I can just call addAll() method
        newContact.emails.addAll (contact.emails);
        newContact.phones.addAll (contact.phones);

        return newContact;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true; // if they're the same instance -> return true
//        if (o == null || getClass() != o.getClass()) return false; // If types of object is different -> return false.
//        Contact contact = (Contact) o;
//        return getName().equals(contact.getName()) &&
//                Objects.equals(emails, contact.emails) &&   // The code is using another utility class, called Objects.
//                Objects.equals(phones, contact.phones);
//        /*
//        - This Object class provides static utility methods to handle nulls, to generate equals results, and hash codes.
//        - Based on the codes, you could guess that a contact is equal, based on the name, and if the other attributes aren't null, by matching on emails on phones.
//         */
//
//
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getName(), emails, phones);
//     /*
//    - It's using the Object class, calling a hash method on that.
//    - This method takes a variable argument parameter, and this code simple passes those off to the hash Code method.
//     */
//


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return getName().equals(contact.getName());
    }

    @Override
    public int hashCode() {
        return 33 * getName().hashCode();

        /*
        - The hashCode doesn't use a multiplier, but simply returns the name's hash code.
        - This means a Contact instance, and a String, which have the value Mickey Mouse, will result in the same hash code.
        - Need to have objects which aren't the same class type, return different hash codes.
        - I will add my own multiplier here, x 33 (a composite number)
        - Using this methods means I've lost a couple of phone numbers and emails in the process.
         */
    }

    public void addEmail(String companyName) {
        String[] names = name.split(" ");
        String email = "%c%s@%s.com".formatted(name.charAt(0), names[names.length-1], companyName.replaceAll(" ", "").toLowerCase());
        if (!emails.add(email)) {
            System.out.println(name + " already has email " + email);
        } else {
            System.out.println(name + " now has email " + email );
        }
    }
    public void replaceEmailIfExists(String oldEmail, String newEmail) {
        if (emails.contains(oldEmail)) {
            emails.remove(oldEmail);
            emails.add(newEmail);
        }
    }
}