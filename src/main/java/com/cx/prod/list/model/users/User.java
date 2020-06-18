package com.cx.prod.list.model.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "CUSTOMERS", uniqueConstraints = {@UniqueConstraint(columnNames = "PHONE_NO")})
public class User {
    public static final int MAX_STR_FIELD_LENGTH = 80;
    public static final int PHONE_NO_FIELD_LENGTH = 9;
    public static final String ID_COLUMN_NAME = "ID";
    public static final String BIRTHDAY_SORT_KEY = "birthDate";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    private Long id;

    @NotEmpty
    @Column(name = "LAST_NAME", nullable = false, length = MAX_STR_FIELD_LENGTH)
    @Size(max = MAX_STR_FIELD_LENGTH)
    private String lastName;

    @NotEmpty
    @Column(name = "FIRST_NAME", nullable = false, length = MAX_STR_FIELD_LENGTH)
    @Size(max = MAX_STR_FIELD_LENGTH)
    private String firstName;

    @Column(nullable = false, name = "BIRTH_DATE")
    @PastOrPresent
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "PHONE_NO", nullable = true, length = PHONE_NO_FIELD_LENGTH)
    @Pattern(regexp = "[0-9]{" + PHONE_NO_FIELD_LENGTH + "}")
    private String phoneNumber;

    public User(@NotEmpty @Size(max = MAX_STR_FIELD_LENGTH) String lastName, @NotEmpty @Size(max = MAX_STR_FIELD_LENGTH) String firstName, @PastOrPresent @NotNull LocalDate birthDate, @Pattern(regexp = "[0-9]{" + PHONE_NO_FIELD_LENGTH + "}") String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
