/**
 * Object of package for the mail room simulator.
 */
public class Package {
    //Data fields
    private String recipient = "";
    private int arrivalDate = 0;
    private double weight=0.0;

    //Constructor
    /**
     * Default constructor of the Package class.
     * @param recipient
     *  String representing name of the recipient of package.
     * @param arrivalDate
     *  Int representing the date of arrival of the package.
     * @param weight
     *  double representing the weight of the package.
     * </dt> Post conditions:
     * This object has been initialized to a package object with
     * specified recipient, arrival date and weight.</>
     */
    public Package(String recipient, int arrivalDate, double weight){
        this.recipient = recipient;
        this.arrivalDate = arrivalDate;
        this.weight = weight;
    }

    /**
     * constructor for a package with just a recipient name.
     * @param recipient
     *  String representing name of the recipient of package.
     */
    public Package(String recipient){
        this.recipient =  recipient;
    }

    /**
     * getter for Recipient of the package.
     * @return
     *  String representing the name of the recipient of package.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * setter for Recipient of the package.
     * @param recipient
     *  String representing the new recipient of the package.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * getter for date of arrival of the package.
     * @return
     *  int representing the date of arrival of package.
     */
    public int getArrivalDate() {
        return arrivalDate;
    }

    /**
     * setter for date of arrival of the package.
     * @param arrivalDate
     *  int to represent new dat of arrival of the package.
     */
    public void setArrivalDate(int arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * getter for weight of the package.
     * @return
     *  double representing the weight of the package.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * setter for weight of the package.
     * @param weight
     *  double to represent the new weight of package.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    //Methods

    /**
     * provides all factors of an object of package.
     * @return
     *      a string representing package's recipient, date of arrival and weight.
     */
    @Override
    public String toString() {
        return  "[" + recipient +
                " " + arrivalDate + "]" ;
    }

    /**
     * equals method of package.
     * @param o
     *  an object to be compared.
     * @return
     *  true if object equals package and false if it doesn't.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Package)) return false;

        Package aPackage = (Package) o;

        if (arrivalDate != aPackage.arrivalDate) return false;
        if (Double.compare(aPackage.weight, weight) != 0) return false;
        return recipient.equals(aPackage.recipient);
    }

    /**
     * equals method for recipient name of package
     * @param o
     *  an object to be compared.
     * @return
     *  true if object's names are equal and false if they aren't.
     */
    public boolean equalNames(Object o){
        if (this == o) return true;
        if (!(o instanceof Package)) return false;

        Package aPackage = (Package) o;

        return recipient.equals(aPackage.recipient);
    }

}
