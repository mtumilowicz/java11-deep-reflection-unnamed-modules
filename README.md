# java11-deep-reflection-unnamed-modules

# preview
Referring my other github projects concerning reflection could be
useful:
* https://github.com/mtumilowicz/java-reflection
* https://github.com/mtumilowicz/java11-deep-reflection

# project description
We will show how to get / set private field cross unnamed modules
using deep reflection.

**All packages in an unnamed module are open to all other modules.**
1. classes
    * class with private field (domain)
        ```
        public class Company {
            private String privateField = "privateField";
        
            public String getPrivateField() {
                return privateField;
            }
        }
        ```
    * accessor (another module - accessor)
        ```
        class Accessor {
            private static String get() throws Exception {
                var company = new Company();
        
                var privateField = company.getClass().getDeclaredField("privateField");
        
                Preconditions.checkState(!privateField.canAccess(company));
        
                Preconditions.checkState(privateField.trySetAccessible());
        
                return (String) privateField.get(company);
            }
        
            private static void set(String newName, Company company) throws Exception {
                var privateField = company.getClass().getDeclaredField("privateField");
        
                Preconditions.checkState(!privateField.canAccess(company));
        
                Preconditions.checkState(privateField.trySetAccessible());
        
                privateField.set(company, newName);
            }
        
            public static void main(String[] args) throws Exception {
                System.out.println(get()); // privateField
        
                var company = new Company();
                set("new value", company);
                System.out.println(company.getPrivateField()); // new value
            }
        }
        ```
# security manager
* by default the security manager is not installed
* access is handled through the Java security manager
* if a security manager is installed access to inaccessible members
depends on the permission granted