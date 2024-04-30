# **Introduction to Sets & HashSet**
> The Set interface, and the classes that implement it
## The Set
- A Set is not implicitly ordered, although some implementations of Set are. 
- A Set contains no duplicates. 
- A Set may contain a single null element. 
- Sets can be useful because operations on them are very fast.
  - In actual fact, the lack of duplicates is the most important differentiator, as there are ordered sets, such as the LinkedHashSet, TreeSet. 

## Set Methods 
- The set interface defines the basic methods add, remove and clear, to maintain the items in the set. 
- We can also check if a specific item is in the set using the contains() method. 
- Interestingly enough, there's no way to retrieve an item from a set. 
- You can check if something exists, using contain() method, and you can iterate over all the elements in the set, but attempting to get the 10th element, for example, from a set isn't possible, with a single method. 

## The HashSet class
- The best-performing implementation of the Set interface is the HashSet class. 
- This class uses hashing mechanisms to store the items. 
  - This means the hash code method is used to support even distributions of objects in the set. 
- Oracle describes this class as offering constant time performance for the basic operations (add, remove, contains and size).
- Contain time has the Big O Notation O(1).
- The HashSet actually uses a HashMap in its own implementation, as of JDK 8. 

## Code Example
- Using List interface
 ```
 Input: 
 
 List<Contact> emails = ContactData.getData("email");
 List<Contact> phones = ContactData.getData("phone");
 printData("Phones List",phones);
 printData("Emails List",emails);
 
 Output:
 
 Phones List
 
 Charlie Brown: [] [(333) 444-5555]
 Maid Marion: [] [(123) 456-7890]
 Mickey Mouse: [] [(999) 888-7777]
 Mickey Mouse: [] [(124) 748-9758]
 Minnie Mouse: [] [(456) 780-5666]
 Robin Hood: [] [(564) 789-3000]
 Robin Hood: [] [(789) 902-8222]
 Lucy Van Pelt: [] [(564) 208-6852]
 Mickey Mouse: [] [(999) 888-7777]
 --------------------------------------------
 Emails List
  
 Mickey Mouse: [mckmouse@gmail.com] []
 Mickey Mouse: [micky1@aws.com] []
 Minnie Mouse: [minnie@verizon.net] []
 Robin Hood: [rhood@gmail.com] []
 Linus Van Pelt: [lvpelt2015@gmail.com] []
 Daffy Duck: [daffy@google.com] []
 ```

- Using Set interface:
 ```
 Input: 
 
 Set<Contact> emailContacts = new HashSet<>(emails);
 Set<Contact> phoneContacts = new HashSet<>(phones);
 printData("Phones Contact",phoneContacts);
 printData("Emails Contact",emailContacts);
 
 Output: 
 
 Phones Contact
 
 Robin Hood: [] [(789) 902-8222]
 Charlie Brown: [] [(333) 444-5555]
 Lucy Van Pelt: [] [(564) 208-6852]
 Robin Hood: [] [(564) 789-3000]
 Maid Marion: [] [(123) 456-7890]
 Minnie Mouse: [] [(456) 780-5666]
 Mickey Mouse: [] [(999) 888-7777]
 Mickey Mouse: [] [(124) 748-9758]
 Mickey Mouse: [] [(999) 888-7777]
 --------------------------------------------
 Emails Contact
  
 Robin Hood: [rhood@gmail.com] []
 Mickey Mouse: [mckmouse@gmail.com] []
 Mickey Mouse: [micky1@aws.com] []
 Daffy Duck: [daffy@google.com] []
 Linus Van Pelt: [lvpelt2015@gmail.com] []
 Minnie Mouse: [minnie@verizon.net] []
 ```
- Note:
  - There are still duplicates in both of these sets, but the order not the same compare in the List. 
  - Why there's duplicates in these Set even though we know that HashSet won't have duplicates ?  
  - Well, duplicates are determined, for hashed collections, first by the hash code, and then the equals' method. 
  - In this instance, both the hash code method and the equal method are using Object's implementation.
  - This means each of these instances of contacts is considered unique. 
  - For contacts, to get rid of duplicates, I'm going to implement a rule with equal method and hash code method. 

- Apply Equal method and hasCode method: 
 ```
 Input: 
 
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
 }
  
 Output: 
 
 Phones Contact
 
 Lucy Van Pelt: [] [(564) 208-6852]
 Charlie Brown: [] [(333) 444-5555]
 Maid Marion: [] [(123) 456-7890]
 Robin Hood: [] [(564) 789-3000]
 Mickey Mouse: [] [(999) 888-7777]
 Minnie Mouse: [] [(456) 780-5666]
 --------------------------------------------
 Emails Contact
 
 Linus Van Pelt: [lvpelt2015@gmail.com] []
 Robin Hood: [rhood@gmail.com] []
 Mickey Mouse: [mckmouse@gmail.com] []
 Daffy Duck: [daffy@google.com] []
 Minnie Mouse: [minnie@verizon.net] []
 ```
  + Using this methods means I've lost a couple of phone numbers and emails in the process.
  + To resolve that, I'm going to add two methods to the Contact class. 

 ```
 Input: 
 
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
 
 Output: 
 
 Emails Contact
 
 Linus Van Pelt: [lvpelt2015@gmail.com] []
 Robin Hood: [rhood@gmail.com] []
 Mickey Mouse: [mckmouse@gmail.com] []
 Daffy Duck: [daffy@google.com] []
 Minnie Mouse: [minnie@verizon.net] []
 Robin Hood now has email RHood@sherwoodforest.com
 Robin Hood already has email RHood@sherwoodforest.com
 Robin Hood: [RHood@sherwoodforest.org, rhood@gmail.com] []
 ```
> ***Recap***:
  - You can see that I successfully replaced the company email, with the .org version, for Robin Hood.
  - Unlike lists, the hash set implementation doesn't include a replace(), or replaceAll method. 
  - Those are the basic functions on set, add, remove, and contains. 
  - There's no get method on a set. 
  - If you want to get an element from the set, you'll have to iterate through every element, and look for a match manually
  - Remember your HashSet is not going to be ordered or sorted.
  - Sets are valuable for groups of elements, when you'll be adding elements, removing duplicates, checking if an element is in the list. 
  - This IS NOT the collection you would use if you mostly want to get elements from your collection, and manipulating values. 