package accessor;

import com.google.common.base.Preconditions;
import company.Company;

/**
 * Created by mtumilowicz on 2019-02-09.
 */
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
        System.out.println(get());

        var company = new Company();
        set("new value", company);
        System.out.println(company.getPrivateField());
    }
}
