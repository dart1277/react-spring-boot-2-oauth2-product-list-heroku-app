package com.cx.prod.list.model.users;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "phoneNumber")})
public class User {
    public static final int MAX_STR_FIELD_LENGTH = 80;
    public static final int PHONE_NO_FIELD_LENGTH = 9;
    public static final String BIRTHDAY_COLUMN_NAME = "birthDate";
    public static final String ID_COLUMN_NAME = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN_NAME)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = MAX_STR_FIELD_LENGTH)
    @Size(max = MAX_STR_FIELD_LENGTH)
    private String lastName;

    @NotEmpty
    @Column(nullable = false, length = MAX_STR_FIELD_LENGTH)
    @Size(max = MAX_STR_FIELD_LENGTH)
    private String firstName;

    @Column(nullable = false, name = BIRTHDAY_COLUMN_NAME)
    @PastOrPresent
    @NotNull
    private LocalDate birthDate;

    @Column(nullable = true, length = PHONE_NO_FIELD_LENGTH)
    @Pattern(regexp = "[0-9]{" + PHONE_NO_FIELD_LENGTH + "}")
    private String phoneNumber;

    public User(@NotEmpty @Size(max = MAX_STR_FIELD_LENGTH) String lastName, @NotEmpty @Size(max = MAX_STR_FIELD_LENGTH) String firstName, @PastOrPresent @NotNull LocalDate birthDate, @Pattern(regexp = "[0-9]{" + PHONE_NO_FIELD_LENGTH + "}") String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }
}
