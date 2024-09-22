package io.ukhin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "holidays")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(Holiday.class)
public class Holiday implements Serializable {
    @Id
    private int month;

    @Id
    private int day;
}
