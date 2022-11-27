package main.project.server.daycal.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DayCal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long dayId;

    String dayDate;

}
