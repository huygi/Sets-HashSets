# **Introduction to Sets & HashSet**
> The Set interface, and the classes that implement it.
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

## Code Example (Manipulating the equals & hashcode methods)
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
 - Using this methods means I've lost a couple of phone numbers and emails in the process.
 - To resolve that, I'm going to add two methods to the Contact class. 

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

## Set Operations
- When you're trying to understand data in multiple sets, you might want to get the data that's in all the sets, that's in every set, or the data where there's no overlap.
- The collection interface's bulk operations (addAll, retainAll, removeAll, and containAll) can be used to perform these set operations.

## Representing Sets in a Venn Diagram
![separte](https://github.com/huygi/Sets-HashSets/assets/105019803/b15dd646-132b-4709-afc2-ab9a964a2c7b)

- Sets are often represented as circles or ovals, with elements inside, on what is called a Venn Diagram. 
- Here, I'm showing two sets that have no elements in common. 
- This venn diagram shows some cartoon characters of the Peanuts and Mickey Mouse cartoons. 
- Because the characters are distinct for each set, the circles representing the sets don't overlap, or intersect. 

![set a u b](https://github.com/huygi/Sets-HashSets/assets/105019803/e2420802-6e80-432e-94a9-1334335647d9)

- This diagram shows two sets of characters that do overlap. 
- Let's say that Goofy and Snoopy, have guest appearances in the other's holiday special show. 
- The intersection of these sets is represented by the area where the two circles (sets) overlap, and contains the elements that are shared by both sets.
- Goofy and Snoopy are both in Set A and Set B, in other words. 
- Venn Diagrams are an easy way to quickly see how elements in multiple sets relate to each other. 

## Set Operations - Union A u B
![pic1](https://github.com/huygi/Sets-HashSets/assets/105019803/7deb9e1f-f396-44f6-97ab-9ff0f66739ae)

- The union of two or more sets will return elements that are in any or all of the sets, removing any duplicates. 
- The slide shown here is showing my two sets, names on an email list, and names on a phone numbers list. 
- The overlap are names that are on both lists. 
- In the example shown above, all names on the email list and phone list will be included in a union of the two sets, but Minnie, Mickie, and Robin Hood, which are the only elements included in both sets, are included in the resulting set only once. 
- Java doesn't have a union method on Collections, but the addAll() method bulk function, when used on a Set, can be used to create a union of multiple sets. 

## Set Operations - Intersect - A ∩ B
![intersect](https://github.com/huygi/Sets-HashSets/assets/105019803/411632b5-1f76-4cb5-b8c7-59d3efa136ef)

- The intersection of two or more sets, will return only the elements that the sets have in common. 
- These are shown in the overlapping area of the sets on this slide, the intersection, shown in green, and includes Mickey and Minnie Mouse, and Robin Hood. 

## Coding Example 

- HashSet implementation: 
  ```
  public HashSet(Collection<? extends E> c) {
    map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
    addAll(c);
  }
  ```
  - This constructor simply calls addAdd() there. 
  - Also noticed it's creating a new HashMap, assigning it to a map field on this HashSet class, in the first statement. 
  - The HashSet uses a HashMap in its implementation. 
  - These two classes are tightly interwoven in current versions of Java. 
  - Regardless of the underlying way the HashSet implements its Collection, and there's never any guarantee in Java that the implementation won't change, the behavior of the HashSet will be consistent. 
  - This means duplicates aren't supported, the collection won't be ordered, and hashing will be used to provide close to constant time access. 



- Intersect between A and B:
  ```
  Input:
  
  Set<Contact> intersectAB = new HashSet<>(emailContacts);
  intersectAB.retainAll(phoneContacts);
  printData("(A \u2229 B) Intersect emails (A) and phones (B)", intersectAB);
  
  Output:
  
  (A ∩ B) Intersect emails (A) and phones (B)
  Robin Hood: [RHood@sherwoodforest.org, rhood@gmail.com] []
  Mickey Mouse: [mckmouse@gmail.com] []
  Minnie Mouse: [minnie@verizon.net] []
  
  ```
  - Notice that the intersection all have emails, because multiple records weren't added.
  - In other words, a duplicate element won't replace the current element.
  - When I added the phone contacts for Mickey, Minnie and Robin, those records were ignored, because the set already had records for them in it.


- Intersect between B and A:
  ```
  Input:
  
  Set<Contact> intersectBA = new HashSet<>(phoneContacts);
  intersectBA.retainAll(emailContacts);
  printData("(B \u2229 A) Intersect phones (B) and emails (A)",intersectBA);
  
  Output:

  (B ∩ A) Intersect phones (B) and emails (A)
  Robin Hood: [] [(564) 789-3000]
  Mickey Mouse: [] [(999) 888-7777]
  Minnie Mouse: [] [(456) 780-5666]
  ```
  - The extra data, the data that wasn't included in the equality test, is different here tho, so now I get phone numbers. 
  - Because the first contact records added for these three, had the phone number on them. 
  - Even tho I flipped the sets in my intersection set, the result was the same, returning Mickey, Minnie and Robin. 



- Difference between Sets A and B:
  ```
  Input:
  
  Set<Contact> AMinusB = new HashSet<>(emailContacts);
  MinusB.removeAll(phoneContacts);
  printData("(A - B) emails (A) - phones (B)",AMinusB);
  
  Output:

  (A - B) emails (A) - phones (B)

  Linus Van Pelt: [lvpelt2015@gmail.com] []
  Daffy Duck: [daffy@google.com] []
  ```
  - This tells us the Linus and Daffy are the only two records in the email contact list, that aren't in the phone list. 



- Symmetric Different of Sets A & B:
  ```
  Input:
  
  Set<Contact> symmetricDifferent = new HashSet<>(AMinusB);
  symmetricDifferent.addAll(BMinusA);
  printData("Symmetric Difference: phones and emails", symmetricDifferent );

  Set<Contact> symmetricDiff2 = new HashSet<>(unionAB);
  symmetricDiff2.removeAll(intersectAB);
  printData( "Symmetric Difference: phones and emails", symmetricDiff2);
  ```


## Set Operations - Symmetric Operations 
- Symmetrical means it is equal on both sides. 
- The ability to evaluate sets, A intersect B and get the same result as B intersect A, means that the intersect operation is a symmetric set operation. 
- Union is also a symmetric operation.
- It doesn't matter if you do A Union B, or B Union A. The final set of elements will all be the same set of names. 
- Another useful evaluation might be to identify which elements are in one set, but not on the other. This is called a set difference. 

## Set Operations - Asymmetric Differences 
![unnamed (1)](https://github.com/huygi/Sets-HashSets/assets/105019803/c0cba89a-ad57-4a4e-a459-a47597bfbd5b)

- A difference subtracts elements in common from one set and another, leaving only the distinct elements from the first set as the result. 
- This is an asymmetric operation because if we take Set A and subtract Set B from it, we'll end up with a different set of elements than if we take Set B and subtract Set A. 
- The sets from these two operations won't result in the same elements. 

## Set Operations - Symmetric Differences 
![differ](https://github.com/huygi/Sets-HashSets/assets/105019803/6428dc0a-d90c-41cc-a23d-ee7f8f8da202)

- You can think of the set symmetric difference, as the elements from all sets that don't intersect. 
- On the example above, these are the elements that are represented in the paler yellow areas. 