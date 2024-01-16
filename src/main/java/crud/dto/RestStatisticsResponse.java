package crud.dto;

import lombok.Getter;

@Getter
public class RestStatisticsResponse {

    private Double averageAge;

    private Integer countOfRegisteredUsers;

    private Integer countOfFemales;

    private Integer countOfMales;
}
