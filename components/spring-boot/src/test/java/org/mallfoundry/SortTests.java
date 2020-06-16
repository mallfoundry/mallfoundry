package org.mallfoundry;

import org.mallfoundry.data.DefaultSort;

public class SortTests {

    public static void main(String[] args) {
        var sort = new DefaultSort();
        sort.from("sdaf:asc,sadjfkla:desc,dasjfk,dsakfjk:asc");
        System.out.println(sort);
    }
}
