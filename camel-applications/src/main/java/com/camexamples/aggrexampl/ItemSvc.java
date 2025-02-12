/*while routing, these two methods are used to set the pricing for the different items like book and phone.*/
package com.camexamples.aggrexampl;

public class ItemSvc {

    public Item processBook(Item item) throws InterruptedException {

        System.out.println("handle book Item:" +item);
        item.setPrice(30);

        System.out.println("book Item processed");

        return item;
    }

    public Item processPhone(Item item) throws InterruptedException {

        System.out.println("handle phone Item:" +item);
        item.setPrice(500);

        System.out.println("phone Item processed");

        return item;
    }
}