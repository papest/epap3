package Test;

public enum SortPrice { Дешевле("Дешевле"),
    Дороже("Дороже"),
    ПоДате("По дате"),
    ПоУмолчанию("По умолчанию");
    public String sortPrice;
    public String getValue() {return sortPrice;}

    SortPrice(String sortPrice) {

            this.sortPrice=sortPrice;

        }
}
