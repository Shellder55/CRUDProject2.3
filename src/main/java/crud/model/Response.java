package crud.model;

public class Response {

    private double averageAge;

    private int countOfRegisteredUsers;

    private int countOfFemales;

    private int countOfMales;

    public double getAverageAge() {
        return averageAge;
    }

    public int getCountOfRegisteredUsers() {
        return countOfRegisteredUsers;
    }

    public int getCountOfFemales() {
        return countOfFemales;
    }

    public int getCountOfMales() {
        return countOfMales;
    }

    @Override
    public String toString() {
        return "Response{" +
                "averageAge=" + averageAge +
                ", countOfRegisteredUsers=" + countOfRegisteredUsers +
                ", countOfFemales=" + countOfFemales +
                ", countOfMales=" + countOfMales +
                '}';
    }
}
